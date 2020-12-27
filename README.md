# laughing-octo-memory

## 핵심 문제해결 전략

1. token 생성
    * 3자리 문자를 미리 입력된 String.charAt(index)를 통해 Random으로 생성된 숫자를 통해 생성합니다.(a-zA-Z0-9, 62진수)
    * 토큰 생성 시 기존 발급된 토큰과 충돌을 피하기 위해 Random으로 생성된 숫자에 1씩 더하며 가능한 토큰을 찾습니다.
    
2. 데이터베이스
    * H2, JPA를 사용하여 개발했습니다.
    * SprinkledMoney, DividedMoney 두 개의 Entity를 만들어 사용했고 1대다 관계로 SprinkledMoney를 통해서만 조회할 수 있도록 했습니다.

3. 받기 만료 시간, 7일동안 조회
    * LocalDateTime.now()를 통해 서버 시간을 반영했습니다. 