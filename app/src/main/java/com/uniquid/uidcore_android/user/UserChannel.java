package com.uniquid.uidcore_android.user;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents an user contract fetched from the blockchain.
 * An user contract is composed by a provider name, a provider address, an user address, a bitmask, a revoke address and
 * a revoke tx id
 *
 * @author Beatrice Formai
 */

public class UserChannel implements Serializable, Comparable<Object> {

    private static final long serialVersionUID = 1L;
    private String providerName;
    private String providerAddress;
    private String userAddress;
    private String bitmask;
    private String revokeAddress;
    private String revokeTxId;

    /**
     * Creates an empty instance
     */
    public UserChannel() {
        // DO NOTHING
    }

    /**
     * Creates an instance from provider name, provider address, user address and bitmask.
     *
     * @param providerName the name of the provider
     * @param providerAddress the address of the provider
     * @param userAddress the address of the user
     * @param bitmask the string representing the bitmask
     */
    public UserChannel(String providerName, String providerAddress, String userAddress, String bitmask){
        this.providerName = providerName;
        this.providerAddress = providerAddress;
        this.userAddress = userAddress;
        this.bitmask = bitmask;
    }

    /**
     * Set the provider name
     * @param providerName the provider name
     */
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    /**
     * Retrieve the provider name
     * @return the provider name
     */
    public String getProviderName() {
        return providerName;
    }

    /**
     * Set the provider address
     * @param providerAddress the provider address
     */
    public void setProviderAddress(String providerAddress) {
        this.providerAddress = providerAddress;
    }

    /**
     * Retrieve the provider address
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
     * Retrieve the user address
     * @return the user address
     */
    public String getUserAddress() {
        return userAddress;
    }

    /**
     * Retrieve the bitmask
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
     * Retrieve the revoke address
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
     * @param revokeTxId the revoke transaction id
     */
    public void setRevokeTxId(String revokeTxId) {
        this.revokeTxId = revokeTxId;
    }

    /**
     * Retrieve the revoke transaction id
     * @return the revoke transaction id
     */
    public String getRevokeTxId() {
        return revokeTxId;
    }

    @Override
    public String toString() {
        return "provider address: " + providerAddress + "; user address: " + userAddress + "; bitmask: " + bitmask +
                "; revoke address: " + revokeAddress + "; revokeTxId: " + revokeTxId;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof UserChannel))
            return false;

        if (this == object)
            return true;

        UserChannel userChannel = (UserChannel) object;

        return Objects.equals(providerName, userChannel.providerName) &&
                Objects.equals(providerAddress, userChannel.providerAddress) &&
                Objects.equals(userAddress, userChannel.userAddress) &&
                Objects.equals(bitmask, userChannel.bitmask) &&
                Objects.equals(revokeAddress, userChannel.revokeAddress) &&
                Objects.equals(revokeTxId, userChannel.revokeTxId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(providerName, providerAddress, userAddress, bitmask, revokeAddress, revokeTxId);

    }

    @Override
    public int compareTo(Object object) {

        UserChannel userChannel = (UserChannel) object;

        return compareStrings(providerName, userChannel.getProviderName());
    }

    private static int compareStrings(String first, String second) {
        final int BEFORE = -1;
        final int EQUALS = 0;
        final int AFTER = 1;

        if (first == null && second == null) {

            return EQUALS;

        } else if (first == null && second != null) {

            return BEFORE;

        } else if (first != null && second != null) {

            return first.compareTo(second);

        } else /* if (first != null && second == null) */ {

            return AFTER;
        }

    }

}
