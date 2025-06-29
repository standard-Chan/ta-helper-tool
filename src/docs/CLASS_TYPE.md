
# 📚 ClassType API 문서

수업 유형(ClassType) 관련 기능을 제공하는 REST API입니다.  
모든 API는 `/api/class-types` 경로를 기준으로 동작합니다.

---

## ✅ 엔드포인트 목록

| 메서드 | 경로                    | 설명                 | 요청 바디         | 응답 바디             |
|--------|-------------------------|----------------------|--------------------|------------------------|
| POST   | `/api/class-types`      | 수업 유형 생성         | ✅ 필요             | 생성된 수업 유형 정보   |
| GET    | `/api/class-types`      | 전체 수업 유형 목록 조회 | ❌ 없음             | 수업 유형 리스트        |
| PUT    | `/api/class-types/{id}` | 수업 유형 수정         | ✅ 필요             | 수정된 수업 유형 정보   |
| DELETE | `/api/class-types/{id}` | 수업 유형 삭제         | ❌ 없음             | 없음 (`200 OK` 응답)    |

---

## 📥 Request Body (ClassTypeRequestDto)

```json
{
  "name": "개념반",
  "book": "개념완성 교재",
  "test": "주간 테스트",
  "homework": "매일 복습 과제"
}
````

| 필드명      | 타입     | 설명           |
| -------- | ------ | ------------ |
| name     | String | 수업 유형 이름     |
| book     | String | 사용하는 교재명     |
| test     | String | 테스트 종류 또는 이름 |
| homework | String | 과제 설명 또는 종류  |

---

## 📤 Response Body (ClassTypeResponseDto)

```json
{
  "id": 1,
  "name": "개념반",
  "book": "개념완성 교재",
  "test": "주간 테스트",
  "homework": "매일 복습 과제"
}
```

| 필드명      | 타입     | 설명       |
| -------- | ------ | -------- |
| id       | Long   | 수업 유형 ID |
| name     | String | 수업 유형 이름 |
| book     | String | 교재 이름    |
| test     | String | 테스트 정보   |
| homework | String | 과제 정보    |

---

## 📌 예시

### 생성 예시 (POST `/api/class-types`)

**요청**

```json
{
  "name": "심화반",
  "book": "심화 문제집",
  "test": "월간 테스트",
  "homework": "심화 과제 제출"
}
```

**응답**

```json
{
  "id": 5,
  "name": "심화반",
  "book": "심화 문제집",
  "test": "월간 테스트",
  "homework": "심화 과제 제출"
}
```

---

> ⛳ 참고: 수업 유형(ClassType)은 학원 수업(ClassEntity)과 연결되는 마스터 테이블로 사용됩니다. 모든 필드는 문자열로 입력되며, 생략 없이 작성되어야 합니다.
