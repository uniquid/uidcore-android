package com.uniquid.uidcore_android.register;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SQLiteHelperPool {

    private Lock lock;
    private Condition notInUse;
    private boolean inUse;
    private SQLiteOpenHelper sqLiteHelper;

    protected SQLiteHelperPool(SQLiteOpenHelper sqLiteHelper) {

        this.lock = new ReentrantLock();
        this.notInUse = lock.newCondition();
        this.inUse = false;
        this.sqLiteHelper = sqLiteHelper;

    }

    public SQLiteDatabaseWrapper borrowObject() throws InterruptedException {

        lock.lock();

        try {

            while (inUse) {

                notInUse.await();

            }

            // set in use
            inUse = true;

            return new SQLiteDatabaseWrapper(sqLiteHelper.getWritableDatabase());

        } finally {

            lock.unlock();

        }


    }

    public class SQLiteDatabaseWrapper implements AutoCloseable {

        private SQLiteDatabase sqLiteDatabase;
        private boolean txInProgress;

        public SQLiteDatabaseWrapper(SQLiteDatabase sqLiteDatabase) {

            this.sqLiteDatabase = sqLiteDatabase;
            this.txInProgress = false;

        }

        @Override
        public void close() throws Exception {

            lock.lock();

            try {

                // set not in use
                inUse = false;
                notInUse.signal();

                if (!txInProgress) {
                    sqLiteDatabase.close();
                }

            } finally {

                lock.unlock();

            }

        }

        public SQLiteDatabase getSQLiteDatabase() {

            return sqLiteDatabase;

        }

        public void setTxInProgress(boolean txInProgress) {

            lock.lock();

            try {

                this.txInProgress = txInProgress;

            } finally {

                lock.unlock();

            }

        }

    }

}
