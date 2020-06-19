import { element, by, ElementFinder } from 'protractor';

export class SpecComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-spec div table .btn-danger'));
  title = element.all(by.css('jhi-spec div h2#page-heading span')).first();
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

export class SpecUpdatePage {
  pageTitle = element(by.id('jhi-spec-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  specNameInput = element(by.id('field_specName'));
  specDescInput = element(by.id('field_specDesc'));
  verIdInput = element(by.id('field_verId'));

  attrSelect = element(by.id('field_attr'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setSpecNameInput(specName: string): Promise<void> {
    await this.specNameInput.sendKeys(specName);
  }

  async getSpecNameInput(): Promise<string> {
    return await this.specNameInput.getAttribute('value');
  }

  async setSpecDescInput(specDesc: string): Promise<void> {
    await this.specDescInput.sendKeys(specDesc);
  }

  async getSpecDescInput(): Promise<string> {
    return await this.specDescInput.getAttribute('value');
  }

  async setVerIdInput(verId: string): Promise<void> {
    await this.verIdInput.sendKeys(verId);
  }

  async getVerIdInput(): Promise<string> {
    return await this.verIdInput.getAttribute('value');
  }

  async attrSelectLastOption(): Promise<void> {
    await this.attrSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async attrSelectOption(option: string): Promise<void> {
    await this.attrSelect.sendKeys(option);
  }

  getAttrSelect(): ElementFinder {
    return this.attrSelect;
  }

  async getAttrSelectedOption(): Promise<string> {
    return await this.attrSelect.element(by.css('option:checked')).getText();
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

export class SpecDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-spec-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-spec'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
