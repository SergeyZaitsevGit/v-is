package ru.mirea.v_is.service;

import ru.mirea.v_is.model.MetricType;

import java.util.List;

public interface MetricTypeService {
    List<MetricType> getAllTypes();

    List<MetricType> getAvailableTypesForDetail(Long detailId);

    MetricType findById(Long id);
}
