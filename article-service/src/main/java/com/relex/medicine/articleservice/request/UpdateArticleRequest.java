package com.relex.medicine.articleservice.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Date;

@Data
public class UpdateArticleRequest {
    @NotBlank(message = "Title can't be blank")
    @Size(max = 100, message = "Exceeded the maximum length of the title")
    private String title;
    @NotNull(message = "Description can't be null")
    @Size(max = 300, message = "Exceeded the maximum length of the description")
    private String shortDescription;
    @NotBlank(message = "Text can't be blank")
    private String articleText;
    @NotBlank(message = "Author's name can't be blank")
    @Size(max = 100, message = "Exceeded the maximum length of author's name")
    private String author;
}
