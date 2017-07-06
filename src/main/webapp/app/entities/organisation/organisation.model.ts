
const enum Subs_Type {
    'FREE',
    'POC',
    'PREMIUM'

};
import { Application } from '../application';
export class Organisation {
    constructor(
        public id?: number,
        public name?: string,
        public subscriptiontype?: Subs_Type,
        public application?: Application,
    ) {
    }
}
