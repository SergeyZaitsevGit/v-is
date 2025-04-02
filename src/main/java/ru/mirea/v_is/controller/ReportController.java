package ru.mirea.v_is.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mirea.v_is.model.Report;
import ru.mirea.v_is.service.ReportService;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String listReports(Model model, @PageableDefault(
            sort = {"id"},
            direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("reports", reportService.getReports(pageable));
        return "report";
    }

    @GetMapping("/{id}/download")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> downloadReport(@PathVariable Long id) {
        Report report = reportService.findById(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + report.getReportType().getDescription() + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(report.getFileContent());
    }

}
