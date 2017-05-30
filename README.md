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
public class MainActivity {

    ...

    // create RegisterFactory
    RegisterFactory factory = new RegisterFactory(MainActivity.this);
    
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
    
    ...
    
}

```