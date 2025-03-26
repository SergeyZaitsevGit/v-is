package ru.mirea.v_is.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.v_is.model.MetricType;
import ru.mirea.v_is.repo.MetricTypeRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MetricTypeServiceImpl implements MetricTypeService {

    private final MetricTypeRepo metricTypeRepo;

    @Override
    public List<MetricType> getAllTypes() {
        return metricTypeRepo.findAll();
    }

    @Override
    public List<MetricType> getAvailableTypesForDetail(Long detailId) {
        return metricTypeRepo.findAvailableTypesForDetail(detailId);
    }

    @Override
    public MetricType findById(Long id) {
        return metricTypeRepo.findById(id).orElseThrow(() -> new RuntimeException("Metric type not found"));
    }
}
