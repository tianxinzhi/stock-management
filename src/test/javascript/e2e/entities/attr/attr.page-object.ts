import { element, by, ElementFinder } from 'protractor';

export class AttrComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-attr div table .btn-danger'));
  title = element.all(by.css('jhi-attr div h2#page-heading span')).first();
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

export class AttrUpdatePage {
  pageTitle = element(by.id('jhi-attr-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  attrNameInput = element(by.id('field_attrName'));
  attrDescInput = element(by.id('field_attrDesc'));

  attrValueIdSelect = element(by.id('field_attrValueId'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setAttrNameInput(attrName: string): Promise<void> {
    await this.attrNameInput.sendKeys(attrName);
  }

  async getAttrNameInput(): Promise<string> {
    return await this.attrNameInput.getAttribute('value');
  }

  async setAttrDescInput(attrDesc: string): Promise<void> {
    await this.attrDescInput.sendKeys(attrDesc);
  }

  async getAttrDescInput(): Promise<string> {
    return await this.attrDescInput.getAttribute('value');
  }

  async attrValueIdSelectLastOption(): Promise<void> {
    await this.attrValueIdSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async attrValueIdSelectOption(option: string): Promise<void> {
    await this.attrValueIdSelect.sendKeys(option);
  }

  getAttrValueIdSelect(): ElementFinder {
    return this.attrValueIdSelect;
  }

  async getAttrValueIdSelectedOption(): Promise<string> {
    return await this.attrValueIdSelect.element(by.css('option:checked')).getText();
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

export class AttrDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-attr-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-attr'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
