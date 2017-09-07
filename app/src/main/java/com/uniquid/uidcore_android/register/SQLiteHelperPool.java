package com.uniquid.uidcore_android.register;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Beatrice Formai
 */

public class SQLiteHelperPool {

    private final Class sqliteOpenHelperClass;
    private final Context context;
    private final List<SQLiteDatabaseWrapper> pool;
    private boolean initialized;
    private int size = 3;

    protected SQLiteHelperPool(final Context context, final Class sqliteOpenHelperClass, int size) {

        this.context = context;
        this.sqliteOpenHelperClass = sqliteOpenHelperClass;
        this.pool = new ArrayList<>();
        this.initialized = false;
        this.size = size;
    }

    public SQLiteDatabaseWrapper borrowObject() throws Exception {

        synchronized (pool) {

            if (!initialized) {

                for (int i = 0; i < size; i++) {

                    // Initialize!
                    SQLiteOpenHelper outerSqLiteOpenHelper = createSQLiteOpenHelperInstance();

                    pool.add(new SQLiteDatabaseWrapper(outerSqLiteOpenHelper));

                }

                initialized = true;

            }

            // wait until there is an available connection
            while (pool.size() == 0) {

                pool.wait();

            }

            return pool.remove(0);

        }

    }

    protected SQLiteOpenHelper createSQLiteOpenHelperInstance() throws Exception {

        // We want the constructor with Context parameter
        Constructor<?> cons = sqliteOpenHelperClass.getConstructor(new Class[] { Context.class });

        // We create the instance!
        return (SQLiteOpenHelper) cons.newInstance(context);

    }

    public class SQLiteDatabaseWrapper implements AutoCloseable {

        private SQLiteOpenHelper wrappedSqLiteOpenHelper;
        private SQLiteDatabase wrappedSqLiteDatabase;

        public SQLiteDatabaseWrapper(SQLiteOpenHelper sqLiteOpenHelper) {

            this.wrappedSqLiteOpenHelper = sqLiteOpenHelper;
            this.wrappedSqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();

        }

        @Override
        public void close() throws Exception {

            // In case we are in a transaction we should not inform other that we are ready!
            if (!wrappedSqLiteDatabase.inTransaction()) {

                synchronized (pool) {

                    pool.add(this);

                    pool.notify();

                }

            }

        }

        public SQLiteDatabase getSQLiteDatabase() {

            return wrappedSqLiteDatabase;

        }

    }
}
