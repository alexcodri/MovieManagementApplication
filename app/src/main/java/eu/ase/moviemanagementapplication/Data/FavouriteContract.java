package eu.ase.moviemanagementapplication.Data;

import android.provider.BaseColumns;

import eu.ase.moviemanagementapplication.R;

public class FavouriteContract {

    public static final class FavoriteEntry implements BaseColumns{
        public static final String TABLE_NAME = "favorite";
        public static final String COLUMN_MOVIEID = "movieid";
        public static final String COLUMN_TITLE= "title";
        public static final String COLUMN_USER_RATING =  "rating";
        public static final String COLUMN_POSTER_PATH = "posterpath";
        public static final String COLUMN_PLOT_SYNOPSIS = "plotsynopsis";
    }

}
