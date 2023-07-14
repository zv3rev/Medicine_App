package com.relex.medicine.articleservice.repository;

import com.relex.medicine.articleservice.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArticleRepository {
    public static final String SELECT_COUNT_QUERY = "SELECT COUNT(*) FROM article";
    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL_QUERY = "SELECT id, title, short_description, article_text, author, publishing_date FROM article";
    private static final String SELECT_BY_ID_QUERY = SELECT_ALL_QUERY + " WHERE id = ?";
    private static final String SELECT_BY_TITLE_QUERY = SELECT_ALL_QUERY + " WHERE title = ?";
    private static final String INSERT_QUERY = "INSERT INTO article (title, short_description, article_text, author, publishing_date) VALUES (?, ?, ?, ?, ?) RETURNING id";
    private static final String UPDATE_QUERY = "UPDATE article SET title = ?, short_description = ?, article_text = ?, author = ?, publishing_date = ? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM article WHERE id = ?";

    private static final RowMapper<Article> mapper = (rs, rowNum) -> Article.builder()
            .id(rs.getLong("id"))
            .title(rs.getString("title"))
            .shortDescription(rs.getString("short_description"))
            .articleText(rs.getString("article_text"))
            .author(rs.getString("author"))
            .publishingDate(rs.getDate("publishing_date"))
            .build();

    public List<Article> findAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY, mapper);
    }

    public Article findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, mapper, id);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Article findByTitle(String title) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_TITLE_QUERY, mapper, title);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Long insert(Article article) {
        return jdbcTemplate.queryForObject(INSERT_QUERY, Long.class, article.getTitle(), article.getShortDescription(), article.getArticleText(), article.getAuthor(), article.getPublishingDate());
    }

    public void update(Article article) {
        jdbcTemplate.update(UPDATE_QUERY, article.getTitle(), article.getShortDescription(), article.getArticleText(), article.getAuthor(), article.getPublishingDate(), article.getId());
    }

    public void delete(Long id) {
        jdbcTemplate.update(DELETE_QUERY, id);
    }

}
