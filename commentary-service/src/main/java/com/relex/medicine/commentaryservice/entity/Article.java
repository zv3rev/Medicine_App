package com.relex.medicine.commentaryservice.entity;

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
public class Article {
    private Long id;
    private String title;
    private String shortDescription;
    private String articleText;
    private String author;
    private Date publishingDate;
}
