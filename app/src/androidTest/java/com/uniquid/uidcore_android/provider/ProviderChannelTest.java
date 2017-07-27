package com.uniquid.uidcore_android.provider;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * @author Beatrice Formai
 */

public class ProviderChannelTest {

    @Test
    public void testEmptyConstructor() {
        ProviderChannel providerChannel = new ProviderChannel();
        assertNull(providerChannel.getProviderAddress());
        assertNull(providerChannel.getUserAddress());
        assertNull(providerChannel.getRevokeAddress());
        assertNull(providerChannel.getRevokeTxId());
        assertNull(providerChannel.getBitmask());
    }

    @Test
    public void testConstructor() {
        String providerAddress = "provider_address";
        String userAddress = "user_address";
        String bitmask = "bitmask";
        ProviderChannel providerChannel = new ProviderChannel(
                providerAddress,
                userAddress,
                bitmask);
        assertEquals(providerAddress, providerChannel.getProviderAddress());
        assertEquals(userAddress, providerChannel.getUserAddress());
        assertEquals(bitmask, providerChannel.getBitmask());
        assertNull(providerChannel.getRevokeAddress());
        assertNull(providerChannel.getRevokeTxId());

        assertEquals("provider address: provider_address; user address: user_address; bitmask: bitmask; revoke address: null; revokeTxId: null; creationTime: 0", providerChannel.toString());

        assertEquals(-736396780, providerChannel.hashCode());

    }

    @Test
    public void testProviderAddress() {

        ProviderChannel providerChannel = new ProviderChannel();

        assertEquals(null, providerChannel.getProviderAddress());

        String providerAddress = "provider_address";

        providerChannel.setProviderAddress(providerAddress);

        assertEquals(providerAddress, providerChannel.getProviderAddress());

    }

    @Test
    public void testUserAddress() {

        ProviderChannel providerChannel = new ProviderChannel();

        assertEquals(null, providerChannel.getUserAddress());

        String userAddress = "user_address";

        providerChannel.setUserAddress(userAddress);

        assertEquals(userAddress, providerChannel.getUserAddress());

    }

    @Test
    public void testBitmask() {

        ProviderChannel providerChannel = new ProviderChannel();

        assertEquals(null, providerChannel.getBitmask());

        String bitmask = "bitmask";

        providerChannel.setBitmask(bitmask);

        assertEquals(bitmask, providerChannel.getBitmask());

    }

    @Test
    public void testRevokeAddress() {

        ProviderChannel providerChannel = new ProviderChannel();

        assertEquals(null, providerChannel.getRevokeAddress());

        String revokeAddress = "revoke_address";

        providerChannel.setRevokeAddress(revokeAddress);

        assertEquals(revokeAddress, providerChannel.getRevokeAddress());

    }

    @Test
    public void testRevokeTxId() {

        ProviderChannel providerChannel = new ProviderChannel();

        assertEquals(null, providerChannel.getRevokeTxId());

        String revokeTxid = "revoke_txid";

        providerChannel.setRevokeTxId(revokeTxid);

        assertEquals(revokeTxid, providerChannel.getRevokeTxId());

    }

    @Test
    public void testCreationTime() {

        ProviderChannel providerChannel = new ProviderChannel();

        long creationTime = System.currentTimeMillis();

        assertEquals(0, providerChannel.getCreationTime());

        providerChannel.setCreationTime(creationTime);

        assertEquals(creationTime, providerChannel.getCreationTime());

    }

    @Test
    public void testEquals() {

        ProviderChannel providerChannel1 = new ProviderChannel();

        ProviderChannel providerChannel2 = new ProviderChannel();

        assertEquals(true, providerChannel1.equals(providerChannel1));

        assertEquals(true, providerChannel1.equals(providerChannel2));

        providerChannel2.setUserAddress("user_address");

        assertEquals(false, providerChannel1.equals(providerChannel2));

        assertEquals(false, providerChannel1.equals(null));

    }

}
