package com.module.reply.domain;

import com.module.board.domain.Board;
import com.module.member.domain.Member;
import com.module.global.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Reply extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String content;

    // null 일 경우 최상위 댓글
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Reply parent;

    // 자식 댓글들
    @ToString.Exclude
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    @Builder.Default
    private List<Reply> children = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}
