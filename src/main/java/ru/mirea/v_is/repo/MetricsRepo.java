package ru.mirea.v_is.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.v_is.model.Metric;

public interface MetricsRepo extends JpaRepository<Metric, Long> {

}
