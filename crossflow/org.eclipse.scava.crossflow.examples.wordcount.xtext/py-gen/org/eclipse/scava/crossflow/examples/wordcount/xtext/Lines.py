from org.eclipse.scava.crossflow.runtime import JobStream
from org.eclipse.scava.crossflow.runtime import QueueInfo, QueueType, str2QueueType
from org.eclipse.scava.crossflow.utils.basic_xstream import bxToObject

import traceback
import sys

class Lines(JobStream):
		
	def __init__(self, workflow, enablePrefetch):
		super().__init__(workflow)
		
		self.preQueue['WordCounter'] = QueueInfo(QueueType.QUEUE, 'LinesPre.WordCounter.' + workflow.getInstanceId())
		self.destination['WordCounter'] = QueueInfo(QueueType.QUEUE, 'LinesDestination.WordCounter.' + workflow.getInstanceId())
		prefetchSize = 0
		self.enablePrefetch = enablePrefetch
		if not self.enablePrefetch:
			prefetchSize = 1
		postQ = QueueInfo(str2QueueType('Queue'), 'LinesPost.WordCounter.' + workflow.getInstanceId(), prefetchSize)

		self.postQueue['WordCounter'] = postQ
		
		for consumerId in self.preQueue.keys():
			preQueue = self.preQueue[consumerId]
			destQueue = self.destination[consumerId]
			postQueue = self.postQueue[consumerId]
			if (workflow.isMaster()):
				def handlePreMessage(message, ackFunc=None):
					try:
						workflow.cancelTermination()
						job = bxToObject(message)
						if (workflow.getCache() != None and workflow.getCache().hasCachedOutputs(job)):
							workflow.setTaskInProgess(self.cacheManagerTask)
							cachedOutputs = workflow.getCache().getCachedOutputs(job)
							workflow.setTaskWaiting(self.cacheManagerTask)
							for outputJob in cachedOutputs:
								if outputJob.getDestination() == 'WordFrequencies':
									workflow.cancelTermination()
									workflow.getWordFrequencies().send(outputJob, consumerId)
						else:
							self.sendMessage(message, destQueue.getStompDestinationName())						
						
						if ackFunc != None:
							ackFunc()						
						
					except Exception as ex:
						traceback.print_exc()
						exc_type, exc_value, exc_traceback = sys.exc_info()
						stack = traceback.format_exception(exc_type, exc_value, exc_traceback)
						workflow.reportInternalException(ex, '', stack)

				self.subscribe(preQueue, handlePreMessage)

				def handleDestinationMessage(message, ackFunc=None):
					try:
						workflow.cancelTermination()
						job = bxToObject(message)
						if (workflow.getCache() != None) and (not job.isCached()):
							if(job.isTransactional()):
								workflow.getCache().cacheTransactionally(job)
							else:
								workflow.getCache().cache(job)
						if(job.getIsTransactionSuccessMessage()):
							return
						self.sendMessage(message, postQueue.getStompDestinationName())
						
						if ackFunc != None:
							ackFunc()						
					except Exception as ex:
						traceback.print_exc()
						exc_type, exc_value, exc_traceback = sys.exc_info()
						stack = traceback.format_exception(exc_type, exc_value, exc_traceback)
						workflow.reportInternalException(ex, '', stack)

				self.subscribe(destQueue, handleDestinationMessage)
	
	def addConsumer(self, consumer, consumerId):
		postQueue = self.postQueue[consumerId]
		#only connect if the consumer exists (for example it will not in a master_bare situation)
		if consumer != None:
			def messageHandler(message, ackFunc=None):		
				try:
					line = bxToObject(message)
					consumer.consumeLinesWithNotifications(line)
						
					if ackFunc != None:
						ackFunc()						
				except Exception as ex:
					traceback.print_exc()
					exc_type, exc_value, exc_traceback = sys.exc_info()
					stack = traceback.format_exception(exc_type, exc_value, exc_traceback)
					self.workflow.reportInternalException(ex, '', stack)

			self.subscribe(postQueue, messageHandler)
