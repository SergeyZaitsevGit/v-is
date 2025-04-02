package ru.mirea.v_is.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics")
public class AnalyticsController {

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String analyticsPage() {
        return "analytics";
    }

}