package com.example.myboard.model.request;

import lombok.Data;

@Data
public class CommentDeleteRequest {
    private Long boardNo;
    private Long commentNo;
}
