from org.eclipse.scava.crossflow.runtime import ControlSignal
from org.eclipse.scava.crossflow.runtime import ControlSignals
from org.eclipse.scava.crossflow.runtime import Workflow
from org.eclipse.scava.crossflow.runtime import Mode

from org.eclipse.scava.crossflow.examples.wordcount.xtext.Filter import Filter
from org.eclipse.scava.crossflow.examples.wordcount.xtext.Lines import Lines
from org.eclipse.scava.crossflow.examples.wordcount.xtext.WordFrequencies import WordFrequencies
from org.eclipse.scava.crossflow.examples.wordcount.xtext.Filtered import Filtered

import argparse
import time
import threading
import sys
import traceback

class WordCountWorkflow(Workflow):

	def __init__(self):
		super().__init__()
		self.enablePrefetch = False
		
		# streams
		self.lines = None
		self.wordFrequencies = None
		self.filtered = None

		# tasks
		self.filter = None
	
	
		
		if (self.isWorker()):
			if (not 'Filter' in self.tasksToExclude):
				self.filter = Filter()
				self.filter.setWorkflow(self)
	
	def createWorker(self):
		worker = WordCountWorkflow(Mode.WORKER)
		worker.setInstanceId(self.instanceId)
		return worker
	
	
	
	
	"""
	 * Run with initial delay in ms before starting execution (after creating broker
	 * if master)
	 * 
	 * @param delay
	"""
	def run(self, delay=0):
		self.delay=delay

		try:
			"""
			# Can't create broker yet
			if (self.isMaster()) {
				if (self.createBroker) {
					brokerService = BrokerService()
					brokerService.setUseJmx(true);
					brokerService.addConnector(getBroker());
					brokerService.start();
				}
			"""
	
			self.connect()
	
			time.sleep(delay)
			
			self.lines = Lines(self, self.enablePrefetch)
			self.activeStreams.append(self.lines)
			self.wordFrequencies = WordFrequencies(self, self.enablePrefetch)
			self.activeStreams.append(self.wordFrequencies)
			self.filtered = Filtered(self, self.enablePrefetch)
			self.activeStreams.append(self.filtered)
			
			
			if (self.isWorker()):
				if (not 'Filter' in self.tasksToExclude):
						self.filter.setResultsTopic(self.resultsTopic);
						self.wordFrequencies.addConsumer(self.filter, "Filter");			
						self.filter.setFiltered(self.filtered);
			
			if (self.isMaster()):
				# run all sources in parallel threads
				def runTask():
					try:
						self.setTaskInProgess(self.lineSource)
						self.lineSource.produce()
						self.setTaskWaiting(self.lineSource)
					except Exception as ex:
						traceback.print_exc()
						exc_type, exc_value, exc_traceback = sys.exc_info()
						stack = traceback.format_exception(exc_type, exc_value, exc_traceback)
						self.reportInternalException(ex, '', stack)
						self.terminate()
				thread = threading.Thread(target=runTask, args={})
				thread.start()  
				
			# delay non-master connections to allow master to create the relevant listeners
			# (in a multi-threaded parallel execution) to facilitate termination, by
			# re-sending worker_added message
			if (not self.isMaster()):
				time.sleep(1)
				self.controlTopic.send(ControlSignal(ControlSignals.WORKER_ADDED, self.getName()))
		except Exception as e:
			traceback.print_exc()
			print(e)
	
	def getLines(self):
		return self.lines
	def getWordFrequencies(self):
		return self.wordFrequencies
	def getFiltered(self):
		return self.filtered
	
	def getLineSource(self):
		return self.lineSource
	def getWordCounter(self):
		return self.wordCounter
	def getFilter(self):
		return self.filter
	def getWordCountSink(self):
		return self.wordCountSink

	"""
	* Sets whether tasks are able to obtain more jobs while they are in the middle of processing one already
	"""
	def isEnablePrefetch(self):
		return self.enablePrefetch

	def setEnablePrefetch(self, enablePrefetch):
		self.enablePrefetch = enablePrefetch

if __name__ == '__main__':
	parser = argparse.ArgumentParser()
	parser.add_argument('-name', default='WordCountWorkflowPython', help='The name of the workflow')
	parser.add_argument('-master', default='localhost', help='IP of the master')
	parser.add_argument('-brokerHost', default='localhost', help='IP of the broker')
	parser.add_argument('-stomp', default=61613, help='Port to use for stomp based messages')
	parser.add_argument('-instance', default=None, help='The instance of the master (to contribute to)')
	parser.add_argument('-mode', default=Mode.WORKER, help='Must be master_bare, master or worker')
	
	parsedArgs = parser.parse_args(sys.argv[1:len(sys.argv)])
	
	app = WordCountWorkflow()
	app.name = parsedArgs.name
	app.master = parsedArgs.master
	app.brokerHost = parsedArgs.brokerHost
	app.stomp = parsedArgs.stomp
	app.instanceId = parsedArgs.instance
	app.mode = parsedArgs.mode
	app.run();
	
