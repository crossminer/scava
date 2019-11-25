"""
Created on 26 Mar 2019

@author: stevet
@author: Jon Co

Implementation notes: Currently a new stomp.py stomp.Connection is created for each Stream. This should be refactored
to use a single connection and the subscribe functionality of stomp.py. This should be equivalent to acquiring the
ActiveMQ session manager """
from __future__ import annotations

from abc import ABC, abstractmethod
import csv
import datetime
from enum import Enum, auto
import hashlib
import logging
import os
from pathlib import Path
import pickle
import sys
import tempfile
import threading
import time
from traceback import TracebackException
from typing import Any
import uuid
import warnings

import stomp

from crossflow import serialization


class LogLevel(Enum):
    """
    LogLevels are used to indicate priority of a log message in the global
    workflow logging topic
    """

    INFO = auto()
    WARNING = auto()
    ERROR = auto()
    DEBUG = auto()

    def __str__(self):
        return self.name

    @staticmethod
    def enum_from_name(name: str) -> "LogLevel":
        """Returns the enum of this constant from it's name

        :param name: the name of the enum to return, case-insensitive
        :type name: str
        :return: Corresponding ControlSignals enum member
        :rtype: LogLevel
        :raises:
            ValueError: if no member of LogLevel can be identified with the given name
        """
        try:
            return LogLevel[name.upper()]
        except KeyError:
            raise ValueError(
                f"No LogLevel exists with name '{name.upper()}'. "
                f"Must be one of  {', '.join([i.name for i in LogLevel])}"
            )


class LogMessage:
    def __init__(
        self,
        level: LogLevel = LogLevel.DEBUG,
        instance_id: str = None,
        workflow: str = None,
        task: str = None,
        message: str = None,
        timestamp: int = None,
    ):
        self.level = level
        self.instanceId = instance_id
        self.workflow = workflow
        self.task = task
        self.message = message
        self.timestamp = timestamp or int(round(time.time() * 1000))

    @classmethod
    def from_workflow(cls, workflow: "Workflow") -> "LogMessage":
        return LogMessage(instance_id=workflow.instance, workflow=workflow.name)

    @classmethod
    def from_task(cls, task: "Task") -> "LogMessage":
        m = LogMessage.from_workflow(workflow=task.workflow)
        m.task = task.task_id
        return m

    @property
    def instance_id(self) -> str:
        return self.instanceId

    @instance_id.setter
    def instance_id(self, value: str):
        self.instanceId = value

    def __str__(self):
        # Timestamp serialization is customised to fit with Java formatting
        as_date = datetime.datetime.utcfromtimestamp(self.timestamp / 1000)
        return "{}{}Z [{}] {}".format(
            as_date.strftime("%Y-%m-%dT%H:%M:%S."),
            str(int(as_date.microsecond / 1000)),
            self.level,
            self.to_external_log_str(),
        )

    def __eq__(self, o):
        if isinstance(o, LogMessage):
            return (
                self.level == o.level
                and self.instance_id == o.instance_id
                and self.workflow == o.workflow
                and self.task == o.task
                and self.message == o.message
                and self.timestamp == o.timestamp
            )
        return False

    def to_external_log_str(self) -> str:
        """Same as calling str() but without the timestamp or level prefixed.

        Useful for external loggers

        :return: log message formatted without timestamp or level prefixed
        :rtype: str
        """
        task_part = ""
        if self.task is not None:
            task_part = f":{self.task}"
        return f"{self.instance_id}:{self.workflow}{task_part} | {self.message}"


class CrossflowLogger:
    def __init__(self, workflow: "Workflow", pre_print: bool = False):
        self.workflow = workflow
        self.pre_print = pre_print

    def log(
        self, level: LogLevel = LogLevel.DEBUG, message: str = "", task: "Task" = None
    ):
        if task:
            m = LogMessage.from_task(task)
        else:
            m = LogMessage.from_workflow(self.workflow)

        m.level = level
        m.message = message

        if self.pre_print:
            if m.level is LogLevel.INFO:
                self.workflow.local_logger.info(m.to_external_log_str())
            if m.level is LogLevel.WARNING:
                self.workflow.local_logger.warning(m.to_external_log_str())
            if m.level is LogLevel.ERROR:
                self.workflow.local_logger.error(m.to_external_log_str())
            if m.level is LogLevel.DEBUG:
                self.workflow.local_logger.debug(m.to_external_log_str())

        self.workflow.log_topic.send(m)


class ControlSignals(Enum):
    """
    Control Signals events

    A control signal is sent between nodes on important workflow events, mainly
    relating to worker registration and overall status
    """

    TERMINATION = auto()
    ACKNOWLEDGEMENT = auto()
    WORKER_ADDED = auto()
    WORKER_REMOVED = auto()

    def __str__(self):
        return self.name

    @staticmethod
    def enum_from_name(name: str) -> "ControlSignals":
        """Returns the enum of this constant from it's name

        :param name: the name of the enum to return, case-insensitive
        :type name: str
        :return: Corresponding ControlSignals enum member
        :rtype: ControlSignals
        :raises:
            ValueError: if no member of ControlSignals can be identified with the given name
        """
        try:
            return ControlSignals[name.upper()]
        except KeyError:
            raise ValueError(
                f"No ControlSignals exists with name '{name.upper()}'. "
                f"Must be one of  {', '.join([i.name for i in ControlSignals])}"
            )


