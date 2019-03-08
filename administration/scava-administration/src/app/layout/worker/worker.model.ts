import { ExecutionTask } from "../project/components/configure-project/execution-task.model";

export interface IWorker {
    workerId?: string,
    currentTask?:ExecutionTask
}

export class Worker implements IWorker {
    constructor(
        public workerId?: string,
        public currentTask?:ExecutionTask
    ) {
    }
}
