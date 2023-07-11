package com.board.reply.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String content;

    private Long boardId;       // TODO : Board 와 연관관계 설정

    private Long memberId;      // TODO: MEMBER 와 연관관계 설정

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
