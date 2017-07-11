import { Stage } from '../stage';
export class Key {
    constructor(
        public id?: number,
        public name?: string,
        public value?: string,
        public description?: string,
        public is_secure?: boolean,
        public stage?: Stage,
    ) {
        this.is_secure = false;
    }
}
