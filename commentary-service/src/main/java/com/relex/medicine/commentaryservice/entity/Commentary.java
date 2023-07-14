package com.relex.medicine.commentaryservice.entity;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder
public class Commentary {
    private Long id;
    private Long articleId;
    private String author;
    private String commentaryText;
    private Date publishingDate;
}
