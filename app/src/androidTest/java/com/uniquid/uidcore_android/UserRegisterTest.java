package com.uniquid.uidcore_android;

import android.support.test.InstrumentationRegistry;

import com.uniquid.register.exception.RegisterException;
import com.uniquid.register.user.UserRegister;
import com.uniquid.uidcore_android.register.RegisterFactoryImpl;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

/**
 * @author Beatrice Formai
 */

public class UserRegisterTest extends com.uniquid.register.user.UserRegisterTest {

    private RegisterFactoryImpl factory;

    @Before
    public void createAndroidRegisterFactory() throws RegisterException {
        InstrumentationRegistry.getContext().deleteDatabase("register.db");
        factory = new RegisterFactoryImpl(InstrumentationRegistry.getContext(), 5);
        assertNotNull(factory);
    }

    @Test
    public void testConstructor() throws RegisterException {
        UserRegister register = factory.getUserRegister();
        assertNotNull(register);
    }

    @Override
    protected UserRegister getUserRegister() throws Exception {
        return factory.getUserRegister();
    }
}