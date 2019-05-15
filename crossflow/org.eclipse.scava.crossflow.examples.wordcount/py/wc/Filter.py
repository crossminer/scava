from wc.FilterBase import FilterBase
from wc.WordFrequency import WordFrequency

class Filter(FilterBase):

	def __init__(self):
		super().__init__()
	
	def consumeWordFrequencies(self, wordFrequency):
		# TODO: Add implementation that instantiates, sets, and submits result objects (example below)
		wordFrequency1 = WordFrequency()
		#	wordFrequency1.setWord( String );
		#	wordFrequency1.setFrequency( int );
		self.sendToFiltered(wordFrequency1)
		
	
