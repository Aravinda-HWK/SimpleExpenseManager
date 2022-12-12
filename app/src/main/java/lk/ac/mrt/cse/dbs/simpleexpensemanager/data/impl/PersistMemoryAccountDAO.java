package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.DatabaseHandler;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class PersistMemoryAccountDAO<contentValues> implements AccountDAO {


    DatabaseHandler accountDatabase;
    public PersistMemoryAccountDAO(DatabaseHandler accoutDatabase) {
        this.accountDatabase =accoutDatabase;
    }
    @Override
    public List<String> getAccountNumbersList() {
        ArrayList<String> arrayList=new ArrayList<>();SQLiteDatabase database= accountDatabase.getReadableDatabase();

        Cursor result=database.rawQuery("select Account_No from Account",null);
        result.moveToFirst();

        while (result.isAfterLast()==false){
            arrayList.add(result.getString(result.getColumnIndex("Account_No")));
            result.moveToNext();
        }
        for (int i=0;i<arrayList.size();i++){
            System.out.println(arrayList.get(i));
        }
        return arrayList;
    }

    @Override
    public List<Account> getAccountsList() {
        List<Account> accountList=new ArrayList<>();
        SQLiteDatabase database= accountDatabase.getReadableDatabase();
        Cursor result=database.rawQuery("select Account_No from Account",null);
        result.moveToFirst();

        while (result.isAfterLast()==false){
            Account account= new Account(result.getString(result.getColumnIndex("Account_No")),
                    result.getString(result.getColumnIndex("Bank")),
                    result.getString(result.getColumnIndex("Account_Holder")),
                    result.getDouble(result.getColumnIndex("Balance")));
            accountList.add(account);
        }
        return accountList;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase database= accountDatabase.getReadableDatabase();
        Cursor result=database.rawQuery("select * from Account",null);
        Account account=null;
        while (result.isAfterLast()==false){
            account=new Account(result.getString(result.getColumnIndex("Account_No")),
                    result.getString(result.getColumnIndex("Bank")),
                    result.getString(result.getColumnIndex("Account_Holder")),
                    result.getDouble(result.getColumnIndex("Balance")));
            result.moveToNext();
        }
        return account;
    }

    @Override
    public void addAccount(Account account) {
        SQLiteDatabase database= accountDatabase.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Account_No",account.getAccountNo());
        contentValues.put("Bank",account.getBankName());
        contentValues.put("Account_Holder",account.getAccountHolderName());
        contentValues.put("Balance",account.getBalance());
        database.insert("Account",null,contentValues);

    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        SQLiteDatabase database= accountDatabase.getWritableDatabase();
        database.delete("Account","Account_No=?",new String[]{accountNo});
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        SQLiteDatabase database= accountDatabase.getWritableDatabase();
        Cursor result=database.rawQuery("select * from Account",null);
        ContentValues contentValues=new ContentValues();
        if (result.moveToFirst()){
            do{
                if (result.getString(0).equals(accountNo)){
                    break;
                }
            }while (result.moveToNext());
        }else{
            String error="There is no account in the Account table";
            throw new InvalidAccountException(error);
        }

        double currentAccountbalance=result.getDouble(3);
        double finalAccountBalance=0;

        if (ExpenseType.EXPENSE==expenseType){
            finalAccountBalance=currentAccountbalance-amount;
        }
        else if (ExpenseType.INCOME==expenseType){
            finalAccountBalance=currentAccountbalance+amount;
        }

        if (finalAccountBalance<0){
            String Query = "DELETE FROM Transaction_Table WHERE Transaction_Id = (SELECT MAX(Transaction_Id) FROM Transaction_Table);";
            database.execSQL(Query);
            String error="Account balance is not sufficient";
            throw new InvalidAccountException(error);
        }else{
            contentValues.put("Balance",finalAccountBalance);
            database.update("Account",contentValues,"Account_No=?",new String[]{accountNo});
        }

    }
}
