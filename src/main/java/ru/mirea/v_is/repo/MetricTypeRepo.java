package ru.mirea.v_is.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mirea.v_is.model.MetricType;

import java.util.List;

public interface MetricTypeRepo extends JpaRepository<MetricType, Long> {

    @Query("SELECT mt FROM MetricType mt WHERE mt.id NOT IN " +
            "(SELECT m.metricType.id FROM Metric m WHERE m.detail.id = :detailId)")
    List<MetricType> findAvailableTypesForDetail(@Param("detailId") Long detailId);

}