class ControlSignal:
    def __init__(self, signal: ControlSignals = None, sender_id: str = None):
        self.signal = signal
        self.senderId = sender_id

    def __eq__(self, other):
        if isinstance(other, ControlSignal):
            return self.signal == other.signal and self.senderId == other.senderId
        return False


class CSVParser(object):
    def __init__(self, path):
        self.parser = csv.DictReader(path)

    def getRecordsIterable(self):
        return self.parser

    def getRecordsList(self):
        return list(self.parser)


class CSVWriter(object):
    def __init__(self, filePath, headers):
        if not os.path.isfile(filePath):
            parent = filePath[0 : filePath.rfind("/") - 1]
            os.mkdir(parent)
        self.writer = csv.writer(filePath)
        self.writer.writerow(headers)

    def writeRecord(self, record):
        self.writer.writerow(record)

    def flush(self):
        self.writer.flush()

    def close(self):
        self.writer.close()


class QueueInfo:
    """Configuration for message queue/topic connections"""

    def __init__(self, name: str, broadcast: bool, prefetch_size: int = 0):
        self._name = name
        self._broadcast = broadcast
        self._prefetch_size = prefetch_size

    @property
    def name(self) -> str:
        return self._name

    @property
    def broadcast(self) -> bool:
        return self._broadcast

    @property
    def prefetch_size(self) -> int:
        return self._prefetch_size

    @property
    def destination(self) -> str:
        return f"/{'topic' if self._broadcast else 'queue'}/{self._name}"


class TaskStatuses(Enum):
    """
    Flags indicating the status of a Task.

    These flags refer to the status of the Task as a whole, not to the
    individual job that is being executed on that task
    """

    STARTED = auto()
    WAITING = auto()
    INPROGRESS = auto()
    BLOCKED = auto()
    FINISHED = auto()

    def __str__(self):
        return self.name

    @staticmethod
    def enum_from_name(name: str):
        """Returns the enum of this constant from it's name

        :param name: the name of the enum to return, case-insensitive
        :type name: str
        :return: Corresponding TaskStatuses enum member
        :rtype: TaskStatuses
        :raises:
            ValueError: if no member of TaskStatuses can be identified with the given name
        """
        try:
            return TaskStatuses[name.upper()]
        except KeyError:
            raise ValueError(
                f"No TaskStatuses exists with name '{name.upper()}'. "
                f"Must be one of  {', '.join([i.name for i in TaskStatuses])}"
            )


class TaskStatus(object):
    def __init__(self, status=TaskStatuses.STARTED, caller="", reason=""):
        self.status = status
        self.caller = caller
        self.reason = reason

    def getStatus(self):
        return self.status

    def getCaller(self):
        return self.caller

    def getReason(self):
        return self.reason

    def __str__(self, *args, **kwargs):
        return (
            str(self.status)
            + " | caller: "
            + str(self.caller)
            + " reason: "
            + str(self.reason)
        )

    def __eq__(self, o):
        if isinstance(o, TaskStatus):
            return (
                self.status == o.status
                and self.caller == o.caller
                and self.reason == o.reason
            )
        return False


class Mode(Enum):
    """
    Flag for indicating the operation mode of the workflow

    MASTER_BARE workflows will only perform orchestration functions
    MASTER workflows will perform orchestration and task execution
    WORKER workflows will only perform task execution
    API workflows will not perform any functionality other than monitoring streams
    """

    MASTER_BARE = auto()
    MASTER = auto()
    WORKER = auto()
    API = auto()

    def __str__(self):
        return self.name

    @staticmethod
    def enum_from_name(name: str):
        """Returns the enum of this constant from it's name

        :param name: the name of the enum to return, case-insensitive
        :type name: str
        :return: Corresponding Mode enum member
        :rtype: Mode
        :raises:
            ValueError: if no member of Mode can be identified with the given name
        """
        try:
            return Mode[name.upper()]
        except KeyError:
            raise ValueError(
                f"No Mode exists with name '{name.upper()}'. "
                f"Must be one of  {', '.join([i.name for i in Mode])}"
            )


