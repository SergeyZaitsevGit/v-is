package ru.mirea.v_is.service;

import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.mirea.v_is.dto.DayleReportDTO;
import ru.mirea.v_is.dto.DayleReportMetricDTO;
import ru.mirea.v_is.model.Report;
import ru.mirea.v_is.model.ReportType;
import ru.mirea.v_is.repo.MetricsRepo;
import ru.mirea.v_is.repo.ReportRepo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class ReportServiceImpl implements ReportService {

    private final MetricsRepo metricsRepo;
    private final ReportRepo reportRepo;

    @SneakyThrows
    @Scheduled(cron = "0 0 0 * * ?")
    public void createDalyReport(){
        LocalDateTime reportStart = LocalDateTime.now().minusDays(1);
        LocalDateTime reportEnd = LocalDateTime.now();
        DayleReportDTO dayleReportDTO = getDailyReport(reportStart, reportEnd);
        Report report = Report.builder()
                .reportType(ReportType.DALY_GENERAL_REPORT)
                .created(reportEnd)
                .fileContent(generateDalyExcelReport(dayleReportDTO))
                .build();
        reportRepo.save(report);
    }

    @Override
    public DayleReportDTO getDailyReport(LocalDateTime start, LocalDateTime end) {
        List<DayleReportMetricDTO> metrics = metricsRepo.getMetrics(start, end);
        Tuple stats = metricsRepo.getGeneralStats(start, end);

        Long total = stats.get("total", Long.class);
        Long defective = stats.get("defective", Long.class);

        return DayleReportDTO.builder()
                .metricDTOMap(metrics)
                .allDetail(total)
                .defectedProc(calculatePercentage(defective, total))
                .build();
    }

    @Override
    public Page<Report> getReports(Pageable pageable) {
        return reportRepo.findAll(pageable);
    }

    @Override
    public Report findById(Long id) {
        return reportRepo.findById(id).get();
    }

    private BigDecimal calculatePercentage(Long defective, Long total) {
        if (total == 0) return BigDecimal.ZERO;
        return new BigDecimal(defective)
                .divide(new BigDecimal(total), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private byte[] generateDalyExcelReport(DayleReportDTO report) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Отчёт");

        // Заголовки
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Метрика");
        headerRow.createCell(1).setCellValue("Среднее");
        headerRow.createCell(2).setCellValue("Мин (день)");
        headerRow.createCell(3).setCellValue("Макс (день)");
        headerRow.createCell(4).setCellValue("Допустимый мин");
        headerRow.createCell(5).setCellValue("Допустимый макс");

        // Данные метрик
        int rowNum = 1;
        for (DayleReportMetricDTO metric : report.getMetricDTOMap()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(metric.getMetricName());
            row.createCell(1).setCellValue(metric.getAvgValue().doubleValue());
            row.createCell(2).setCellValue(metric.getMinDayValue().doubleValue());
            row.createCell(3).setCellValue(metric.getMaxDayValue().doubleValue());
            row.createCell(4).setCellValue(metric.getMinValue().doubleValue());
            row.createCell(5).setCellValue(metric.getMaxValue().doubleValue());
        }

        // Статистика брака
        Row statsRow = sheet.createRow(rowNum + 1);
        statsRow.createCell(0).setCellValue("Всего деталей:");
        statsRow.createCell(1).setCellValue(report.getAllDetail());

        Row defectRow = sheet.createRow(rowNum + 2);
        defectRow.createCell(0).setCellValue("Процент брака:");
        defectRow.createCell(1).setCellValue(report.getDefectedProc() + "%");

        // Конвертация в byte[]
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream.toByteArray();
    }
}
