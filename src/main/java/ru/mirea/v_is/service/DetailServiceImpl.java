package ru.mirea.v_is.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mirea.v_is.model.Detail;
import ru.mirea.v_is.model.User;
import ru.mirea.v_is.repo.DetailRepo;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DetailServiceImpl implements DetailService {

    private final UserService userService;
    private final DetailRepo detailRepo;
    private final MetricTypeService metricTypeService;

    @Override
    @Transactional
    public Detail save(Detail detail) {
        detail.getMetrics().forEach(metric -> {
            metric.setDetail(detail);
            metric.setCreated(LocalDateTime.now());
            metric.setMetricType(metricTypeService.findById(metric.getMetricType().getId()));
            metric.setValue(metric.getValue());
            if (metric.getValue().compareTo(metric.getMetricType().getMinValue()) < 0 ||
                    metric.getValue().compareTo(metric.getMetricType().getMaxValue()) > 0) {
                metric.setDefected(true);
            }
        });
        detail.setUser(userService.getAuthenticationUser());
        return detailRepo.save(detail);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        detailRepo.deleteById(id);
    }

    @Override
    @Transactional
    public Page<Detail> getDetailByAuthUser(Pageable pageable) {
        return detailRepo.getDetailByUser(userService.getAuthenticationUser(), pageable);
    }

    @Override
    public Detail findById(Long id) {
        return detailRepo.findById(id).orElseThrow(() -> new RuntimeException("Detail not found"));
    }
}
