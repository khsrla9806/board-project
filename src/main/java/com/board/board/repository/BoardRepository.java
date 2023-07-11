package com.board.board.repository;

import com.board.board.domain.Board;
import com.board.board.type.Category;
import com.board.board.type.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAllByCategoryAndStatus(Category category, Status status, Pageable pageable);
}
