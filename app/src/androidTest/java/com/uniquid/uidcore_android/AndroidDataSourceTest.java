package com.uniquid.uidcore_android;

import android.support.test.InstrumentationRegistry;

import com.uniquid.register.RegisterFactory;
import com.uniquid.register.exception.RegisterException;
import com.uniquid.register.transaction.TransactionManagerTest;
import com.uniquid.uidcore_android.register.RegisterFactoryImpl;

/**
 * @author Beatrice Formai
 */

public class AndroidDataSourceTest extends TransactionManagerTest {

    @Override
    public RegisterFactory getRegisterFactory() throws RegisterException {
        return new RegisterFactoryImpl(InstrumentationRegistry.getContext(), 7);
    }
}
