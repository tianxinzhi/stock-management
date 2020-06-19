import { element, by, ElementFinder } from 'protractor';

export class StockBalanceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-stock-balance div table .btn-danger'));
  title = element.all(by.css('jhi-stock-balance div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class StockBalanceUpdatePage {
  pageTitle = element(by.id('jhi-stock-balance-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  subInventorySelect = element(by.id('field_subInventory'));
  quantityOnhandInput = element(by.id('field_quantityOnhand'));
  quantityReservedInput = element(by.id('field_quantityReserved'));

  stockItemSelect = element(by.id('field_stockItem'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setSubInventorySelect(subInventory: string): Promise<void> {
    await this.subInventorySelect.sendKeys(subInventory);
  }

  async getSubInventorySelect(): Promise<string> {
    return await this.subInventorySelect.element(by.css('option:checked')).getText();
  }

  async subInventorySelectLastOption(): Promise<void> {
    await this.subInventorySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setQuantityOnhandInput(quantityOnhand: string): Promise<void> {
    await this.quantityOnhandInput.sendKeys(quantityOnhand);
  }

  async getQuantityOnhandInput(): Promise<string> {
    return await this.quantityOnhandInput.getAttribute('value');
  }

  async setQuantityReservedInput(quantityReserved: string): Promise<void> {
    await this.quantityReservedInput.sendKeys(quantityReserved);
  }

  async getQuantityReservedInput(): Promise<string> {
    return await this.quantityReservedInput.getAttribute('value');
  }

  async stockItemSelectLastOption(): Promise<void> {
    await this.stockItemSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async stockItemSelectOption(option: string): Promise<void> {
    await this.stockItemSelect.sendKeys(option);
  }

  getStockItemSelect(): ElementFinder {
    return this.stockItemSelect;
  }

  async getStockItemSelectedOption(): Promise<string> {
    return await this.stockItemSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class StockBalanceDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-stockBalance-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-stockBalance'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
