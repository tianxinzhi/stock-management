import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  StockTransactionComponentsPage,
  /* StockTransactionDeleteDialog, */
  StockTransactionUpdatePage
} from './stock-transaction.page-object';

const expect = chai.expect;

describe('StockTransaction e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let stockTransactionComponentsPage: StockTransactionComponentsPage;
  let stockTransactionUpdatePage: StockTransactionUpdatePage;
  /* let stockTransactionDeleteDialog: StockTransactionDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.loginWithOAuth('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load StockTransactions', async () => {
    await navBarPage.goToEntity('stock-transaction');
    stockTransactionComponentsPage = new StockTransactionComponentsPage();
    await browser.wait(ec.visibilityOf(stockTransactionComponentsPage.title), 5000);
    expect(await stockTransactionComponentsPage.getTitle()).to.eq('stockManagementApp.stockTransaction.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(stockTransactionComponentsPage.entities), ec.visibilityOf(stockTransactionComponentsPage.noResult)),
      1000
    );
  });

  it('should load create StockTransaction page', async () => {
    await stockTransactionComponentsPage.clickOnCreateButton();
    stockTransactionUpdatePage = new StockTransactionUpdatePage();
    expect(await stockTransactionUpdatePage.getPageTitle()).to.eq('stockManagementApp.stockTransaction.home.createOrEditLabel');
    await stockTransactionUpdatePage.cancel();
  });

  /* it('should create and save StockTransactions', async () => {
        const nbButtonsBeforeCreate = await stockTransactionComponentsPage.countDeleteButtons();

        await stockTransactionComponentsPage.clickOnCreateButton();

        await promise.all([
            stockTransactionUpdatePage.subinventoryFromSelectLastOption(),
            stockTransactionUpdatePage.subinventoryToSelectLastOption(),
            stockTransactionUpdatePage.transactionTypeSelectLastOption(),
            stockTransactionUpdatePage.setTransactionQuantityInput('5'),
            stockTransactionUpdatePage.setReferenceInput('reference'),
            stockTransactionUpdatePage.stockItemSelectLastOption(),
        ]);

        expect(await stockTransactionUpdatePage.getTransactionQuantityInput()).to.eq('5', 'Expected transactionQuantity value to be equals to 5');
        expect(await stockTransactionUpdatePage.getReferenceInput()).to.eq('reference', 'Expected Reference value to be equals to reference');

        await stockTransactionUpdatePage.save();
        expect(await stockTransactionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await stockTransactionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last StockTransaction', async () => {
        const nbButtonsBeforeDelete = await stockTransactionComponentsPage.countDeleteButtons();
        await stockTransactionComponentsPage.clickOnLastDeleteButton();

        stockTransactionDeleteDialog = new StockTransactionDeleteDialog();
        expect(await stockTransactionDeleteDialog.getDialogTitle())
            .to.eq('stockManagementApp.stockTransaction.delete.question');
        await stockTransactionDeleteDialog.clickOnConfirmButton();

        expect(await stockTransactionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
