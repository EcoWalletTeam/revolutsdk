package com.ecowallet.revolutsdk.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RevolutCounterParty {
    @JsonProperty("id")
    private String id;
    @JsonProperty("account_id")
    private String accountId;
    @JsonProperty("name")
    private String name;
}
