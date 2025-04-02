package ru.mirea.v_is.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.v_is.model.Report;

public interface ReportRepo extends JpaRepository<Report, Long> {
}
