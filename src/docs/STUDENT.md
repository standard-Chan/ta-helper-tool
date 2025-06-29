
# 👨‍🎓 Student API 문서 (with Pagination & Multi-Class)

학생(Student) 관련 기능을 제공하는 REST API입니다.
모든 API는 `/api/students` 경로를 기준으로 동작합니다.

---

## ✅ 엔드포인트 목록

| 메서드    | 경로                         | 설명              | 요청 바디 | 응답 바디                      |
| ------ | -------------------------- | --------------- | ----- | -------------------------- |
| POST   | `/api/students`            | 학생 생성           | ✅ 필요  | 생성된 학생 정보                  |
| GET    | `/api/students`            | 전체 학생 페이징 조회    | ❌ 없음  | `Page<StudentResponseDto>` |
| GET    | `/api/students/class/{id}` | 특정 수업의 학생 목록 조회 | ❌ 없음  | 학생 리스트                     |
| GET    | `/api/students/{id}`       | 특정 학생 조회        | ❌ 없음  | 학생 상세 정보                   |
| PUT    | `/api/students/{id}`       | 학생 정보 수정        | ✅ 필요  | 수정된 학생 정보                  |
| DELETE | `/api/students/{id}`       | 학생 삭제           | ❌ 없음  | 없음 (`200 OK` 응답)           |

---

## 📥 Request Body (StudentRequestDto)

```json
{
  "name": "김철수",
  "classId": 3,
  "school": "서울고등학교",
  "parentPhoneNumber": "010-1234-5678",
  "phoneNumber": "010-8765-4321",
  "email": "chulsoo@example.com",
  "age": 17
}
```

| 필드명               | 타입      | 설명        |
| ----------------- | ------- | --------- |
| name              | String  | 학생 이름     |
| classId           | Long    | 소속 수업 ID  |
| school            | String  | 학교명       |
| parentPhoneNumber | String  | 보호자 전화번호  |
| phoneNumber       | String  | 학생 전화번호   |
| email             | String  | 학생 이메일 주소 |
| age               | Integer | 나이        |

---

## 📤 Response Body (StudentResponseDto)

```json
{
  "id": 12,
  "name": "김철수",
  "classId": [3, 5],
  "school": "서울고등학교",
  "parentPhoneNumber": "010-1234-5678",
  "phoneNumber": "010-8765-4321",
  "email": "chulsoo@example.com",
  "age": 17
}
```

| 필드명               | 타입         | 설명                    |
| ----------------- | ---------- | --------------------- |
| id                | Long       | 학생 ID                 |
| name              | String     | 학생 이름                 |
| classId           | Set\<Long> | 수업 ID 리스트 (N:1 이상 가능) |
| school            | String     | 학교명                   |
| parentPhoneNumber | String     | 보호자 전화번호              |
| phoneNumber       | String     | 학생 전화번호               |
| email             | String     | 학생 이메일 주소             |
| age               | Integer    | 나이                    |

---

## 📌 페이징 조회

### 전체 학생 페이징 조회

`GET /api/students?page=0&size=100`

* `page`: 0부터 시작하는 페이지 번호
* `size`: 페이지 당 학생 수 (기본값: 100)

**응답 예시 (Page 구조):**

```json
{
  "content": [
    {
      "id": 1,
      "name": "김철수",
      "classId": [2],
      "school": "서울고",
      "parentPhoneNumber": "010-1111-2222",
      "phoneNumber": "010-9999-8888",
      "email": "kim@example.com",
      "age": 17
    },
    ...
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 100
  },
  "totalElements": 200,
  "totalPages": 2,
  "last": false,
  "first": true
}
```

---

## ⛳ 참고

* 학생은 여러 수업(`classId` 리스트)과 연결될 수 있습니다.
* 학생 생성 시에는 단일 수업(`classId`)에 등록되며, 추후 다른 API를 통해 수업 연결을 추가할 수 있습니다.
* 페이징 API를 사용할 때는 성능 상의 이유로 `Page<StudentResponseDto>` 형식을 활용하는 것이 권장됩니다.
