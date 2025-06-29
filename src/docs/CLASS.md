# 🏫 Class API 문서

수업(ClassEntity) 관련 기능을 제공하는 REST API입니다.  
모든 API는 `/api/classes` 경로를 기준으로 동작합니다.

---

## ✅ 엔드포인트 목록

| 메서드 | 경로              | 설명               | 요청 바디         | 응답 바디            |
|--------|-------------------|--------------------|--------------------|-----------------------|
| POST   | `/api/classes`    | 수업 생성           | ✅ 필요             | 생성된 수업 정보       |
| GET    | `/api/classes`    | 전체 수업 조회      | ❌ 없음             | 수업 리스트            |
| GET    | `/api/classes/{id}`| 특정 수업 조회      | ❌ 없음             | 수업 상세 정보         |
| PUT    | `/api/classes/{id}`| 수업 수정           | ✅ 필요             | 수정된 수업 정보       |
| DELETE | `/api/classes/{id}`| 수업 삭제           | ❌ 없음             | 없음 (`200 OK` 응답)   |

---

## 📥 Request Body (ClassRequestDto)

```json
{
  "academyId": 1,
  "classTypeId": 2,
  "days": "MONDAY",
  "startTime": "14:00:00",
  "endTime": "16:00:00"
}
````

| 필드명         | 타입        | 설명             |
| ----------- | --------- | -------------- |
| academyId   | Long      | 학원 ID          |
| classTypeId | Long      | 수업 유형 ID       |
| days        | DayOfWeek | 요일 (예: MONDAY) |
| startTime   | LocalTime | 수업 시작 시간       |
| endTime     | LocalTime | 수업 종료 시간       |

---

## 📤 Response Body (ClassResponseDto)

```json
{
  "id": 10,
  "academyId": 1,
  "academyName": "시대인재",
  "classTypeId": 2,
  "classTypeName": "심화반",
  "days": "MONDAY",
  "startTime": "14:00:00",
  "endTime": "16:00:00"
}
```

| 필드명           | 타입        | 설명       |
| ------------- | --------- | -------- |
| id            | Long      | 수업 ID    |
| academyId     | Long      | 학원 ID    |
| academyName   | String    | 학원 이름    |
| classTypeId   | Long      | 수업 유형 ID |
| classTypeName | String    | 수업 유형 이름 |
| days          | DayOfWeek | 요일       |
| startTime     | LocalTime | 수업 시작 시간 |
| endTime       | LocalTime | 수업 종료 시간 |

---

## 📌 예시

### 생성 예시 (POST `/api/classes`)

**요청**

```json
{
  "academyId": 1,
  "classTypeId": 2,
  "days": "WEDNESDAY",
  "startTime": "10:00:00",
  "endTime": "12:00:00"
}
```

**응답**

```json
{
  "id": 15,
  "academyId": 1,
  "academyName": "메가스터디",
  "classTypeId": 2,
  "classTypeName": "개념반",
  "days": "WEDNESDAY",
  "startTime": "10:00:00",
  "endTime": "12:00:00"
}
```

---

> ⛳ 참고: 시간 포맷은 `"HH:mm:ss"`이며, 요일은 `MONDAY`, `TUESDAY`, `WEDNESDAY` 등의 Enum 값으로 전달해야 합니다.
> 학원(`academyId`)과 수업 유형(`classTypeId`)은 미리 등록되어 있어야 합니다.