class Task(ABC):
    """Abstract implementation of a Task instance"""

    def __init__(
        self, cacheable: bool = True, timeout: int = 0, workflow: "Workflow" = None
    ):
        self._cacheable = cacheable
        self._timeout = timeout
        self._workflow = workflow
        self._subscription_id = uuid.uuid4().int

    @property
    def task_id(self) -> str:
        return f"{self._workflow.name}:{self.name}"

    @property
    def workflow(self) -> "Workflow":
        return self._workflow

    @workflow.setter
    def workflow(self, value: "Workflow"):
        self._workflow = value

    @property
    def cacheable(self) -> bool:
        return self._cacheable

    @cacheable.setter
    def cacheable(self, value: bool):
        self._cacheable = value

    @property
    def timeout(self) -> int:
        return self._timeout

    @timeout.setter
    def timeout(self, value: int):
        self._timeout = value

    @property
    def subscription_id(self):
        return self._subscription_id

    @property
    @abstractmethod
    def name(self) -> str:
        """The name of this task as defined in the original model
        
        :return: the name of this task
        :rtype: str
        """
        pass

    def task_blocked(self, reason):
        self._workflow.set_task_blocked(reason)

    def task_unblocked(self):
        self._workflow.set_task_unblocked(self)

    def close(self):
        """Optional cleanup method to execute on close"""
        pass


class MessageListener(stomp.ConnectionListener):
    def __init__(
        self,
        connection: stomp.Connection,
        consumer: Any,
        workflow: "Workflow",
        destination: str,
        deserialize: bool = False,
        ack: bool = False,
    ):
        self._connection = connection
        self._consumer = consumer
        self._workflow = workflow
        self._destination = destination
        self._deserialize = deserialize
        self._ack = ack
        self._listener_id = uuid.uuid4()

    def _do_ack(self, headers: dict):
        self._connection.ack(headers["message-id"], headers["subscription"])

    def on_error(self, headers, message):
        if headers["destination"] == self._destination:
            self._workflow.local_logger.debug("Received an error: \n{}".format(message))

    def on_message(self, headers, message):
        if headers["destination"] == self._destination:
            try:
                obj = (
                    self._workflow.serializer.deserialize(message)
                    if self._deserialize
                    else message
                )
                if self._ack:
                    self._consumer.consume(obj, self._do_ack(headers))
                else:
                    self._consumer.consume(obj, None)
            except Exception as ex:
                if self._workflow is not None:
                    self._workflow.report_internal_exception(ex)
        else:
            self._workflow.local_logger.debug(
                f"{self._destination} discarded message from {headers['destination']}: {message}"
            )

    def on_disconnected(self):
        self._workflow.local_logger.debug(f"{self._destination} Disconnected")

    @property
    def listener_id(self) -> str:
        return str(self._listener_id)


class DirectoryCache(object):
    def __init__(self, directory=None):
        self.jobFolderMap = {}
        self.jobMap = {}
        self.workflow = None
        self.pendingTransactions = {}

        if directory == None:
            self.directory = tempfile.NamedTemporaryFile(prefix="crossflow")
        else:
            self.directory = open(directory)

        self.directoryFullpath = os.path.realpath(self.directory.name)

        if not Path(directory).is_dir():
            return

        for streamFolder in os.listdir(self.directoryFullpath):
            if not Path(streamFolder).is_dir():
                continue
            for jobFolder in os.listdir(streamFolder):
                if not Path(jobFolder).is_dir():
                    continue
                jobFolderObject = open(jobFolder)
                self.jobFolderMap[jobFolderObject.name] = jobFolderObject

    def getCachedOutputs(self, input_job: "Job"):
        if self.hasCachedOutputs(input_job):
            outputs = []
            input_folder = self.jobFolderMap.get(input_job.getHash())
            for outputFilePath in os.listdir(os.path.realpath(input_folder.name)):
                output_file = open(outputFilePath)
                output_job: Job = self.workflow.serializer.deserialize(
                    output_file.read()
                )
                output_file.close()
                output_job.jobId = str(uuid.uuid4())
                output_job.correlationId = input_job.jobId
                output_job.cached = True
                outputs.append(output_job)
            return outputs
        else:
            return []

    def hasCachedOutputs(self, input_job: "Job"):
        return input_job.getHash() in self.jobFolderMap.keys()

    def cache(self, output_job):
        if not output_job.cacheable:
            return

        self.jobMap[output_job.jobId] = output_job
        input_job = self.jobMap.get(output_job.correlationId)

        if input_job is not None:
            stream_folder = self.directoryFullpath + "/" + input_job.getDestination()
            try:
                input_folder = stream_folder + "/" + input_job.getHash()
                os.makedirs(input_folder)
                with open(input_folder + "/" + output_job.getHash()) as outputFile:
                    self.jobFolderMap[input_job.name] = input_folder
                    self.save(output_job, outputFile)
            except Exception as ex:
                self.workflow.local_logger.exception("Error caching Job")
                self.workflow.report_internal_exception(ex)

    def getDirectory(self):
        return self.directory

    def save(self, job, file):
        file.write(job.getXml())
        file.flush()

    def setWorkflow(self, workflow):
        self.workflow = workflow

    def cacheTransactionally(self, outputJob):

        if outputJob.isTransactionSuccessMessage():
            self.cachePendingTransactions(outputJob.getCorrelationId())
            return

        if not outputJob.cacheable:
            return

        # even though the task producing this job may have failed, this job itself is
        # complete so should be indexed in the job map regardless
        self.jobMap[outputJob.getId()] = outputJob

        if outputJob.getCorrelationId() == None:
            return

        currentPending = self.pendingTransactions[outputJob.getCorrelationId()]
        if currentPending == None:
            currentPending = []
        currentPending.append(outputJob)

        self.pendingTransactions[outputJob.getCorrelationId()] = currentPending

    def cachePendingTransactions(self, correlationId):
        inputJob = self.jobMap[correlationId]

        if inputJob != None:
            streamFolder = self.directoryFullpath + "/" + inputJob.getDestination()
            try:
                pending = self.pendingTransactions[correlationId]
                if not pending == None:
                    inputFolder = streamFolder + "/" + inputJob.getHash()
                    os.makedirs(inputFolder)
                    for outputJob in pending:
                        with open(
                            inputFolder + "/" + outputJob.getHash()
                        ) as outputFile:
                            self.jobFolderMap[inputJob.getHash()] = inputFolder
                            self.save(outputJob, outputFile)
            except Exception as ex:
                self.workflow.local_logger.exception(f"Error caching pending transaction for CorrelationID: {correlationId}")
                self.workflow.report_internal_exception(ex)


