export interface IStockItem {
  id?: number;
  itemNumber?: string;
  itemDescription?: string;
}

export class StockItem implements IStockItem {
  constructor(public id?: number, public itemNumber?: string, public itemDescription?: string) {}
}
