from crossflow.runtime import JobStream, QueueInfo, Workflow


class InitialRepositoryAnalyses(JobStream):
    """NOTE: Auto-generated by Steam2Class on 2019-12-18T13:41:54.162Z    
    Do not edit this class manually
    """

    def __init__(self, workflow: Workflow, enable_prefetch: bool):
        super().__init__("InitialRepositoryAnalyses", workflow)

        # PythonRepositoryAnalyzer
        self._pre["PythonRepositoryAnalyzer"] = QueueInfo(
            f"InitialRepositoryAnalysesPre.PythonRepositoryAnalyzer.{self.workflow.instance}",
            False
        )
        self._destination['PythonRepositoryAnalyzer'] = QueueInfo(
            f"InitialRepositoryAnalysesDestination.PythonRepositoryAnalyzer.{self.workflow.instance}",
            False
        )
        self._post['PythonRepositoryAnalyzer'] = QueueInfo(
            f"InitialRepositoryAnalysesPost.PythonRepositoryAnalyzer.{self.workflow.instance}",
            False,
            0 if enable_prefetch else 1
        )
        
    
    def add_consumer(self, consumer, consumer_id: str):
        post_queue = self._post[consumer_id]
        # only connect if the consumer exists (for example it will not in a master_bare situation)
        if consumer is not None:
            def handler(message, ack_func=None):        
                try:
                    obj = self.workflow.serializer.deserialize(message)
                    consumer.consumeInitialRepositoryAnalysesWithNotifications(obj)
                    if ack_func is not None:
                        ack_func()
                except Exception as ex:
                    self.workflow.local_logger.exception("")
                    self.workflow.report_internal_exception(ex)

            self.subscribe(post_queue, handler)
