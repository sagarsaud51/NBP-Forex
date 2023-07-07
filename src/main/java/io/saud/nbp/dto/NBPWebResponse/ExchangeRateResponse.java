package io.saud.nbp.dto.NBPWebResponse;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.saud.nbp.entity.ExchangeRate;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor

public class ExchangeRateResponse {


    private String code;
    private BigDecimal sell;
    @JsonIgnore
    private BigDecimal buy;

    private BigDecimal mid;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate effectiveDate;

    public ExchangeRateResponse(ExchangeRate exchangeRate) {
        this.code = exchangeRate.getCode();
        this.sell = exchangeRate.getAsk();
        this.buy = exchangeRate.getBid();
        this.mid = exchangeRate.getMid();
        this.effectiveDate = exchangeRate.getEffectiveDate();
    }
}
