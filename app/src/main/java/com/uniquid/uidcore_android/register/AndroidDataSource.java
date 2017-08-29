package com.uniquid.uidcore_android.register;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Constructor;

import com.uniquid.register.transaction.TransactionException;
import com.uniquid.register.transaction.TransactionManager;

/**
 * @author Beatrice Formai
 */

public class AndroidDataSource implements TransactionManager {

    private static final ThreadLocal<SQLiteHelperPool.SQLiteDatabaseWrapper> context = new ThreadLocal<SQLiteHelperPool.SQLiteDatabaseWrapper>();

    private SQLiteHelperPool sqLiteHelperPool;
    AndroidDataSource(final Context context, final Class sqliteOpenHelperClass) {

        this.sqLiteHelperPool = new SQLiteHelperPool(context, sqliteOpenHelperClass);

    }

    @Override
    public void startTransaction() throws TransactionException {

        try {

            SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper = context.get();

            if (sqLiteDatabaseWrapper != null) {

                // There is already an instance in the context!!!! This is a problem!

                throw new IllegalStateException("A transaction was already started!!!");

            }

            sqLiteDatabaseWrapper = sqLiteHelperPool.borrowObject();

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