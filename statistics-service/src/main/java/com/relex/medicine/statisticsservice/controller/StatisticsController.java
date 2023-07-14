package com.relex.medicine.statisticsservice.controller;

import com.relex.medicine.statisticsservice.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping
    public ResponseEntity<Map<String,Integer>> getStatistics(){
        return ResponseEntity.ok(statisticsService.getStatistics());
    }
}
