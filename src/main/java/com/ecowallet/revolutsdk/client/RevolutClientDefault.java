package com.ecowallet.revolutsdk.client;

import com.ecowallet.revolutsdk.models.response.AccessToken;
import com.ecowallet.revolutsdk.models.response.RevolutAccount;
import com.ecowallet.revolutsdk.models.response.RevolutCounterParty;
import com.ecowallet.revolutsdk.models.response.RevolutTransaction;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.Feign;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.java.Log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Builder
@Getter
@Log
public class RevolutClientDefault {
    private static RevolutClient revolutClient;
    private String baseUrl;
    private String refreshToken;
    private String grantType;
    private String clientAssertionType;
    private String clientAssertion;

    public List<RevolutTransaction> getTransactions(LocalDateTime from, LocalDateTime to, UUID accountId) {
        Map<String, Object> queryMap = new HashMap<>() {{
            put("from", from.format(DateTimeFormatter.ISO_DATE));
            put("to", to.format(DateTimeFormatter.ISO_DATE));
            put("account", accountId);
        }};
        revolutClient = Feign.builder()
            .decoder(new JacksonDecoder(List.of(new JavaTimeModule())))
            .target(RevolutClient.class, baseUrl);
        log.info("Looking up Transaction from " + from.toString() + " to " + to.toString() + " for accountID " + accountId);
        return revolutClient.getTransactions(generateAccessToken().getToken(), queryMap);
    }

    public RevolutAccount getAccount(String id) {
        revolutClient = Feign.builder()
            .decoder(new JacksonDecoder(List.of(new JavaTimeModule())))
            .target(RevolutClient.class, baseUrl);
        log.info("Looking up account with id: " + id);
        return revolutClient.getAccount(generateAccessToken().getToken(), id);
    }

    public RevolutCounterParty getCounterParty(String id) {
        revolutClient = Feign.builder()
            .decoder(new JacksonDecoder(List.of(new JavaTimeModule())))
            .target(RevolutClient.class, baseUrl);
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

}
