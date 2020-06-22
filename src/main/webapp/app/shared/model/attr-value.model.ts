import { IAttr } from 'app/shared/model/attr.model';

export interface IAttrValue {
  id?: number;
  attrValue?: string;
  unitOfMeasure?: string;
  valueFrom?: string;
  valueTo?: string;
  attrs?: IAttr[];
}

export class AttrValue implements IAttrValue {
  constructor(
    public id?: number,
    public attrValue?: string,
    public unitOfMeasure?: string,
    public valueFrom?: string,
    public valueTo?: string,
    public attrs?: IAttr[]
  ) {}
}
