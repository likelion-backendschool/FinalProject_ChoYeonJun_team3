## Title: [1Week] 조연준

### 미션 요구사항 분석 & 체크리스트

- [x]  Post CRUD 구현
- [x]  Login/Logout Spring Security 구현
- [x]  SignUp 구현
- [x]  회원 정보 수정 구현
- [x]  회원 비밀번호 변경 구현
- [x]  회원 아이디 찾기 구현

### N주차 미션 요약

**[접근방법]**

프론트 단의 개발이 확실시되지 않은 상태이기 때문에 모든 Controller의 return 타입을 void로 설정,
추후 프론트 경로와 네이밍이 정해지면 대대적으로 수정 예정

**SignUp** <br>
SMTP 프로토콜을 사용하여 회원가입시 환영 메일 전송

**PostHashTag Table**  <br>
PostHashTag Table 에서 MemberId를 중복으로 저장할 필요성을 크게 느끼지 못해 저장하지 않고, 필요시 연관된 Post Entity에서 AuthorId를 참고하는 방식을 사용할 계획

    
<br>

**[특이사항]**

ToDo 리스트를 좀 더 세밀하게 정의하고 Issue에 등록한 다음 그에 관련된 커밋을 하는 방식으로 진행해야 할것.

주석 작성을 좀 더 신경써서 할 것

