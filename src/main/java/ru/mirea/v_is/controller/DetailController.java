package ru.mirea.v_is.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mirea.v_is.model.Detail;
import ru.mirea.v_is.model.Metric;
import ru.mirea.v_is.model.MetricForm;
import ru.mirea.v_is.model.MetricType;
import ru.mirea.v_is.repo.MetricsRepo;
import ru.mirea.v_is.service.DetailService;
import ru.mirea.v_is.service.MetricTypeService;
import ru.mirea.v_is.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/details")
@RequiredArgsConstructor
public class DetailController {

    private final DetailService detailService;
    private final MetricTypeService metricTypeService;
    private final MetricsRepo metricsRepo;

    @GetMapping
    public String getAllDetails(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) String keyword,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Detail> detailPage = detailService.getDetailByAuthUser(pageable);

        model.addAttribute("detailPage", detailPage);
        return "details";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Detail detail = new Detail();
        model.addAttribute("detail", detail);
        model.addAttribute("metricTypes", metricTypeService.getAllTypes());
        model.addAttribute("availableMetricTypes", metricTypeService.getAllTypes());
        return "detail-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Detail detail = detailService.findById(id);
        List<MetricType> availableTypes = metricTypeService.getAvailableTypesForDetail(id);

        model.addAttribute("detail", detail);
        model.addAttribute("availableMetricTypes", availableTypes);
        return "detail-form";
    }

    @PostMapping("/save")
    public String saveDetail(
            @ModelAttribute("detail") Detail detail) {


        detail.getMetrics().forEach(metric -> {
            metric.setDetail(detail);
            metric.setMetricType(metricTypeService.findById(metric.getMetricType().getId()));
            metric.setValue(metric.getValue());
            if (metric.getValue().compareTo(metric.getMetricType().getMinValue()) < 0 ||
                    metric.getValue().compareTo(metric.getMetricType().getMaxValue()) > 0) {
                metric.setDefected(true);
            }
        });
        detailService.save(detail);


        return "redirect:/details";
    }

}
