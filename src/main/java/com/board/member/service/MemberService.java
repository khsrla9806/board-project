package com.board.member.service;

import com.board.member.dto.MemberRegistration;
import com.board.response.dto.CommonResult;
import com.board.response.dto.SingleResult;

public interface MemberService {

    SingleResult<Long> register(MemberRegistration parameter);

    CommonResult authConfirm(Long id, String emailAuthToken);
}
