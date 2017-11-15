package com.uniquid.uidcore_android;

import android.support.test.InstrumentationRegistry;

import com.uniquid.register.exception.RegisterException;
import com.uniquid.register.user.UserRegister;
import com.uniquid.uidcore_android.register.RegisterFactoryImpl;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;

/**
 * @author Beatrice Formai
 */

public class UserRegisterNameTest {

    private RegisterFactoryImpl factory;
    private RegisterFactoryImpl factoryName;

    @Before
    public void createAndroidRegisterFactory() throws RegisterException {
        InstrumentationRegistry.getContext().deleteDatabase("register.db");
        factory = new RegisterFactoryImpl(InstrumentationRegistry.getContext(), 5);
        assertNotNull(factory);

        InstrumentationRegistry.getContext().deleteDatabase("test.db");
        factoryName = new RegisterFactoryImpl(InstrumentationRegistry.getContext(), 5, "test.db");
        assertNotNull(factoryName);

        assertNotSame(factory, factoryName);
    }

    @Test
    public void testConstructor() throws RegisterException {
        UserRegister register = factory.getUserRegister();
        assertNotNull(register);

        UserRegister registerName = factoryName.getUserRegister();
        assertNotNull(registerName);

        assertNotSame(register, registerName);


    }

}
