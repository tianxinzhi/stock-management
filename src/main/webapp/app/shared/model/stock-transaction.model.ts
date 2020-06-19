import { IStockItem } from 'app/shared/model/stock-item.model';
import { Subinventory } from 'app/shared/model/enumerations/subinventory.model';
import { TransactionType } from 'app/shared/model/enumerations/transaction-type.model';

export interface IStockTransaction {
  id?: number;
  subinventoryFrom?: Subinventory;
  subinventoryTo?: Subinventory;
  transactionType?: TransactionType;
  transactionQuantity?: number;
  reference?: string;
  stockItem?: IStockItem;
}

export class StockTransaction implements IStockTransaction {
  constructor(
    public id?: number,
    public subinventoryFrom?: Subinventory,
    public subinventoryTo?: Subinventory,
    public transactionType?: TransactionType,
    public transactionQuantity?: number,
    public reference?: string,
    public stockItem?: IStockItem
  ) {}
}
