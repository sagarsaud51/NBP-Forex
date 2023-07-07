package io.saud.nbp.service;

import io.saud.nbp.dto.ExchangeRateCalculatorRequest;

import java.time.LocalDate;

public interface NBPService {


    Object getExchangeRateForCurrencyByDate(String code, LocalDate forDate);
    Object calculateForexValue(ExchangeRateCalculatorRequest calculatorRequest);
}
