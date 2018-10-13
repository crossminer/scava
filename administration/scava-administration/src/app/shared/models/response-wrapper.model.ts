import { HttpHeaders } from "@angular/common/http";

export class ResponseWrapper {
    constructor(
        public headers: HttpHeaders,
        public json: any,
        public status: number
    ) {
    }
}