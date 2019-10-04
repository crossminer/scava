
import uuid
from org.eclipse.scava.crossflow.runtime import Job

class WordFrequency(Job):  
	
	def __init__(self, word = None , frequency = None, correlation = None):
		super().__init__()
		self.word = word
		self.frequency = frequency
		if correlation == None:
			self.correlationId = ''
		else:
			self.correlationId = correlation.getId()
		if self.word == None:
			self.word = ""
		if self.frequency == None:
			self.frequency = 0
		
	def setWord(self, word):
		self.word = word
	
	def getWord(self):
		return self.word
	
	def setFrequency(self, frequency):
		self.frequency = frequency
	
	def getFrequency(self):
		return self.frequency
	
	
	def toObjectArray(self):
		ret = []
		ret.append(self.getWord())
		ret.append(self.getFrequency())
		return ret
	
	
	def __str__(self, *args, **kwargs):
		return 'WordFrequency (' + " word=" + str(self.word) + " frequency=" + str(self.frequency) + " id=" + str(self.id) + " correlationId=" + str(self.correlationId) + " destination=" + str(self.destination) + ")"

