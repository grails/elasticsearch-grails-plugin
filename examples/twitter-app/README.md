# Twitter example app

This is a simple Elasticsearch example app implementing a basic message website.

You can post short message as different users and tag them for categorization and better searchability.

## Implementation notes

* We use the `sequence` id generator for our domain objects, so that different domain objects do not have colliding ids. That makes our "global search" code easier, where we can find and list hits of different types

## Running the app with secured Elasticsearch

If you want to run the app with secured Elasticsearch, your first have to setup a Elasticsearch cluster with `xpack.security` enabled. An example setup can be found in the `compose.yaml` in the examples directory.

If you use the trust strategy `all` or `self-signed`, your are already ready to go. Just enable `ssl` in the configuration and set the correct username and password.

If you want to increase security even more, you have to use the `trust-store` strategy and configure a truststore with the Elasticsearch CA certificate.

You may extract the Elasticsearch CA certificate from the docker volume and import it into your Java keystore using a command like this:

```
keytool -importcert -alias elasticsearch-ca -file ca/ca.crt -keystore elasticsearch-truststore.jks -storetype JKS -storepass super_secure
```
