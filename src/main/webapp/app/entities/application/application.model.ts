import { Stage } from '../stage';
import { Organisation } from '../organisation';
export class Application {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public stage?: Stage,
        public organisation?: Organisation,
    ) {
    }
}
