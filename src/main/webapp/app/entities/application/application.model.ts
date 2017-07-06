import { Key } from '../key';
import { Stage } from '../stage';
import { Organisation } from '../organisation';
export class Application {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public key?: Key,
        public stage?: Stage,
        public organisation?: Organisation,
    ) {
    }
}
