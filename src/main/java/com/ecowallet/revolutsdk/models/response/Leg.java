package com.ecowallet.revolutsdk.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Leg {
    @JsonProperty("leg_id")
    private UUID id;
    @JsonProperty("account_id")
    private String accountID;
    @JsonProperty("counterparty")
    private RevolutCounterParty counterParty;
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("bill_amount")
    private Double billAmount;
    @JsonProperty("bill_currency")
    private String billCurrency;
    @JsonProperty("description")
    private String description;
    @JsonProperty("balance")
    private Double balance;
}
