import { element, by, ElementFinder } from 'protractor';

export class AttrValueComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-attr-value div table .btn-danger'));
  title = element.all(by.css('jhi-attr-value div h2#page-heading span')).first();
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

export class AttrValueUpdatePage {
  pageTitle = element(by.id('jhi-attr-value-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  attrValueInput = element(by.id('field_attrValue'));
  unitOfMeasureInput = element(by.id('field_unitOfMeasure'));
  valueFromInput = element(by.id('field_valueFrom'));
  valueToInput = element(by.id('field_valueTo'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setAttrValueInput(attrValue: string): Promise<void> {
    await this.attrValueInput.sendKeys(attrValue);
  }

  async getAttrValueInput(): Promise<string> {
    return await this.attrValueInput.getAttribute('value');
  }

  async setUnitOfMeasureInput(unitOfMeasure: string): Promise<void> {
    await this.unitOfMeasureInput.sendKeys(unitOfMeasure);
  }

  async getUnitOfMeasureInput(): Promise<string> {
    return await this.unitOfMeasureInput.getAttribute('value');
  }

  async setValueFromInput(valueFrom: string): Promise<void> {
    await this.valueFromInput.sendKeys(valueFrom);
  }

  async getValueFromInput(): Promise<string> {
    return await this.valueFromInput.getAttribute('value');
  }

  async setValueToInput(valueTo: string): Promise<void> {
    await this.valueToInput.sendKeys(valueTo);
  }

  async getValueToInput(): Promise<string> {
    return await this.valueToInput.getAttribute('value');
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

export class AttrValueDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-attrValue-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-attrValue'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
