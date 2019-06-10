import traceback
import sys

from org.eclipse.scava.crossflow.utils.basic_xstream import getSimpleClassName
from org.eclipse.scava.crossflow.runtime import FailedJob
from org.eclipse.scava.crossflow.runtime import Task

from org.eclipse.scava.crossflow.examples.wordcount.xtext.WordFrequency import WordFrequency


class FilterBase(Task):

	def __init__(self):
		super().__init__()
		self.workflow = None
		self.filtered = None
	
	def setWorkflow(self, workflow):
		self.workflow = workflow
	
	def getWorkflow(self):
		return self.workflow
	
	def getId(self):
		return "Filter:" + self.workflow.getName()
	
	def setFiltered(self, filtered):
		self.filtered = filtered
	
	def getFiltered(self):
		return self.filtered
	
	def sendToFiltered(self, wordFrequency):
		wordFrequency.setCacheable(self.cacheable)
		self.getFiltered().send(wordFrequency, getSimpleClassName(self))
	
	def consumeWordFrequenciesWithNotifications(self, wordFrequency):
		try:
			self.workflow.setTaskInProgessWithReason(self, wordFrequency.getId())
			result = self.consumeWordFrequencies(wordFrequency);
			if result is not None:
				if self.cacheable:
					result.setCorrelationId(wordFrequency.getId())
				result.setTransactional(False)
				self.sendToFiltered(result)
		except Exception as ex:
			traceback.print_exc()
			try:
				wordFrequency.setFailures(wordFrequency.getFailures() + 1)
				self.workflow.getFailedJobsQueue().send(FailedJob(wordFrequency, ex, self.workflow.getName(), "Filter"))
			except Exception as e:
				traceback.print_exc()
				exc_type, exc_value, exc_traceback = sys.exc_info()
				stack = traceback.format_exception(exc_type, exc_value, exc_traceback)
				self.workflow.reportInternalException(e, '', stack)
		finally:
			try:
				self.workflow.setTaskWaiting(self)
			except Exception as e:
				traceback.print_exc()
				exc_type, exc_value, exc_traceback = sys.exc_info()
				stack = traceback.format_exception(exc_type, exc_value, exc_traceback)
				self.workflow.reportInternalException(e, '', stack)
	
	def consumeWordFrequencies(self, wordFrequency):
		pass
	
