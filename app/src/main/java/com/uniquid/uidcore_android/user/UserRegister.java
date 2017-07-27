package com.uniquid.uidcore_android.user;

import com.uniquid.uidcore_android.exception.RegisterException;

import java.util.List;

/**
 * Data Access Object pattern for User Channel.
 *
 * Is used to separate low level data accessing API from high level business services.
 *
 * @author Beatrice Formai
 */

public interface UserRegister {

    /**
     * Returns a List containing all the {@code UserChannel} present in the data store.
     * In case no {@code UserChannel} is present an empty list is returned.
     * @return a List containing all the {@code UserChannel} present in the data store or an empty List.
     * @throws RegisterException in case a problem occurs.
     */
    List<UserChannel> getAllUserChannels() throws RegisterException;

    /**
     * Return an {@code UserChannel} from its provider name or null if no channel is found.
     * @param providerName the name of the provider
     * @return an {@code UserChannel} from its provider name or null if no channel is found.
     * @throws RegisterException in case a problem occurs.
     */
    public UserChannel getChannelByName(String providerName) throws RegisterException;

    /**
     * Return an {@code UserChannel} from its provider address or null if no channel is found.
     * @param providerAddress the address of the provider
     * @return an {@code UserChannel} from its provider address or null if no channel is found.
     * @throws RegisterException in case a problem occurs.
     */
    UserChannel getChannelByProviderAddress(String providerAddress) throws RegisterException;

    /**
     * Return an {@code UserChannel} from its revoke transaction id or null if no channel is found.
     * @param revokeTxId the revoke transaction id
     * @return an {@code UserChannel} from its revoke transaction id or null if no channel is found.
     * @throws RegisterException in case a problem occurs.
     */
    UserChannel getUserChannelByRevokeTxId(String revokeTxId) throws RegisterException;

    /**
     * Return an {@code UserChannel} from its revoker address id or null if no channel is found.
     * @param revokerAddress the revoker address
     * @return an {@code UserChannel} from its revoker address id or null if no channel is found.
     * @throws RegisterException in case a problem occurs.
     */
    public UserChannel getUserChannelByRevokeAddress(String revokerAddress) throws RegisterException;

    /**
     * Creates an {@code UserChannel} by persisting its content in the data store.
     * @param userChannel the User Channel to persist.
     * @throws RegisterException in case a problem occurs.
     */
    void insertChannel(UserChannel userChannel) throws RegisterException;

    /**
     * Deletes an {@code UserChannel} from the data store.
     * @param userChannel the User Channel to delete.
     * @throws RegisterException in case a problem occurs.
     */
    void deleteChannel(UserChannel userChannel) throws RegisterException;

}
