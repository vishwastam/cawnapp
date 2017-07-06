import { Organisation } from '../organisation';
export class Application {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public organisation?: Organisation,
    ) {
    }
}
