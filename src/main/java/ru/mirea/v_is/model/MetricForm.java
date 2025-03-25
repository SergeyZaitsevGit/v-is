package ru.mirea.v_is.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MetricForm {
    private Long id;
    private MetricType metricType;
    private BigDecimal value;
}