class InternalException(Exception):
    def __init__(
        self, reason: str = None, stacktrace: str = None, sender_id: str = None
    ):
        self._reason = reason
        self._stacktrace = stacktrace
        self._sender_id = sender_id

    @classmethod
    def from_exception(
        cls, exception: Exception, sender_id: str
    ) -> "InternalException":
        from_exception = TracebackException.from_exception(exception)
        reason = next(from_exception.format_exception_only()).strip()
        stacktrace = "".join(from_exception.format()).strip()
        return InternalException(
            reason=reason, stacktrace=stacktrace, sender_id=sender_id
        )

    @property
    def reason(self) -> str:
        return self._reason

    @reason.setter
    def reason(self, reason: str):
        self._reason = reason

    @property
    def stacktrace(self) -> str:
        return self._stacktrace

    @stacktrace.setter
    def stacktrace(self, stacktrace):
        self._stacktrace = stacktrace

    @property
    def sender_id(self) -> str:
        return self._sender_id

    @sender_id.setter
    def sender_id(self, sender_id):
        self._sender_id = sender_id


class CacheManagerTask(Task):
    @property
    def name(self) -> str:
        return "CacheManagerTask"


class Job:
    def __init__(self):
        self.jobId = str(uuid.uuid4())
        self.correlationId = ""
        self.destination = ""
        self.cached = False
        self.failures = 0
        self.cacheable = True
        self.timeout = 0
        # sets whether self job requires a transactional level of caching (usually due
        # to being created multiple times per single task)
        self.transactional = True
        # denotes that self job is a simple message denoting success of a transaction
        # (with self correlationId)
        self.isTransactionSuccessMessage = False
        self.totalOutputs = 0

    def __str__(self):
        return str(self.__class__) + ": " + str(self.__dict__)

    def isTransactional(self):
        return self.transactional

    def setTransactional(self, transactional):
        self.transactional = transactional

    def setId(self, job_id):
        warnings.warn("deprecated", category=DeprecationWarning, stacklevel=2)
        self.jobId = job_id

    def getId(self):
        warnings.warn("deprecated", category=DeprecationWarning, stacklevel=2)
        return self.jobId

    def getJobId(self):
        return self.jobId

    def setJobId(self, job_id):
        self.jobId = job_id

    def setCorrelationId(self, correlation_id):
        self.correlationId = correlation_id

    def getCorrelationId(self):
        return self.correlationId

    def setDestination(self, destination):
        self.destination = destination

    def getDestination(self):
        return self.destination

    def isCached(self):
        return self.cached

    def setCached(self, cached):
        self.cached = cached

    def isCacheable(self):
        return self.cacheable

    def setCacheable(self, cacheable):
        self.cacheable = cacheable

    def getTimeout(self):
        return self.timeout

    def setTimeout(self, timeout: int):
        self.timeout = timeout

    def getFailures(self):
        return self.failures

    def setFailures(self, failures):
        self.failures = failures

    def getIsTransactionSuccessMessage(self):
        return self.isTransactionSuccessMessage

    def setIsTransactionSuccessMessage(self, is_transaction_success_message):
        self.isTransactionSuccessMessage = is_transaction_success_message

    def getPickleBytes(self):
        id = self.jobId
        failures = self.failures
        correlationId = self.correlationId
        cached = self.cached
        cacheable = self.cacheable

        self.jobId = None
        self.correlationId = None
        self.failures = 0
        self.cached = False
        self.cacheable = True

        pickleBytes = pickle.dumps(self)

        self.jobId = id
        self.correlationId = correlationId
        self.cached = cached
        self.failures = failures
        self.cacheable = cacheable

        return pickleBytes

    def getHash(self):
        # FIXME if two outputs have the same signature (aka if a task outputs two
        # identical elements) then duplicates are lost!
        h = hashlib.md5()
        h.update(self.getPickleBytes())
        return str(h.digest())


