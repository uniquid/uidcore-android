package com.uniquid.uidcore_android.register;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper class to manage database creation and upgrade.
 *
 * @author Beatrice Formai
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    protected static final String DB_NAME = "register.db";
    protected static final int DB_VERSION = 1;

    //    USER
    static final String TABLE_USER = "user_channel";
    static final String USER_CLM_PROVIDER_NAME = "provider_name";
    static final String USER_CLM_PROVIDER_ADDRESS = "provider_address";
    static final String USER_CLM_USER_ADDRESS = "user_address";
    static final String USER_CLM_BITMASK = "bitmask";
    static final String USER_CLM_REVOKE_ADDRESS = "revoke_address";
    static final String USER_CLM_REVOKE_TX_ID = "revoke_tx_id";
    static final String USER_CLM_PATH = "path";
    private static final String USER_CREATE = "create table " + TABLE_USER + "(" +
            USER_CLM_PROVIDER_NAME + " text not null, " +
            USER_CLM_PROVIDER_ADDRESS + " text not null, " +
            USER_CLM_USER_ADDRESS + " text not null, " +
            USER_CLM_BITMASK + " text not null, " +
            USER_CLM_REVOKE_ADDRESS + " text not null, " +
            USER_CLM_REVOKE_TX_ID + " text not null, " +
			USER_CLM_PATH + " text not null, primary key (" +
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
    static final String PROVIDER_CLM_SINCE = "since";
    static final String PROVIDER_CLM_UNTIL = "until";
    static final String PROVIDER_CLM_PATH = "path";
    private static final String PROVIDER_CREATE = "create table " + TABLE_PROVIDER + "(" +
            PROVIDER_CLM_PROVIDER_ADDRESS + " text not null, " +
            PROVIDER_CLM_USER_ADDRESS + " text not null, " +
            PROVIDER_CLM_BITMASK + " text not null, " +
            PROVIDER_CLM_REVOKE_ADDRESS + " text not null, " +
            PROVIDER_CLM_REVOKE_TX_ID + " text not null, " +
            PROVIDER_CLM_CREATION_TIME + " integer not null, " +
            PROVIDER_CLM_SINCE + " integer, " +
            PROVIDER_CLM_UNTIL + " integer, " +
            PROVIDER_CLM_PATH + " text not null, primary key (" +
            PROVIDER_CLM_PROVIDER_ADDRESS + ", " +
            PROVIDER_CLM_USER_ADDRESS + "));";

    public SQLiteHelper(Context context) {
        this(context, DB_NAME);
    }

    public SQLiteHelper(Context context, String dbName) {
        super(context, dbName, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_CREATE);
        db.execSQL(PROVIDER_CREATE);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        setWriteAheadLoggingEnabled(true);
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