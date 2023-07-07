package io.saud.nbp.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class ForexInquiryRequest {

    private BigDecimal amount;
    private String code;
}
