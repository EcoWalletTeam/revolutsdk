# Revolut SDK

This sdk is a library for interacting with Revolut Business API for getting transactions, account and counterparty

## How to use this sdk

```java
package com.ecowallet.revolutsdk;

import com.ecowallet.revolutsdk.client.RevolutClientDefault;
import com.ecowallet.revolutsdk.models.response.RevolutAccount;
import com.ecowallet.revolutsdk.models.response.RevolutCounterParty;
import com.ecowallet.revolutsdk.models.response.RevolutTransaction;
import lombok.extern.java.Log;

import java.time.LocalDateTime;
import java.util.List;

@Log
public class RevolutSdkExample {

  private static final String GRANT_TYPE = "refresh_token";
  private static final String REFRESH_TOKEN = "REFRESH";
  private static final String CLIENT_ASSERTION_TYPE = "urn:ietf:params:oauth:client-assertion-type:jwt-bearer";
  private static final String CLIENT_ASSERTION = "JWT";

  public static void main(String[] args) {
    String baseUrl = "https://sandbox-b2b.revolut.com/api/1.0";
    RevolutClientDefault revolutClientDefault = RevolutClientDefault
      .builder()
      .refreshToken(REFRESH_TOKEN)
      .grantType(GRANT_TYPE)
      .clientAssertionType(CLIENT_ASSERTION_TYPE)
      .clientAssertion(CLIENT_ASSERTION)
      .baseUrl(baseUrl)
      .build();
    try {
      List<RevolutTransaction> transactions = revolutClientDefault.getTransactions(LocalDateTime.now().minusDays(1), LocalDateTime.now(), null);
      RevolutAccount account = revolutClientDefault.getAccount(transactions.get(0).getLegs().get(0).getAccountID());
      RevolutCounterParty counterParty = revolutClientDefault.getCounterParty(transactions.get(0).getLegs().get(0).getCounterParty().getId());
      System.out.println(transactions);
      System.out.println(account);
      System.out.println(counterParty);
    } catch (Exception e) {
      log.warning(e.getMessage());
    }

  }
}
```

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.7/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.7/maven-plugin/reference/html/#build-image)

