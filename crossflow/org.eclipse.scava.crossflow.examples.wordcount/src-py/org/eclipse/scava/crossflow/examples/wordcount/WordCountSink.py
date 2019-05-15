from org.eclipse.scava.crossflow.examples.wordcount.WordCountSinkBase import WordCountSinkBase

class WordCountSink(WordCountSinkBase):

	def __init__(self):
		super().__init__()
	
	def consumeFiltered(self, wordFrequency):
		# TODO: Add implementation that instantiates, sets, and submits result objects (example below)
		print('[' + self.workflow.getName() + '] Result is ' + str(wordFrequency) + ' (cached=' + wordFrequency.isCached() + ')')
	
