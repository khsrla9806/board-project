package com.module.board.domain;

import com.module.board.type.Category;
import com.module.board.type.Status;
import com.module.member.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Status status;

    private LocalDateTime createdAt; // TODO: BaseEntity 분리
    private LocalDateTime updatedAt; // TODO: BaseEntity 분리
}
