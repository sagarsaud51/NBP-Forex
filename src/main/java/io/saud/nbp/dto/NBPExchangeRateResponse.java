package io.saud.nbp.dto;


import io.saud.nbp.dto.NBPWebResponse.NBPRateResponse;
import lombok.Data;

import java.util.ArrayList;

@Data
public class NBPExchangeRateResponse {
    private String table;
    private String currency;
    private String code;
    private ArrayList<NBPRateResponse> rates;
}
