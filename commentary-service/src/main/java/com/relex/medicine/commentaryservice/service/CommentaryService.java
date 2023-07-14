package com.relex.medicine.commentaryservice.service;

import com.relex.medicine.commentaryservice.entity.Article;
import com.relex.medicine.commentaryservice.entity.Commentary;
import com.relex.medicine.commentaryservice.exception.NoEntityFoundException;
import com.relex.medicine.commentaryservice.exception.WrongAtributesException;
import com.relex.medicine.commentaryservice.repository.CommentaryRepository;
import com.relex.medicine.commentaryservice.request.CreateCommentaryRequest;
import com.relex.medicine.commentaryservice.request.UpdateCommentaryRequest;
import com.relex.medicine.commentaryservice.response.CommentaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentaryService {
    public static final String GET_ARTICLE_BY_ID_URL = "http://localhost:8082/article-service/articles/";
    private final CommentaryRepository commentaryRepository;

    public CommentaryResponse getCommentary(Long articleId,Long commentaryId) throws NoEntityFoundException, WrongAtributesException {
        Commentary commentary = commentaryRepository.findById(commentaryId);
        checkExsistance(articleId, commentary);

        return mapToResponse(commentary);
    }

    private static void checkExsistance(Long articleId, Commentary commentary) throws NoEntityFoundException, WrongAtributesException {
        if (commentary == null){
            throw new NoEntityFoundException();
        }
        if (!Objects.equals(commentary.getArticleId(), articleId)){
            throw new WrongAtributesException("This commentary not belong to specified article");
        }
    }

    public List<CommentaryResponse> getCommentaryByArticleId(Long articleId){
        return commentaryRepository.findByArticleId(articleId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Long addCommentary(Long articleId ,CreateCommentaryRequest request) throws NoEntityFoundException {
        ResponseEntity<Article> responseEntity = new RestTemplate().getForEntity(
                GET_ARTICLE_BY_ID_URL + articleId, Article.class);

        if (!responseEntity.getStatusCode().is2xxSuccessful()){
            throw new NoEntityFoundException();
        }

        Commentary commentaryToAdd = Commentary.builder()
                .articleId(articleId)
                .author(request.getAuthor())
                .commentaryText(request.getCommentaryText())
                .publishingDate(new Date(System.currentTimeMillis()))
                .build();

        return commentaryRepository.insert(commentaryToAdd);
    }

    public void deleteCommentary(Long articleId, Long commentaryId) throws WrongAtributesException, NoEntityFoundException {
        Commentary commentary = commentaryRepository.findById(commentaryId);
        checkExsistance(articleId, commentary);

        commentaryRepository.delete(commentaryId);
    }

    public void updateCommentary(Long articleId, Long commentaryId, UpdateCommentaryRequest request) throws WrongAtributesException, NoEntityFoundException {
        Commentary commentary = commentaryRepository.findById(commentaryId);
        checkExsistance(articleId, commentary);

        commentary.setCommentaryText(request.getCommentaryText());
        commentary.setAuthor(request.getAuthor());

        commentaryRepository.update(commentary);
    }

    private CommentaryResponse mapToResponse(Commentary entity) {
        return new CommentaryResponse(
                entity.getId(),
                entity.getArticleId(),
                entity.getAuthor(),
                entity.getCommentaryText(),
                entity.getPublishingDate()
        );
    }
}
