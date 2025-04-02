package ru.mirea.v_is.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mirea.v_is.model.*;
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
        boolean isDefected = detail.getMetrics().stream().anyMatch(Metric::getDefected);
        detail.setDetailStatus(isDefected ? DetailStatus.DEFECTIVE : DetailStatus.NORMAL);
        detail.setUser(userService.getAuthenticationUser());
        detail.setCreated(LocalDateTime.now());
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
        User authenticationUser = userService.getAuthenticationUser();
        if (authenticationUser.getRole().equals(Role.ROLE_ADMIN)){
            return detailRepo.findAll(pageable);
        }
        return detailRepo.getDetailByUser(authenticationUser, pageable);
    }

    @Override
    public Detail findById(Long id) {
        return detailRepo.findById(id).orElseThrow(() -> new RuntimeException("Detail not found"));
    }
}
