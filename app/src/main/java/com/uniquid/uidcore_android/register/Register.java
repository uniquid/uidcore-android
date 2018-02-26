package com.uniquid.uidcore_android.register;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.uniquid.register.exception.RegisterException;
import com.uniquid.register.provider.ProviderChannel;
import com.uniquid.register.provider.ProviderRegister;
import com.uniquid.register.user.UserChannel;
import com.uniquid.register.user.UserRegister;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.uniquid.uidcore_android.register.SQLiteHelper.TABLE_PROVIDER;
import static com.uniquid.uidcore_android.register.SQLiteHelper.TABLE_USER;

/**
 * Class implementation of {@code UserRegister} and {@code ProviderRegister} to manage {@code UserChannel} and {@code ProviderChannel} database records
 *
 * @author Beatrice Formai
 */

public class Register implements UserRegister, ProviderRegister {

    private static final Logger LOGGER = LoggerFactory.getLogger(Register.class);


    protected AndroidDataSource androidDataSource;

    public Register(AndroidDataSource androidDataSource) {
        this.androidDataSource = androidDataSource;
    }

    /**
     * Delete all rows from {@code UserChannel} table
     * @throws RegisterException in case an error occurs
     * */
    void cleanUserTable() throws RegisterException {

        LOGGER.debug("Cleaning table USER...");

        try (SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper =
                     androidDataSource.getSQLiteDatabaseWrapper()) {

            SQLiteDatabase db = sqLiteDatabaseWrapper.getSQLiteDatabase();

            db.delete(TABLE_USER, "1", null);
        } catch (Throwable t) {
            LOGGER.error("Exception while cleaning table USER", t);
            throw new RegisterException("Exception", t);
        }
    }

    /**
     * Delete all rows from {@code ProviderChannel} table
     * @throws RegisterException in case an error occurs
     * */
    void cleanProviderTable() throws RegisterException {

        LOGGER.debug("Cleaning table PROVIDER...");

        try (SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper =
                     androidDataSource.getSQLiteDatabaseWrapper()) {

            SQLiteDatabase db = sqLiteDatabaseWrapper.getSQLiteDatabase();

            db.delete(TABLE_PROVIDER, "1", null);
        } catch (Throwable t) {
            LOGGER.error("Exception while cleaning table PROVIDER", t);
            throw new RegisterException("Exception", t);
        }
    }

    /**
     * Return a List containing all the {@code UserChannel} present in the data store.
     * In case no {@code UserChannel} is present an empty list is returned.
     * @return a List containing all the {@code UserChannel} present in the data store or an empty list.
     * @throws RegisterException in case an error occurs
     * */
    public List<UserChannel> getAllUserChannels() throws RegisterException {

        try {

            try (SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper =
                         androidDataSource.getSQLiteDatabaseWrapper()) {

                SQLiteDatabase db = sqLiteDatabaseWrapper.getSQLiteDatabase();

                List<UserChannel> channels = new ArrayList<>();
                Cursor cursor = db.rawQuery("select * from " + TABLE_USER, null);
                if (cursor.moveToFirst()) {
                    do {
                        channels.add(createUserChannelFromCursor(cursor));
                    } while (cursor.moveToNext());
                }
                cursor.close();
                return channels;
            }
        } catch (Throwable t) {
            throw new RegisterException("Exception", t);
        }
    }

    /**
     * Return a {@code UserChannel} with the specified provider name.
     * @param providerName the name of the other machine.
     * @return the {@code UserChannel} with the specified name or null.
     * @throws RegisterException if providerName is empty or null, or an error occurs.
     * */
    public UserChannel getChannelByName(String providerName) throws RegisterException {

        if(providerName == null || providerName.isEmpty())
            throw new RegisterException("name is not valid");

        try {

            try (SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper = androidDataSource.getSQLiteDatabaseWrapper()) {

                SQLiteDatabase db = sqLiteDatabaseWrapper.getSQLiteDatabase();

                String query = "select * from " + TABLE_USER +
                        " where " + SQLiteHelper.USER_CLM_PROVIDER_NAME + " = ?";

                try (Cursor cursor = db.rawQuery(
                        query,
                        new String[]{providerName})) {
                    if (cursor.moveToFirst()) {
                        return createUserChannelFromCursor(cursor);
                    } else {
                        return null;
                    }
                }
            }
        } catch (Throwable t) {
            throw new RegisterException("Exception", t);
        }
    }

