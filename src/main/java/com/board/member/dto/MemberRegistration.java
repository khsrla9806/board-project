package com.board.member.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@Builder
public class MemberRegistration {

    @NotBlank(message = "이름이 누락되었습니다.")
    @Size(min = 2, max = 8, message = "이름은 최소 2글자 이상 최대 8글자 이하로 작성해야 합니다.")
    private String username;
    @NotBlank(message = "닉네임이 누락되었습니다.")
    @Size(min = 2, max = 8, message = "닉네임은 최소 2글자 이상 최대 8글자 이하로 작성해야 합니다.")
    private String nickname;
    @NotBlank(message = "이메일이 누락되었습니다.")
    @Email(message = "이메일 주소 형식이 잘못되었습니다.")
    private String email;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?!.*\\s).+$"
            , message = "비밀번호는 영어와 숫자를 혼용해야 하며 공백은 사용할 수 없습니다.")
    @Size(min = 8, max = 16, message = "비밀번호는 최소 8글자 이상 최대 16글자 이하로 작성해야 합니다.")
    private String password;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?!.*\\s).+$"
            , message = "비밀번호는 영어와 숫자를 혼용해야 하며 공백은 사용할 수 없습니다.")
    @Size(min = 8, max = 16, message = "비밀번호는 최소 8글자 이상 최대 16글자 이하로 작성해야 합니다.")
    private String confirmPassword;
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$"
            , message = "전화번호 형식이 맞지 않습니다.(01X-YYY(Y)-ZZZZ)")
    private String phone;
}
