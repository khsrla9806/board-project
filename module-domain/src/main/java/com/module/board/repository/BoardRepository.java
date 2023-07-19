package com.module.board.repository;

import com.module.board.domain.Board;
import com.module.board.type.Category;
import com.module.board.type.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Page<Board> findAllByCategoryAndStatus(Category category, Status status, Pageable pageable);

    @Query(
            "SELECT b " +
            "FROM Board b " +
            "LEFT JOIN b.member m " +
            "WHERE (b.title LIKE %:keyword% OR b.content LIKE %:keyword% OR m.nickname LIKE %:keyword%) " +
            "AND b.category = :category AND b.status = :status"
    )
    Page<Board> findAllByCategoryAndKeyword(
            @Param("category") Category category,
            @Param("status") Status status,
            @Param("keyword") String keyword,
            Pageable pageable);

    Page<Board> findAllByStatus(Status status, Pageable pageable);

    @Query(
            "SELECT b " +
            "FROM Board b " +
            "LEFT JOIN b.member m " +
            "WHERE (b.title LIKE %:keyword% OR b.content LIKE %:keyword% OR m.nickname LIKE %:keyword%) " +
            "AND b.status = :status"
    )
    Page<Board> findAllByKeyword(@Param("status") Status status, @Param("keyword") String keyword, Pageable pageable);
}
