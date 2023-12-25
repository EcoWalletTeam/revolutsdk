package com.ecowallet.revolutsdk.client;

import com.ecowallet.revolutsdk.models.response.AccessToken;
import com.ecowallet.revolutsdk.models.response.RevolutAccount;
import com.ecowallet.revolutsdk.models.response.RevolutCounterParty;
import com.ecowallet.revolutsdk.models.response.RevolutTransaction;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;

import java.util.List;
import java.util.Map;

public interface RevolutClient {

    @RequestLine("GET /transactions?{parameters}")
    @Headers({"Authorization: Bearer {token}"})
    List<RevolutTransaction> getTransactions(@Param("token") String token, @QueryMap Map<String, Object> parameters);

    @RequestLine("GET /accounts/{id}")
    @Headers("Authorization: Bearer {token}")
    RevolutAccount getAccount(@Param("token") String token, @Param("id") String id);

    @RequestLine("GET /counterparty/{counterparty_id}")
    @Headers("Authorization: Bearer {token}")
    RevolutCounterParty getCounterParty(@Param("token") String token, @Param("counterparty_id") String id);

    @RequestLine("POST /auth/token")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    AccessToken getAccessToken(@Param("grant_type") String grantType, @Param("refresh_token") String refreshToken,
                               @Param("client_assertion_type") String clientAssertionType,
                               @Param("client_assertion") String clientAssertion);
}
