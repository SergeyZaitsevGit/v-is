package ru.mirea.v_is.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mirea.v_is.model.Detail;
import ru.mirea.v_is.model.MetricType;
import ru.mirea.v_is.repo.MetricsRepo;
import ru.mirea.v_is.service.DetailService;
import ru.mirea.v_is.service.MetricTypeService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/details")
@RequiredArgsConstructor
public class DetailController {

    private final DetailService detailService;
    private final MetricTypeService metricTypeService;
    private final MetricsRepo metricsRepo;

    @GetMapping
    public String getAllDetails(
            @PageableDefault(
                    sort = {"id"},
                    direction = Sort.Direction.DESC) Pageable pageable,
            Model model) {

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

    @GetMapping("delete/{id}")
    public String delete(@PathVariable Long id) {
        detailService.delete(id);
        return "redirect:/details";
    }

    @PostMapping("/save")
    public String saveDetail(@ModelAttribute("detail") Detail detail) {
        detailService.save(detail);
        return "redirect:/details";
    }

}
