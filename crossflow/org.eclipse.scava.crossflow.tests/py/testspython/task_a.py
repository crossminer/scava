from crossflow.testspython.task_a import TaskABase



class TaskA(TaskABase):

    def __init__(self):
        super().__init__()

    def consumeQueueA(self, type_a: TypeA):
        print(f"{self.workflow.name}:TaskA:consumeQueueA received {type_a}")
        
        # Do work here
        
