package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class DatabaseHandler extends SQLiteOpenHelper {


    private static final int VERSION=1;
    private static final String Database_Name="200045U";
    private static final String Table_Name="Account";
    private static final String Account_Number="Account_No";
    private static final String Bank="Bank";
    private static final String Account_Holder="Account_Holder";
    private static final String Balance="Balance";
    private static final String Table_Name_2="Transaction_Table";
    private static final String Transaction_Id="Transaction_Id";
    private static final String Date="Date";
    private static final String Type="Type";
    private static final String Amount="Amount";

    public DatabaseHandler(@Nullable Context context) {
        super(context, Database_Name, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String Table_Create_Query_1="create table Account (Account_No text primary key,Bank text,Account_Holder text,Balance real)";
        String Table_Create_Query_2="create table Transaction_Table (Transaction_Id integer primary key autoincrement,Date text,Account_No text,Type text,Amount real)";
        sqLiteDatabase.execSQL(Table_Create_Query_1);
        sqLiteDatabase.execSQL(Table_Create_Query_2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE_QUERY_1="DROP TABLE IF EXISTS Account";
        String DROP_TABLE_QUERY_2="DROP TABLE IF EXISTS Transaction_Table";
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY_1);
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY_2);
        onCreate(sqLiteDatabase);
    }
}
