package com.relex.medicine.articleservice.service;

import com.relex.medicine.articleservice.entity.Article;
import com.relex.medicine.articleservice.exception.NoEntityFoundException;
import com.relex.medicine.articleservice.exception.NotUniqueEntry;
import com.relex.medicine.articleservice.repository.ArticleRepository;
import com.relex.medicine.articleservice.request.CreateArticleRequest;
import com.relex.medicine.articleservice.request.UpdateArticleRequest;
import com.relex.medicine.articleservice.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleResponse getArticle(Long articleId) throws NoEntityFoundException {
        Article article = articleRepository.findById(articleId);
        if (article == null){
            throw new NoEntityFoundException();
        }
        return mapToResponse(article);
    }

    public List<ArticleResponse> getAllArticles() {
        return articleRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long addArticle(CreateArticleRequest request) throws NotUniqueEntry {
        if (articleRepository.findByTitle(request.getTitle()) != null )
        {
            throw new NotUniqueEntry("An article with this title already exists");
        }

        Article articleToAdd = Article.builder()
                .title(request.getTitle())
                .shortDescription(request.getShortDescription())
                .articleText(request.getArticleText())
                .author(request.getAuthor())
                .publishingDate(new Date(System.currentTimeMillis()))
                .build();
        return articleRepository.insert(articleToAdd);
    }

    public void deleteArticle(Long articleId) {
        articleRepository.delete(articleId);
    }


    @Transactional
    public void updateArticle(Long articleId, UpdateArticleRequest request) throws NoEntityFoundException {
        Article articleToEdit = articleRepository.findById(articleId);
        if (articleToEdit == null){
            throw new NoEntityFoundException();
        }

        articleToEdit.setTitle(request.getTitle());
        articleToEdit.setShortDescription(request.getShortDescription());
        articleToEdit.setArticleText(request.getArticleText());
        articleToEdit.setAuthor(request.getAuthor());
        articleRepository.update(articleToEdit);
    }

    private ArticleResponse mapToResponse(Article entity){
        return new ArticleResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getShortDescription(),
                entity.getArticleText(),
                entity.getAuthor(),
                entity.getPublishingDate()
        );
    }
}
