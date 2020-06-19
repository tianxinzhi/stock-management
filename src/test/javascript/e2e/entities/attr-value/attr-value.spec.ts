import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AttrValueComponentsPage, AttrValueDeleteDialog, AttrValueUpdatePage } from './attr-value.page-object';

const expect = chai.expect;

describe('AttrValue e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let attrValueComponentsPage: AttrValueComponentsPage;
  let attrValueUpdatePage: AttrValueUpdatePage;
  let attrValueDeleteDialog: AttrValueDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load AttrValues', async () => {
    await navBarPage.goToEntity('attr-value');
    attrValueComponentsPage = new AttrValueComponentsPage();
    await browser.wait(ec.visibilityOf(attrValueComponentsPage.title), 5000);
    expect(await attrValueComponentsPage.getTitle()).to.eq('stockManagementApp.attrValue.home.title');
    await browser.wait(ec.or(ec.visibilityOf(attrValueComponentsPage.entities), ec.visibilityOf(attrValueComponentsPage.noResult)), 1000);
  });

  it('should load create AttrValue page', async () => {
    await attrValueComponentsPage.clickOnCreateButton();
    attrValueUpdatePage = new AttrValueUpdatePage();
    expect(await attrValueUpdatePage.getPageTitle()).to.eq('stockManagementApp.attrValue.home.createOrEditLabel');
    await attrValueUpdatePage.cancel();
  });

  it('should create and save AttrValues', async () => {
    const nbButtonsBeforeCreate = await attrValueComponentsPage.countDeleteButtons();

    await attrValueComponentsPage.clickOnCreateButton();

    await promise.all([
      attrValueUpdatePage.setAttrValueInput('attrValue'),
      attrValueUpdatePage.setUnitOfMeasureInput('unitOfMeasure'),
      attrValueUpdatePage.setValueFromInput('valueFrom'),
      attrValueUpdatePage.setValueToInput('valueTo')
    ]);

    expect(await attrValueUpdatePage.getAttrValueInput()).to.eq('attrValue', 'Expected AttrValue value to be equals to attrValue');
    expect(await attrValueUpdatePage.getUnitOfMeasureInput()).to.eq(
      'unitOfMeasure',
      'Expected UnitOfMeasure value to be equals to unitOfMeasure'
    );
    expect(await attrValueUpdatePage.getValueFromInput()).to.eq('valueFrom', 'Expected ValueFrom value to be equals to valueFrom');
    expect(await attrValueUpdatePage.getValueToInput()).to.eq('valueTo', 'Expected ValueTo value to be equals to valueTo');

    await attrValueUpdatePage.save();
    expect(await attrValueUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await attrValueComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last AttrValue', async () => {
    const nbButtonsBeforeDelete = await attrValueComponentsPage.countDeleteButtons();
    await attrValueComponentsPage.clickOnLastDeleteButton();

    attrValueDeleteDialog = new AttrValueDeleteDialog();
    expect(await attrValueDeleteDialog.getDialogTitle()).to.eq('stockManagementApp.attrValue.delete.question');
    await attrValueDeleteDialog.clickOnConfirmButton();

    expect(await attrValueComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
