package io.saud.nbp.service.impl;

import io.saud.nbp.dto.ExchangeRateCalculatorRequest;
import io.saud.nbp.dto.ForexCalculatorResponse;
import io.saud.nbp.dto.NBPExchangeRateResponse;
import io.saud.nbp.dto.NBPWebResponse.ExchangeRateResponse;
import io.saud.nbp.entity.ExchangeRate;
import io.saud.nbp.exceptions.NBPBadRequestException;
import io.saud.nbp.exceptions.NBPException;
import io.saud.nbp.nbpscrapper.NBPWebService;
import io.saud.nbp.nbpscrapper.impl.NBPWebServiceImpl;
import io.saud.nbp.repository.ExchangeRateRepo;
import io.saud.nbp.service.NBPService;
import io.saud.nbp.utils.ResponseHelperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;


@Service
@Slf4j
public class NBPServiceImpl implements NBPService {


    private final ExchangeRateRepo exchangeRateRepo;
    private final NBPWebService nbpWebService;

    public NBPServiceImpl(ExchangeRateRepo exchangeRateRepo, NBPWebServiceImpl nbpWebService) {
        this.exchangeRateRepo = exchangeRateRepo;
        this.nbpWebService = nbpWebService;
    }


    @Override
    public Object getExchangeRateForCurrencyByDate(String code, LocalDate forDate) {
        ExchangeRate exchangeRate;
        try {
            validateCurrencyCode(code);
            Optional<ExchangeRate> optionalExchangeRate = exchangeRateRepo.findExchangeRateByCodeEqualsAndEffectiveDateEquals(code, forDate);
            if (optionalExchangeRate.isPresent()) {
                exchangeRate = optionalExchangeRate.get();
            } else {
                NBPExchangeRateResponse response = nbpWebService.fetchExchangeRateForCurrencyByDate(code, forDate);
                exchangeRate = persistExchangeRateFromWebServices(response);
            }
            return new ExchangeRateResponse(exchangeRate);
        } catch (NBPBadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseHelperUtils.ErrorResponseHelper(e.getLocalizedMessage()));
        } catch (NBPException nbpException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseHelperUtils.ErrorResponseHelper(nbpException.getLocalizedMessage()));
        }
    }

    @Override
    public Object calculateForexValue(ExchangeRateCalculatorRequest calculatorRequest) {
        try {
            //validate request
            calculatorRequest.getForexRequests().forEach(r -> validateCurrencyCode(r.getCode()));
            Map<String, BigDecimal> calculatedForex = new HashMap<>();
            calculatorRequest.getForexRequests().forEach(forexInquiryRequest -> {
                ExchangeRateResponse exchangeRate = (ExchangeRateResponse) getExchangeRateForCurrencyByDate(forexInquiryRequest.getCode(), calculatorRequest.getDate());
                BigDecimal value = Objects.isNull(exchangeRate.getBuy()) ? exchangeRate.getMid() : exchangeRate.getBuy();
                calculatedForex.put(forexInquiryRequest.getCode(), value.multiply(forexInquiryRequest.getAmount()));
            });
            BigDecimal sum = calculatedForex.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            return new ForexCalculatorResponse(calculatedForex, sum, calculatorRequest.getDate());
        } catch (NBPBadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseHelperUtils.ErrorResponseHelper(e.getLocalizedMessage()));
        } catch (NBPException nbpException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseHelperUtils.ErrorResponseHelper(nbpException.getLocalizedMessage()));
        }
    }


    private ExchangeRate persistExchangeRateFromWebServices(NBPExchangeRateResponse response) {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setEffectiveDate(response.getRates().get(0).getEffectiveDate());
        exchangeRate.setCode(response.getCode());
        exchangeRate.setBid(response.getRates().get(0).getBid());
        exchangeRate.setAsk(response.getRates().get(0).getAsk());
        exchangeRate.setMid(response.getRates().get(0).getMid());
        exchangeRateRepo.save(exchangeRate);
        return exchangeRate;
    }

    private void validateCurrencyCode(String code) {
        try {
            Currency.getInstance(code);
        } catch (Exception e) {
            log.error("Invalid Currency Code");
            throw new NBPBadRequestException("Bad Currency Code");
        }
    }
}
