package io.saud.nbp.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForexCalculatorResponse {

    private Map<String, BigDecimal> details;
    private BigDecimal total;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate effectiveDate;
}
