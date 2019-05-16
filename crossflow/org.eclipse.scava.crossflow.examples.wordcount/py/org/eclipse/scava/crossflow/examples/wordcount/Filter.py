from org.eclipse.scava.crossflow.examples.wordcount.FilterBase import FilterBase

class Filter(FilterBase):

	def __init__(self):
		super().__init__()

	def consumeWordFrequencies(self, wordFrequency):
		
		ignoredWords = [ "the", "and", "is", "or" ]
	
		if not wordFrequency.word in ignoredWords:
			self.sendToFiltered(wordFrequency)
	
		
	
