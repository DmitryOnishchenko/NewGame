# TEST

# web3-java: Java wrapper for web3.js library
This library allows to use web3.js library in Java application.
In future you can invoke any js function from any js object.

Currently, only web3, eth, contract, filter and event objects are tested. They are not fully covered with tests though. Nevertheless, this allows you to do all what you need to work with Ethereum network.

**NOTICE: This library is only wrapper for original web3.js, so consider you know how to use it. <br>
More info on [Web3 API](https://github.com/ethereum/wiki/wiki/JavaScript-API)**

## Usage
**NOTICE: web3-java throws only runtime exceptions**

#### Setup/init
You need to have geth node, either local or remote.
You need to pass url into constructor, otherwise it will use default url  (http://localhost:8545).
<br>_Throws InitException_

```java
String gethNodeUrl = "http://<url>:<port>";
Web3Provider web3Provider = new Web3Provider(gethNodeUrl);
```

#### Get web3 object
_Throws NoSuchObjectException_
```java
WrappedObject web3 = web3Provider.get("web3");
```

#### Function invocation
Lets invoke JS "sha3" function from web3 object.
<br>WrappedObject.invoke(function, Object... args) can execute any function of wrapped object with any type of argument.
<br>_Throws NoSuchFunctionException, ScriptExecutionException_
```java
String sha3result = web3.invoke("sha3", "test_message");
```

#### Get contract instance
To get contract instance you need to know ABI and address of the contract.

```java
String abi = "<abi>";
String address = "<address>";

Contract customContract = web3Provider.getContract(abi, address);
```

#### Invoke contract function
```java
String balance = customContract.invoke("<functionName>", "<param1>", param2, ...);
```

You can also invoke contract function with callback in js style.
<br>This is very convenient with java 1.8 Lambda Expression.
```java
customContract.invoke("<functionName>", new Object[]{"<address>, "<param1>", param2, ...},
        ((error, result) -> System.out.println("Callback: " + result)));
```

#### Contract events
How to get event instance

``` java
ContractEvent event = customContract.getEvent("<event_name>");
```

You can put Filter and/or EventFilter objects.
```java
ContractEvent event1 = customContract.getEvent("<event_name>", Filter.latest());

EventFilter eventFilter = new EventFilter().set("symbol", new String[]{currencySymbol});
ContractEvent event2 = customContract.getEvent("<event_name>", eventFilter, Filter.create().fromBlock(100));
```

You can also subscribe to events with callback in js style.
<br>**NOTICE: if you want to subcribe to events, you need to enable event loop for js engine. 
<br>Just add web3Provider.enableEvents() before.**
```java
ContractEvent event = customContract.getEvent("<event_name>", Filter.latest());
event.watch((error, result) -> {
            if (error.equals("null")) {
                System.out.println("New event: " + result);
            } else {
                System.out.println("Error: " + error);
            }
        });
```
Or put callback function in last argument.
```java
customContract.getEventAndWatch("<event_name>", Filter.latest(), (error, result) -> {
            if (error.equals("null")) {
                System.out.println("New event: " + result);
            } else {
                System.out.println("Error: " + error);
            }
        });
```

#### Transactions
To send tx to specific contract method (customContract.transfer.sendTransaction(...) e.g.) 
just try to get function object and then invoke target method.
<br>**NOTICE: sender address should be an unlocked account in the used gethNode.**
```java
String sender = "0xa085e2f5b4d6e8e611853ad585a1b6c444116ce2";
String gasPrice = "20000000000";
String gas = "1000000";

// create tx object
JSObject object = web3Provider.emptyJSObject()
        .set("from", sender)
        .set("gasPrice", gasPrice)
        .set("gas", gas);

// call function
String destination = "0x3ec0e5b6e2d9fc1d55d62f73fd2eaab473b18e51";
long amount = 1000;

String response = customContract.get("transfer").invoke("sendTransaction",
        destination,
        amount,
        object.get());
```
