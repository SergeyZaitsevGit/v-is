package ru.mirea.v_is.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mirea.v_is.dto.MetricAnalyticsDTO;
import ru.mirea.v_is.model.Metric;

import java.util.List;

public interface MetricsRepo extends JpaRepository<Metric, Long> {
    @Query("""
            SELECT new ru.mirea.v_is.dto.MetricAnalyticsDTO(
                m.metricType.id,
                m.metricType.metricName,
                CAST(m.created AS localdatetime),
                CAST(m.value AS double )
            )
            FROM Metric m
            WHERE m.created >= CURRENT_DATE
            ORDER BY CAST(m.created AS localdatetime)
        """)
    List<MetricAnalyticsDTO> getDailyAverageMetrics();
}
