package ru.mirea.v_is.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MetricAnalyticsDTO {
    private Long metricTypeId;
    private String metricTypeName;
    private LocalDateTime date;
    private Double average;
}
