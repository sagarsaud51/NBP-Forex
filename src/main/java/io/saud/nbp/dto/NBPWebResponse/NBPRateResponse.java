package io.saud.nbp.dto.NBPWebResponse;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class NBPRateResponse {

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate effectiveDate;
    // buying price
    private BigDecimal bid;
    //Selling Price
    private BigDecimal ask;

    private BigDecimal mid;
}
