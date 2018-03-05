package com.uniquid.uidcore_android;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.uniquid.register.exception.RegisterException;
import com.uniquid.register.provider.ProviderChannel;
import com.uniquid.register.provider.ProviderRegister;
import com.uniquid.register.user.UserChannel;
import com.uniquid.register.user.UserRegister;
import com.uniquid.uidcore_android.register.RegisterFactoryImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

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

    @After
    @Test
    public void testClean() throws RegisterException {
        Context context = InstrumentationRegistry.getContext();

        RegisterFactoryImpl factory = new RegisterFactoryImpl(context, 5);

        UserRegister userRegister = factory.getUserRegister();
        UserChannel userChannel = new UserChannel();
        userChannel.setProviderName("providerName");
        userChannel.setProviderAddress("providerAddress");
        userChannel.setUserAddress("userAddress");
        userChannel.setBitmask("11111");
        userChannel.setRevokeAddress("revokeAddress");
        userChannel.setRevokeTxId("revokeTxId");
        userChannel.setPath("path");

        userRegister.insertChannel(userChannel);

        assertTrue(userRegister.getAllUserChannels().size() > 0);

        factory.cleanTables();

        assertEquals(0, userRegister.getAllUserChannels().size());

    }

    @Override
    protected UserRegister getUserRegister() throws Exception {
        return factory.getUserRegister();
    }
}