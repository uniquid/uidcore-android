package com.uniquid.uidcore_android.register;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Constructor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Beatrice Formai
 */

public class SQLiteHelperPool {

    private Lock lock;
    private Condition notInUse;
    private boolean inUse;
    private final Class sqliteOpenHelperClass;
    private final Context context;
    private SQLiteOpenHelper outerSqLiteOpenHelper;
    private SQLiteDatabase outerSqLiteDatabase;

    protected SQLiteHelperPool(final Context context, final Class sqliteOpenHelperClass) {

        this.lock = new ReentrantLock();
        this.notInUse = lock.newCondition();
        this.inUse = false;
        this.context = context;
        this.sqliteOpenHelperClass = sqliteOpenHelperClass;

    }

    public SQLiteDatabaseWrapper borrowObject() throws Exception {

        lock.lock();

        try {

            //Wait until somebody release DB Access
            while (inUse) {

                notInUse.await();

            }

            // Initialize!
            if (outerSqLiteOpenHelper == null) {

                outerSqLiteOpenHelper = createSQLiteOpenHelperInstance();

            }

            if (outerSqLiteDatabase == null) {

                outerSqLiteDatabase = outerSqLiteOpenHelper.getWritableDatabase();

            }

            // set in use
            inUse = true;

            return new SQLiteDatabaseWrapper(outerSqLiteDatabase);

        } finally {

            lock.unlock();

        }


    }

    protected SQLiteOpenHelper createSQLiteOpenHelperInstance() throws Exception {

        // We want the constructor with Context parameter
        Constructor<?> cons = sqliteOpenHelperClass.getConstructor(new Class[] { Context.class });

        // We create the instance!
        return (SQLiteOpenHelper) cons.newInstance(context);

    }

    public class SQLiteDatabaseWrapper implements AutoCloseable {

        private SQLiteDatabase wrappedSqLiteDatabase;

        public SQLiteDatabaseWrapper(SQLiteDatabase sqLiteDatabase) {

            this.wrappedSqLiteDatabase = sqLiteDatabase;

        }

        @Override
        public void close() throws Exception {

            lock.lock();

            try {

                // In case we are in a transaction we should not inform other that we are ready!
                if (!wrappedSqLiteDatabase.inTransaction()) {
                    outerSqLiteOpenHelper.close(); // Close the database!
                    outerSqLiteOpenHelper = null; // Destroy reference!
                    outerSqLiteDatabase = null; // Destory reference!

                    // set not in use
                    inUse = false;
                    notInUse.signal();
                }

            } finally {

                lock.unlock();

            }

        }

        public SQLiteDatabase getSQLiteDatabase() {

            return wrappedSqLiteDatabase;

        }

    }
}
