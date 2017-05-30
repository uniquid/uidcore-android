package com.uniquid.uidcore_android;

import android.content.Context;

import com.uniquid.register.exception.RegisterException;
import com.uniquid.register.provider.ProviderRegister;
import com.uniquid.register.user.UserRegister;

/**
 * Class implementation of {@code RegisterFactory} that uses SQLite as data store.
 *
 * @author Beatrice Formai
 */

public class RegisterFactory implements com.uniquid.register.RegisterFactory {
    private Register instance;

    public RegisterFactory(final Context context) {
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
}
