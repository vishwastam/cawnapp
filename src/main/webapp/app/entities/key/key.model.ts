import { Application } from '../application';
export class Key {
    constructor(
        public id?: number,
        public name?: string,
        public value?: string,
        public description?: string,
        public application?: Application,
    ) {
    }
}
