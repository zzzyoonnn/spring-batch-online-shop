# Online Shop Spring Batch

온라인 쇼핑몰 백오피스에서 반복적으로 수행되는 운영 업무를 Spring Batch로 자동화하기 위한
학습 및 실습 프로젝트입니다. 회원·정산, 상품·주문, 데이터·통계 영역을 세 단계로 나누어
DTO와 배치 처리 구조를 설계합니다.

## 현재 구현 상태

- Spring Boot, Spring Batch JDBC, JPA, MySQL 기반 프로젝트 구성
- Spring Security가 적용된 관리자 배치 실행 화면
- 기본 `helloJob` 실행 및 최근 Job 실행 이력 조회
- `@StepScope`와 Job Parameter를 사용하는 상품 CSV Reader
- 1~3단계 배치 도메인 DTO 설계
- 1단계 User/Settlement 배치 검증용 MySQL 더미데이터
- H2 기반 애플리케이션 및 DTO 테스트

> 현재 업무 배치 Job은 순차적으로 구현 중입니다. DTO와 테스트 데이터가 존재한다고 해서
> 해당 업무 Job 전체가 구현된 것은 아닙니다.

## 배치 업무 구성

| 단계 | 도메인 | 배치 업무 | 준비 상태 |
| --- | --- | --- | --- |
| 1단계 | User / Settlement | 휴면 계정 전환, 일일 매출 정산, 포인트 만료 처리 | DTO 및 MySQL 더미데이터 |
| 2단계 | Product / Order | 대량 상품 엑셀 업로드, 배송 상태 자동 변경, 품절 안내 알림 | DTO 및 CSV Reader 프로토타입 |
| 3단계 | Data / Stats | 일일 접속 통계 집계, 오래된 로그 삭제, 검색 인덱스 동기화 | DTO |

전체 처리 영역은 다음 순서로 확장합니다.

```text
회원·정산  →  상품·주문  →  데이터·통계
   1단계          2단계           3단계
```

## 주요 DTO

### 1단계: User / Settlement

| DTO | 주요 필드 |
| --- | --- |
| `UserDto` | 회원 ID, 비밀번호 해시, 이름, 포인트 잔액, 계정 상태, 마지막 로그인, 휴면 전환 시각 |
| `UserDto.PurchaseHistory` | 주문·상품 ID, 상품명, 수량, 단가, 총금액, 주문 상태, 주문 시각 |
| `UserDto.PointBalance` | 포인트 ID, 잔여 포인트, 적립 시각, 만료 시각 |
| `SettlementDto` | 정산일, 주문 건수, 판매 수량, 총매출, 환불액, 순매출, 계산 시각 |
| `SettlementLineDto` | 상품 ID, 상품명, 판매 수량, 주문 당시 단가, 상품별 매출액 |

### 2단계: Product / Order

| DTO | 주요 필드 |
| --- | --- |
| `ProductDto` | 상품 ID·코드, 상품명, 구성 수량, 가격, 재고, 카테고리, 판매 상태 |
| `OrderDto` | 주문·회원 ID, 주문자명, 전체 수량·금액, 배송 상태, 운송장, 주문 시각 |
| `OrderDto.OrderLine` | 상품 ID·상품명, 주문 수량, 단가, 상품별 총금액 |
| `ProductCsvRowDto` | CSV 상품명, 가격, 카테고리 코드 |
| `DeliveryStatusUpdateDto` | 주문 ID, 운송장, 이전·다음 배송 상태, 변경 시각 |
| `SoldOutNotificationDto` | 상품·회원 ID, 상품명, 알림 채널·상태, 생성 시각 |

### 3단계: Data / Stats

| DTO | 주요 필드 |
| --- | --- |
| `DailyAccessStatDto` | 통계일, 전체 접속 수, 순방문자 수, 페이지뷰, 계산 시각 |
| `OldLogCleanupRequest` | 삭제 기준 시각, 로그 종류, 한 번에 삭제할 건수 |
| `SearchIndexSyncDto` | 인덱스·문서 ID, 원본 종류·ID, 작업 유형, 검색 문서, 원본 수정 시각 |

## 기술 스택

- Java 21
- Spring Boot 4.1.2
- Spring Batch JDBC
- Spring Data JPA
- Spring Security
- Thymeleaf
- MySQL
- H2 Database(Test)
- Gradle Wrapper

## 1단계 MySQL 더미데이터

2026년 11월 한 달을 기준으로 다음 데이터가 준비되어 있습니다.

| 데이터 | 건수 |
| --- | ---: |
| 회원 | 50명 |
| 주문 | 784건, 일별 20~30건 |
| 주문 상품 | 1,552건 |
| 포인트 원장 | 750건, 만료일별 20~30건 |
| 휴면 전환 기대 결과 | 30건 |
| 일별 정산 기대 결과 | 30건 |
| 상품별 정산 기대 결과 | 297건 |
| 포인트 만료 기대 결과 | 30건 |

SQL 파일에는 테이블 생성문, 전체 더미데이터, 배치별 기대 결과가 포함됩니다.

## 애플리케이션 실행

```shell
./gradlew bootRun
```

실행 후 <http://localhost:8080/admin>에 접속합니다.

개발용 관리자 계정은 다음과 같습니다.

- 아이디: `admin`
- 비밀번호: `1234`

이 계정은 로컬 실습용이므로 운영 환경에서는 별도의 인증 정보로 교체해야 합니다.

## 더미데이터 정산 기준

- 휴면 계정: `ACTIVE` 상태이고 마지막 로그인일로부터 1년이 지난 회원
- 매출 정산: `CANCELLED` 주문 제외
- 환불 주문: 총매출에 포함한 뒤 환불액에서 차감
- 순매출: `총매출 - 환불액`
- 포인트 만료: `AVAILABLE` 상태이고 잔액이 있으며 만료 시각이 지난 포인트
