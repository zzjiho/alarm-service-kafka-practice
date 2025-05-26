package com.fc.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 실제론 이게 entity가 되겠다
@Getter
@AllArgsConstructor
public class Post {
    private long id;
    private long userId;
    private String imageUrl;
    private String content;
}
