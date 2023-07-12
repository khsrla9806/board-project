package com.board.member.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Builder
public class MemberRegistration {

    @NotBlank(message = "이메일이 누락되었습니다.")
    @Email(message = "이메일 주소 형식이 잘못되었습니다.")
    private String email;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?!.*\\s).+$"
            , message = "비밀번호는 영어와 숫자를 혼용해야 하며 공백은 사용할 수 없습니다.")
    private String password;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?!.*\\s).+$"
            , message = "비밀번호는 영어와 숫자를 혼용해야 하며 공백은 사용할 수 없습니다.")
    private String confirmPassword;
    @Pattern(regexp = "^\\d{2,3}\\d{3,4}\\d{4}$"
            , message = "전화번호 형식이 맞지 않습니다.(01XYYY(Y)ZZZZ)")
    private String phone;
    @NotBlank(message = "이름이 누락되었습니다.")
    private String username;
    @NotBlank(message = "닉네임이 누락되었습니다.")
    private String nickname;
}
