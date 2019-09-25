export class CommunicationChannels {
    constructor(
        public type?: string,
        public name?: string,
        public url?: string,
        public executionFrequency?: string,
        public loginURL?: string,
        public username?: string,
        public usernameFieldName?: string,
        public password?: string,
        public passwordFieldName?: string,
        public loginOption?: string
    ) {
    }
}