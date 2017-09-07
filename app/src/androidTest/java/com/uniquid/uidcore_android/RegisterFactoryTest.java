package com.uniquid.uidcore_android;

import android.support.test.InstrumentationRegistry;

import com.uniquid.register.exception.RegisterException;
import com.uniquid.uidcore_android.register.RegisterFactoryImpl;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

/**
 * @author Beatrice Formai
 */

public class RegisterFactoryTest {

    private final int connections = 7;

    @Test
    public void testConstructor() throws RegisterException {
        RegisterFactoryImpl registerFactory = new RegisterFactoryImpl(InstrumentationRegistry.getContext(), connections);
        assertNotNull(registerFactory);
    }

    @Test
    public void testGetProviderRegister() throws RegisterException {
        RegisterFactoryImpl registerFactory = new RegisterFactoryImpl(InstrumentationRegistry.getContext(), connections);
        assertNotNull(registerFactory.getProviderRegister());
    }

    @Test
    public void testGetUserRegister() throws RegisterException {
        RegisterFactoryImpl registerFactory = new RegisterFactoryImpl(InstrumentationRegistry.getContext(), connections);
        assertNotNull(registerFactory.getUserRegister());
    }
}
