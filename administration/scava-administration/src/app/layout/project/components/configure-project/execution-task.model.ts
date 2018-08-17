export interface IExecutionTask {
    analysisTaskId?: string,
    label?: string,
    type?: string,
    startDate?: Date,
    endDate?: Date, 
    scheduling?:SchedulingInformation
}


export class ExecutionTask implements IExecutionTask {
    constructor(
        public analysisTaskId?: string,
        public label?: string,
        public type?: string,
        public startDate?: Date,
        public endDate?: Date, 
        public scheduling?:SchedulingInformation
    ) {
    }
}

export interface ISchedulingInformation{
    status?: string,
    currentDate?: Date,
    workerId?: string,
    progress?: number,
    acurrentMetric?: string,  
    executionRequestDate?: Date,
    lastDailyExecutionDuration?: number
}

export class SchedulingInformation implements ISchedulingInformation {
    constructor(
       public status?: string,
       public currentDate?: Date,
       public workerId?: string,
       public progress?: number,
       public acurrentMetric?: string,  
       public executionRequestDate?: Date,
       public lastDailyExecutionDuration?: number
    ) {
    }
}