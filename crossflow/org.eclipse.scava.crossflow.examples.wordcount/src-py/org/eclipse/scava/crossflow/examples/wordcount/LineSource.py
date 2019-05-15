import threading
from org.eclipse.scava.crossflow.examples.wordcount.LineSourceBase import LineSourceBase
from org.eclipse.scava.crossflow.examples.wordcount.Line import Line

class LineSource(LineSourceBase):

	def __init__(self):
		super().__init__()
	
	def produce(self):
		def run():
			# TODO: Add implementation that instantiates, sets, and submits source objects (example below)
			line1 = Line()
			#	line1.setText( String )
		
			self.sendToLines( line1)
			
			threading.Timer(100, run).start()
		run()			
