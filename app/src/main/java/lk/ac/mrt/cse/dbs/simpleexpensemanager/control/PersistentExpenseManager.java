package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;
import android.support.annotation.Nullable;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

public class PersistentExpenseManager extends ExpenseManager{
    DatabaseHandler accountDatabase;
    public PersistentExpenseManager(@Nullable Context context) throws ExpenseManagerException {
        accountDatabase =new DatabaseHandler(context);
        setup();
    }

    @Override
    public void setup() throws ExpenseManagerException {
        AccountDAO persistMemoryAccountDAO=new PersistMemoryAccountDAO(accountDatabase);
        setAccountsDAO(persistMemoryAccountDAO);

        TransactionDAO persistMemoryTransactionDAO=new PersistMemoryTransactionDAO(accountDatabase);
        setTransactionsDAO(persistMemoryTransactionDAO);

       /* Account dummyAcct1 = new Account("12345A", "Yoda Bank", "Anakin Skywalker", 10000.0);
        Account dummyAcct2 = new Account("78945Z", "Clone BC", "Obi-Wan Kenobi", 80000.0);
        //PersistentExpenseManager pe=new PersistentExpenseManager();
        persistMemoryAccountDAO.addAccount(dummyAcct1);
        persistMemoryAccountDAO.addAccount(dummyAcct2);*/
    }
}
