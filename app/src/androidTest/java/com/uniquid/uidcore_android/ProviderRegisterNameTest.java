package com.uniquid.uidcore_android;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;

import com.uniquid.register.exception.RegisterException;
import com.uniquid.register.provider.ProviderChannel;
import com.uniquid.register.provider.ProviderRegister;
import com.uniquid.uidcore_android.register.RegisterFactoryImpl;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.fail;

/**
 * @author Beatrice Formai
 */

public class ProviderRegisterNameTest {

    private RegisterFactoryImpl factoryName;
    private RegisterFactoryImpl factory;

    @Before
    public void createAndroidRegisterFactory() {
        InstrumentationRegistry.getContext().deleteDatabase("test.db");
        factoryName = new RegisterFactoryImpl(InstrumentationRegistry.getContext(), 5, "test.db");
        assertNotNull(factoryName);

        InstrumentationRegistry.getContext().deleteDatabase("register.db");
        factory = new RegisterFactoryImpl(InstrumentationRegistry.getContext(), 5);
        assertNotNull(factory);

        assertNotSame(factoryName, factory);

    }

    @Test
    public void testConstructor() throws RegisterException {
        ProviderRegister registerName = factoryName.getProviderRegister();
        assertNotNull(registerName);
    }

    @LargeTest
    public void testInsert() throws RegisterException {
        ProviderRegister register = factory.getProviderRegister();

        List<ProviderChannel> channels = register.getAllChannels();

        assertNotNull(channels);
        assertEquals(0, channels.size());

        ProviderChannel providerChannel = new ProviderChannel();
        providerChannel.setProviderAddress("mfuta5iXJNe7yzCaPtmm4W2saiqTbTfxNG");
        providerChannel.setUserAddress("mkw5u34vDegrah5GasD5gKCJQ1NhNGG8tJ");
        providerChannel.setRevokeAddress("mjgWHUCV86eLp7B8mhHUuBAyCS136hz7SH");
        providerChannel.setRevokeTxId("97ab3c1a7bbca566712ab843a65d2e1bf94594b26b2ffe9d3348e4403065c1db");
        providerChannel.setBitmask("00000");

        try {
            register.insertChannel(null);
            fail();
        } catch (RegisterException ex) {
            assertEquals("providerChannel is null!", ex.getLocalizedMessage());
        }

        register.insertChannel(providerChannel);

        try {
            register.insertChannel(providerChannel);
            fail();
        } catch (RegisterException ex) {
            assertEquals("Exception while insertChannel()", ex.getLocalizedMessage());
        }

        channels = register.getAllChannels();

        assertEquals(channels.size(), 1);

        assertEquals(true, providerChannel.equals(channels.get(0)));

        ProviderRegister registerName = factoryName.getProviderRegister();

        List<ProviderChannel> channelsName = registerName.getAllChannels();

        assertNotNull(channelsName);
        assertEquals(0, channelsName.size());

        ProviderChannel providerChannelName = new ProviderChannel();
        providerChannelName.setProviderAddress("providerAddress");
        providerChannelName.setUserAddress("userAddress");
        providerChannelName.setRevokeAddress("revokerAddress");
        providerChannelName.setRevokeTxId("revokeTxid");
        providerChannelName.setBitmask("11111");

        try {
            registerName.insertChannel(null);
            fail();
        } catch (RegisterException ex) {
            assertEquals("providerChannel is null!", ex.getLocalizedMessage());
        }

        registerName.insertChannel(providerChannelName);

        try {
            registerName.insertChannel(providerChannelName);
            fail();
        } catch (RegisterException ex) {
            assertEquals("Exception while insertChannel()", ex.getLocalizedMessage());
        }

        channelsName = registerName.getAllChannels();

        assertEquals(1, channelsName.size());

        assertEquals(true, providerChannelName.equals(channelsName.get(0)));

        assertNotSame(channels.get(0), channelsName.get(0));
    }
}
