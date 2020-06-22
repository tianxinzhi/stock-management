import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SpecComponentsPage, SpecDeleteDialog, SpecUpdatePage } from './spec.page-object';

const expect = chai.expect;

describe('Spec e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let specComponentsPage: SpecComponentsPage;
  let specUpdatePage: SpecUpdatePage;
  let specDeleteDialog: SpecDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Specs', async () => {
    await navBarPage.goToEntity('spec');
    specComponentsPage = new SpecComponentsPage();
    await browser.wait(ec.visibilityOf(specComponentsPage.title), 5000);
    expect(await specComponentsPage.getTitle()).to.eq('stockManagementApp.spec.home.title');
    await browser.wait(ec.or(ec.visibilityOf(specComponentsPage.entities), ec.visibilityOf(specComponentsPage.noResult)), 1000);
  });

  it('should load create Spec page', async () => {
    await specComponentsPage.clickOnCreateButton();
    specUpdatePage = new SpecUpdatePage();
    expect(await specUpdatePage.getPageTitle()).to.eq('stockManagementApp.spec.home.createOrEditLabel');
    await specUpdatePage.cancel();
  });

  it('should create and save Specs', async () => {
    const nbButtonsBeforeCreate = await specComponentsPage.countDeleteButtons();

    await specComponentsPage.clickOnCreateButton();

    await promise.all([
      specUpdatePage.setSpecNameInput('specName'),
      specUpdatePage.setSpecDescInput('specDesc'),
      specUpdatePage.setVerIdInput('verId')
      // specUpdatePage.attrsSelectLastOption(),
    ]);

    expect(await specUpdatePage.getSpecNameInput()).to.eq('specName', 'Expected SpecName value to be equals to specName');
    expect(await specUpdatePage.getSpecDescInput()).to.eq('specDesc', 'Expected SpecDesc value to be equals to specDesc');
    expect(await specUpdatePage.getVerIdInput()).to.eq('verId', 'Expected VerId value to be equals to verId');

    await specUpdatePage.save();
    expect(await specUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await specComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Spec', async () => {
    const nbButtonsBeforeDelete = await specComponentsPage.countDeleteButtons();
    await specComponentsPage.clickOnLastDeleteButton();

    specDeleteDialog = new SpecDeleteDialog();
    expect(await specDeleteDialog.getDialogTitle()).to.eq('stockManagementApp.spec.delete.question');
    await specDeleteDialog.clickOnConfirmButton();

    expect(await specComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
