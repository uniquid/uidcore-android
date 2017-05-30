package com.uniquid.uidcore_android;

import android.support.test.InstrumentationRegistry;

import com.uniquid.register.exception.RegisterException;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

/**
 * @author Beatrice Formai
 */

public class RegisterFactoryTest {
    @Test
    public void testConstructor() throws RegisterException {
        RegisterFactory registerFactory = new RegisterFactory(InstrumentationRegistry.getContext());
        assertNotNull(registerFactory);
    }

    @Test
    public void testGetProviderRegister() throws RegisterException {
        RegisterFactory registerFactory = new RegisterFactory(InstrumentationRegistry.getContext());
        assertNotNull(registerFactory.getProviderRegister());
    }

    @Test
    public void testGetUserRegister() throws RegisterException {
        RegisterFactory registerFactory = new RegisterFactory(InstrumentationRegistry.getContext());
        assertNotNull(registerFactory.getUserRegister());
    }
}
