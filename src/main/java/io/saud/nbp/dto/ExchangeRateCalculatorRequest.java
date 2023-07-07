package io.saud.nbp.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class ExchangeRateCalculatorRequest {

    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate date;

    Set<ForexInquiryRequest> forexRequests;

}
