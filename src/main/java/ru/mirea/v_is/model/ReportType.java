package ru.mirea.v_is.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportType {
    DALY_GENERAL_REPORT("Ежедневный отчет по показателям");

    private String description;
}
