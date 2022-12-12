package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;
import android.support.annotation.Nullable;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistMemoryAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.PersistMemoryTransactionDAO;


public class PersistentExpenseManager extends ExpenseManager{
    DatabaseHandler accountDatabase;
    public PersistentExpenseManager(@Nullable Context context) throws ExpenseManagerException {
        accountDatabase =new DatabaseHandler(context);
        setup();
    }

    //Create PersistMemoryAccountDAO class and PersistMemoryTransaction class
    @Override
    public void setup() {
        AccountDAO persistMemoryAccountDAO=new PersistMemoryAccountDAO(accountDatabase);
        setAccountsDAO(persistMemoryAccountDAO);

        TransactionDAO persistMemoryTransactionDAO=new PersistMemoryTransactionDAO(accountDatabase);
        setTransactionsDAO(persistMemoryTransactionDAO);

    }
}
