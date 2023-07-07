package io.saud.nbp.controller;


import io.saud.nbp.dto.ExchangeRateCalculatorRequest;
import io.saud.nbp.nbpscrapper.NBPWebService;
import io.saud.nbp.nbpscrapper.impl.NBPWebServiceImpl;
import io.saud.nbp.service.NBPService;
import io.saud.nbp.service.impl.NBPServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping("exchange-rate")
public class NBPController {

    final
    NBPService nbpService;

    public NBPController(NBPServiceImpl nbpService) {
        this.nbpService = nbpService;
    }


    @GetMapping()
    public Object getExchangeRateForCurrencyByDate(@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate forDate, @RequestParam(required = true) String code) {
        return nbpService.getExchangeRateForCurrencyByDate(code, forDate);
    }

    @PostMapping("calculate")
    public Object calculateForexValue(@RequestBody @Valid ExchangeRateCalculatorRequest rateCalculatorRequest) {
        return nbpService.calculateForexValue(rateCalculatorRequest);
    }
}