class Stream(ABC):
    """Streams are used to pass messages between Tasks.
    
    They can be either queue or topic (broadcast) based. 
    """

    def __init__(self, name: str, workflow: "Workflow"):
        self._name = name
        self._workflow = workflow

    @property
    def name(self) -> str:
        return self._name

    @name.setter
    def name(self, value: str):
        self._name = value

    @property
    def workflow(self) -> "Workflow":
        return self._workflow

    @workflow.setter
    def workflow(self, value: "Workflow"):
        self._workflow = value

    @property
    @abstractmethod
    def broadcast(self) -> bool:
        pass

    @property
    @abstractmethod
    def destination_names(self) -> list:
        pass


class JobStream(Stream):
    """JobStreams are used to communicate Jobs between different Tasks"""

    def __init__(self, name: str, workflow: "Workflow"):
        super().__init__(name, workflow)

        self._destination = {}
        self._pre = {}
        self._post = {}
        self._rx_connections = {}
        self._tx_connection = stomp.Connection(self.workflow.broker)
        self._tx_connection.connect(wait=True)
        self._consumers = []

        self._cache_manager_task = CacheManagerTask()
        self._cache_manager_task.workflow = workflow

    # TODO should probably make this thread safe
    def send_message(self, body, destination):
        self._tx_connection.send(body=body, destination=destination)

    def get_rx_connection(self, destination):
        if destination in self._rx_connections:
            return self._rx_connections[destination]
        connection = stomp.Connection(self.workflow.broker)
        connection.connect(wait=True)  # add credentials?
        self._rx_connections[destination] = connection
        return connection

    def subscribe(self, queue_info: QueueInfo, callback_func):
        destination = queue_info.destination
        headers = {}
        ack_mode = "auto"
        if queue_info.prefetch_size > 0:
            headers["activemq.prefetchSize"] = queue_info.prefetch_size
            ack_mode = "client"

        consumer = BuiltinStreamConsumer(callback_func)
        connection = self.get_rx_connection(destination)
        listener = MessageListener(
            connection,
            consumer,
            self.workflow,
            destination,
            False,
            ack_mode == "client",
        )
        connection.set_listener(listener.listener_id, listener)
        connection.subscribe(
            destination, consumer.subscription_id, ack=ack_mode, headers=headers
        )
        self._consumers.append(consumer)

    def send(self, job: Job, task_id: str):
        try:
            # if the sender is one of the targets of this stream, it has re-sent a message
            # so it should only be put in the relevant physical queue
            job.setDestination(type(self).__name__)
            body = self.workflow.serializer.serialize(job)
            destination = self._pre.get(task_id, None)
            if destination is not None:
                # producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT); - stomp is NON_PERSISTENT by default
                self.send_message(body, destination.destination)
            else:
                # otherwise the sender must be the source of this stream so intends to
                # propagate its messages to all the physical queues
                for task_id in self._pre.keys():
                    self.send_message(body, self._pre[task_id].destination)
        except Exception as ex:
            self.workflow.local_logger.exception(f"Error sending {job}")
            self.workflow.report_internal_exception(ex)

    @property
    def destination_names(self) -> list:
        names = [k.destination for k in self._pre.keys()]
        names += [k.destination for k in self._destination.keys()]
        names += [k.destination for k in self._post.keys()]
        return names

    def stop(self):
        self._tx_connection.stop()
        for rxConKey in self._rx_connections:
            self._rx_connections[rxConKey].stop()

    @property
    def broadcast(self) -> bool:
        return next(iter(self._destination.values())).broadcast

    @property
    def all_queues(self):
        queues = [q.name for q in self._pre.values()]
        queues += [q.name for q in self._destination.values()]
        queues += [q.name for q in self._post.values()]
        return set(queues)

    @abstractmethod
    def add_consumer(self, consumer, consumer_id: str):
        raise NotImplementedError


