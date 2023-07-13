package com.board.reply.repository;

import com.board.reply.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findByBoardIdAndParentIsNull(Long boardId);

}
