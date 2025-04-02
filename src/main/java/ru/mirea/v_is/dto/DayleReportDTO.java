package ru.mirea.v_is.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DayleReportDTO {

    private List<DayleReportMetricDTO> metricDTOMap = new ArrayList<>();
    private Long allDetail;
    private BigDecimal defectedProc;

}
