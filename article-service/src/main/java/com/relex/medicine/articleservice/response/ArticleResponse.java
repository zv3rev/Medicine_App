package com.relex.medicine.articleservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class ArticleResponse {
    private Long id;
    private String title;
    private String shortDescription;
    private String articleText;
    private String author;
    private Date publishingDate;
}