    /**
     * Return a {@code UserChannel} with the specified provider address.
     * @param address the address of the provider.
     * @return a {@code UserChannel} that involves the specified provider or null if there is no {@code UserChannel} with the specified provider address
     * @throws RegisterException if address is null or empty, or an error occurs.
     * */
    public UserChannel getChannelByProviderAddress(String address) throws RegisterException {

        if(address == null || address.isEmpty())
            throw new RegisterException("name is not valid");

        try {

            try (SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper =
                         androidDataSource.getSQLiteDatabaseWrapper()) {

                SQLiteDatabase db = sqLiteDatabaseWrapper.getSQLiteDatabase();

                String query = "select * from " + TABLE_USER +
                        " where " + SQLiteHelper.USER_CLM_PROVIDER_ADDRESS + " = ?";
                try(Cursor cursor = db.rawQuery(
                        query,
                        new String[]{address})) {
                    if (cursor.moveToFirst()) {
                        return createUserChannelFromCursor(cursor);
                    } else {
                        return null;
                    }
                }
            }

        } catch (Throwable t) {

            throw new RegisterException("Exception", t);

        }
    }

    /**
     * Return a {@code UserChannel} by the transaction identifier of the revoke contract
     * @param revokeTxId the transaction identifier of the revoke contract
     * @return {@code UserChannel} that involves the specified revokeTxId or null if there is no record with the specified revoketxId
     * @throws RegisterException id revokeTxid is null or empty, or an error occurs
     * */
    @Override
    public UserChannel getUserChannelByRevokeTxId(String revokeTxId) throws RegisterException {

        if(revokeTxId == null || revokeTxId.isEmpty())
            throw new RegisterException("revokeTxId is not valid");

        try {
            try (SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper =
                         androidDataSource.getSQLiteDatabaseWrapper()) {
                SQLiteDatabase db = sqLiteDatabaseWrapper.getSQLiteDatabase();
                String query = "select * from " + TABLE_USER +
                        " where " + SQLiteHelper.USER_CLM_REVOKE_TX_ID + " = ?";
                try (Cursor cursor = db.rawQuery(
                        query,
                        new String[]{revokeTxId})) {
                    if (cursor.moveToFirst()) {
                        return createUserChannelFromCursor(cursor);
                    } else {
                        return null;
                    }
                }
            }
        } catch (Throwable t) {
            throw new RegisterException("Exception", t);
        }
    }

    /**
     * Return a {@code UserChannel} by the address of the revoker
     * @param revokeAddress the address of the revoker
     * @return {@code UserChannel} object that involves the specified revokeAddress, or null if there is no {@code UserChannel} with the specified revokeAddress
     * @throws RegisterException if revokeAddress is null or empty, or an error occurs
     * */
    @Override
    public UserChannel getUserChannelByRevokeAddress(String revokeAddress) throws RegisterException {

        if(revokeAddress == null || revokeAddress.isEmpty())
            throw new RegisterException("revokeAddress is not valid");

        try {
            try (SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper = androidDataSource.getSQLiteDatabaseWrapper()) {
                SQLiteDatabase db = sqLiteDatabaseWrapper.getSQLiteDatabase();
                String query = "select * from " + TABLE_USER + " where " +
                        SQLiteHelper.USER_CLM_REVOKE_ADDRESS + " = ?";
                try(Cursor cursor = db.rawQuery(
                        query,
                        new String[]{revokeAddress})) {
                    if (cursor.moveToFirst()) {
                        return createUserChannelFromCursor(cursor);
                    } else {
                        return null;
                    }
                }
            }
        } catch (Throwable t) {
            throw new RegisterException("Exception", t);
        }
    }

