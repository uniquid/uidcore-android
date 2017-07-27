package com.uniquid.uidcore_android.provider;

import com.uniquid.uidcore_android.exception.RegisterException;

import java.util.List;

/**
 * Data Access Object pattern for Provider Channel.
 *
 * Is used to separate low level data accessing API from high level business services.
 *
 * @author Beatrice Formai
 */

public interface ProviderRegister {

    /**
     * Returns a List containing all the {@code ProviderChannel} present in the data store.
     * In case no {@code ProviderChannel} is present an empty list is returned.
     * @return a List containing all the {@code ProviderChannel} present in the data store or an empty List.
     * @throws RegisterException in case a problem occurs.
     */
    public List<ProviderChannel> getAllChannels() throws RegisterException;

    /**
     * Return an {@code ProviderChannel} from its user address or null if no channel is found.
     * @param userAddress the address of the user
     * @return an {@code ProviderChannel} from its user address or null if no channel is found.
     * @throws RegisterException in case a problem occurs.
     */
    public ProviderChannel getChannelByUserAddress(String userAddress) throws RegisterException;

    /**
     * Return an {@code ProviderChannel} from its revoker address id or null if no channel is found.
     * @param revokerAddress the revoker address
     * @return an {@code ProviderChannel} from its revoker address id or null if no channel is found.
     * @throws RegisterException in case a problem occurs.
     */
    public ProviderChannel getChannelByRevokeAddress(String revokerAddress) throws RegisterException;

    /**
     * Return an {@code ProviderChannel} from its revoke transaction id or null if no channel is found.
     * @param revokeTxId the revoke transaction id
     * @return an {@code ProviderChannel} from its revoke transaction id or null if no channel is found.
     * @throws RegisterException in case a problem occurs.
     */
    public ProviderChannel getChannelByRevokeTxId(String revokeTxId) throws RegisterException;

    /**
     * Creates an {@code ProviderChannel} by persisting its content in the data store.
     * @param providerChannel the Provider Channel to persist.
     * @throws RegisterException in case a problem occurs.
     */
    public void insertChannel(ProviderChannel providerChannel) throws RegisterException;

    /**
     * Deletes an {@code ProviderChannel} from the data store.
     * @param providerChannel the Provider Channel to delete.
     * @throws RegisterException in case a problem occurs.
     */
    public void deleteChannel(ProviderChannel providerChannel) throws RegisterException;
}
