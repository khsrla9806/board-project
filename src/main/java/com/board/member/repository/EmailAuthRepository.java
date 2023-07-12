package com.board.member.repository;

import com.board.member.domain.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long> {
}
