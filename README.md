# uidcore-android
Android library for SQLite register management.
It allows to create and manage registers containing UniquId elements.

## Getting started
To get started, it is best to have the latest JDK. 
The HEAD of the `master` branch contains the latest development code and various production releases 
are provided on feature branches.

This library depends on the [uidcore-java project](https://github.com/uniquid/uidcore-java.git). If you need 
to work on both project, you have to clone that linked repository.

In your Android application you have to add this line to gradle app:

`implementation 'org.fusesource.mqtt-client:mqtt-client:1.12'`

### Usage example
```java
public class MainActivity extends Activity implements UniquidNodeEventListener {
	
	private static final NetworkParameters UNIQNET = UniquidRegTest.get();
	
	private String registryUrl = "http://appliance4.uniquid.co:8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        String machineName = "machineName";
        
        RegisterFactory registerFactory = new RegisterFactoryImpl(MainActivity.this, 5);

        // create Uniquid Node
        File providerWalletFile = new File(getBaseContext().getExternalFilesDir(null), "/provider.wallet");
        File userWalletFile = new File(getBaseContext().getExternalFilesDir(null), "/user.wallet");
        File chainFile = new File(getBaseContext().getExternalFilesDir(null), "/chain.spvchain");
        File userChainFile = new File(getBaseContext().getExternalFilesDir(null), "/userChain.spvchain");
        UniquidNodeImpl.UniquidNodeBuilder uBuilder = new UniquidNodeImpl.UniquidNodeBuilder();
        
        DefaultUserClientFactory defaultUserClientFactory = new DefaultUserClientFactory(PreferencesUtils.getBroker(MainActivity.this), 20);
        
        uBuilder.setNetworkParameters(UNIQNET).
            setProviderFile(providerWalletFile).
            setUserFile(userWalletFile).
            setProviderChainFile(chainFile).
            setUserChainFile(userChainFile).
            setRegisterFactory(registerFactory).
            setRegistryUrl(registryUrl).
            setUserClientFactory(defaultUserClientFactory).
            setNodeName(machineName);
            
        UniquidNodeImpl uniquidNode = uBuilder.build();
    
        // add event listener
        uniquidNode.addUniquidNodeEventListener(MainActivity.this);
    
        Connector connector = new MQTTConnector.Builder()
                .set_broker("broker.mqttdashboard.com:8000")
                .set_topic(machineName)
                .build();
        
        // create UniquidSimplifier
        UniquidSimplifier simplifier = new UniquidSimplifier(
                    registerFactory,
                    connector,
                    uniquidNode
                    );
                    
        // start simplifier
        simplifier.start();
        
        List<UserChannel> channelArrayList = simplifier.getRegisterFactory().getUserRegister().getAllUserChannels();
        
    }
    
    @Override
    public void onProviderContractCreated(ProviderChannel providerChannel) {
        
    }

    @Override
    public void onProviderContractRevoked(ProviderChannel providerChannel) {

    }

    @Override
    public void onUserContractCreated(UserChannel userChannel) {

    }

    @Override
    public void onUserContractRevoked(UserChannel userChannel) {

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

    @Override
    public void onPeerConnected(Peer peer, int i) {

    }

    @Override
    public void onPeerDisconnected(Peer peer, int i) {

    }

    @Override
    public void onPeersDiscovered(Set<PeerAddress> set) {

    }
        
}

```