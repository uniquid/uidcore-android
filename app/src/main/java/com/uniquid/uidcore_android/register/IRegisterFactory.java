package com.uniquid.uidcore_android.register;

import com.uniquid.uidcore_android.exception.RegisterException;
import com.uniquid.uidcore_android.provider.ProviderRegister;
import com.uniquid.uidcore_android.user.UserRegister;

/**
 * Class implementation of {@code IRegisterFactory} that uses SQLite as data store.
 *
 * @author Beatrice Formai
 */

public interface IRegisterFactory {

    /**
     * Returns a ProviderRegister instance
     *
     * @return a ProviderRegister instance
     * @throws RegisterException in case a problem occurs
     */
    public ProviderRegister getProviderRegister() throws RegisterException;

    /**
     * Returns a UserRegister instance
     *
     * @return a UserRegister instance
     * @throws RegisterException in case a problem occurs
     */
    public UserRegister getUserRegister() throws RegisterException;

}