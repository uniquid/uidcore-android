package com.uniquid.uidcore_android;

import android.support.test.InstrumentationRegistry;

import com.uniquid.register.exception.RegisterException;
import com.uniquid.register.provider.ProviderChannel;
import com.uniquid.register.provider.ProviderRegister;
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

    @After
    @Test
    public void cleanTest() throws RegisterException{

        ProviderRegister providerRegister = factory.getProviderRegister();
        ProviderChannel providerChannel = new ProviderChannel();
        providerChannel.setProviderAddress("providerAddress");
        providerChannel.setUserAddress("userAddress");
        providerChannel.setCreationTime(123456789L);
        providerChannel.setBitmask("11111");
        providerChannel.setRevokeAddress("revokeAddress");
        providerChannel.setRevokeTxId("revokeTxId");
        providerChannel.setSince(0L);
        providerChannel.setUntil(600000L);
        providerChannel.setPath("path");

        providerRegister.insertChannel(providerChannel);

        assertTrue(providerRegister.getAllChannels().size() > 0);

        factory.cleanTables();

        assertEquals(0, providerRegister.getAllChannels().size());

    }
}
