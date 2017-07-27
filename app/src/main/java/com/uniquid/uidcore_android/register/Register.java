package com.uniquid.uidcore_android.register;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.uniquid.uidcore_android.exception.RegisterException;
import com.uniquid.uidcore_android.provider.ProviderChannel;
import com.uniquid.uidcore_android.provider.ProviderRegister;
import com.uniquid.uidcore_android.user.UserChannel;
import com.uniquid.uidcore_android.user.UserRegister;

import java.util.ArrayList;
import java.util.List;

/**
 * Class implementation of {@code UserRegister} and {@code ProviderRegister} to manage {@code UserChannel} and {@code ProviderChannel} database records
 *
 * @author Beatrice Formai
 */

public class Register implements UserRegister, ProviderRegister {
    private static SQLiteDatabase db;
    private SQLiteHelper dbHelper;

    public Register(android.content.Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public Register(android.content.Context context, String name) {
        dbHelper = new SQLiteHelper(context, name);
    }

    /**
     * Return a List containing all the {@code UserChannel} present in the data store.
     * In case no {@code UserChannel} is present an empty list is returned.
     * @return a List containing all the {@code UserChannel} present in the data store or an empty list.
     * */
    public List<UserChannel> getAllUserChannels(){
        db = dbHelper.getReadableDatabase();
        List<UserChannel> channels = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + SQLiteHelper.TABLE_USER, null);
        if(cursor.moveToFirst()){
            do{
                UserChannel userChannel = new UserChannel();
                userChannel.setProviderName(cursor.getString(0));
                userChannel.setProviderAddress(cursor.getString(1));
                userChannel.setUserAddress(cursor.getString(2));
                userChannel.setBitmask(cursor.getString(3));
                userChannel.setRevokeAddress(cursor.getString(4));
                userChannel.setRevokeTxId(cursor.getString(5));
                channels.add(userChannel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return channels;
    }

    /**
     * Return a {@code UserChannel} with the specified provider name.
     * @param providerName the name of the other machine.
     * @return the {@code UserChannel} with the specified name.
     * @throws RegisterException if there is no {@code UserChannel} with the specified name.
     * */
    public UserChannel getChannelByName(String providerName) throws RegisterException {
        UserChannel userChannel = new UserChannel();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + SQLiteHelper.TABLE_USER +
                " where " + SQLiteHelper.USER_CLM_PROVIDER_NAME + " = ?", new String[]{providerName});
        if(cursor.moveToFirst()){
            userChannel.setProviderName(cursor.getString(0));
            userChannel.setProviderAddress(cursor.getString(1));
            userChannel.setUserAddress(cursor.getString(2));
            userChannel.setBitmask(cursor.getString(3));
            userChannel.setRevokeAddress(cursor.getString(4));
            userChannel.setRevokeTxId(cursor.getString(5));
            cursor.close();
        } else {
            throw new RegisterException("Doesn't exist any record with specified name");
        }
        return userChannel;
    }

    /**
     * Return a {@code UserChannel} with the specified provider address.
     * @param address the address of the provider.
     * @return a {@code UserChannel} that involves the specified provider.
     * @throws RegisterException if there is no {@code UserChannel} with the specified provider address.
     * */
    public UserChannel getChannelByProviderAddress(String address) throws RegisterException {
        UserChannel userChannel = new UserChannel();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + SQLiteHelper.TABLE_USER +
                        " where " + SQLiteHelper.USER_CLM_PROVIDER_ADDRESS + " = ?",
                new String[]{address});
        if(cursor.moveToFirst()){
            userChannel.setProviderName(cursor.getString(0));
            userChannel.setProviderAddress(cursor.getString(1));
            userChannel.setUserAddress(cursor.getString(2));
            userChannel.setBitmask(cursor.getString(3));
            userChannel.setRevokeAddress(cursor.getString(4));
            userChannel.setRevokeTxId(cursor.getString(5));
            cursor.close();
        } else {
            throw new RegisterException("Doesn't exist any record with specified provider address");
        }
        return userChannel;
    }

    /**
     * Return a {@code UserChannel} by the transaction identifier of the revoke contract
     * @param revokeTxId the transaction identifier of the revoke contract
     * @return {@code UserChannel} that involves the specified revokeTxId
     * @throws RegisterException if there is no record with the specified revoketxId
     * */
    @Override
    public UserChannel getUserChannelByRevokeTxId(String revokeTxId) throws RegisterException {
        UserChannel userChannel = new UserChannel();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + SQLiteHelper.TABLE_USER +
                        " where " + SQLiteHelper.USER_CLM_REVOKE_TX_ID + " = ?",
                new String[]{revokeTxId});
        if(cursor.moveToFirst()){
            userChannel.setProviderName(cursor.getString(0));
            userChannel.setProviderAddress(cursor.getString(1));
            userChannel.setUserAddress(cursor.getString(2));
            userChannel.setBitmask(cursor.getString(3));
            userChannel.setRevokeAddress(cursor.getString(4));
            userChannel.setRevokeTxId(cursor.getString(5));
            cursor.close();
        } else {
            throw new RegisterException("Doesn't exist any record with specified revoke txId");
        }
        return userChannel;
    }

