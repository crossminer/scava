export interface IExecutionTask {
    oldAnalysisTaskId?: string,
    analysisTaskId?: string,
    label?: string,
    type?: string,
    startDate?: string,
    endDate?: string,
    projectId?: string,
    metricExecutions?: MetricExecutions[],
    metricProviders?: string[],
    scheduling?: SchedulingInformation
}


export class ExecutionTask implements IExecutionTask {
    constructor(
        public oldAnalysisTaskId?: string,
        public analysisTaskId?: string,
        public label?: string,
        public type?: string,
        public startDate?: string,
        public endDate?: string, 
        public projectId?: string,
        public metricExecutions?: MetricExecutions[],
        public metricProviders?: string[],
        public scheduling?: SchedulingInformation
    ) {
    }
}

export interface ISchedulingInformation {
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

export interface IMetricExecutions {
    metricProviderId?: string,
    projectId?: string,
    lastExecutionDate?: string
}

export class MetricExecutions implements IMetricExecutions {
    constructor(
       public metricProviderId?: string,
       public projectId?: string,
       public lastExecutionDate?: string
    ) {
    }
}

export interface IMetricProvider {
    metricProviderId?: string,
    label?: string,
    kind?: string,
    description?: string,
    dependOf?: IMetricProvider[]
}

export class MetricProvider implements IMetricProvider {
    constructor(
        public metricProviderId?: string,
        public label?: string,
        public kind?: string,
        public description?: string,
        public dependOf?: IMetricProvider[]
    ){
    }
}