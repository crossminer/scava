export interface IMetricProviders {
    metricProviderId?: string,
    label?: string,
    kind?: string,
    description?: string,
    dependOf?: IMetricProviders[]
}

export class MetricProviders implements IMetricProviders {
    constructor(
        public metricProviderId?: string,
        public label?: string,
        public kind?: string,
        public description?: string,
        public dependOf?: IMetricProviders[]
    ){
    }
}
