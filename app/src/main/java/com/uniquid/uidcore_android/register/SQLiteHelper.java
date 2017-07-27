package com.uniquid.uidcore_android.register;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper class to manage database creation and upgrade.
 *
 * @author Beatrice Formai
 */

class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "register.db";
    private static final int DB_VERSION = 1;

    //    USER
    static final String TABLE_USER = "user_channel";
    static final String USER_CLM_PROVIDER_NAME = "provider_name";
    static final String USER_CLM_PROVIDER_ADDRESS = "provider_address";
    static final String USER_CLM_USER_ADDRESS = "user_address";
    static final String USER_CLM_BITMASK = "bitmask";
    static final String USER_CLM_REVOKE_ADDRESS = "revoke_address";
    static final String USER_CLM_REVOKE_TX_ID = "revoke_tx_id";
    private static final String USER_CREATE = "create table " + TABLE_USER + "(" +
            USER_CLM_PROVIDER_NAME + " text not null, " +
            USER_CLM_PROVIDER_ADDRESS + " text not null, " +
            USER_CLM_USER_ADDRESS + " text not null, " +
            USER_CLM_BITMASK + " text not null, " +
            USER_CLM_REVOKE_ADDRESS + " text not null, " +
            USER_CLM_REVOKE_TX_ID + " text not null, primary key (" +
            USER_CLM_PROVIDER_NAME + ", " +
            USER_CLM_PROVIDER_ADDRESS + ", " +
            USER_CLM_USER_ADDRESS + "));";

    //    PROVIDER
    static final String TABLE_PROVIDER = "provider_channel";
    static final String PROVIDER_CLM_PROVIDER_ADDRESS = "provider_address";
    static final String PROVIDER_CLM_USER_ADDRESS = "user_address";
    static final String PROVIDER_CLM_BITMASK = "bitmask";
    static final String PROVIDER_CLM_REVOKE_ADDRESS = "revoke_address";
    static final String PROVIDER_CLM_REVOKE_TX_ID = "revoke_tx_id";
    static final String PROVIDER_CLM_CREATION_TIME = "creation_time";
    private static final String PROVIDER_CREATE = "create table " + TABLE_PROVIDER + "(" +
            PROVIDER_CLM_PROVIDER_ADDRESS + " text not null, " +
            PROVIDER_CLM_USER_ADDRESS + " text not null, " +
            PROVIDER_CLM_BITMASK + " text not null, " +
            PROVIDER_CLM_REVOKE_ADDRESS + " text not null, " +
            PROVIDER_CLM_REVOKE_TX_ID + " text not null, " +
            PROVIDER_CLM_CREATION_TIME + " integer not null, primary key (" +
            PROVIDER_CLM_PROVIDER_ADDRESS + ", " +
            PROVIDER_CLM_USER_ADDRESS + "));";

    SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    SQLiteHelper(Context context, String name) {
        super(context, name, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_CREATE);
        db.execSQL(PROVIDER_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop current table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROVIDER);
        // create new table
        onCreate(db);
    }
}