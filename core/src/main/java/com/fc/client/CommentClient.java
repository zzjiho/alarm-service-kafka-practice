package com.fc.client;

import com.fc.domain.Comment;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommentClient {

    // 실제사용하려면 코멘트 서비스를 호출해서 댓글을 가져오도록 하는 코드가 필요함.

    private final Map<Long, Comment> comments = new HashMap<>();

    public CommentClient() {
        // User 1 comments
        comments.put(1L, new Comment(1L, 1L, "content1", Instant.now()));
        comments.put(2L, new Comment(2L, 1L, "content2", Instant.now()));
        comments.put(3L, new Comment(3L, 1L, "content3", Instant.now()));
        comments.put(4L, new Comment(4L, 1L, "content4", Instant.now()));
        comments.put(5L, new Comment(5L, 1L, "content5", Instant.now()));

        // User 2 comments
        comments.put(6L, new Comment(6L, 2L, "content6", Instant.now()));
        comments.put(7L, new Comment(7L, 2L, "content7", Instant.now()));
        comments.put(8L, new Comment(8L, 2L, "content8", Instant.now()));
        comments.put(9L, new Comment(9L, 2L, "content9", Instant.now()));
        comments.put(10L, new Comment(10L, 2L, "content10", Instant.now()));
    }

    public Comment getComment(long id) {
        return comments.get(id);
    }
}
