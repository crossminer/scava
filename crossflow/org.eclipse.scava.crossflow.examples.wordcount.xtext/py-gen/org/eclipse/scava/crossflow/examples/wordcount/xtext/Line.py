
import uuid
from org.eclipse.scava.crossflow.runtime import Job

class Line(Job):  
	
	def __init__(self, text = None, correlation = None):
		super().__init__()
		self.text = text
		if correlation == None:
			self.correlationId = ''
		else:
			self.correlationId = correlation.getId()
		if self.text == None:
			self.text = ""
		
	def setText(self, text):
		self.text = text
	
	def getText(self):
		return self.text
	
	
	def toObjectArray(self):
		ret = []
		ret.append(self.getText())
		return ret
	
	
	def __str__(self, *args, **kwargs):
		return 'Line (' + " text=" + str(self.text) + " id=" + str(self.id) + " correlationId=" + str(self.correlationId) + " destination=" + str(self.destination) + ")"

