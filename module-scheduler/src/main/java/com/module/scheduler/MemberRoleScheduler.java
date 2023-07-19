package com.module.scheduler;

import com.module.member.domain.Member;
import com.module.member.repository.MemberRepository;
import com.module.member.type.MemberRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberRoleScheduler {

    private final MemberRepository memberRepository;

    private static final Long NUMBER_FOR_PREMIUM_UPGRADE = 10L;

    @Transactional
    @Scheduled(cron = "${scheduler.cron}")
    public void MemberRoleScheduling() {
        // 1. 게시물 수가 10개 이상인 회원 목록 조회
        List<Member> members = memberRepository.findMembersWithBoardCount(NUMBER_FOR_PREMIUM_UPGRADE);
        
        if (members != null) {
            // 2. 회원 등급 업그레이드
            try {
                members.forEach(member -> {
                    member.updateMemberRole(MemberRole.PREMIUM);
                    memberRepository.save(member);

                    log.info("member role upgraded. {}", member.getNickname());
                });

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }
}
