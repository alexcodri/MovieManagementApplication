package eu.ase.moviemanagementapplication.UserAccessManagement.DataUAM;

import android.provider.BaseColumns;

public class UserContract {


    public static final class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_USER = "username";
        public static final String COLUMN_PASSWORD = "password";
    }
}