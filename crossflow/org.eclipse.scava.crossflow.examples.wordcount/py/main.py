import time

from org.eclipse.scava.crossflow.runtime import Mode
from org.eclipse.scava.crossflow.examples.wordcount.WordCountWorkflow import WordCountWorkflow

worker = WordCountWorkflow(mode=Mode.WORKER)
worker.setMaster('localhost')
worker.setInstanceId("instance")
worker.setName("Worker")

worker.run()
while (not worker.hasTerminated()):
    time.sleep(0.1)
print("terminated")