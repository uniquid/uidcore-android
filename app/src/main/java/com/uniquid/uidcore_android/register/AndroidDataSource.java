package com.uniquid.uidcore_android.register;

import android.database.sqlite.SQLiteOpenHelper;

import com.uniquid.register.transaction.TransactionException;
import com.uniquid.register.transaction.TransactionManager;

/**
 * Created by giuseppe on 02/08/17.
 */

public class AndroidDataSource implements TransactionManager {

    private static final ThreadLocal<SQLiteHelperPool.SQLiteDatabaseWrapper> context = new ThreadLocal<SQLiteHelperPool.SQLiteDatabaseWrapper>();

    private SQLiteHelperPool sqLiteHelperPool;

    AndroidDataSource(SQLiteOpenHelper dbHelper) {

        this.sqLiteHelperPool = new SQLiteHelperPool(dbHelper);

    }

    @Override
    public void startTransaction() throws TransactionException {

        try {

            SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper = getSQLiteDatabaseWrapper();

            sqLiteDatabaseWrapper.setTxInProgress(true);

            sqLiteDatabaseWrapper.getSQLiteDatabase().beginTransaction();

            context.set(sqLiteDatabaseWrapper);

        } catch (Exception ex) {

            throw new TransactionException("Exception starting transaction");
        }

    }

    @Override
    public void commitTransaction() throws TransactionException {

        try {

            try (SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper = context.get()) {

                sqLiteDatabaseWrapper.getSQLiteDatabase().setTransactionSuccessful();

                sqLiteDatabaseWrapper.getSQLiteDatabase().endTransaction();

                sqLiteDatabaseWrapper.setTxInProgress(false);

            }

        } catch (Throwable t) {

            throw new TransactionException("Exception committing transaction");

        } finally {

            // remember to remove the wrapper from the threadlocal!
            context.remove();

        }

    }

    @Override
    public void rollbackTransaction() throws TransactionException {

        try {

            try (SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper = context.get()) {

                sqLiteDatabaseWrapper.getSQLiteDatabase().endTransaction();

                sqLiteDatabaseWrapper.setTxInProgress(false);

            }

        } catch (Throwable t) {

            throw new TransactionException("Exception rollbacking transaction");

        } finally {

            // remember to remove the wrapper from the threadlocal!
            context.remove();

        }

    }

    public SQLiteHelperPool.SQLiteDatabaseWrapper getSQLiteDatabaseWrapper() throws Exception {

        SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper = context.get();

        if (sqLiteDatabaseWrapper == null) {

            sqLiteDatabaseWrapper = sqLiteHelperPool.borrowObject();

        }

        return sqLiteDatabaseWrapper;

    }

}
