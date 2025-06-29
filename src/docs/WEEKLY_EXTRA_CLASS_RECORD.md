# 🗓️ WeeklyExtraClassRecord API 문서

보충 수업 주차별 출결 및 기록 데이터를 관리하는 REST API입니다.  
모든 API는 `/api/weekly-extra-records` 경로를 기준으로 동작합니다.

---

## ✅ 엔드포인트 목록

| 메서드 | 경로                                                 | 설명                                   | 요청 바디                         | 응답 바디                                     |
|--------|------------------------------------------------------|----------------------------------------|-----------------------------------|------------------------------------------------|
| POST   | `/api/weekly-extra-records`                          | 보충수업 주차 기록 생성 (여러 개 입력)     | ✅ 필요 (`List<WeeklyExtraClassRecordRequestDto>`) | 없음 (201 응답)                              |
| GET    | `/api/weekly-extra-records`                          | 주차별 보충수업 기록 목록 (학생 정보 포함) | ❌ 쿼리 파라미터 사용 (`extraClass`, `week`) | `WeeklyExtraClassRecordWithStudentResponseDto[]` |
| GET    | `/api/weekly-extra-records/{id}`                     | 특정 주차 기록 단건 조회                  | ❌ 없음                            | `WeeklyExtraClassRecordResponseDto`           |
| PUT    | `/api/weekly-extra-records/{id}`                     | 주차 기록 수정                           | ✅ 필요                            | 수정된 기록 정보                              |
| DELETE | `/api/weekly-extra-records/{id}`                     | 주차 기록 삭제                           | ❌ 없음                            | `"삭제 완료"` 텍스트                          |
| GET    | `/api/weekly-extra-records/extraClass/{id}/week`     | 특정 보충수업의 주차 리스트 조회             | ❌ 없음                            | `Integer[]` 주차 번호 목록                     |

---

## 📥 Request Body (WeeklyExtraClassRecordRequestDto)

```json
{
  "studentId": 11,
  "extraClassId": 7,
  "weekNo": 3,
  "reason": "지각",
  "attended": true,
  "attendedTime": "18:10:00",
  "exitTime": "20:00:00",
  "testScore": 85
}
````

| 필드명          | 타입        | 설명            |
| ------------ | --------- | ------------- |
| studentId    | Long      | 학생 ID         |
| extraClassId | Long      | 보충수업 ID       |
| weekNo       | Integer   | 주차 번호         |
| reason       | String    | 결석/지각 이유 등 메모 |
| attended     | Boolean   | 출석 여부         |
| attendedTime | LocalTime | 실제 출석 시간      |
| exitTime     | LocalTime | 실제 퇴실 시간      |
| testScore    | Integer   | 테스트 점수        |

---

## 📤 Response Body

### 기본 응답 (WeeklyExtraClassRecordResponseDto)

```json
{
  "id": 100,
  "studentId": 11,
  "extraClassId": 7,
  "weekNo": 3,
  "reason": "지각",
  "attended": true,
  "attendedTime": "18:10:00",
  "exitTime": "20:00:00",
  "testScore": 85,
  "createdById": 2,
  "updatedById": 2,
  "createdAt": "2025-06-25T10:00:00.000+09:00",
  "updatedAt": "2025-06-25T10:00:00.000+09:00"
}
```

---

### 학생 포함 응답 (WeeklyExtraClassRecordWithStudentResponseDto)

```json
{
  "id": 100,
  "extraClassId": 7,
  "weekNo": 3,
  "reason": "지각",
  "attended": true,
  "attendedTime": "18:10:00",
  "exitTime": "20:00:00",
  "testScore": 85,
  "createdById": 2,
  "updatedById": 2,
  "createdAt": "2025-06-25T10:00:00.000+09:00",
  "updatedAt": "2025-06-25T10:00:00.000+09:00",
  "student": {
    "id": 11,
    "name": "김철수",
    "school": "서울고등학교",
    "parentPhoneNumber": "010-1234-5678",
    "phoneNumber": "010-8765-4321",
    "email": "chulsoo@example.com",
    "age": 17
  }
}
```

---

## 📌 예시

### 🔁 다건 생성 예시 (POST `/api/weekly-extra-records`)

**요청**

```json
[
  {
    "studentId": 11,
    "extraClassId": 7,
    "weekNo": 3,
    "reason": "정상 출석",
    "attended": true,
    "attendedTime": "18:00:00",
    "exitTime": "20:00:00",
    "testScore": 88
  },
  {
    "studentId": 12,
    "extraClassId": 7,
    "weekNo": 3,
    "reason": "지각",
    "attended": true,
    "attendedTime": "18:30:00",
    "exitTime": "20:00:00",
    "testScore": 75
  }
]
```

**응답**

* 상태 코드: `201 Created`

---

### 📅 주차 리스트 조회 예시 (GET `/api/weekly-extra-records/extraClass/7/week`)

**응답**

```json
[1, 2, 3, 4]
```

---

> ⛳ 참고:
>
> * `POST`, `PUT` 요청에는 로그인한 사용자의 정보(`CustomUserDetails`)가 포함되어야 하며, `createdById`, `updatedById`에 자동 반영됩니다.
> * 다건 생성 시 각 기록은 내부적으로 `upsert` 방식(없으면 insert, 있으면 update)으로 처리됩니다.
> * 시간은 `"HH:mm:ss"` 포맷으로 전달해야 합니다.

