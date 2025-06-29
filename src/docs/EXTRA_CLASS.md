# ⏰ ExtraClass API 문서

보충 수업(ExtraClass) 관련 기능을 제공하는 REST API입니다.  
모든 API는 `/api/extra-classes` 경로를 기준으로 동작합니다.

---

## ✅ 엔드포인트 목록

| 메서드 | 경로                          | 설명               | 요청 바디                  | 응답 바디              |
|--------|-------------------------------|--------------------|-----------------------------|-------------------------|
| POST   | `/api/extra-classes`          | 보충 수업 생성       | ✅ 필요 (`ExtraClassRequestDto`) | 생성된 보충 수업 정보     |
| GET    | `/api/extra-classes`          | 전체 보충 수업 조회    | ❌ 없음                      | 보충 수업 리스트          |
| GET    | `/api/extra-classes/{id}`     | 특정 보충 수업 조회    | ❌ 없음                      | 보충 수업 상세 정보       |
| PUT    | `/api/extra-classes/{id}`     | 보충 수업 수정       | ✅ 필요                      | 수정된 보충 수업 정보     |
| DELETE | `/api/extra-classes/{id}`     | 보충 수업 삭제       | ❌ 없음                      | `"삭제 완료"` 텍스트      |

---

## 📥 Request Body (ExtraClassRequestDto)

```json
{
  "academyId": 1,
  "staffId": 2,
  "days": "FRIDAY",
  "startTime": "18:00:00",
  "endTime": "20:00:00"
}
````

| 필드명       | 타입        | 설명             |
| --------- | --------- | -------------- |
| academyId | Long      | 학원 ID          |
| staffId   | Long      | 담당 조교 ID       |
| days      | DayOfWeek | 요일 (예: MONDAY) |
| startTime | LocalTime | 시작 시간          |
| endTime   | LocalTime | 종료 시간          |

---

## 📤 Response Body (ExtraClassResponseDto)

```json
{
  "id": 10,
  "academyId": 1,
  "academyName": "시대인재",
  "staffId": 2,
  "staffName": "이은정",
  "days": "FRIDAY",
  "startTime": "18:00:00",
  "endTime": "20:00:00"
}
```

| 필드명         | 타입        | 설명       |
| ----------- | --------- | -------- |
| id          | Long      | 보충 수업 ID |
| academyId   | Long      | 학원 ID    |
| academyName | String    | 학원 이름    |
| staffId     | Long      | 조교 ID    |
| staffName   | String    | 조교 이름    |
| days        | DayOfWeek | 요일       |
| startTime   | LocalTime | 시작 시간    |
| endTime     | LocalTime | 종료 시간    |

---

## 📌 예시

### 생성 예시 (POST `/api/extra-classes`)

**요청**

```json
{
  "academyId": 1,
  "staffId": 2,
  "days": "FRIDAY",
  "startTime": "18:00:00",
  "endTime": "20:00:00"
}
```

**응답**

```json
{
  "id": 10,
  "academyId": 1,
  "academyName": "시대인재",
  "staffId": 2,
  "staffName": "이은정",
  "days": "FRIDAY",
  "startTime": "18:00:00",
  "endTime": "20:00:00"
}
```

---

> ⛳ 참고:
>
> * 요일은 Enum 값(`MONDAY`, `TUESDAY`, ...)으로 전달해야 하며, 시간은 `"HH:mm:ss"` 형식입니다.
> * `academyId`와 `staffId`는 이미 존재하는 학원과 조교여야 합니다.

