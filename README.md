# register-android
Android library for SQLite register management.
It allows to create and manage registers containing UniquId elements.

## Getting started
To get started, it is best to have the latest JDK. 
The HEAD of the `master` branch contains the latest development code and various production releases 
are provided on feature branches.
This library depends on the [uidcore-java project](https://github.com/uniquid/uidcore-java.git). If you need 
to work on both project, you have to clone that linked repository.

### Usage example
```java
public class MainActivity extends Activity implements UniquidNodeEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // create Uniquid Node
        File providerWalletFile = new File(getBaseContext().getExternalFilesDir(null), "/provider.wallet");
        File userWalletFile = new File(getBaseContext().getExternalFilesDir(null), "/user.wallet");
        File chainFile = new File(getBaseContext().getExternalFilesDir(null), "/chain.spvchain");
        File userChainFile = new File(getBaseContext().getExternalFilesDir(null), "/userChain.spvchain");
        UniquidNodeImpl.Builder uniquidNode = new UniquidNodeImpl.Builder().
                set_params(UniquidRegTest.get()).
                set_providerFile(providerWalletFile).
                set_userFile(userWalletFile).
                set_chainFile(chainFile).
                set_userChainFile(userChainFile).
                set_registerFactory(registerFactory)
                .build();
    
        // add event listener
        uniquidNode.addUniquidNodeEventListener(MainActivity.this);
    
        Connector connector = new MQTTConnector.Builder()
                .set_broker(Utils.BROKER)
                .set_topic(machineName)
                .build();
    
        // create RegisterFactory
        RegisterFactory factory = new RegisterFactory(MainActivity.this);
        
        // create UniquidSimplifier
        UniquidSimplifier simplifier = new UniquidSimplifier(
                    registerFactory,
                    connector,
                    uniquidNode
                    );
                    
        // start simplifier
        simplifier.start();
        
        ...
        
        // create UserChannel
        UserChannel userChannel = new UserChannel(
                "providerName", 
                "providerAddress", 
                "userAddress", 
                "bitmask"
                );
                
        userChannel.setRevokeAddress("revokeAddress");
        userChannel.setRevokeTxId("revokeTxId");
        
        // get UserRegister
        UserRegister register = factory.getUserRegister();
    
        // store new UserChannel into register
        register.insertChannel(userChannel);
    }
    
    @Override
    public void onProviderContractCreated(com.uniquid.register.provider.ProviderChannel providerChannel) {
    
    }
    
    @Override
    public void onProviderContractRevoked(com.uniquid.register.provider.ProviderChannel providerChannel) {
    
    }
    
    @Override
    public void onUserContractCreated(final UserChannel userChannel) {
   
    }
    
    @Override
    public void onUserContractRevoked(final UserChannel userChannel) {
    
    }
    
    @Override
    public void onSyncNodeStart() {
            
    }
    
    @Override
    public void onSyncNodeEnd() {
            
    }
    
    @Override
    public void onSyncStarted(int i) {
            
    }
    
    @Override
    public void onSyncProgress(double v, int i, Date date) {
            
    }
    
    @Override
    public void onSyncEnded() {
    
    }
    
    @Override
    public void onNodeStateChange(UniquidNodeState uniquidNodeState) {
    
    }
        
}

```