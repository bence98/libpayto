# libpayto
A Java library for handling `payto://` URIs, as described in RFC 8905

## `payto://` URIs
RFC 8905 specifies a standard that allows encoding payment information into a regular URI. When a user tries to load such an URI (by for example, clicking on a Web link), their payment handler app should load, and with the payment details information populated by the data in the URI, prompt the user to make the transaction.
This is akin to `mailto:` URIs, which allow Web developers to place links on their sites that prepares an e-mail when clicked by visitors. Should this standard proliferate, it would create a seamless, two-click payment experience for the customer (who, after clicking the payment link, only needs to verify the details and authorize the transaction).
Standardization allows for a single scheme being used by all vendors, thereby creating an uniform experience for cutomers of all banks, credit card issuers, fintechs, credit unions and other financial services. Web developers need not to place a separate payment button for all these, but rather can use a single, standardized solution.

## Using the library
The heart of the API is the [`LibPayto`](https://github.com/bence98/libpayto/blob/master/src/main/hu/agyklub/csokicraft/libpayto/LibPayto.java) class, which handles parsing of an URI, as well as notifying registered handlers about new payment data being available. Handlers are to be registered through [`PaytoHandlerRegistry`](https://github.com/bence98/libpayto/blob/master/src/main/hu/agyklub/csokicraft/libpayto/PaytoHandlerRegistry.java).

A minimal application registers its handlers through `PaytoHandlerRegistry.INSTANCE.register()`, as well as sets itself as the application to open `payto://` URIs with (this is done through the OS's API). When it then receives an URI to open, it passes it to `LibPayto.dispatchURI()`. A minimal working example for an Android-based app can be found in the [LibpaytoExample](https://github.com/bence98/LibpaytoExample) repository.
