package com.relex.medicine.commentaryservice.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCommentaryRequest {
    @NotBlank(message = "Author can't be blank")
    @Size(max = 100, message = "Exceeded the maximum length of the author's name")
    private String author;
    @NotBlank(message = "Commentary can't be blank")
    private String commentaryText;
}
