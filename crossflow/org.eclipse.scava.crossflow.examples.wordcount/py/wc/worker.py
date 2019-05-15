from org.eclipse.scava.crossflow.runtime import Mode
from wc import WordCountWorkflow
import time

if __name__ == '__main__':
 worker = WordCountWorkflow(mode=Mode.WORKER)
 worker.setMaster('localhost')
 worker.setInstanceId('wc')
 worker.setName('workerPY')
 worker.run()
 while (not worker.hasTerminated()):
   time.sleep(0.1)