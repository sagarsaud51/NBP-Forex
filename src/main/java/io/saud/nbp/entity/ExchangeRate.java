package io.saud.nbp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.saud.nbp.dto.NBPExchangeRateResponse;
import io.saud.nbp.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "EXCHANGE_RATE")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRate extends BaseEntity {

    @Column
    private String code;


    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate effectiveDate;

    @Column
    private BigDecimal bid;

    @Column
    private BigDecimal ask;

    @Column
    private BigDecimal mid;


}
