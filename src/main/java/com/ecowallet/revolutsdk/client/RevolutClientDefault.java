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
    private final String baseUrl;
    private final String refreshToken;
    private final String grantType;
    private final String clientAssertionType;
    private final String clientAssertion;

    public List<RevolutTransaction> getTransactions(LocalDateTime from, LocalDateTime to, UUID accountId) {
        Map<String, Object> queryMap = new HashMap<>() {{
            put("from", from.format(DateTimeFormatter.ISO_DATE));
            put("to", to.format(DateTimeFormatter.ISO_DATE));
            put("account", accountId);
        }};
        log.info("Looking up Transaction from " + from.toString() + " to " + to.toString() + " for accountID " + accountId);
        return getRevolutClient().getTransactions(generateAccessToken().getToken(), queryMap);
    }

    public RevolutAccount getAccount(String id) {
        log.info("Looking up account with id: " + id);
        return getRevolutClient().getAccount(generateAccessToken().getToken(), id);
    }

    public RevolutCounterParty getCounterParty(String id) {
        log.info("Looking up counterparty with id: " + id);
        return getRevolutClient().getCounterParty(generateAccessToken().getToken(), id);
    }

    public AccessToken generateAccessToken() {
        RevolutClient revolutAuthClient = Feign.builder()
            .encoder(new FormEncoder())
            .decoder(new JacksonDecoder())
            .target(RevolutClient.class, baseUrl);
        return revolutAuthClient.getAccessToken(grantType, refreshToken, clientAssertionType, clientAssertion);
    }

    private RevolutClient getRevolutClient() {
        return revolutClient != null ? revolutClient : createFeignClient();
    }

    private RevolutClient createFeignClient() {
        return Feign.builder()
            .decoder(new JacksonDecoder(List.of(new JavaTimeModule())))
            .target(RevolutClient.class, baseUrl);
    }
}
