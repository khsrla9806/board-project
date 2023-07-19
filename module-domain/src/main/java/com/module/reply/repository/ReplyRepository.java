package com.module.reply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.module.reply.domain.Reply;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findByBoardIdAndParentIsNull(Long boardId);
    void deleteAllByBoardId(Long boardId);
}
