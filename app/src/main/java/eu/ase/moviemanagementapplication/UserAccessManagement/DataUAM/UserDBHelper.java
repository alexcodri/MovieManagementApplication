package eu.ase.moviemanagementapplication.UserAccessManagement.DataUAM;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import eu.ase.moviemanagementapplication.Model.UserInfo;
import eu.ase.moviemanagementapplication.R;

public class UserDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;
    private static final String LOGTAG = "USER";
    private SQLiteOpenHelper dbHandler;
    private SQLiteDatabase database;

    public UserDBHelper (Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public void close(){
        Log.i(LOGTAG,String.format("%s",R.string.database_closed));
        dbHandler.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + " (" +
                UserContract.UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                UserContract.UserEntry.COLUMN_USER + " TEXT NOT NULL, " +
                UserContract.UserEntry.COLUMN_PASSWORD + " TEXT NOT NULL " +"); ";
        db.execSQL(SQL_CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ UserContract.UserEntry.TABLE_NAME);
        onCreate(db);
    }

    public void addUsers(UserInfo user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_USER,user.getUsername().toLowerCase());
        values.put(UserContract.UserEntry.COLUMN_PASSWORD,user.getPassword());

        db.insert(UserContract.UserEntry.TABLE_NAME,null,values);
        db.close();
    }

    public boolean verifyUser(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username= ?",new String[] {username.toLowerCase()});
        if(cursor.getCount()> 0 ){
            cursor.close();
            return true;}
        else {
            cursor.close();
            return false;
        }
    }

    public boolean checkUser(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username= ? and password= ?",new String[] {username.toLowerCase(),password});
        if(cursor.getCount()> 0 ){
            cursor.close();
            return true;}
        else {
            cursor.close();
            return false;
        }
    }

    public boolean deleteUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("user","username "+"= ?", new String[] {username.toLowerCase()}) >0;

    }

    public void changePassword(String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password",password);
        db.update("user",contentValues,"username "+" = ?",new String[] {username.toLowerCase()});

    }


}