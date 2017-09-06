package com.uniquid.uidcore_android.register;

import android.content.Context;

import com.uniquid.register.transaction.TransactionException;
import com.uniquid.register.transaction.TransactionManager;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Beatrice Formai
 */

public class AndroidDataSource implements TransactionManager {

    private static final ThreadLocal<SQLiteHelperPool.SQLiteDatabaseWrapper> context = new ThreadLocal<SQLiteHelperPool.SQLiteDatabaseWrapper>();

    private SQLiteHelperPool sqLiteHelperPool;
    private final Lock writerLock;

    AndroidDataSource(final Context context, final Class sqliteOpenHelperClass) {

        this.sqLiteHelperPool = new SQLiteHelperPool(context, sqliteOpenHelperClass);
        this.writerLock = new ReentrantLock();

    }

    @Override
    public void startTransaction() throws TransactionException {

        try {

            writerLock.lock();

            SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper = context.get();

            if (sqLiteDatabaseWrapper != null) {

                // There is already an instance in the context!!!! This is a problem!

                throw new IllegalStateException("A transaction was already started!!!");

            }

            sqLiteDatabaseWrapper = sqLiteHelperPool.borrowObject();

            sqLiteDatabaseWrapper.getSQLiteDatabase().beginTransactionNonExclusive();

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

            writerLock.unlock();

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

            writerLock.unlock();

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