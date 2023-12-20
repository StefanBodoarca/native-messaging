# Native Messaging protocol implementation
Docs: https://developer.chrome.com/docs/extensions/develop/concepts/native-messaging


[## Client side module](
module: https://github.com/StefanBodoarca/data-to-extension/tree/master/client%20side
)

Send simple messages to a local/server application (native messaging host):

Created a port and sent/received message to native messaging host.
Code from js/bkg/background.js

```agsl
const port = chrome.runtime.connectNative('com.google.chrome.example.echo');

port.onMessage.addListener(portListener);

port.onDisconnect.addListener(() => {
console.log("Disconnected");
});

port.postMessage({ text: "Hello, received from chrome extension" });
```


[# Server side module](module: https://github.com/StefanBodoarca/listener-to-es)

This module receives messages (documents) from a chrome extension and stores them in Elasticsearch.


Checkout the message reader:
com/ro/controller/reader/MessageReader.java

```agsl
public String readMessage() {
        try {

            if (inputStream.read(len) == 4) {
                int length = ByteBuffer.wrap(len).order(ByteOrder.LITTLE_ENDIAN).getInt();
                logger.info("Received length: " + length);

                if (length == 0) {
                    logger.info("Message length is 0. Systems exit");
                    System.exit(0);
                } else {
                    byte[] message = new byte[length];
                    if (inputStream.read(message) > 0) {
                        String messageOutput = new String(message, StandardCharsets.UTF_8);
                        return messageOutput;
                    }
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return "";
    }
```

Checkout the message writer (sends back message to the extension):
com/ro/controller/sender/MessageSender.java

```agsl
public void sendMessage(String jsonString) {
    try {
        System.out.write(BytesHelper.intToBytes(jsonString.length()));
        System.out.write(jsonString.getBytes(StandardCharsets.UTF_8));
        System.out.flush();
    } catch (IOException ioe) {
        logger.error(ioe.getMessage(), ioe);
    }
}
```
