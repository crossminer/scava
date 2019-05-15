from org.eclipse.scava.crossflow.examples.wordcount.WordCounterBase import WordCounterBase
from org.eclipse.scava.crossflow.examples.wordcount.WordFrequency import WordFrequency

class WordCounter(WordCounterBase):

	def __init__(self):
		super().__init__()
	
	def consumeLines(self, line):
		# TODO: Add implementation that instantiates, sets, and submits result objects (example below)
		wordFrequency1 = WordFrequency()
		#	wordFrequency1.setWord( String );
		#	wordFrequency1.setFrequency( int );
		self.sendToWordFrequencies(wordFrequency1)
		
	
