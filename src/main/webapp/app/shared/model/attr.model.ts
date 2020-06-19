import { IAttrValue } from 'app/shared/model/attr-value.model';

export interface IAttr {
  id?: number;
  attrName?: string;
  attrDesc?: string;
  attrValueId?: IAttrValue;
}

export class Attr implements IAttr {
  constructor(public id?: number, public attrName?: string, public attrDesc?: string, public attrValueId?: IAttrValue) {}
}