    /**
     * Creates a {@code UserChannel} by persisting its content in the data store.
     * @param userChannel the {@code UserChannel} to persist.
     * @throws RegisterException in case a problem occurs or the specified {@code UserChannel} is already present or userChannel is null
     * */
    public void insertChannel(UserChannel userChannel) throws RegisterException {

        if(userChannel == null)
            throw new RegisterException("userchannel is null!");

        try {
            try (SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper =
                         androidDataSource.getSQLiteDatabaseWrapper()) {
                SQLiteDatabase db = sqLiteDatabaseWrapper.getSQLiteDatabase();

                ContentValues values = new ContentValues();
                values.put(SQLiteHelper.USER_CLM_PROVIDER_NAME, userChannel.getProviderName());
                values.put(SQLiteHelper.USER_CLM_PROVIDER_ADDRESS, userChannel.getProviderAddress());
                values.put(SQLiteHelper.USER_CLM_USER_ADDRESS, userChannel.getUserAddress());
                values.put(SQLiteHelper.USER_CLM_BITMASK, userChannel.getBitmask());
                values.put(SQLiteHelper.USER_CLM_REVOKE_ADDRESS, userChannel.getRevokeAddress());
                values.put(SQLiteHelper.USER_CLM_REVOKE_TX_ID, userChannel.getRevokeTxId());
                long db_index = db.insert(TABLE_USER, null, values);
                if (db_index < 0)
                    throw new RegisterException("Error inserting new channel");
            }

        } catch (Throwable t) {
            throw new RegisterException("Exception", t);
        }
    }

    /**
     * Deletes a {@code UserChannel} that matches the specified value.
     * @param userChannel the {@code UserChannel} to delete from the data store.
     * @throws RegisterException in case a problem occurs or the specified {@code UserChannel} doesn't exist or is null
     */
    public void deleteChannel(UserChannel userChannel) throws RegisterException {

        if(userChannel == null)
            throw new RegisterException("userchannel is null!");

        try {

            try (SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper =
                         androidDataSource.getSQLiteDatabaseWrapper()) {

                SQLiteDatabase db = sqLiteDatabaseWrapper.getSQLiteDatabase();

                int d = db.delete(TABLE_USER, SQLiteHelper.USER_CLM_PROVIDER_NAME + " = ? AND " +
                                SQLiteHelper.USER_CLM_PROVIDER_ADDRESS + " = ? AND " + SQLiteHelper.USER_CLM_USER_ADDRESS + " = ?",
                        new String[]{userChannel.getProviderName(), userChannel.getProviderAddress(), userChannel.getUserAddress()});
                if (d == 0)
                    throw new RegisterException("Channel not present");
            }

        } catch (Throwable t) {

            throw new RegisterException("Exception", t);

        }
    }


