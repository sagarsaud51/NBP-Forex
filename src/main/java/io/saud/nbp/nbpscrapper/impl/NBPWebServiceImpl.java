package io.saud.nbp.nbpscrapper.impl;

import io.saud.nbp.dto.NBPExchangeRateResponse;
import io.saud.nbp.exceptions.NBPBadRequestException;
import io.saud.nbp.exceptions.NBPException;
import io.saud.nbp.nbpscrapper.NBPWebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;


@Service
@Slf4j
public class NBPWebServiceImpl implements NBPWebService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${npb.api.baseURL}")
    private String NBP_BASE_URL;

    @Value("${npb.api.exchangeRates}")
    private String EXCHANGE_RATE_URL;

    @Value("${npb.api.tableCode}")
    private String TABLE_CODE;


    @Value("${npb.api.secondaryTableCode}")
    private String SECONDARY_TABLE_CODE;

    @Override
    public NBPExchangeRateResponse fetchExchangeRateForCurrencyByDate(String code, LocalDate forDate) throws NBPException {
        try {
            log.debug("fetching Code for {} from API", code);
            ResponseEntity<NBPExchangeRateResponse> response = restTemplate.getForEntity(NBP_BASE_URL + EXCHANGE_RATE_URL + TABLE_CODE + "/" + code + "/" + forDate.toString() + "/", NBPExchangeRateResponse.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().is4xxClientError()) {
                log.info("Could not find data for {} on table {} so trying in table {}", code, TABLE_CODE, SECONDARY_TABLE_CODE);
                return fetchExchangeRateForCurrencyByDateForTableA(code, forDate);
            }
            throw new NBPException(e.getMessage());
        }

    }

    private NBPExchangeRateResponse fetchExchangeRateForCurrencyByDateForTableA(String code, LocalDate forDate) {
        try {
            ResponseEntity<NBPExchangeRateResponse> response = restTemplate.getForEntity(NBP_BASE_URL + EXCHANGE_RATE_URL + SECONDARY_TABLE_CODE + "/" + code + "/" + forDate.toString() + "/", NBPExchangeRateResponse.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().is4xxClientError()) {
                throw new NBPBadRequestException("[Bad Request] " + e.getMessage());
            }
            throw new NBPException(e.getMessage());

        }

    }
}