    /**
     * Return a {@code UserChannel} by the address of the revoker
     * @param revokeAddress the address of the revoker
     * @return {@code UserChannel} object that involves the specified revokeAddress
     * @throws RegisterException if there is no {@code UserChannel} with the specified revokeAddress
     * */
    @Override
    public UserChannel getUserChannelByRevokeAddress(String revokeAddress) throws RegisterException {
        UserChannel userChannel = new UserChannel();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + SQLiteHelper.TABLE_USER +
                        " where " + SQLiteHelper.USER_CLM_REVOKE_ADDRESS + " = ?",
                new String[]{revokeAddress});
        if(cursor.moveToFirst()){
            userChannel.setProviderName(cursor.getString(0));
            userChannel.setProviderAddress(cursor.getString(1));
            userChannel.setUserAddress(cursor.getString(2));
            userChannel.setBitmask(cursor.getString(3));
            userChannel.setRevokeAddress(cursor.getString(4));
            userChannel.setRevokeTxId(cursor.getString(5));
            cursor.close();
        } else {
            throw new RegisterException("Doesn't exist any record with the specified name");
        }
        return userChannel;
    }

    /**
     * Creates a {@code UserChannel} by persisting its content in the data store.
     * @param userChannel the {@code UserChannel} to persist.
     * @throws RegisterException in case a problem occurs or the specified {@code UserChannel} is already present
     * */
    public void insertChannel(UserChannel userChannel) throws RegisterException {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.USER_CLM_PROVIDER_NAME, userChannel.getProviderName());
        values.put(SQLiteHelper.USER_CLM_PROVIDER_ADDRESS, userChannel.getProviderAddress());
        values.put(SQLiteHelper.USER_CLM_USER_ADDRESS, userChannel.getUserAddress());
        values.put(SQLiteHelper.USER_CLM_BITMASK, userChannel.getBitmask());
        values.put(SQLiteHelper.USER_CLM_REVOKE_ADDRESS, userChannel.getRevokeAddress());
        values.put(SQLiteHelper.USER_CLM_REVOKE_TX_ID, userChannel.getRevokeTxId());
        long db_index = db.insert(SQLiteHelper.TABLE_USER, null, values);
        if(db_index < 0)
            throw new RegisterException("Error inserting new channel");
    }

    /**
     * Deletes a {@code UserChannel} that matches the specified value.
     * @param userChannel the {@code UserChannel} to delete from the data store.
     * @throws RegisterException in case a problem occurs or the specified {@code UserChannel} doesn't exist
     */
    public void deleteChannel(UserChannel userChannel) throws RegisterException {
        db = dbHelper.getWritableDatabase();
        int d = db.delete(SQLiteHelper.TABLE_USER, SQLiteHelper.USER_CLM_PROVIDER_NAME + " = ? AND " +
                        SQLiteHelper.USER_CLM_PROVIDER_ADDRESS + " = ? AND " + SQLiteHelper.USER_CLM_USER_ADDRESS + " = ?" ,
                new String[]{userChannel.getProviderName(), userChannel.getProviderAddress(), userChannel.getUserAddress()});
        if(d == 0)
            throw new RegisterException("Channel not present");
    }


    /*
    PROVIDER
    */
    /**
     * Returns a List containing all the {@code ProviderChannel} present in the data store.
     * In case no {@code ProviderChannel} is present an empty list is returned.
     * @return a List containing all the {@code ProviderChannel} present in the data store or an empty List.
     */
    public List<ProviderChannel> getAllChannels(){
        List<ProviderChannel> channels = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + SQLiteHelper.TABLE_PROVIDER, null);
        if(cursor.moveToFirst()){
            do{
                ProviderChannel channel = new ProviderChannel();
                channel.setProviderAddress(cursor.getString(0));
                channel.setUserAddress(cursor.getString(1));
                channel.setBitmask(cursor.getString(2));
                channel.setRevokeAddress(cursor.getString(3));
                channel.setRevokeTxId(cursor.getString(4));
                channel.setCreationTime(cursor.getInt(5));
                channels.add(channel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return channels;
    }

    /**
     * Return a {@code ProviderChannel} with the specified user address.
     * @param userAddress the address of the other machine.
     * @return the {@code ProviderChannel} with the specified user address.
     * @throws RegisterException if there is no {@code ProviderChannel} with the specified user address.
     * */
    public ProviderChannel getChannelByUserAddress(String userAddress) throws RegisterException {
        ProviderChannel providerChannel = new ProviderChannel();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + SQLiteHelper.TABLE_PROVIDER +
                        " where " + SQLiteHelper.PROVIDER_CLM_USER_ADDRESS + " = ?",
                new String[]{userAddress});
        if(cursor.moveToFirst()){
            providerChannel.setProviderAddress(cursor.getString(0));
            providerChannel.setUserAddress(cursor.getString(1));
            providerChannel.setBitmask(cursor.getString(2));
            providerChannel.setRevokeAddress(cursor.getString(3));
            providerChannel.setRevokeTxId(cursor.getString(4));
            providerChannel.setCreationTime(cursor.getInt(5));
            cursor.close();
        } else {
            throw new RegisterException("Doesn't exist any record with specified name");
        }
        return providerChannel;
    }

    /**
     * Return a {@code ProviderChannel} with the specified revoke address.
     * @param revokeAddress the address of the revoker machine.
     * @return the {@code ProviderChannel} with the specified revoke address or null if there is no {@code ProviderChannel} with the specified revoke address.
     * @throws RegisterException in case an error occurs.
     * */
    @Override
    public ProviderChannel getChannelByRevokeAddress(String revokeAddress) throws RegisterException {
        ProviderChannel providerChannel = new ProviderChannel();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + SQLiteHelper.TABLE_PROVIDER +
                        " where " + SQLiteHelper.PROVIDER_CLM_REVOKE_ADDRESS + " = ?",
                new String[]{revokeAddress});
        if(cursor.moveToFirst()){
            providerChannel.setProviderAddress(cursor.getString(0));
            providerChannel.setUserAddress(cursor.getString(1));
            providerChannel.setBitmask(cursor.getString(2));
            providerChannel.setRevokeAddress(cursor.getString(3));
            providerChannel.setRevokeTxId(cursor.getString(4));
            providerChannel.setCreationTime(cursor.getInt(5));
            cursor.close();
        } else {
            return null;
        }
        return providerChannel;
    }

    @Override
    public ProviderChannel getChannelByRevokeTxId(String revokeTxId) throws RegisterException {
        ProviderChannel providerChannel = new ProviderChannel();
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + SQLiteHelper.TABLE_PROVIDER +
                        " where " + SQLiteHelper.PROVIDER_CLM_REVOKE_TX_ID + " = ?",
                new String[]{revokeTxId});
        if(cursor.moveToFirst()){
            providerChannel.setProviderAddress(cursor.getString(0));
            providerChannel.setUserAddress(cursor.getString(1));
            providerChannel.setBitmask(cursor.getString(2));
            providerChannel.setRevokeAddress(cursor.getString(3));
            providerChannel.setRevokeTxId(cursor.getString(4));
            providerChannel.setCreationTime(cursor.getInt(5));
            cursor.close();
        } else {
            return null;
        }
        return providerChannel;
    }

    /**
     * Creates a {@code ProviderChannel} by persisting its content in the data store.
     * @param providerChannel the {@code ProviderChannel} to persist.
     * @throws RegisterException in case a problem occurs or the specified {@code ProviderChannel} is already present
     * */
    public void insertChannel(ProviderChannel providerChannel) throws RegisterException{
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.PROVIDER_CLM_PROVIDER_ADDRESS, providerChannel.getProviderAddress());
        values.put(SQLiteHelper.PROVIDER_CLM_USER_ADDRESS, providerChannel.getUserAddress());
        values.put(SQLiteHelper.PROVIDER_CLM_BITMASK, providerChannel.getBitmask());
        values.put(SQLiteHelper.PROVIDER_CLM_REVOKE_ADDRESS, providerChannel.getRevokeAddress());
        values.put(SQLiteHelper.PROVIDER_CLM_REVOKE_TX_ID, providerChannel.getRevokeTxId());
        values.put(SQLiteHelper.PROVIDER_CLM_CREATION_TIME, providerChannel.getCreationTime());
        long db_index = db.insert(SQLiteHelper.TABLE_PROVIDER, null, values);
        if(db_index < 0)
            throw new RegisterException("Error inserting new channel");
    }

    /**
     * Deletes a {@code ProviderChannel} that matches the specified value.
     * @param providerChannel the {@code ProviderChannel} to delete from the data store.
     * @throws RegisterException in case a problem occurs or the specified {@code ProviderChannel} doesn't exist
     */
    public void deleteChannel(ProviderChannel providerChannel) throws RegisterException{
        db = dbHelper.getWritableDatabase();
        int d = db.delete(SQLiteHelper.TABLE_PROVIDER,
                SQLiteHelper.PROVIDER_CLM_PROVIDER_ADDRESS + " = ?",
                new String[]{providerChannel.getProviderAddress()});
        if(d == 0)
            throw new RegisterException("Channel not present");
    }
}