package com.uniquid.uidcore_android.register;

import android.content.Context;

import com.uniquid.register.transaction.TransactionException;
import com.uniquid.register.transaction.TransactionManager;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class to manage database transactions
 *
 * @author Beatrice Formai
 */

public class AndroidDataSource implements TransactionManager {

    private static final ThreadLocal<SQLiteHelperPool.SQLiteDatabaseWrapper> context = new ThreadLocal<>();

    private SQLiteHelperPool sqLiteHelperPool;
    private final Lock writerLock;

    /**
     * Create a new {@link AndroidDataSource}
     * @param context the application {@link Context}
     * @param sqliteOpenHelperClass the helper class for the connections
     * @param connections number of allowed connections to the database at the same time
     * */
    AndroidDataSource(final Context context, final Class sqliteOpenHelperClass, int connections) {

        this.sqLiteHelperPool = new SQLiteHelperPool(context, sqliteOpenHelperClass, connections);
        this.writerLock = new ReentrantLock();

    }

    /**
     * Create a new {@link AndroidDataSource}
     * @param context the application {@link Context}
     * @param sqliteOpenHelperClass the helper class for the connections
     * @param connections number of allowed connections to the database at the same time
     * @param dbName name of the database
     * */
    AndroidDataSource(final Context context, final Class sqliteOpenHelperClass, int connections, String dbName) {

        this.sqLiteHelperPool = new SQLiteHelperPool(context, sqliteOpenHelperClass, connections, dbName);
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

    @Override
    public boolean insideTransaction() {
        return (context.get() != null);
    }

    public SQLiteHelperPool.SQLiteDatabaseWrapper getSQLiteDatabaseWrapper() throws Exception {

        SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper = context.get();

        if (sqLiteDatabaseWrapper == null) {

            sqLiteDatabaseWrapper = sqLiteHelperPool.borrowObject();

        }

        return sqLiteDatabaseWrapper;

    }

}