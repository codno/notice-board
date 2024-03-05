package com.example.myboard.service;

import com.example.myboard.model.DeleteStatus;
import com.example.myboard.model.entity.Board;
import com.example.myboard.model.entity.Comment;
import com.example.myboard.model.request.CommentDeleteRequest;
import com.example.myboard.model.request.CommentPostRequest;
import com.example.myboard.model.response.BoardResponse;
import com.example.myboard.repository.BoardRepository;
import com.example.myboard.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 댓글 등록
    @Transactional
    public BoardResponse writeComment(CommentPostRequest request) {
        // 예외 처리 - 존재하는 게시글인지 확인
        Optional<Board> boardOptional = boardRepository.findBoardWithCommentsByBoardNo(request.getBoardNo());
        Board board = boardOptional.orElseThrow(() -> new RuntimeException("존재하지 않는 게시글입니다."));

        board.addComment(request.getCommentBody());
        boardRepository.save(board);

        return BoardResponse.from(board);
    }

    // 댓글 삭제
    @Transactional
    public String deleteComment(CommentDeleteRequest request) {
        // 예외 처리 - 존재하는 게시글 + 댓글인지 확인
        Optional<Comment> commentOptional = commentRepository.findByCommentNoAndBoard_BoardNoAndDeleteStatus(
                request.getCommentNo(), request.getBoardNo(), DeleteStatus.ACTIVE
        );
        Comment comment = commentOptional.orElseThrow(() -> new RuntimeException("존재하지 않는 댓글입니다."));

        commentRepository.delete(comment);

        return "OK";
    }
}
