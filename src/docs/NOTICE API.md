---

### 📄 `notice-api.md`

````md
# 📢 Notice API 문서

공지사항 관련 기능을 제공하는 REST API입니다.  
모든 API는 `/api/notices` 경로를 기준으로 동작합니다.

---

## ✅ 엔드포인트 목록

| 메서드 | 경로                     | 설명                             | 요청 바디 | 응답 바디                 |
|--------|--------------------------|----------------------------------|-----------|----------------------------|
| POST   | `/api/notices`           | 공지사항 생성                    | ✅ 필요    | 생성된 공지사항 정보       |
| GET    | `/api/notices`           | 전체 공지사항 조회               | ❌ 없음    | 공지사항 리스트            |
| GET    | `/api/notices/details`   | 전체 공지사항 + 수업 상세 조회   | ❌ 없음    | 공지사항 + 수업 정보 리스트 |
| GET    | `/api/notices/{id}`      | 특정 공지사항 조회               | ❌ 없음    | 공지사항 상세 정보         |
| PUT    | `/api/notices/{id}`      | 공지사항 수정                    | ✅ 필요    | 수정된 공지사항 정보       |
| DELETE | `/api/notices/{id}`      | 공지사항 삭제                    | ❌ 없음    | `"삭제 완료"` 텍스트       |

---

## 📥 Request Body (NoticeRequestDto)

```json
{
  "classId": 1,
  "content": "다음 주 시험 공지입니다.",
  "weekNo": 3
}
````

| 필드명     | 타입      | 설명       |
| ------- | ------- | -------- |
| classId | Long    | 대상 수업 ID |
| content | String  | 공지 내용    |
| weekNo  | Integer | 주차 정보    |

---

## 📤 Response Body (NoticeResponseDto)

```json
{
  "id": 5,
  "classId": 1,
  "content": "다음 주 시험 공지입니다.",
  "createdAt": "2025-06-23T14:00:00.000+09:00",
  "weekNo": 3
}
```

| 필드명       | 타입        | 설명      |
| --------- | --------- | ------- |
| id        | Long      | 공지사항 ID |
| classId   | Long      | 수업 ID   |
| content   | String    | 공지 내용   |
| createdAt | Timestamp | 생성 시각   |
| weekNo    | Integer   | 주차 정보   |

---

## 📌 예시

### 생성 예시 (POST `/api/notices`)

**요청**

```json
{
  "classId": 2,
  "content": "보충 수업은 금요일에 있습니다.",
  "weekNo": 4
}
```

**응답**

```json
{
  "id": 6,
  "classId": 2,
  "content": "보충 수업은 금요일에 있습니다.",
  "createdAt": "2025-06-23T15:00:00.000+09:00",
  "weekNo": 4
}
```

---


## 📤 Response Body (NoticeDetailResponseDto)

```json
{
  "id": 5,
  "classId": 1,
  "content": "다음 주 시험 공지입니다.",
  "createdAt": "2025-06-23T14:00:00.000+09:00",
  "weekNo": 3,
  "academyName": "메가스터디",
  "classTypeName": "개념반",
  "days": "WEDNESDAY"
}
```

| 필드명        | 타입      | 설명                     |
|--------------|-----------|--------------------------|
| id           | Long      | 공지사항 ID              |
| classId      | Long      | 수업 ID                  |
| content      | String    | 공지 내용                |
| createdAt    | Timestamp | 생성 시각                |
| weekNo       | Integer   | 주차 정보                |
| academyName  | String    | 공지 대상 수업의 학원 이름 |
| classTypeName| String    | 수업 유형 (ex. 개념반)   |
| days         | String    | 수업 요일 (ex. MONDAY)   |

---
## 📌 상세 조회 예시 (GET `/api/notices/details`)

**응답**

```json
[
  {
    "id": 5,
    "classId": 1,
    "content": "다음 주 시험 공지입니다.",
    "createdAt": "2025-06-23T14:00:00.000+09:00",
    "weekNo": 3,
    "academyName": "메가스터디",
    "classTypeName": "개념반",
    "days": "WEDNESDAY"
  },
  {
    "id": 6,
    "classId": 2,
    "content": "보충 수업은 금요일에 있습니다.",
    "createdAt": "2025-06-24T14:00:00.000+09:00",
    "weekNo": 4,
    "academyName": "시대인재",
    "classTypeName": "심화반",
    "days": "FRIDAY"
  }
]
```
---

> ⛳ 참고: 모든 API는 JWT 인증 또는 기타 인증 방식과 함께 사용될 수 있으며, 필요 시 헤더에 `Authorization: Bearer <token>`을 포함해야 합니다.

```

---

필요하면 파일로도 저장해줄게. 다음 API도 문서화할 준비됐으면 알려줘!
```

