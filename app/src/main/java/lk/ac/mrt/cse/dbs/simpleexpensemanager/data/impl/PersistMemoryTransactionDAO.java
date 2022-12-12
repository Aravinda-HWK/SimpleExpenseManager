package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.DatabaseHandler;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class PersistMemoryTransactionDAO implements TransactionDAO {
    private DatabaseHandler databaseHandler;

    public PersistMemoryTransactionDAO(DatabaseHandler database) {
        this.databaseHandler = database;
    }
    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        SQLiteDatabase database=databaseHandler.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Date",date.toString());
        contentValues.put("Account_No",accountNo);
        contentValues.put("Type",expenseType.toString());
        contentValues.put("Amount",amount);
        database.insert("Transaction_Table",null,contentValues);
    }

    @Override
    public List<Transaction> getAllTransactionLogs() throws ParseException {
        ArrayList<Transaction> transactionArrayList=new ArrayList<>();
        SQLiteDatabase database=databaseHandler.getReadableDatabase();
        Cursor cursor=database.rawQuery("select * from Transaction_Table",null);
        if (cursor.moveToFirst()){
            do{
                ExpenseType type;
                if (cursor.getString(3).equals("EXPENSE")){
                    type=ExpenseType.EXPENSE;
                }
                else{
                    type=ExpenseType.INCOME;
                }
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                Date date=new Date();
                date=simpleDateFormat.parse(cursor.getString(1));
                transactionArrayList.add(new Transaction(date,cursor.getString(2),type,cursor.getDouble(4)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return transactionArrayList;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) throws ParseException {

        ArrayList<Transaction> transactionArrayList=new ArrayList<>();
        transactionArrayList= (ArrayList<Transaction>) getAllTransactionLogs();
        SQLiteDatabase database=databaseHandler.getReadableDatabase();
        if (transactionArrayList.size()>limit){
            return transactionArrayList.subList(transactionArrayList.size()-limit,transactionArrayList.size());
        }else{
            return transactionArrayList;
        }
    }
}
