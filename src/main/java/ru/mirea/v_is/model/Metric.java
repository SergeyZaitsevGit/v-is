package ru.mirea.v_is.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "metric")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Metric {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "metric_type_id")
    private MetricType metricType;

    @ManyToOne
    @JoinColumn(name = "detail_id")
    private Detail detail;

    private LocalDateTime created;

    private BigDecimal value;

    private Boolean defected;

}
