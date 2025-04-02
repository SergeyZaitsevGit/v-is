package ru.mirea.v_is.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.mirea.v_is.dto.DayleReportDTO;
import ru.mirea.v_is.model.Report;

import java.time.LocalDateTime;

public interface ReportService {
    DayleReportDTO getDailyReport(LocalDateTime start, LocalDateTime end);

    Page<Report> getReports(Pageable pageable);

    Report findById(Long id);
}
