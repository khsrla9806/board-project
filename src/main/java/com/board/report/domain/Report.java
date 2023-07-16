package com.board.report.domain;

import com.board.board.domain.Board;
import com.board.member.domain.Member;
import com.board.report.type.Reason;
import lombok.*;

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
