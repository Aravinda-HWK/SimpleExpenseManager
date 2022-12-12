package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;


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

    //This method is used to create tables for the database which is named as my registration number
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String Table_Create_Query_1="create table "+Table_Name+"("+Account_Number+" text primary key,"+Bank+" text,"+Account_Holder+" text,"+Balance+" real)";
        String Table_Create_Query_2="create table "+Table_Name_2+" ("+Transaction_Id+" integer primary key autoincrement,"+Date+" text,"+Account_Number+" text,"+Type+" text,"+Amount+" real)";
        sqLiteDatabase.execSQL(Table_Create_Query_1);
        sqLiteDatabase.execSQL(Table_Create_Query_2);

    }

    //This method is used to upgrade purposes in database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE_QUERY_1="DROP TABLE IF EXISTS "+Table_Name;
        String DROP_TABLE_QUERY_2="DROP TABLE IF EXISTS "+Table_Name_2;
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY_1);
        sqLiteDatabase.execSQL(DROP_TABLE_QUERY_2);
        onCreate(sqLiteDatabase);
    }
}