class BuiltinStream(Stream):
    """Generic stream that is not tied to any Task input or output

    For streams that are specifically used to communicate Job in/out between
    tasks see JobStream

    """

    def __init__(self, name: str, workflow: "Workflow", broadcast: bool = True):
        super().__init__(name, workflow)
        self._destination = None
        self._connection = None
        self._broadcast = broadcast
        self._consumers = []
        self._pending_consumers = []
        self._listeners = []

        self.sessionCreated = False

    def init(self):
        self._connection = stomp.Connection(
            host_and_ports=self.workflow.broker,
            reconnect_sleep_initial=5,
            reconnect_sleep_increase=0.0,
            reconnect_sleep_jitter=0.0,
            reconnect_sleep_max=5.0,
            reconnect_attempts_max=-1,
        )
        self._connection.connect(wait=True)  # add credentials?
        # session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        type_ = "topic" if self._broadcast else "queue"
        self._destination = f"/{type_}/{self.destination_name}"

        for consumer in self._pending_consumers:
            self.add_consumer(consumer)
        self._pending_consumers.clear()

    @property
    def session_created(self) -> bool:
        return self._connection is not None

    @property
    def destination(self) -> str:
        return self._destination

    @property
    def destination_name(self) -> str:
        return f"{self._name}.{self._workflow.instance}"

    def send(self, message):
        if type(message) is str:
            body = message
        else:
            body = self.workflow.serializer.serialize(message)
        self._connection.send(body=body, destination=self._destination)

    @property
    def broadcast(self) -> bool:
        return self._broadcast

    @property
    def destination_names(self) -> list:
        return [self._destination] if self._destination else None

    def add_consumer(self, consumer: "BuiltinStreamConsumer"):
        if not self.session_created:
            self._pending_consumers.append(consumer)
            return

        self._consumers.append(consumer)
        listener = MessageListener(
            self._connection,
            consumer,
            self.workflow,
            self._destination,
            consumer.deserialize,
        )
        self._listeners.append(listener)
        self._connection.set_listener(listener.listener_id, listener)
        self._connection.subscribe(
            self._destination, id=consumer.subscription_id, ack="auto"
        )

    def stop(self):
        for c in self._consumers:
            self._connection.unsubscribe(c.subscription_id)
        self._consumers.clear()

        for l in self._listeners:
            self._connection.remove_listener(l.listener_id)

        self._connection.disconnect()


class BuiltinStreamConsumer:
    def __init__(self, function, deserialize: bool = True):
        """Consumer for built in streams

        :param function: the consumer function to call
        :param deserialize: set to true to enable built-in deserialization of incoming messages
        """
        self._function = function
        self._deserialize = deserialize
        self._subscription_id = uuid.uuid4().int

    @property
    def subscription_id(self):
        return self._subscription_id

    @property
    def deserialize(self):
        return self._deserialize

    def consume(self, message, ack_func=None):
        self._function(message) if ack_func is None else self._function(
            message, ack_func
        )


