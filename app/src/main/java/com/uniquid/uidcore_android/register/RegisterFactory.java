package com.uniquid.uidcore_android.register;

import android.content.Context;

import com.uniquid.uidcore_android.exception.RegisterException;
import com.uniquid.uidcore_android.provider.ProviderRegister;
import com.uniquid.uidcore_android.user.UserRegister;

/**
 * @author Beatrice Formai
 */

public class RegisterFactory implements IRegisterFactory {

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
