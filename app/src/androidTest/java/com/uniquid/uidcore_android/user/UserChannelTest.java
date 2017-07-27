package com.uniquid.uidcore_android.user;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * @author Beatrice Formai
 */

public class UserChannelTest {

    @Test
    public void testEmptyConstructor() {
        UserChannel userChannel = new UserChannel();
        assertNull(userChannel.getProviderName());
        assertNull(userChannel.getProviderAddress());
        assertNull(userChannel.getUserAddress());
        assertNull(userChannel.getRevokeAddress());
        assertNull(userChannel.getRevokeTxId());
        assertNull(userChannel.getBitmask());
    }

    @Test
    public void testConstructor() {
        String providerName = "providerName";
        String providerAddress = "providerAddress";
        String userAddress = "userAddress";
        String bitmask = "bitmask";

        UserChannel userChannel = new UserChannel(providerName, providerAddress, userAddress, bitmask);

        assertEquals(providerName, userChannel.getProviderName());
        assertEquals(providerAddress, userChannel.getProviderAddress());
        assertEquals(userAddress, userChannel.getUserAddress());
        assertEquals(bitmask, userChannel.getBitmask());
        assertEquals(null, userChannel.getRevokeAddress());
        assertEquals(null, userChannel.getRevokeTxId());

        assertEquals("provider address: providerAddress; user address: userAddress; bitmask: bitmask; revoke address: null; revokeTxId: null", userChannel.toString());
        assertEquals(-763790120, userChannel.hashCode());
    }

    @Test
    public void testProviderName() {

        UserChannel userChannel = new UserChannel();

        assertEquals(null, userChannel.getProviderName());

        String providerName = "providerName";

        userChannel.setProviderName(providerName);

        assertEquals(providerName, userChannel.getProviderName());

    }

    @Test
    public void testProviderAddress() {

        UserChannel userChannel = new UserChannel();

        assertEquals(null, userChannel.getProviderAddress());

        String providerAddress = "providerAddress";

        userChannel.setProviderAddress(providerAddress);

        assertEquals(providerAddress, userChannel.getProviderAddress());

    }

    @Test
    public void testUserAddress() {

        UserChannel userChannel = new UserChannel();

        assertEquals(null, userChannel.getUserAddress());

        String userAddress = "userAddress";

        userChannel.setUserAddress(userAddress);

        assertEquals(userAddress, userChannel.getUserAddress());

    }

    @Test
    public void testBitmask() {

        UserChannel userChannel = new UserChannel();

        assertEquals(null, userChannel.getBitmask());

        String bitmask = "bitmask";

        userChannel.setBitmask(bitmask);

        assertEquals(bitmask, userChannel.getBitmask());

    }

    @Test
    public void testRevokeAddress() {

        UserChannel userChannel = new UserChannel();

        assertEquals(null, userChannel.getRevokeAddress());

        String revokeAddress = "revokeAddress";

        userChannel.setRevokeAddress(revokeAddress);

        assertEquals(revokeAddress, userChannel.getRevokeAddress());

    }

    @Test
    public void testRevokeTxId() {

        UserChannel userChannel = new UserChannel();

        assertEquals(null, userChannel.getRevokeTxId());

        String revokeTxid = "revokeTxid";

        userChannel.setRevokeTxId(revokeTxid);

        assertEquals(revokeTxid, userChannel.getRevokeTxId());

    }

    @Test
    public void testEquals() {

        UserChannel userChannel1 = new UserChannel();

        UserChannel userChannel2 = new UserChannel();

        assertEquals(true, userChannel1.equals(userChannel2));

        userChannel2.setProviderName("other");

        assertEquals(false, userChannel1.equals(userChannel2));

        assertEquals(false, userChannel1.equals(null));

        assertEquals(true, userChannel1.equals(userChannel1));

    }

    @Test
    public void testCompareTo() {

        UserChannel userChannel1 = new UserChannel();

        UserChannel userChannel2 = new UserChannel();

        assertEquals(0, userChannel1.compareTo(userChannel1));

        assertEquals(0, userChannel1.compareTo(userChannel2));

        UserChannel userChannel3 = new UserChannel("address0", "provider", "user", "bitmask");

        UserChannel userChannel4 = new UserChannel("address0", "provider", "user", "bitmask");

        UserChannel userChannel5 = new UserChannel("address1", "provider", "user", "bitmask");

        assertEquals(0, userChannel3.compareTo(userChannel3));

        assertEquals(0, userChannel3.compareTo(userChannel4));

        assertEquals(0, userChannel4.compareTo(userChannel3));

        assertEquals(1, userChannel3.compareTo(userChannel1));

        assertEquals(-1, userChannel1.compareTo(userChannel3));

        assertEquals(-1, userChannel4.compareTo(userChannel5));

        assertEquals(1, userChannel5.compareTo(userChannel4));

    }

}
