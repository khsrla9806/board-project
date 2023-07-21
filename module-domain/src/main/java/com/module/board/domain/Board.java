package com.module.board.domain;

import com.module.board.type.Category;
import com.module.board.type.Status;
import com.module.global.BaseEntity;
import com.module.member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board extends BaseEntity {
    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Setter
    private String thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Category category;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Status status;
}
