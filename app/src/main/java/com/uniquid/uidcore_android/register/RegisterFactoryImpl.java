package com.uniquid.uidcore_android.register;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import com.uniquid.register.RegisterFactory;
import com.uniquid.register.exception.RegisterException;
import com.uniquid.register.provider.ProviderRegister;
import com.uniquid.register.transaction.TransactionManager;
import com.uniquid.register.user.UserRegister;


/**
 * @author Beatrice Formai
 */

public class RegisterFactoryImpl implements RegisterFactory {

    protected AndroidDataSource androidDataSource;

    public RegisterFactoryImpl(final Context context) {

        SQLiteOpenHelper sqLiteHelper = getSQLiteOpenHelper(context);

        androidDataSource = new AndroidDataSource(sqLiteHelper);
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

    protected SQLiteOpenHelper getSQLiteOpenHelper(Context context) {

        return new SQLiteHelper(context);

    }
}
