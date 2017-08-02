package com.uniquid.uidcore_android.register;

import android.content.Context;

import com.uniquid.register.RegisterFactory;
import com.uniquid.register.exception.RegisterException;
import com.uniquid.register.provider.ProviderRegister;
import com.uniquid.register.transaction.TransactionManager;
import com.uniquid.register.user.UserRegister;


/**
 * @author Beatrice Formai
 */

public class RegisterFactoryImpl implements RegisterFactory {

    private Register instance;

    public RegisterFactoryImpl(final Context context) {
        instance = new Register(context);
    }

    /**
     * Returns a ProviderRegister instance
     *
     * @return a ProviderRegister instance
     * @throws RegisterException in case a problem occurs
     */
    @Override
    public ProviderRegister getProviderRegister() throws RegisterException {
        return instance;
    }

    /**
     * Returns a UserRegister instance
     *
     * @return a UserRegister instance
     * @throws RegisterException in case a problem occurs
     */
    @Override
    public UserRegister getUserRegister() throws RegisterException {
        return instance;
    }

    @Override
    public TransactionManager getTransactionManager() throws RegisterException {
        return null;
    }
}
