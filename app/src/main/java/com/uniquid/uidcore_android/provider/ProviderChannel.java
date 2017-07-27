package com.uniquid.uidcore_android.provider;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a provider contract fetched from the blockchain.
 * A provider contract is composed by a provider address, an user address, a revoke address, a bitmask, and
 * a revoke tx id and a creation time.
 *
 * @author Beatrice Formai
 */

public class ProviderChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String providerAddress;
    private String userAddress;
    private String revokeAddress;
    private String bitmask;
    private String revokeTxId;
    private long creationTime;

    /**
     * Creates an empty instance
     */
    public ProviderChannel() {
        // DO NOTHING
    }

    /**
     * Creates an instance from provider address, user address and bitmask.
     *
     * @param providerAddress the address of the provider
     * @param userAddress the address of the user
     * @param bitmask the string representing the bitmask
     */
    public ProviderChannel(String providerAddress, String userAddress, String bitmask) {
        this.providerAddress = providerAddress;
        this.userAddress = userAddress;
        this.bitmask = bitmask;
    }

    /**
     * Set the provider address
     * @param providerAddress the provider address
     */
    public void setProviderAddress(String providerAddress) {
        this.providerAddress = providerAddress;
    }

    /**
     * Returns the provider address
     * @return the provider address
     */
    public String getProviderAddress() {
        return providerAddress;
    }

    /**
     * Set the user address
     * @param userAddress the user address
     */
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    /**
     * Returns the user address
     * @return the user address
     */
    public String getUserAddress() {
        return userAddress;
    }

    /**
     * Returns the bitmask
     * @return the bitmask
     */
    public String getBitmask() {
        return bitmask;
    }

    /**
     * Set the bitmask
     * @param bitmask the bitmask
     */
    public void setBitmask(String bitmask) {
        this.bitmask = bitmask;
    }

    /**
     * Return the revoke address
     * @return the revoke address
     */
    public String getRevokeAddress() {
        return revokeAddress;
    }

    /**
     * Set the revoke address
     * @param revokeAddress the revoke address
     */
    public void setRevokeAddress(String revokeAddress) {
        this.revokeAddress = revokeAddress;
    }

    /**
     * Set the revoke transaction id
     * @param revokeTxId revoke transaction id
     */
    public void setRevokeTxId(String revokeTxId) {
        this.revokeTxId = revokeTxId;
    }

    /**
     * Returns the revoke transaction id
     * @return the revoke transaction id
     */
    public String getRevokeTxId() {
        return revokeTxId;
    }

    /**
     * Returns the creation time
     * @return the creation time
     */
    public long getCreationTime() {
        return creationTime;
    }

    /**
     * Set the creation time
     * @param creationTime the creation time
     */
    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "provider address: " + providerAddress + "; user address: " + userAddress + "; bitmask: " + bitmask +
                "; revoke address: " + revokeAddress + "; revokeTxId: " + revokeTxId + "; creationTime: " + creationTime;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof ProviderChannel))
            return false;

        if (this == object)
            return true;

        ProviderChannel providerChannel = (ProviderChannel) object;

        return Objects.equals(providerAddress, providerChannel.providerAddress) &&
                Objects.equals(userAddress, providerChannel.userAddress) &&
                Objects.equals(revokeAddress, providerChannel.revokeAddress) &&
                Objects.equals(bitmask, providerChannel.bitmask) &&
                Objects.equals(revokeTxId, providerChannel.revokeTxId) &&
                creationTime == providerChannel.creationTime;
    }

    @Override
    public int hashCode() {

        return Objects.hash(providerAddress, userAddress, revokeAddress, bitmask, revokeTxId, creationTime);

    }

}
