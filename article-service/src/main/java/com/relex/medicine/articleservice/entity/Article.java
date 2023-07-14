package com.relex.medicine.articleservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
public class Article {
    private Long id;
    private String title;
    private String shortDescription;
    private String articleText;
    private String author;
    private Date publishingDate;
}
