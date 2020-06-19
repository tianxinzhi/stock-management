import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { StockItemComponentsPage, StockItemDeleteDialog, StockItemUpdatePage } from './stock-item.page-object';

const expect = chai.expect;

describe('StockItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let stockItemComponentsPage: StockItemComponentsPage;
  let stockItemUpdatePage: StockItemUpdatePage;
  let stockItemDeleteDialog: StockItemDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load StockItems', async () => {
    await navBarPage.goToEntity('stock-item');
    stockItemComponentsPage = new StockItemComponentsPage();
    await browser.wait(ec.visibilityOf(stockItemComponentsPage.title), 5000);
    expect(await stockItemComponentsPage.getTitle()).to.eq('stockManagementApp.stockItem.home.title');
    await browser.wait(ec.or(ec.visibilityOf(stockItemComponentsPage.entities), ec.visibilityOf(stockItemComponentsPage.noResult)), 1000);
  });

  it('should load create StockItem page', async () => {
    await stockItemComponentsPage.clickOnCreateButton();
    stockItemUpdatePage = new StockItemUpdatePage();
    expect(await stockItemUpdatePage.getPageTitle()).to.eq('stockManagementApp.stockItem.home.createOrEditLabel');
    await stockItemUpdatePage.cancel();
  });

  it('should create and save StockItems', async () => {
    const nbButtonsBeforeCreate = await stockItemComponentsPage.countDeleteButtons();

    await stockItemComponentsPage.clickOnCreateButton();

    await promise.all([
      stockItemUpdatePage.setItemNumberInput('itemNumber'),
      stockItemUpdatePage.setItemDescriptionInput('itemDescription')
    ]);

    expect(await stockItemUpdatePage.getItemNumberInput()).to.eq('itemNumber', 'Expected ItemNumber value to be equals to itemNumber');
    expect(await stockItemUpdatePage.getItemDescriptionInput()).to.eq(
      'itemDescription',
      'Expected ItemDescription value to be equals to itemDescription'
    );

    await stockItemUpdatePage.save();
    expect(await stockItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await stockItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last StockItem', async () => {
    const nbButtonsBeforeDelete = await stockItemComponentsPage.countDeleteButtons();
    await stockItemComponentsPage.clickOnLastDeleteButton();

    stockItemDeleteDialog = new StockItemDeleteDialog();
    expect(await stockItemDeleteDialog.getDialogTitle()).to.eq('stockManagementApp.stockItem.delete.question');
    await stockItemDeleteDialog.clickOnConfirmButton();

    expect(await stockItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
