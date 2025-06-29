
# 🏫 Academy API 문서

학원(Academy) 관련 기능을 제공하는 REST API입니다.  
모든 API는 `/api/academies` 경로를 기준으로 동작합니다.

---

## ✅ 엔드포인트 목록

| 메서드 | 경로                   | 설명             | 요청 바디         | 응답 바디             |
|--------|------------------------|------------------|--------------------|------------------------|
| POST   | `/api/academies`       | 학원 생성         | ✅ 필요             | 생성된 학원 정보        |
| GET    | `/api/academies`       | 전체 학원 목록 조회| ❌ 없음             | 학원 리스트             |
| PUT    | `/api/academies/{id}`  | 학원 정보 수정     | ✅ 필요             | 수정된 학원 정보        |
| DELETE | `/api/academies/{id}`  | 학원 삭제         | ❌ 없음             | 없음 (`200 OK` 응답)    |

---

## 📥 Request Body (AcademyRequestDto)

```json
{
  "name": "메가스터디",
  "address": "서울특별시 강남구 역삼로 123",
  "tel": "02-1234-5678"
}
````

| 필드명     | 타입     | 설명      |
| ------- | ------ | ------- |
| name    | String | 학원 이름   |
| address | String | 학원 주소   |
| tel     | String | 학원 전화번호 |

---

## 📤 Response Body (AcademyResponseDto)

```json
{
  "id": 1,
  "name": "메가스터디",
  "address": "서울특별시 강남구 역삼로 123",
  "tel": "02-1234-5678"
}
```

| 필드명     | 타입     | 설명      |
| ------- | ------ | ------- |
| id      | Long   | 학원 ID   |
| name    | String | 학원 이름   |
| address | String | 학원 주소   |
| tel     | String | 학원 전화번호 |

---

## 📌 예시

### 생성 예시 (POST `/api/academies`)

**요청**

```json
{
  "name": "시대인재",
  "address": "서울특별시 노원구 상계로 45",
  "tel": "02-9876-5432"
}
```

**응답**

```json
{
  "id": 2,
  "name": "시대인재",
  "address": "서울특별시 노원구 상계로 45",
  "tel": "02-9876-5432"
}
```

---

> ⛳ 참고: 학원 정보는 `이름`, `주소`, `전화번호` 모두 필수로 입력되어야 하며, 수업(Class), 학생(Student) 등과 연관된 마스터 테이블로 활용됩니다.


