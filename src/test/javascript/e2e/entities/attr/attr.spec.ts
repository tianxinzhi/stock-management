import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AttrComponentsPage, AttrDeleteDialog, AttrUpdatePage } from './attr.page-object';

const expect = chai.expect;

describe('Attr e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let attrComponentsPage: AttrComponentsPage;
  let attrUpdatePage: AttrUpdatePage;
  let attrDeleteDialog: AttrDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Attrs', async () => {
    await navBarPage.goToEntity('attr');
    attrComponentsPage = new AttrComponentsPage();
    await browser.wait(ec.visibilityOf(attrComponentsPage.title), 5000);
    expect(await attrComponentsPage.getTitle()).to.eq('stockManagementApp.attr.home.title');
    await browser.wait(ec.or(ec.visibilityOf(attrComponentsPage.entities), ec.visibilityOf(attrComponentsPage.noResult)), 1000);
  });

  it('should load create Attr page', async () => {
    await attrComponentsPage.clickOnCreateButton();
    attrUpdatePage = new AttrUpdatePage();
    expect(await attrUpdatePage.getPageTitle()).to.eq('stockManagementApp.attr.home.createOrEditLabel');
    await attrUpdatePage.cancel();
  });

  it('should create and save Attrs', async () => {
    const nbButtonsBeforeCreate = await attrComponentsPage.countDeleteButtons();

    await attrComponentsPage.clickOnCreateButton();

    await promise.all([
      attrUpdatePage.setAttrNameInput('attrName'),
      attrUpdatePage.setAttrDescInput('attrDesc')
      // attrUpdatePage.attrValuesSelectLastOption(),
    ]);

    expect(await attrUpdatePage.getAttrNameInput()).to.eq('attrName', 'Expected AttrName value to be equals to attrName');
    expect(await attrUpdatePage.getAttrDescInput()).to.eq('attrDesc', 'Expected AttrDesc value to be equals to attrDesc');

    await attrUpdatePage.save();
    expect(await attrUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await attrComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Attr', async () => {
    const nbButtonsBeforeDelete = await attrComponentsPage.countDeleteButtons();
    await attrComponentsPage.clickOnLastDeleteButton();

    attrDeleteDialog = new AttrDeleteDialog();
    expect(await attrDeleteDialog.getDialogTitle()).to.eq('stockManagementApp.attr.delete.question');
    await attrDeleteDialog.clickOnConfirmButton();

    expect(await attrComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
