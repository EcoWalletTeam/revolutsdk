package com.ecowallet.revolutsdk.client;

import com.ecowallet.revolutsdk.models.request.RevolutTransactionRequest;
import com.ecowallet.revolutsdk.models.response.AccessToken;
import com.ecowallet.revolutsdk.models.response.RevolutAccount;
import com.ecowallet.revolutsdk.models.response.RevolutCounterParty;
import com.ecowallet.revolutsdk.models.response.RevolutTransaction;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.Feign;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import lombok.Builder;
import lombok.extern.java.Log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Log
public class RevolutClientDefault {
    private static RevolutClient revolutClient;
    private final String baseUrl;
    private final String refreshToken;
    private final String grantType;
    private final String clientAssertionType;
    private final String clientAssertion;

    @Builder
    private RevolutClientDefault(String baseUrl, String refreshToken, String grantType, String clientAssertionType,
                                 String clientAssertion) {
        this.baseUrl = baseUrl;
        this.refreshToken = refreshToken;
        this.grantType = grantType;
        this.clientAssertionType = clientAssertionType;
        this.clientAssertion = clientAssertion;
        revolutClient = createFeignClientDefault();
    }

    public List<RevolutTransaction> getTransactions(LocalDateTime from, LocalDateTime to, UUID accountId,
                                                    Integer count) {
        Map<String, Object> queryMap = new HashMap<>() {{
            put("from", from.format(DateTimeFormatter.ISO_DATE));
            put("to", to.format(DateTimeFormatter.ISO_DATE));
            put("account", accountId);
            put("count", count);
        }};
        log.info("Looking up Transaction from " + from.toString() + " to " + to.toString() + " for accountID " +
            accountId + " with count " + count);
        return revolutClient.getTransactions(generateAccessToken().getToken(), queryMap);
    }

    public List<RevolutTransaction> getTransactions(RevolutTransactionRequest request) {
        return getTransactions(request.getFrom(), request.getTo(), request.getAccountId(), request.getCount());
    }

    public RevolutAccount getAccount(String id) {
        log.info("Looking up account with id: " + id);
        return revolutClient.getAccount(generateAccessToken().getToken(), id);
    }

    public RevolutCounterParty getCounterParty(String id) {
        log.info("Looking up counterparty with id: " + id);
        return revolutClient.getCounterParty(generateAccessToken().getToken(), id);
    }

    public AccessToken generateAccessToken() {
        RevolutClient revolutAuthClient = Feign.builder()
            .encoder(new FormEncoder())
            .decoder(new JacksonDecoder())
            .target(RevolutClient.class, baseUrl);
        return revolutAuthClient.getAccessToken(grantType, refreshToken, clientAssertionType, clientAssertion);
    }

    private RevolutClient createFeignClientDefault() {
        return Feign.builder()
            .decoder(new JacksonDecoder(List.of(new JavaTimeModule())))
            .target(RevolutClient.class, baseUrl);
    }
}
