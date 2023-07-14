package com.relex.medicine.commentaryservice.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class CommentaryResponse {
    private Long id;
    private Long articleId;
    private String author;
    private String commentaryText;
    private Date date;
}
