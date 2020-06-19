import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  StockBalanceComponentsPage,
  /* StockBalanceDeleteDialog, */
  StockBalanceUpdatePage
} from './stock-balance.page-object';

const expect = chai.expect;

describe('StockBalance e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let stockBalanceComponentsPage: StockBalanceComponentsPage;
  let stockBalanceUpdatePage: StockBalanceUpdatePage;
  /* let stockBalanceDeleteDialog: StockBalanceDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load StockBalances', async () => {
    await navBarPage.goToEntity('stock-balance');
    stockBalanceComponentsPage = new StockBalanceComponentsPage();
    await browser.wait(ec.visibilityOf(stockBalanceComponentsPage.title), 5000);
    expect(await stockBalanceComponentsPage.getTitle()).to.eq('stockManagementApp.stockBalance.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(stockBalanceComponentsPage.entities), ec.visibilityOf(stockBalanceComponentsPage.noResult)),
      1000
    );
  });

  it('should load create StockBalance page', async () => {
    await stockBalanceComponentsPage.clickOnCreateButton();
    stockBalanceUpdatePage = new StockBalanceUpdatePage();
    expect(await stockBalanceUpdatePage.getPageTitle()).to.eq('stockManagementApp.stockBalance.home.createOrEditLabel');
    await stockBalanceUpdatePage.cancel();
  });

  /* it('should create and save StockBalances', async () => {
        const nbButtonsBeforeCreate = await stockBalanceComponentsPage.countDeleteButtons();

        await stockBalanceComponentsPage.clickOnCreateButton();

        await promise.all([
            stockBalanceUpdatePage.subInventorySelectLastOption(),
            stockBalanceUpdatePage.setQuantityOnhandInput('5'),
            stockBalanceUpdatePage.setQuantityReservedInput('5'),
            stockBalanceUpdatePage.stockItemSelectLastOption(),
        ]);

        expect(await stockBalanceUpdatePage.getQuantityOnhandInput()).to.eq('5', 'Expected quantityOnhand value to be equals to 5');
        expect(await stockBalanceUpdatePage.getQuantityReservedInput()).to.eq('5', 'Expected quantityReserved value to be equals to 5');

        await stockBalanceUpdatePage.save();
        expect(await stockBalanceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await stockBalanceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last StockBalance', async () => {
        const nbButtonsBeforeDelete = await stockBalanceComponentsPage.countDeleteButtons();
        await stockBalanceComponentsPage.clickOnLastDeleteButton();

        stockBalanceDeleteDialog = new StockBalanceDeleteDialog();
        expect(await stockBalanceDeleteDialog.getDialogTitle())
            .to.eq('stockManagementApp.stockBalance.delete.question');
        await stockBalanceDeleteDialog.clickOnConfirmButton();

        expect(await stockBalanceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
