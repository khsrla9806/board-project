# 🖥️ 게시판 프로젝트

프로젝트 기간: `2023.07.10 ~ 2023.07.21`

<br>

## 🧑🏻‍💻 팀 구성
<table>
   <tr>
      <td align="center">
        <a href="https://github.com/khsrla9806">
          <img src="https://avatars.githubusercontent.com/u/70641477?v=4" width="100px;" alt=""/><br>
          <sub><b>김훈섭</b></sub><br>
          <sub><b>khsrla9806</b></sub>
        </a>
      </td>
     <td align="center">
        <a href="https://github.com/jinyngg">
          <img src="https://avatars.githubusercontent.com/u/96164211?v=4" width="100px;" alt=""/><br>
          <sub><b>장진영</b></sub><br>
          <sub><b>khsrla9806</b></sub>
        </a>
      </td>
     <td align="center">
        <a href="https://github.com/zjdtm">
          <img src="https://avatars.githubusercontent.com/u/35757620?v=4" width="100px;" alt=""/><br>
          <sub><b>서용현</b></sub><br>
          <sub><b>khsrla9806</b></sub>
        </a>
      </td>
      <td align="center">
        <a href="https://github.com/jeanparkk">
          <img src="https://avatars.githubusercontent.com/u/119830820?v=4" width="100px;" alt=""/><br>
          <sub><b>박장희</b></sub><br>
          <sub><b>jeanparkk</b></sub>
        </a>
      </td>
   </tr>
</table>

<br><br><br>

## 🛠️ 프로젝트에 사용된 기술스택
### 🧑🏻‍💻 Back
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=springboot&logoColor=white"> <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white"> <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=flat-square&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/MyBatis-41454A?style=flat-square&logo=&logoColor=white"> <img src="https://img.shields.io/badge/Java 11-FF160B?style=flat-square&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=gradle&logoColor=white"> <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white">

<br>

### 🧑🏻‍💻 Front (Server Side Rendering)
<img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=flat-square&logo=thymeleaf&logoColor=white"> <img src="https://img.shields.io/badge/Javascript-F7DF1E?style=flat-square&logo=javascript&logoColor=white"> <img src="https://img.shields.io/badge/HTML5-E34F26?style=flat-square&logo=html5&logoColor=white"> <img src="https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=css3&logoColor=white"> <img src="https://img.shields.io/badge/Bootstrap-7952B3?style=flat-square&logo=bootstrap&logoColor=white">

<br>

### 🙆🏻‍♂️ Collaboration Tool & IDEA
[![My Skills](https://skillicons.dev/icons?i=idea,git,github)](https://skillicons.dev) <img src="https://github.com/khsrla9806/board-project/assets/70641477/7c3f68ac-1a63-46a8-8fce-9715fd1d033e" width="50">

<br><br><br>

## 🙋🏻‍♂️ 팀원 역할
`김훈섭`
- 게시글 작성 기능 (썸머노트 적용)
- 게시글 카테고리별 저장 기능 (회원의 등급에 따라 새싹, 우수 카테고리 게시글 저장)
- 게시글 목록보기 (전체, 새싹, 우수 목록 분리, 게시글 썸네일 이미지 가져오기)
- 게시글 페이징 기능 (페이지당 6개의 게시글, 그리드 카드 형식)
- 게시글 상세보기 기능 (제목, 작성자, 본문 내용, 관련된 댓글 리스트 불러오기)
- 게시글 검색 기능 (검색 키워드가 제목, 내용, 작성자에 포함된 글을 페이징으로 가져오기)
- 게시글 삭제 (자신이 작성한 글만 삭제 가능)
- 게시글 수정 (제목, 본문, 썸네일 변경 가능, 자신이 작성한 글만 수정 가능)
- 게시글 신고 (같은 사용자가 같은 게시글을 2번이상 신고하지 못하도록 제약조건)
- **[관리자]** 회원 권한 관리 기능 (관리자는 모든 회원의 Role을 변경할 수 있음)

<br>

`장진영`
- 회원가입 기능 (이메일 인증 기능 적용)
- 로그인 기능 (Spring Security 적용)
- 중복체크 기능
  - 동일 닉네임, 동일 이메일, 동일 전화번호, 동일 비밀번호 조건에 대한 중복 체크 기능
- 회원정보 조회
- 회원정보, 비밀번호 수정 (비밀번호를 입력하여 통과한 경우 수정 가능)
- 스케쥴러 등록 (1분에 한번씩 작동하여 게시글 10개 이상인 유저의 등급을 올리는 스케쥴러 등록)
- 멀티모듈 구성 (관리자 모듈, 클라이언트 모듈, 도메인 모듈, 스케쥴러 모듈 분리)
- **[관리자]** 회원 Email 전송 기능
- **[관리자]** 모든 게시글에 대한 CRUD 기능 구현 (관리자 페이지에서 모든 게시글의 상태를 관리 가능)
- **[관리자]** 회원 블랙리스트 등록 기능

<br>

`서용현`
- 댓글 작성 (로그인한 사용자만 댓글을 작성 가능)
- 대댓글 작성 (1 depth까지 구현)
- 댓글 삭제 (본인이 작성한 댓글만 삭제 가능)
- **[관리자]** 페이지 UI 구성 (adminLite 적용)
- **[관리자]** 게시글 통계 관리
  - 모든 유저의 정보와 작성한 게시글, 댓글 개수를 볼 수 있는 통계 페이지
  - 모든 유저의 정보를 게시글 작성수, 댓글 작성수를 통해 오름차순, 내림차순 정렬로 볼 수 있음
- **[관리자]** 신고 목록 조회 기능
  - 신고당한 게시글의 유형(음란, 비방, 욕설)에 따라서 필터링해서 확인 가능
- **[관리자]** 신고당한 게시글의 작성자 블랙 리스트 등록 기능

<br><br>

## ✋🏻 깃허브 커밋 메시지 규칙
```
[#이슈번호] feat: 새로운 기능 추가했을 때
[#이슈번호] fix: 버그나 오류 수정했을 때
[#이슈번호] refactor: 코드 리팩토링했을 때
[#이슈번호] chore: 약간 애매한 기타 변경사항
[#이슈번호] docs: 리드미 파일이나 md 파일 수정할 때 (문서작업)
```

<br><br>

## 🙌🏻 협업 규칙
- 각자 저장소를 Fork하여 작업
- Git Flow 전략에 따라서 feature, develop, main으로 브랜치 관리
- 작업 시작전에 해당 작업에 대한 이슈를 등록
- 브랜치를 생성 (ex. `feature/#{이슈번호}-{간단한 설명}`)
- 작업 진행
- 해당 기능에 대한 모든 작업이 끝나면 develop으로 Pull Request 요청
- 팀원들에게 PR 내용을 공유 후 Rebase and Merge 적용 (Commit Message를 깔끔하게 관리하기 위해 채택)
- Pull Request 과정에서 충돌이 난 경우 충돌난 코드와 관련된 팀원들이 소통하여 충돌 해결 후 Merge
- develop에서 모든 작업이 끝난 후 main으로 Pull Request & Rebase and Merge 후 프로젝트 종료
