import { IAttr } from 'app/shared/model/attr.model';

export interface ISpec {
  id?: number;
  specName?: string;
  specDesc?: string;
  verId?: string;
  attrId?: IAttr;
}

export class Spec implements ISpec {
  constructor(public id?: number, public specName?: string, public specDesc?: string, public verId?: string, public attrId?: IAttr) {}
}
