package com.uniquid.uidcore_android;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.uniquid.register.exception.RegisterException;
import com.uniquid.register.user.UserChannel;
import com.uniquid.register.user.UserRegister;
import com.uniquid.uidcore_android.register.RegisterFactoryImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
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

    @Test
    public void testClean() throws RegisterException {

        UserRegister userRegister = factory.getUserRegister();

        UserChannel userChannel = new UserChannel();
        userChannel.setProviderName("providerName");
        userChannel.setProviderAddress("providerAddress");
        userChannel.setUserAddress("userAddress");
        userChannel.setBitmask("11111");
        userChannel.setRevokeAddress("revokeAddress");
        userChannel.setRevokeTxId("revokeTxId");
        userChannel.setSince(1528200741000L);		// 06/05/2018 @ 12:12pm (UTC)
        userChannel.setUntil(1591367400000L);		// 06/05/2020 @ 2:30pm (UTC)
        userChannel.setPath("path");

        userRegister.insertChannel(userChannel);

        assertTrue(userRegister.getAllUserChannels().size() > 0);

        UserChannel userChannel1 = new UserChannel();
        userChannel1.setProviderName("providerName1");
        userChannel1.setProviderAddress("providerAddress1");
        userChannel1.setUserAddress("userAddress1");
        userChannel1.setBitmask("11111");
        userChannel1.setRevokeAddress("revokeAddress");
        userChannel1.setRevokeTxId("revokeTxId");
        userChannel1.setSince(1528200741000L);		// 06/05/2018 @ 12:12pm (UTC)
        userChannel1.setUntil(1528200741000L);		// 06/05/2018 @ 12:12pm (UTC)
        userChannel1.setPath("path");

        userRegister.insertChannel(userChannel1);

        assertTrue(userRegister.getAllUserChannels().size() > 0);

        int size = userRegister.getAllUserChannels().size();

        factory.deleteExpired();

        assertEquals(size, userRegister.getAllUserChannels().size());

        factory.cleanTables();

        assertEquals(0, userRegister.getAllUserChannels().size());

    }

    @Override
    protected UserRegister getUserRegister() throws Exception {
        return factory.getUserRegister();
    }
}