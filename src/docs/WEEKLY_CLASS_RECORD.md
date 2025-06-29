# 📘 WeeklyClassRecord API 문서

주차별 수업 기록(출결/시험/과제/비고 등)을 관리하는 REST API입니다.  
모든 API는 `/api/weekly-records` 경로를 기준으로 동작합니다.

---

## ✅ 엔드포인트 목록

| 메서드 | 경로                                          | 설명                                  | 요청 바디                    | 응답 바디                            |
|--------|-----------------------------------------------|---------------------------------------|-------------------------------|---------------------------------------|
| POST   | `/api/weekly-records`                         | 주차 수업 기록 생성                     | ✅ 필요                        | 생성된 기록 정보 (`WeeklyClassRecordResponseDto`) |
| GET    | `/api/weekly-records?class={id}&week={no}`    | 특정 수업 & 주차의 기록 목록 조회 (학생 포함) | ❌ 없음                        | `WeeklyClassRecordWithStudentResponseDto[]` |
| GET    | `/api/weekly-records/class/{classId}/week`    | 특정 수업에 기록된 주차 번호 조회           | ❌ 없음                        | `Integer[]` (주차 리스트)             |
| GET    | `/api/weekly-records/{id}`                    | 특정 기록 단건 조회                      | ❌ 없음                        | `WeeklyClassRecordResponseDto`        |
| PUT    | `/api/weekly-records/{id}`                    | 주차 수업 기록 수정                      | ✅ 필요                        | 수정된 주차 수업 기록                 |
| DELETE | `/api/weekly-records/{id}`                    | 주차 수업 기록 삭제                      | ❌ 없음                        | `"삭제 완료"` 텍스트                  |

---

## 📥 Request Body (WeeklyClassRecordRequestDto)

```json
{
  "studentId": 11,
  "classId": 4,
  "weekNo": 3,
  "attended": true,
  "testScore": 85,
  "homeworkScore": 90,
  "note": "과제 훌륭함",
  "createdById": 1,
  "updatedById": 1
}
````

| 필드명           | 타입      | 설명                     |
| ------------- | ------- | ---------------------- |
| studentId     | Long    | 학생 ID                  |
| classId       | Long    | 수업 ID                  |
| weekNo        | Integer | 몇 번째 주차인지 (1\~N주차)     |
| attended      | Boolean | 출석 여부 (`true`/`false`) |
| testScore     | Integer | 시험 점수                  |
| homeworkScore | Integer | 과제 점수                  |
| note          | String  | 비고 메모                  |
| createdById   | Long    | 등록자 ID                 |
| updatedById   | Long    | 수정자 ID                 |

---

## 📤 Response Body

### 기본 조회 (WeeklyClassRecordResponseDto)

```json
{
  "id": 21,
  "studentId": 11,
  "classId": 4,
  "weekNo": 3,
  "attended": true,
  "testScore": 85,
  "homeworkScore": 90,
  "note": "과제 훌륭함",
  "createdById": 1,
  "updatedById": 1,
  "createdAt": "2025-06-23T10:00:00.000+09:00",
  "updatedAt": "2025-06-23T10:00:00.000+09:00"
}
```

---

### 학생 포함 조회 (WeeklyClassRecordWithStudentResponseDto)

```json
{
  "id": 21,
  "classId": 4,
  "weekNo": 3,
  "attended": true,
  "testScore": 85,
  "homeworkScore": 90,
  "note": "과제 훌륭함",
  "createdById": 1,
  "updatedById": 1,
  "createdAt": "2025-06-23T10:00:00.000+09:00",
  "updatedAt": "2025-06-23T10:00:00.000+09:00",
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

### ✅ 주차별 수업 기록 조회 (학생 포함)

**요청 (GET `/api/weekly-records?class=4&week=3`)**

**응답**

```json
[
  {
    "id": 21,
    "classId": 4,
    "weekNo": 3,
    "attended": true,
    "testScore": 85,
    "homeworkScore": 90,
    "note": "정상 출석",
    "createdById": 1,
    "updatedById": 1,
    "createdAt": "2025-06-23T10:00:00.000+09:00",
    "updatedAt": "2025-06-23T10:00:00.000+09:00",
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
]
```

---

### 📅 주차 목록 조회 예시 (GET `/api/weekly-records/class/4/week`)

**응답**

```json
[1, 2, 3, 4, 5]
```

> 특정 수업(`classId`)에 대해 기록이 있는 주차 번호들을 배열로 반환합니다.

---

> ⛳ 참고:
>
> * `/api/weekly-records?class=...&week=...`은 학생 정보까지 포함된 상세 리스트를 반환합니다.
> * `/api/weekly-records/class/{id}/week`은 해당 수업에서 기록된 주차 번호만 추출합니다.
> * 모든 기록은 `createdById`, `updatedById`를 포함하며, 이는 조교 또는 관리자 권한 계정의 ID여야 합니다.
