
export interface ITokenAuthorities {
    label?: string,
    accessToken?: string,
    monitoringAuthorities?: boolean
}

export class TokenAuthorities implements ITokenAuthorities{
    constructor(
        public label?: string,
        public accessToken?: string,
        public monitoringAuthorities?: boolean
    ) {
    }
}