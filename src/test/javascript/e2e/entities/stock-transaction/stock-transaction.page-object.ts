import { element, by, ElementFinder } from 'protractor';

export class StockTransactionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-stock-transaction div table .btn-danger'));
  title = element.all(by.css('jhi-stock-transaction div h2#page-heading span')).first();
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

export class StockTransactionUpdatePage {
  pageTitle = element(by.id('jhi-stock-transaction-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  subinventoryFromSelect = element(by.id('field_subinventoryFrom'));
  subinventoryToSelect = element(by.id('field_subinventoryTo'));
  transactionTypeSelect = element(by.id('field_transactionType'));
  transactionQuantityInput = element(by.id('field_transactionQuantity'));
  referenceInput = element(by.id('field_reference'));

  stockItemSelect = element(by.id('field_stockItem'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setSubinventoryFromSelect(subinventoryFrom: string): Promise<void> {
    await this.subinventoryFromSelect.sendKeys(subinventoryFrom);
  }

  async getSubinventoryFromSelect(): Promise<string> {
    return await this.subinventoryFromSelect.element(by.css('option:checked')).getText();
  }

  async subinventoryFromSelectLastOption(): Promise<void> {
    await this.subinventoryFromSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setSubinventoryToSelect(subinventoryTo: string): Promise<void> {
    await this.subinventoryToSelect.sendKeys(subinventoryTo);
  }

  async getSubinventoryToSelect(): Promise<string> {
    return await this.subinventoryToSelect.element(by.css('option:checked')).getText();
  }

  async subinventoryToSelectLastOption(): Promise<void> {
    await this.subinventoryToSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setTransactionTypeSelect(transactionType: string): Promise<void> {
    await this.transactionTypeSelect.sendKeys(transactionType);
  }

  async getTransactionTypeSelect(): Promise<string> {
    return await this.transactionTypeSelect.element(by.css('option:checked')).getText();
  }

  async transactionTypeSelectLastOption(): Promise<void> {
    await this.transactionTypeSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setTransactionQuantityInput(transactionQuantity: string): Promise<void> {
    await this.transactionQuantityInput.sendKeys(transactionQuantity);
  }

  async getTransactionQuantityInput(): Promise<string> {
    return await this.transactionQuantityInput.getAttribute('value');
  }

  async setReferenceInput(reference: string): Promise<void> {
    await this.referenceInput.sendKeys(reference);
  }

  async getReferenceInput(): Promise<string> {
    return await this.referenceInput.getAttribute('value');
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

export class StockTransactionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-stockTransaction-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-stockTransaction'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
