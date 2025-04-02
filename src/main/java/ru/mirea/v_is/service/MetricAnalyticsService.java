package ru.mirea.v_is.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.mirea.v_is.dto.MetricAnalyticsDTO;
import ru.mirea.v_is.repo.MetricsRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class MetricAnalyticsService {
    private final SimpMessagingTemplate messagingTemplate;
    private final MetricsRepo metricRepository;

    @Scheduled(fixedRate = 5000)
    public void sendAnalytics() {
        List<MetricAnalyticsDTO> analytics = metricRepository.getDailyAverageMetrics();
        messagingTemplate.convertAndSend("/topic/metrics-analytics", analytics);
    }
}
