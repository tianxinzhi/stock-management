import { IAttrValue } from 'app/shared/model/attr-value.model';
import { ISpec } from 'app/shared/model/spec.model';

export interface IAttr {
  id?: number;
  attrName?: string;
  attrDesc?: string;
  attrValues?: IAttrValue[];
  specs?: ISpec[];
}

export class Attr implements IAttr {
  constructor(
    public id?: number,
    public attrName?: string,
    public attrDesc?: string,
    public attrValues?: IAttrValue[],
    public specs?: ISpec[]
  ) {}
}