class Workflow(ABC):
    """Abstract implementation of Workflow classes

    A workflow represents an individual node in the defined Workflow as a whole. Workflows manage connections
    to streams, tasks and communication with the master
    """

    def __init__(
        self,
        name="",
        instance=None,
        broker_host="localhost",
        stomp_port=61613,
        mode=Mode.WORKER,
        cache=None,
        cache_enabled=True,
        delete_cache=None,
        excluded_tasks=None,
        enable_prefetch=False,
    ):
        # General properties
        self._name = name
        self._instance = instance or str(uuid.uuid4())
        self._broker_host = broker_host
        self._stomp_port = stomp_port
        self._mode = mode

        # Cache related
        self._cache = cache
        self._cache_enabled = cache_enabled
        self._delete_cache = delete_cache

        # excluded tasks from workers
        self._excluded_tasks = excluded_tasks or []
        self._enable_prefetch = enable_prefetch

        # Task Tracking
        self._active_jobs = []
        self._active_streams = []
        self._active_worker_ids = []  # TODO: is this needed in workers?
        self._terminated_worker_ids = []  # TODO: is this needed in workers?

        # Termination flags
        self._delay = 0
        self._terminated = False
        self._termination_timer = None
        self._termination_timeout = 10000
        self._about_to_terminate = False  # TODO: is this needed in workers?

        # Init serializer
        self._serializer = None

        self._input_directory = ""
        self._output_directory = ""
        self._temp_directory = None

        # Global streams
        self._task_status_topic = BuiltinStream("TaskStatusPublisher", self)
        self._control_topic = BuiltinStream("ControlTopic", self)
        self._log_topic = BuiltinStream("LogTopic", self)
        self._failed_jobs_topic = BuiltinStream("FailedJobs", self)
        self._internal_exceptions_queue = BuiltinStream(
            "InternalExceptions", self, False
        )

        self._allStreams = []
        self._allStreams.append(self._task_status_topic)
        self._allStreams.append(self._control_topic)
        self._allStreams.append(self._log_topic)
        self._allStreams.append(self._failed_jobs_topic)
        self._allStreams.append(self._internal_exceptions_queue)

        self._failed_jobs = None
        self._internal_exceptions = None

        # Init local_logger
        self.local_logger = logging.getLogger(self.name)
        self.local_logger.setLevel(logging.DEBUG)
        __fmt = logging.Formatter(
            "{}[{}] | {}".format("%(asctime)s", "%(levelname)5s", "%(message)s")
        )
        __handler = logging.StreamHandler(sys.stdout)
        __handler.setLevel(logging.DEBUG)
        __handler.setFormatter(__fmt)
        self.local_logger.addHandler(__handler)
        self.local_logger.debug("local_logger Initialised to sys.stdout")

        self.logger = CrossflowLogger(self)

    """
    PRIMITIVE PROPERTIES
    """

    @property
    def name(self) -> str:
        return self._name

    @name.setter
    def name(self, value: str):
        self._name = value

    @property
    def instance(self) -> str:
        return self._instance

    @instance.setter
    def instance(self, instance: str):
        self._instance = instance

    @property
    def broker_host(self) -> str:
        return self._broker_host

    @broker_host.setter
    def broker_host(self, broker_host: str):
        self._broker_host = broker_host

    @property
    def stomp_port(self) -> int:
        return self._stomp_port

    @stomp_port.setter
    def stomp_port(self, stomp_port: int):
        self._stomp_port = stomp_port

    @property
    def mode(self) -> Mode:
        return self._mode

    @mode.setter
    def mode(self, mode: Mode):
        if mode is None:
            raise AttributeError
        self._mode = mode

    @property
    def cache(self):
        return self._cache

    @cache.setter
    def cache(self, cache):
        self._cache = cache

    @property
    def cache_enabled(self) -> bool:
        return self._cache_enabled

    @cache_enabled.setter
    def cache_enabled(self, cache_enabled: bool):
        self._cache_enabled = cache_enabled

    @property
    def delete_cache(self) -> bool:
        return self._delete_cache

    @delete_cache.setter
    def delete_cache(self, delete_cache: bool):
        self._delete_cache = delete_cache

    @property
    def excluded_tasks(self) -> list:
        return self._excluded_tasks

    @excluded_tasks.setter
    @abstractmethod
    def excluded_tasks(self, tasks: list):
        raise NotImplementedError

    @property
    def enable_prefetch(self) -> bool:
        return self._enable_prefetch

    @enable_prefetch.setter
    def enable_prefetch(self, enable_prefetch: bool):
        self._enable_prefetch = enable_prefetch

    @property
    def input_directory(self) -> str:
        return self._input_directory

    @input_directory.setter
    def input_directory(self, input_directory: str):
        self._input_directory = input_directory

    @property
    def output_directory(self) -> str:
        return self._output_directory

    @output_directory.setter
    def output_directory(self, output_directory: str):
        self._output_directory = output_directory

    @property
    def temp_directory(self) -> str:
        return self._temp_directory

    @temp_directory.setter
    def temp_directory(self, temp_directory: str):
        self._temp_directory = temp_directory

    @abstractmethod
    def _create_serializer(self) -> serialization.Serializer:
        pass

    @abstractmethod
    def _register_custom_serialization_types(self):
        pass

    @property
    def serializer(self) -> serialization.Serializer:
        if self._serializer is None:
            # TODO: commented out types are currently only available in Java
            self._serializer = self._create_serializer()
            self._serializer.register_type(FailedJob)
            self._serializer.register_type(InternalException)
            self._serializer.register_type(Job)
            # self._serializer.register_type(LoggingStrategy)
            self._serializer.register_type(Mode)

            self._serializer.register_type(ControlSignal)
            self._serializer.register_type(ControlSignals)
            self._serializer.register_type(LogLevel)
            self._serializer.register_type(LogMessage)
            # self._serializer.register_type(StreamMetadata)
            # self._serializer.register_type(StreamMetadataSnapshot)
            self._serializer.register_type(TaskStatus)
            self._serializer.register_type(TaskStatuses)

            self._register_custom_serialization_types()
        return self._serializer

    @property
    def broker(self) -> list:
        return [(self._broker_host, self._stomp_port)]

    @property
    def termination_timeout(self) -> int:
        return self._termination_timeout

    @termination_timeout.setter
    def termination_timeout(self, termination_timeout: int):
        self._termination_timeout = termination_timeout

    @property
    def terminated(self) -> bool:
        return self._terminated

    @terminated.setter
    def terminated(self, terminated: bool):
        self._terminated = terminated

    """
    STREAMS
    """

    @property
    def task_status_topic(self) -> BuiltinStream:
        return self._task_status_topic

    @property
    def control_topic(self) -> BuiltinStream:
        return self._control_topic

    @property
    def log_topic(self) -> BuiltinStream:
        return self._log_topic

    @property
    def failed_jobs_topic(self) -> BuiltinStream:
        return self._failed_jobs_topic

    @property
    def internal_exceptions_queue(self) -> BuiltinStream:
        return self._internal_exceptions_queue

    @property
    def failed_jobs(self) -> list:
        return self._failed_jobs

    @property
    def internal_exceptions(self) -> list:
        return self._internal_exceptions

    def connect(self):
        if self._temp_directory is None:
            self._temp_directory = tempfile.NamedTemporaryFile(prefix="crossflow")

        self._task_status_topic.init()
        self._control_topic.init()
        self._log_topic.init()
        self._failed_jobs_topic.init()
        self._internal_exceptions_queue.init()

        self._active_streams.append(self._task_status_topic)
        self._active_streams.append(self._failed_jobs_topic)
        self._active_streams.append(self._internal_exceptions_queue)

        # XXX do not add this topic/queue or any other non-essential ones to
        # activestreams as the workflow should be able to terminate regardless of their
        # state
        # activeStreams.add(_control_topic);
        self._control_topic.add_consumer(
            BuiltinStreamConsumer(self.consume_control_signal)
        )
        # XXX if the worker sends this before the master is listening to this topic
        # / this information is lost which affects termination
        self._control_topic.send(ControlSignal(ControlSignals.WORKER_ADDED, self.name))

    """
     * used to manually add local workers to master as they may be enabled too
     * quickly to be registered using the control topic when on the same machine
    """

    def add_active_worker_id(self, worker_id: str):
        self._active_worker_ids.append(worker_id)

    # noinspection PyBroadException
    def consume_control_signal(self, signal: ControlSignals):
        if signal.signal == ControlSignals.TERMINATION:
            try:
                self.terminate()
            except Exception:
                self.local_logger.exception("Failed to handle TERMINATION signal")

    def cancel_termination(self):
        # TODO: is this needed? Should the master not control termination?
        self._about_to_terminate = False

    def is_master(self) -> bool:
        warnings.warn(
            "Deprecated, python does not have master capability", stacklevel=2
        )
        return self.mode in [Mode.MASTER, Mode.MASTER_BARE]

    def is_worker(self) -> bool:
        return self.mode in [Mode.WORKER, Mode.MASTER]

    @abstractmethod
    def run(self, delay: int = 0):
        """Main run function of Workflow subclasses, used to setup streams and tasks specific to the workflow.
        This is usually autogenerated

        :param delay: how long to delay execution of run when called in ms
        """
        pass

    def terminate(self):
        term_thread = threading.Thread(target=self.__terminate)
        term_thread.start()

    # noinspection PyBroadException
    def __terminate(self):
        if self._terminated:
            return

        if self._termination_timer is not None:
            self._termination_timer.cancel()

        self.local_logger.info(f"Terminating workflow {self.name}...")
        self._control_topic.send(
            ControlSignal(ControlSignals.ACKNOWLEDGEMENT, self.name)
        )

        for stream in self._allStreams:
            self.local_logger.info(f"Terminating stream {stream.name}")
            try:
                stream.stop()
            except Exception:
                self.local_logger.exception(
                    f"Failed to stop stream ({stream.name})during termination"
                )

        self._terminated = True
        self.local_logger.info(f"Workflow {self.name} successfully terminated")

    def report_internal_exception(self, exception: Exception):
        self.local_logger.exception("")
        try:
            internal_exception = InternalException.from_exception(exception, self.name)
            serialized = self.serializer.serialize(internal_exception)
            self._internal_exceptions_queue.send(serialized)
        except Exception as e:
            self.local_logger.exception(
                "Could not propagate exception, serialisation error encountered"
            )
            raise e

    def set_task_in_progress(self, caller: Task, reason: str = "Reason"):
        self._task_status_topic.send(
            TaskStatus(TaskStatuses.INPROGRESS, caller.task_id, reason)
        )

    def set_task_waiting(self, caller):
        self._task_status_topic.send(
            TaskStatus(TaskStatuses.WAITING, caller.task_id, "")
        )

    def set_task_blocked(self, caller, reason):
        self._task_status_topic.send(
            TaskStatus(TaskStatuses.BLOCKED, caller.task_id, reason)
        )

    def set_task_unblocked(self, caller):
        self._task_status_topic.send(
            TaskStatus(TaskStatuses.INPROGRESS, caller.task_id, "")
        )


