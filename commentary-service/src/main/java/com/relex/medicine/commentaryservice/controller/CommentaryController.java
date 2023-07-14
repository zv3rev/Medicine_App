package com.relex.medicine.commentaryservice.controller;

import com.relex.medicine.commentaryservice.exception.NoEntityFoundException;
import com.relex.medicine.commentaryservice.exception.NotUniqueEntry;
import com.relex.medicine.commentaryservice.exception.WrongAtributesException;
import com.relex.medicine.commentaryservice.request.CreateCommentaryRequest;
import com.relex.medicine.commentaryservice.request.UpdateCommentaryRequest;
import com.relex.medicine.commentaryservice.response.CommentaryResponse;
import com.relex.medicine.commentaryservice.service.CommentaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

@Controller
@RequiredArgsConstructor
    @RequestMapping("articles/{articleId}/commentaries")
public class CommentaryController {
    private final CommentaryService commentaryService;

    @GetMapping("/{commentaryId}")
    public ResponseEntity<CommentaryResponse> getCommentary(@PathVariable("articleId") Long articleId, @PathVariable("commentaryId") Long commentaryId) throws NoEntityFoundException, WrongAtributesException {
        CommentaryResponse commentary = commentaryService.getCommentary(articleId, commentaryId);
        return ResponseEntity.ok(commentary);
    }

    @GetMapping()
    public ResponseEntity<List<CommentaryResponse>> getArticleCommentaries(@PathVariable("articleId") Long articleId){
        return ResponseEntity.ok(commentaryService.getCommentaryByArticleId(articleId));
    }

    @PostMapping
    public ResponseEntity<Void> addCommentary(@PathVariable("articleId") Long articleId, @Valid @RequestBody CreateCommentaryRequest request) throws NoEntityFoundException, NoEntityFoundException {
        Long commentaryId = commentaryService.addCommentary(articleId ,request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{commentaryId}")
                .buildAndExpand(commentaryId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{commentaryId}")
    public ResponseEntity<Void> deleteCommentary(@PathVariable("articleId") Long articleId , @PathVariable("commentaryId") Long commentaryId) throws NoEntityFoundException, WrongAtributesException {
        commentaryService.deleteCommentary(articleId, commentaryId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{commentaryId}")
    public ResponseEntity<Void> updateCommentary(@PathVariable("articleId") Long articleId,
                                              @PathVariable("commentaryId") Long commentaryId,
                                              @Valid @RequestBody UpdateCommentaryRequest request) throws NoEntityFoundException, WrongAtributesException {
        commentaryService.updateCommentary(articleId, commentaryId, request);
        return ResponseEntity.ok().build();
    }
}
