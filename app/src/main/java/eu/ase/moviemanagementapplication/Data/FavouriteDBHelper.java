package eu.ase.moviemanagementapplication.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import eu.ase.moviemanagementapplication.Model.Movie;
import eu.ase.moviemanagementapplication.R;
//import eu.ase.moviemanagementapplication.UserAccessManagement.DataUAM.UserDBHelper;

public class FavouriteDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorite.db";
    private static final int DATABASE_VERSION = 1;
    public static final String LOGTAG = "FAVORITE";
    public SQLiteOpenHelper dbHandler;

    public FavouriteDBHelper (Context context){
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
    }

    public void close(){
        Log.i(LOGTAG,String.format("%s",R.string.database_closed));
        dbHandler.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + FavouriteContract.FavoriteEntry.TABLE_NAME + " (" +
                FavouriteContract.FavoriteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username"+" TEXT REFERENCES "+"user"+" ,"+
                FavouriteContract.FavoriteEntry.COLUMN_MOVIEID + " INTEGER , " +
                FavouriteContract.FavoriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavouriteContract.FavoriteEntry.COLUMN_USER_RATING + " REAL NOT NULL, " +
                FavouriteContract.FavoriteEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                FavouriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS + " TEXT NOT NULL" +
                "); ";

        db.execSQL(SQL_CREATE_FAVORITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ FavouriteContract.FavoriteEntry.TABLE_NAME);
        onCreate(db);
    }

    public void addFavorites(Movie movie,String username) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values  = new ContentValues();
        values.put(FavouriteContract.FavoriteEntry.COLUMN_MOVIEID,movie.getId());
        values.put("username",username);
        values.put(FavouriteContract.FavoriteEntry.COLUMN_TITLE,movie.getOriginalTitle());
        values.put(FavouriteContract.FavoriteEntry.COLUMN_USER_RATING,movie.getAverageVote());
        values.put(FavouriteContract.FavoriteEntry.COLUMN_POSTER_PATH,movie.getPosterPath());
        values.put(FavouriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS,movie.getMovieOverview());

        db.insert(FavouriteContract.FavoriteEntry.TABLE_NAME,null,values);

        db.close();
    }

    public boolean deleteFavorites(String username,int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("favorite","username = ? and movieid =?", new String[] {username, String.format("%s",id)})>0;
    }

    public boolean deleteMovies(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("favorite","username "+"= ?", new String[] {username.toLowerCase()}) >0;
    }

    public boolean checkIfFavourite(String username, int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from favorite where username =? and movieid= ? ", new String[] {username.toLowerCase(),String.format("%s",id)});
        if(cursor.getCount() > 0){
            return true;
        }
        else
            return false;
    }

    public ArrayList<String> queryData(String username, String value){
        ArrayList<String> movieArray = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from favorite where username =? and rating >= ?", new String[] {username.toLowerCase(),value});

        while(cursor.moveToNext()){
            movieArray.add("Rating is: "+cursor.getString(4)+" Movie Title is: "+cursor.getString(3));
        }
        return movieArray;
   }

    public Cursor movieGraph(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT movieid, COUNT(*) as nrids, title " +
                "FROM favorite " +
                "GROUP BY movieid",new String [] {});
       return cursor;

    }

    public ArrayList<String> viewData(String username){
        ArrayList<String> mArray = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from favorite where username= ?", new String[] {username.toLowerCase()});

        while(cursor.moveToNext()){
            mArray.add(cursor.getString(3));
        }
        return mArray;
    }

}
