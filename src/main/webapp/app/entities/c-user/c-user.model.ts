import { User } from '../../shared';
import { Organisation } from '../organisation';
export class C_user {
    constructor(
        public id?: number,
        public user?: User,
        public organisation?: Organisation,
    ) {
    }
}
