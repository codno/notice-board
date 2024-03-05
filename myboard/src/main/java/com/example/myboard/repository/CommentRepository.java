package com.example.myboard.repository;

import com.example.myboard.model.DeleteStatus;
import com.example.myboard.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByCommentNoAndBoard_BoardNoAndDeleteStatus(Long commentId, Long boardId, DeleteStatus deleteStatus);
}
