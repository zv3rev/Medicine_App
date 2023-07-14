package com.relex.medicine.commentaryservice.repository;

import com.relex.medicine.commentaryservice.entity.Commentary;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentaryRepository {

    private static final RowMapper<Commentary> mapper = (rs, rowNum) -> Commentary.builder()
            .id(rs.getLong("id"))
            .articleId(rs.getLong("article_id"))
            .author(rs.getString("author"))
            .commentaryText(rs.getString("commentary_text"))
            .publishingDate(rs.getDate("publishing_date"))
            .build();
    private static final String SELECT_BY_ARTICLE_ID_QUERY = "SELECT id, article_id, author, commentary_text, publishing_date FROM commentary WHERE article_id = ?";
    public static final String SELECT_BY_ID_QUERY = "SELECT id, article_id, author, commentary_text, publishing_date FROM commentary WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO commentary (article_id, author, commentary_text, publishing_date) VALUES (?, ?, ?, ?) RETURNING id";
    private static final String DELETE_QUERY = "DELETE FROM commentary WHERE id = ?";
    public static final String UPDATE_QUERY = "UPDATE article SET author = ? commentary_text = ? WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;

    public List<Commentary> findByArticleId(Long articleId) {
        return jdbcTemplate.query(SELECT_BY_ARTICLE_ID_QUERY, mapper, articleId);
    }

    public Commentary findById(Long commentaryId) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, mapper, commentaryId);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }


    public Long insert(Commentary commentary) {
        return jdbcTemplate.queryForObject(INSERT_QUERY, Long.class, commentary.getArticleId(), commentary.getAuthor(), commentary.getCommentaryText(), commentary.getPublishingDate());
    }

    public void delete(Long id) {
        jdbcTemplate.update(DELETE_QUERY, id);
    }

    public void update(Commentary commentary) {
        jdbcTemplate.update(UPDATE_QUERY, commentary.getAuthor(), commentary.getCommentaryText(), commentary.getId());
    }
}
