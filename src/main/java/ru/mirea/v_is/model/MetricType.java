package ru.mirea.v_is.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "metric_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MetricType {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;

    private String metricName;

    private BigDecimal maxValue;

    private BigDecimal minValue;
}
