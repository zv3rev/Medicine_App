package com.relex.medicine.statisticsservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.sql.Date;

@Data
@Builder
@Jacksonized
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Commentary {
    private Long id;
    private Long articleId;
    private String author;
    private String commentaryText;
    private Date publishingDate;
}
