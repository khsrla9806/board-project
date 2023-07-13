package com.board.reply.domain;

import com.board.board.domain.Board;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String content;

    // null 일 경우 최상위 댓글
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    @Setter
    private Reply parent;

    // 자식 댓글들
    @ToString.Exclude
    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Reply> children = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private Long memberId;      // TODO: MEMBER 와 연관관계 설정

    @CreatedDate
    private LocalDateTime createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Reply reply = (Reply) o;
        return id != null && Objects.equals(id, reply.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
