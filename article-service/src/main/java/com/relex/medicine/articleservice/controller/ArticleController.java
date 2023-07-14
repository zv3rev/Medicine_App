package com.relex.medicine.articleservice.controller;

import com.relex.medicine.articleservice.exception.NoEntityFoundException;
import com.relex.medicine.articleservice.exception.NotUniqueEntry;
import com.relex.medicine.articleservice.request.CreateArticleRequest;
import com.relex.medicine.articleservice.request.UpdateArticleRequest;
import com.relex.medicine.articleservice.response.ArticleResponse;
import com.relex.medicine.articleservice.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponse> getArticle(@PathVariable("articleId") Long articleId) throws NoEntityFoundException {
        ArticleResponse article = articleService.getArticle(articleId);
        return ResponseEntity.ok(article);
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponse>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }

    @PostMapping
    public ResponseEntity<Void> addArticle(@Valid @RequestBody CreateArticleRequest request) throws NotUniqueEntry {
        Long articleId = articleService.addArticle(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{articleId}")
                .buildAndExpand(articleId)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{articleId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable("articleId") Long articleId) {
        articleService.deleteArticle(articleId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{articleId}")
    public ResponseEntity<Void> updateArticle(@PathVariable("articleId") Long articleId,
                                              @Valid @RequestBody UpdateArticleRequest request) throws NoEntityFoundException {
        articleService.updateArticle(articleId, request);
        return ResponseEntity.ok().build();
    }

}
