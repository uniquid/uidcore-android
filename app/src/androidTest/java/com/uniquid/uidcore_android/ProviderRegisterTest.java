package com.uniquid.uidcore_android;

import android.support.test.InstrumentationRegistry;

import com.uniquid.register.exception.RegisterException;
import com.uniquid.register.provider.ProviderRegister;
import com.uniquid.uidcore_android.register.RegisterFactoryImpl;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

/**
 * @author Beatrice Formai
 */

public class ProviderRegisterTest extends com.uniquid.register.provider.ProviderRegisterTest {

    private RegisterFactoryImpl factory;

    @Before
    public void createAndroidRegisterFactory() throws RegisterException {
        InstrumentationRegistry.getContext().deleteDatabase("register.db");
        factory = new RegisterFactoryImpl(InstrumentationRegistry.getContext(), 5);
        assertNotNull(factory);
    }

    @Test
    public void testConstructor() throws RegisterException {
        ProviderRegister register = factory.getProviderRegister();
        assertNotNull(register);
    }

    @Override
    protected ProviderRegister getProviderRegister() throws Exception {
        return factory.getProviderRegister();
    }
}