    /*
    PROVIDER
    */
    /**
     * Returns a List containing all the {@code ProviderChannel} present in the data store.
     * In case no {@code ProviderChannel} is present an empty list is returned.
     * @return a List containing all the {@code ProviderChannel} present in the data store or an empty List
     * @throws RegisterException in case an error occurs
     */
    public List<ProviderChannel> getAllChannels() throws RegisterException {

        try {
            try (SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper =
                         androidDataSource.getSQLiteDatabaseWrapper()) {

                SQLiteDatabase db = sqLiteDatabaseWrapper.getSQLiteDatabase();
                List<ProviderChannel> channels = new ArrayList<>();

                String query = "select * from " + SQLiteHelper.TABLE_PROVIDER + " order by " + SQLiteHelper.PROVIDER_CLM_CREATION_TIME + " desc";
                try(Cursor cursor = db.rawQuery(query,
						null)) {
                    if (cursor.moveToFirst()) {
                        do {
                            channels.add(createProviderChannelFromCursor(cursor));
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                }
                return channels;
            }
        } catch (Throwable t) {
            throw new RegisterException("Exception", t);
        }
    }

    /**
     * Return a {@code ProviderChannel} with the specified user address
     * @param userAddress the address of the other machine
     * @return the {@code ProviderChannel} with the specified user address or null if there is no {@code ProviderChannel} with the specified user address
     * @throws RegisterException if userAddress is null or empty, or in case an error occurs
     * */
    public ProviderChannel getChannelByUserAddress(String userAddress) throws RegisterException {

        if(userAddress == null || userAddress.isEmpty())
            throw new RegisterException("address is not valid");

        try {
            try (SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper =
                         androidDataSource.getSQLiteDatabaseWrapper()) {
                SQLiteDatabase db = sqLiteDatabaseWrapper.getSQLiteDatabase();
                String query = "select * from " + SQLiteHelper.TABLE_PROVIDER +
                        " where " + SQLiteHelper.PROVIDER_CLM_USER_ADDRESS + " = ?";
                try(Cursor cursor = db.rawQuery(query,
                        new String[]{userAddress})) {
                    if (cursor.moveToFirst()) {
                        return createProviderChannelFromCursor(cursor);
                    } else {
                        return null;
                    }
                }
            }
        } catch (Throwable t) {
            throw new RegisterException("Exception", t);
        }
    }

    /**
     * Return a {@code ProviderChannel} with the specified revoke address.
     * @param revokeAddress the address of the revoker machine.
     * @return the {@code ProviderChannel} with the specified revoke address or null if there is no {@code ProviderChannel} with the specified revoke address.
     * @throws RegisterException if revokeAddress is null or empty, or in case an error occurs.
     * */
    @Override
    public ProviderChannel getChannelByRevokeAddress(String revokeAddress) throws RegisterException {

        if(revokeAddress == null || revokeAddress.isEmpty())
            throw new RegisterException("revokeAddress is not valid");

        try {
            try (SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper =
                         androidDataSource.getSQLiteDatabaseWrapper()) {
                SQLiteDatabase db = sqLiteDatabaseWrapper.getSQLiteDatabase();
                String query = "select * from " + SQLiteHelper.TABLE_PROVIDER +
                        " where " + SQLiteHelper.PROVIDER_CLM_REVOKE_ADDRESS + " = ?";

                try(Cursor cursor = db.rawQuery(query,
                        new String[]{revokeAddress})) {
                    if (cursor.moveToFirst()) {
                        return createProviderChannelFromCursor(cursor);
                    } else {
                        return null;
                    }
                }
            }
        } catch (Throwable t) {
            throw new RegisterException("Exception", t);
        }
    }

    /**
     * Return an {@code ProviderChannel} from its revoke transaction id or null if no channel is found
     * @param revokeTxId the revoke transaction id
     * @return a {@code ProviderChannel} from its revoke transaction id or null if no channel is found
     * @throws RegisterException if revokeTxId is null or empty, or in case a problem occurs
     */
    @Override
    public ProviderChannel getChannelByRevokeTxId(String revokeTxId) throws RegisterException {

        if(revokeTxId == null || revokeTxId.isEmpty())
            throw new RegisterException("revokeTxId is not valid");

        try {
            try (SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper =
                         androidDataSource.getSQLiteDatabaseWrapper()) {

                SQLiteDatabase db = sqLiteDatabaseWrapper.getSQLiteDatabase();
                String query = "select * from " + SQLiteHelper.TABLE_PROVIDER +
                        " where " + SQLiteHelper.PROVIDER_CLM_REVOKE_TX_ID + " = ?";
                try (Cursor cursor = db.rawQuery(query,
                        new String[]{revokeTxId})){
                    if (cursor.moveToFirst()) {
                        return createProviderChannelFromCursor(cursor);
                    } else {
                        return null;
                    }
                }
            }
        } catch (Throwable t) {
            throw new RegisterException("Exception", t);
        }
    }

    /**
     * Creates a {@code ProviderChannel} by persisting its content in the data store
     * @param providerChannel the {@code ProviderChannel} to persist
     * @throws RegisterException in case a problem occurs or the specified {@code ProviderChannel} is already present
     * */
    public void insertChannel(ProviderChannel providerChannel) throws RegisterException{

        if(providerChannel == null)
            throw new RegisterException("providerChannel is null!");

        try {
            try (SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper =
                         androidDataSource.getSQLiteDatabaseWrapper()) {

                SQLiteDatabase db = sqLiteDatabaseWrapper.getSQLiteDatabase();

                ContentValues values = new ContentValues();
                values.put(SQLiteHelper.PROVIDER_CLM_PROVIDER_ADDRESS, providerChannel.getProviderAddress());
                values.put(SQLiteHelper.PROVIDER_CLM_USER_ADDRESS, providerChannel.getUserAddress());
                values.put(SQLiteHelper.PROVIDER_CLM_BITMASK, providerChannel.getBitmask());
                values.put(SQLiteHelper.PROVIDER_CLM_REVOKE_ADDRESS, providerChannel.getRevokeAddress());
                values.put(SQLiteHelper.PROVIDER_CLM_REVOKE_TX_ID, providerChannel.getRevokeTxId());
                values.put(SQLiteHelper.PROVIDER_CLM_CREATION_TIME, providerChannel.getCreationTime());
                values.put(SQLiteHelper.PROVIDER_CLM_SINCE, providerChannel.getSince());
                values.put(SQLiteHelper.PROVIDER_CLM_UNTIL, providerChannel.getUntil());

                long db_index = db.insert(SQLiteHelper.TABLE_PROVIDER, null, values);
                if (db_index < 0)
                    throw new RegisterException("Exception while insertChannel()");
            }
        } catch (Throwable t) {

            throw new RegisterException("Exception while insertChannel()", t);

        }
    }

    /**
     * Deletes a {@code ProviderChannel} that matches the specified value
     * @param providerChannel the {@code ProviderChannel} to delete from the data store
     * @throws RegisterException in case a problem occurs or the specified {@code ProviderChannel} doesn't exist
     */
    public void deleteChannel(ProviderChannel providerChannel) throws RegisterException {

        if(providerChannel == null)
            throw new RegisterException("providerChannel is null!");

        try {
            try (SQLiteHelperPool.SQLiteDatabaseWrapper sqLiteDatabaseWrapper =
                         androidDataSource.getSQLiteDatabaseWrapper()) {

                SQLiteDatabase db = sqLiteDatabaseWrapper.getSQLiteDatabase();

                db.delete(SQLiteHelper.TABLE_PROVIDER,
                        SQLiteHelper.PROVIDER_CLM_PROVIDER_ADDRESS + " = ?",
                        new String[]{providerChannel.getProviderAddress()});
            }
        } catch (Throwable t) {
            throw new RegisterException("Exception", t);
        }
    }

    private UserChannel createUserChannelFromCursor(Cursor cursor) {
        UserChannel userChannel = new UserChannel();
        userChannel.setProviderName(cursor.getString(cursor.getColumnIndex(SQLiteHelper.USER_CLM_PROVIDER_NAME)));
        userChannel.setProviderAddress(cursor.getString(cursor.getColumnIndex(SQLiteHelper.USER_CLM_PROVIDER_ADDRESS)));
        userChannel.setUserAddress(cursor.getString(cursor.getColumnIndex(SQLiteHelper.USER_CLM_USER_ADDRESS)));
        userChannel.setBitmask(cursor.getString(cursor.getColumnIndex(SQLiteHelper.USER_CLM_BITMASK)));
        userChannel.setRevokeAddress(cursor.getString(cursor.getColumnIndex(SQLiteHelper.USER_CLM_REVOKE_ADDRESS)));
        userChannel.setRevokeTxId(cursor.getString(cursor.getColumnIndex(SQLiteHelper.USER_CLM_REVOKE_TX_ID)));
        return userChannel;
    }

    private ProviderChannel createProviderChannelFromCursor(Cursor cursor) {
        ProviderChannel providerChannel = new ProviderChannel();
        providerChannel.setProviderAddress(cursor.getString(0));
        providerChannel.setUserAddress(cursor.getString(1));
        providerChannel.setBitmask(cursor.getString(2));
        providerChannel.setRevokeAddress(cursor.getString(3));
        providerChannel.setRevokeTxId(cursor.getString(4));
        providerChannel.setCreationTime(cursor.getInt(5));
        providerChannel.setSince(cursor.getInt(6));
        providerChannel.setUntil(cursor.getInt(7));
        return providerChannel;
    }
}