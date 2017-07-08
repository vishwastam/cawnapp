import { Key } from '../key';
import { Application } from '../application';
export class Stage {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public application?: Application,
    ) {
    }
}
