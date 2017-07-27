package com.uniquid.uidcore_android;

import android.support.test.InstrumentationRegistry;

import com.uniquid.uidcore_android.exception.RegisterException;
import com.uniquid.uidcore_android.provider.ProviderChannel;
import com.uniquid.uidcore_android.provider.ProviderRegister;
import com.uniquid.uidcore_android.register.IRegisterFactory;
import com.uniquid.uidcore_android.register.Register;
import com.uniquid.uidcore_android.register.RegisterFactory;
import com.uniquid.uidcore_android.user.UserChannel;
import com.uniquid.uidcore_android.user.UserRegister;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.fail;

/**
 * @author Beatrice Formai
 */

public class RegisterTest {

    private IRegisterFactory factory;

    private String providerAddress = "mfuta5iXJNe7yzCaPtmm4W2saiqTbTfxNG";
    private String userAddress = "mkw5u34vDegrah5GasD5gKCJQ1NhNGG8tJ";
    private String bitmask = "00000";
    private String revokeAddress = "mjgWHUCV86eLp7B8mhHUuBAyCS136hz7SH";
    private String revokeTxId = "97ab3c1a7bbca566712ab843a65d2e1bf94594b26b2ffe9d3348e4403065c1db";

    @Before
    public void createAndroidRegisterFactory() throws RegisterException {
        factory = new RegisterFactory(InstrumentationRegistry.getContext());
        assertNotNull(factory);
    }

    @Test
    public void testConstructor() {
        Register register = new Register(InstrumentationRegistry.getContext());
        assertNotNull(register);
    }

    @Test
    public void testSecondConstructor() {
        Register register = new Register(InstrumentationRegistry.getContext(), "test");
        assertNotNull(register);
    }

    @Test
    public void testUserChannel() throws Exception {
        String providerName = "Test";

        UserChannel userChannel = new UserChannel(
                providerName,
                providerAddress,
                userAddress,
                bitmask
        );
        userChannel.setRevokeAddress(revokeAddress);
        userChannel.setRevokeTxId(revokeTxId);

        UserRegister register = factory.getUserRegister();

        List<UserChannel> channels = register.getAllUserChannels();
        Assert.assertNotNull(channels);
        Assert.assertEquals(0, channels.size());

        try {
            register.insertChannel(null);
            fail();
        } catch (Exception ex) {
            // do nothing
        }

        register.insertChannel(userChannel);
        channels = register.getAllUserChannels();
        Assert.assertEquals(1, channels.size());

        try {
            register.insertChannel(userChannel);
            fail();
        } catch (Exception ex) {
            // do nothing
        }

        UserChannel userChannelByProviderName = register.getChannelByName(providerName);
        assertEquals(userChannel, userChannelByProviderName);

        UserChannel userChannelByProviderAddress = register.getChannelByProviderAddress(providerAddress);
        assertEquals(userChannel, userChannelByProviderAddress);

        UserChannel userChannelByRevokeAddress = register.getUserChannelByRevokeAddress(revokeAddress);
        assertEquals(userChannel, userChannelByRevokeAddress);

        UserChannel userChannelByRevokeTxId = register.getUserChannelByRevokeTxId(revokeTxId);
        assertEquals(userChannel, userChannelByRevokeTxId);

        register.deleteChannel(userChannel);
        channels = register.getAllUserChannels();
        assertEquals(0, channels.size());

        try {
            register.deleteChannel(userChannel);
            fail();
        } catch (RegisterException e) {
            // do nothing
        }

        try {
            register.getUserChannelByRevokeAddress(revokeAddress);
            fail();
        } catch (RegisterException e) {
            // do nothing
        }

        try {
            register.getUserChannelByRevokeTxId(revokeTxId);
            fail();
        } catch (RegisterException e) {
            // do nothing
        }

        try {
            register.getChannelByName(providerName);
            fail();
        } catch (RegisterException e) {
            // do nothing
        }

        try {
            register.getChannelByProviderAddress(providerAddress);
            fail();
        } catch (RegisterException e) {
            // do nothing
        }
    }

    @Test
    public void testProviderChannel() throws Exception {
        ProviderChannel providerChannel = new ProviderChannel();
        providerChannel.setProviderAddress(providerAddress);
        providerChannel.setUserAddress(userAddress);
        providerChannel.setRevokeAddress(revokeAddress);
        providerChannel.setRevokeTxId(revokeTxId);
        providerChannel.setBitmask(bitmask);

        ProviderRegister register = factory.getProviderRegister();
        List<ProviderChannel> channels = register.getAllChannels();
        assertNotNull(channels);
        assertEquals(0, channels.size());

        try{
            register.insertChannel(null);
            fail();
        } catch (Exception e) {
            // do nothing
        }

        register.insertChannel(providerChannel);
        channels = register.getAllChannels();
        assertEquals(1, channels.size());

        try {
            register.insertChannel(providerChannel);
            fail();
        } catch (RegisterException e) {
            // do nothing
        }

        ProviderChannel channelByUserAddress = register.getChannelByUserAddress(userAddress);
        assertEquals(providerChannel, channelByUserAddress);

        ProviderChannel channelByRevokeAddress = register.getChannelByRevokeAddress(revokeAddress);
        assertEquals(providerChannel, channelByRevokeAddress);

        ProviderChannel channelByRevokeTxId = register.getChannelByRevokeTxId(revokeTxId);
        assertEquals(providerChannel, channelByRevokeTxId);

        register.deleteChannel(providerChannel);
        channels = register.getAllChannels();
        assertEquals(0, channels.size());

        try {
            register.deleteChannel(providerChannel);
            fail();
        } catch (RegisterException e) {
            // do nothing
        }

        try {
            register.getChannelByUserAddress(userAddress);
            fail();
        } catch (RegisterException e) {
            // do nothing
        }

        assertNull(register.getChannelByRevokeTxId(revokeTxId));

        assertNull(register.getChannelByRevokeAddress(revokeAddress));

    }

}
