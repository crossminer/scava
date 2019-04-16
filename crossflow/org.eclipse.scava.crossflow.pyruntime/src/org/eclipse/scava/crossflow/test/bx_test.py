'''
Created on 26 Mar 2019

@author: stevet
'''

from org.eclipse.scava.crossflow.runtime import QueueInfo
from org.eclipse.scava.crossflow.runtime import QueueType
from org.eclipse.scava.crossflow.runtime import TaskStatuses
from org.eclipse.scava.crossflow.runtime import TaskStatus

import org.eclipse.scava.crossflow.utils.basic_xstream as bxstream



test_str = """
<org.eclipse.scava.crossflow.runtime.utils.TaskStatus>
  <status>WAITING</status>
  <caller>caller foo</caller>
  <reason>reason bar</reason>
</org.eclipse.scava.crossflow.runtime.utils.TaskStatus>
"""
        
test_status_obj = """
<org.eclipse.scava.crossflow.runtime.TaskStatus>
    <caller></caller>
    <reason></reason>
    <status>STARTED</status>
</org.eclipse.scava.crossflow.runtime.TaskStatus>
"""

if __name__ == '__main__':
    print(bxstream.javaClassToPyClass('org.eclipse.scava.crossflow.examples.ack.IntElement'))
    print(bxstream.bxToString(TaskStatuses.WAITING))
    qi = QueueInfo(QueueType.QUEUE, 'test_queue', False)
    print(bxstream.bxToString(qi))
    ts = TaskStatus()
    iObj = bxstream.inspect.getmembers(ts)
    #print(inspect.getmembers(iObj))
    print(bxstream.bxToString(ts))
    new_obj = bxstream.bxToObject(test_status_obj)
    print(new_obj)