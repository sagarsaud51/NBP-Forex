package io.saud.nbp.repository;

import io.saud.nbp.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface ExchangeRateRepo extends JpaRepository<ExchangeRate, Long> {

    Optional<ExchangeRate> findExchangeRateByCodeEqualsAndEffectiveDateEquals(String code, LocalDate localDate);
    List<ExchangeRate> findExchangeRateByEffectiveDateEqualsAndCodeIn(LocalDate forDate, List<String> codes);
}
