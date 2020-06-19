import { IStockItem } from 'app/shared/model/stock-item.model';
import { Subinventory } from 'app/shared/model/enumerations/subinventory.model';

export interface IStockBalance {
  id?: number;
  subInventory?: Subinventory;
  quantityOnhand?: number;
  quantityReserved?: number;
  stockItem?: IStockItem;
}

export class StockBalance implements IStockBalance {
  constructor(
    public id?: number,
    public subInventory?: Subinventory,
    public quantityOnhand?: number,
    public quantityReserved?: number,
    public stockItem?: IStockItem
  ) {}
}