class FailedJob(object):
    def __init__(
        self,
        job: Job = None,
        reason: str = None,
        stacktrace: str = None,
        task: str = None,
        workflow: str = None,
    ):
        self._job = job
        self._reason = reason
        self._stacktrace = stacktrace
        self._task = task
        self._workflow = workflow

    @classmethod
    def from_exception(cls, job: Job, exception: Exception, task: Task):
        from_exception = TracebackException.from_exception(exception)
        reason = next(from_exception.format_exception_only()).strip()
        stacktrace = "".join(from_exception.format()).strip()
        return FailedJob(
            job=job,
            reason=reason,
            stacktrace=stacktrace,
            task=task.name,
            workflow=task.workflow.name,
        )

    @property
    def job(self) -> Job:
        return self._job

    @job.setter
    def job(self, job: Job):
        self._job = job

    @property
    def reason(self) -> str:
        return self._reason

    @reason.setter
    def reason(self, reason: str):
        self._reason = reason

    @property
    def stacktrace(self) -> str:
        return self._stacktrace

    @stacktrace.setter
    def stacktrace(self, stacktrace: str):
        self._stacktrace = stacktrace

    @property
    def task(self) -> str:
        return self._task

    @task.setter
    def task(self, task: str):
        self._task = task

    @property
    def workflow(self) -> str:
        return self._workflow

    @workflow.setter
    def workflow(self, workflow: str):
        self._workflow = workflow
