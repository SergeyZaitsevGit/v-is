package ru.mirea.v_is.repo;

import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mirea.v_is.dto.DayleReportDTO;
import ru.mirea.v_is.dto.DayleReportMetricDTO;
import ru.mirea.v_is.dto.MetricAnalyticsDTO;
import ru.mirea.v_is.model.Metric;

import java.time.LocalDateTime;
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

    @Query("""
        SELECT new ru.mirea.v_is.dto.DayleReportMetricDTO(
            m.metricType.metricName,
            CAST(AVG(m.value) AS bigdecimal),
            MAX(m.metricType.maxValue),
            MIN(m.metricType.minValue),
            MAX(m.value),
            MIN(m.value)
        )
        FROM Metric m
        WHERE m.created BETWEEN :start AND :end
        GROUP BY m.metricType.metricName
    """)
    List<DayleReportMetricDTO> getMetrics(@Param("start") LocalDateTime start,
                                          @Param("end") LocalDateTime end);

    @Query("""
        SELECT 
            COUNT(d) as total,
            SUM(CASE WHEN d.detailStatus = 'DEFECTIVE' THEN 1 ELSE 0 END) as defective
        FROM Detail d
        WHERE d.created BETWEEN :start AND :end
    """)
    Tuple getGeneralStats(@Param("start") LocalDateTime start,
                          @Param("end") LocalDateTime end);
}
