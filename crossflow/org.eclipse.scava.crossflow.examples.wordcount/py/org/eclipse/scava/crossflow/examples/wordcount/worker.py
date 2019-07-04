from org.eclipse.scava.crossflow.examples.wordcount.runtime.WordCountWorkflow import WordCountWorkflow
import time

worker = WordCountWorkflow()
worker.setMaster('localhost')
worker.setInstanceId('WordCountWorkflow')
worker.setName('workerPY')
worker.run()
while (not worker.hasTerminated()):
    time.sleep(0.1)
print("terminated")
