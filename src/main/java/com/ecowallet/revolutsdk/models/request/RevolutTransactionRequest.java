package com.ecowallet.revolutsdk.models.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class RevolutTransactionRequest {
    private LocalDateTime from;
    private LocalDateTime to;
    private UUID accountId;
    private Integer count;
}
