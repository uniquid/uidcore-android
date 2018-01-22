package com.uniquid.uidcore_android.register;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.uniquid.register.RegisterFactory;
import com.uniquid.register.exception.RegisterException;
import com.uniquid.register.provider.ProviderRegister;
import com.uniquid.register.transaction.TransactionManager;
import com.uniquid.register.user.UserRegister;

import static com.uniquid.uidcore_android.register.SQLiteHelper.TABLE_PROVIDER;
import static com.uniquid.uidcore_android.register.SQLiteHelper.TABLE_USER;

/**
 * Concrete class implementation of {@code RegisterFactory} that uses SQLite as data store.
 *
 * @author Beatrice Formai
 */

public class RegisterFactoryImpl implements RegisterFactory {

    protected AndroidDataSource androidDataSource;

    /**
     * Create a new RegisterFactoryImpl
     * @param context the application context
     * @param connections the number of allowed connections to the database
     * */
    public RegisterFactoryImpl(final Context context, int connections) {

        Class sqliteOpenHelperClass = getSQLiteHelperClass();

        // Tell android dataSource what implementation to use
        androidDataSource = new AndroidDataSource(context, sqliteOpenHelperClass, connections);
    }

    /**
     * Create a new RegisterFactoryImpl
     * @param context the application context
     * @param connections the number of allowed connections to the database
     * @param dbName name of the database to create
     * */
    public RegisterFactoryImpl(final Context context, int connections, String dbName) {

        Class sqliteOpenHelperClass = getSQLiteHelperClass();

        // Tell android dataSource what implementation to use
        androidDataSource = new AndroidDataSource(context, sqliteOpenHelperClass, connections, dbName);
    }

    public void cleanTables() throws RegisterException {
        Register register = new Register(androidDataSource);
        register.cleanUserTable();
        register.cleanProviderTable();
    }

    /**
     * Returns a ProviderRegister instance
     *
     * @return a ProviderRegister instance
     * @throws RegisterException in case a problem occurs
     */
    @Override
    public ProviderRegister getProviderRegister() throws RegisterException {
        return new Register(androidDataSource);
    }

    /**
     * Returns a UserRegister instance
     *
     * @return a UserRegister instance
     * @throws RegisterException in case a problem occurs
     */
    @Override
    public UserRegister getUserRegister() throws RegisterException {
        return new Register(androidDataSource);
    }

    @Override
    public TransactionManager getTransactionManager() throws RegisterException {
        return androidDataSource;
    }

    protected Class getSQLiteHelperClass() {

        return SQLiteHelper.class;

    }
}
