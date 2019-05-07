export class Stacktraces {
    constructor(
        public projectId?: string,
        public projectName?: string,
        public clazz?: string,
        public stackTrace?: string,
        public workerIdentifier?: string,
        public executionErrorDate?: string,
        public analysisRangeErrorDate?: string,
    ) {
    }
}
