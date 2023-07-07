package io.saud.nbp.nbpscrapper;

import io.saud.nbp.dto.NBPExchangeRateResponse;
import io.saud.nbp.exceptions.NBPException;

import java.time.LocalDate;

public interface NBPWebService {

    NBPExchangeRateResponse fetchExchangeRateForCurrencyByDate(String currencyCode, LocalDate forDate) throws NBPException;
}
