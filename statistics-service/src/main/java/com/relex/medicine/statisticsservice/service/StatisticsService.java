package com.relex.medicine.statisticsservice.service;

import com.relex.medicine.statisticsservice.entity.Article;
import com.relex.medicine.statisticsservice.entity.Commentary;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    private static final String GET_ARTICLES = "http://localhost:8082/article-service/articles";
    private static final String GET_ARTICLE_COMMENTARIES = "http://localhost:8082/commentary-service/articles/";
    public Map<String,Integer> getStatistics() {
        ResponseEntity<List<Article>> articleEntity = new RestTemplate().exchange(
                GET_ARTICLES, HttpMethod.GET, null, new ParameterizedTypeReference<List<Article>>() {
                });
        List<Article> articles = articleEntity.getBody();

        Map<String, Integer> report = new HashMap<>();
        for (Article article: articles) {
            ResponseEntity<List<Commentary>> commentaryEntity = new RestTemplate().exchange(
                    GET_ARTICLE_COMMENTARIES + article.getId() + "/commentaries", HttpMethod.GET, null, new ParameterizedTypeReference<List<Commentary>>() {
                    });
            report.put(article.getTitle(), commentaryEntity.getBody().size());
        }

        return report;
    }
}
