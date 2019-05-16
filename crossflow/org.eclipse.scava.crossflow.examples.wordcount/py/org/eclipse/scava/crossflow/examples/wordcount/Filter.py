from org.eclipse.scava.crossflow.examples.wordcount.FilterBase import FilterBase

class Filter(FilterBase):

	def __init__(self):
		super().__init__()
	
	def consumeWordFrequencies(self, wordFrequency):
		self.sendToFiltered(wordFrequency)
		
	
