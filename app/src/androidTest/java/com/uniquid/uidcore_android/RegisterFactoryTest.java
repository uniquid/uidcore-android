package com.uniquid.uidcore_android;

import android.support.test.InstrumentationRegistry;

import com.uniquid.register.exception.RegisterException;
import com.uniquid.register.provider.ProviderRegister;
import com.uniquid.register.user.UserRegister;
import com.uniquid.uidcore_android.register.RegisterFactoryImpl;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;

/**
 * @author Beatrice Formai
 */

public class RegisterFactoryTest {
    @Test
    public void testConstructor() throws RegisterException {
        RegisterFactoryImpl registerFactory = new RegisterFactoryImpl(InstrumentationRegistry.getContext(), 5);
        assertNotNull(registerFactory);

        RegisterFactoryImpl registerName = new RegisterFactoryImpl(InstrumentationRegistry.getContext(), 5, "test.db");
        assertNotNull(registerName);

        assertNotSame(registerFactory, registerName);
    }

    @Test
    public void testGetProviderRegister() throws RegisterException {
        RegisterFactoryImpl registerFactory = new RegisterFactoryImpl(InstrumentationRegistry.getContext(), 5);
        ProviderRegister register = registerFactory.getProviderRegister();
        assertNotNull(register);

        RegisterFactoryImpl factoryName = new RegisterFactoryImpl(InstrumentationRegistry.getContext(), 5, "test.db");
        ProviderRegister registerName = factoryName.getProviderRegister();
        assertNotNull(registerName);

        assertNotSame(register, registerName);
    }

    @Test
    public void testGetUserRegister() throws RegisterException {
        RegisterFactoryImpl registerFactory = new RegisterFactoryImpl(InstrumentationRegistry.getContext(), 5);
        UserRegister register = registerFactory.getUserRegister();
        assertNotNull(register);

        RegisterFactoryImpl factoryName = new RegisterFactoryImpl(InstrumentationRegistry.getContext(), 5, "test.db");
        UserRegister registerName = factoryName.getUserRegister();
        assertNotNull(registerName);

        assertNotSame(register, registerName);

    }
}
