package com.module.report.domain;

import com.module.board.domain.Board;
import com.module.global.BaseEntity;
import com.module.member.domain.Member;
import com.module.report.type.Reason;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "member_id", "board_id" }) })
public class Report extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @Enumerated(EnumType.STRING)
    private Reason reason;

    @Setter
    private String evidenceImage;

}
