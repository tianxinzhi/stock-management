import { element, by, ElementFinder } from 'protractor';

export class StockItemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-stock-item div table .btn-danger'));
  title = element.all(by.css('jhi-stock-item div h2#page-heading span')).first();
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

export class StockItemUpdatePage {
  pageTitle = element(by.id('jhi-stock-item-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  itemNumberInput = element(by.id('field_itemNumber'));
  itemDescriptionInput = element(by.id('field_itemDescription'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setItemNumberInput(itemNumber: string): Promise<void> {
    await this.itemNumberInput.sendKeys(itemNumber);
  }

  async getItemNumberInput(): Promise<string> {
    return await this.itemNumberInput.getAttribute('value');
  }

  async setItemDescriptionInput(itemDescription: string): Promise<void> {
    await this.itemDescriptionInput.sendKeys(itemDescription);
  }

  async getItemDescriptionInput(): Promise<string> {
    return await this.itemDescriptionInput.getAttribute('value');
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

export class StockItemDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-stockItem-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-stockItem'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
