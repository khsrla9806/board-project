package com.board.board.domain;

import com.board.board.type.Category;
import com.board.board.type.Status;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private String thumbnail;

    private Long member_id; // TODO: Member ManyToOne 관계 형성

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Status status;

    private LocalDateTime createdAt; // TODO: BaseEntity 분리
    private LocalDateTime updatedAt; // TODO: BaseEntity 분리
}
