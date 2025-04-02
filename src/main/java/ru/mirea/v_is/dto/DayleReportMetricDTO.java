package ru.mirea.v_is.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DayleReportMetricDTO {

    private String metricName;
    private BigDecimal avgValue;
    private BigDecimal maxValue;
    private BigDecimal minValue;
    private BigDecimal maxDayValue;
    private BigDecimal minDayValue;

}
