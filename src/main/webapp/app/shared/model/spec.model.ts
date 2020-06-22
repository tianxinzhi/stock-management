import { IAttr } from 'app/shared/model/attr.model';
import { IType } from 'app/shared/model/type.model';

export interface ISpec {
  id?: number;
  specName?: string;
  specDesc?: string;
  verId?: string;
  attrs?: IAttr[];
  types?: IType[];
}

export class Spec implements ISpec {
  constructor(
    public id?: number,
    public specName?: string,
    public specDesc?: string,
    public verId?: string,
    public attrs?: IAttr[],
    public types?: IType[]
  ) {}
}
