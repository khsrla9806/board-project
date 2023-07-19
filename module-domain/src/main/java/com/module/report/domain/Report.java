package com.module.report.domain;

import com.module.board.domain.Board;
import lombok.*;
import com.module.member.domain.Member;
import com.module.report.type.Reason;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "member_id", "board_id" }) })
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @Enumerated(EnumType.STRING)
    private Reason reason;

    @Setter
    private String evidenceImage;

    private LocalDateTime createdAt; // TODO: BaseEntity 분리
    private LocalDateTime updatedAt; // TODO: BaseEntity 분리
}
