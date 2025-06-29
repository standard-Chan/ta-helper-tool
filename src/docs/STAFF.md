
# 👩‍🏫 Staff API 문서

조교(Staff) 관련 기능을 제공하는 REST API입니다.  
모든 API는 `/api/staffs` 경로를 기준으로 동작합니다.

---

## ✅ 엔드포인트 목록

| 메서드 | 경로                    | 설명                  | 요청 바디                              | 응답 바디          | 권한    |
| --- | --------------------- | ------------------- | ---------------------------------- | -------------- | ----- |
| POST | `/api/staffs`         | 조교 생성               | ✅ 필요 (`StaffRequestDto`)           | 생성된 조교 정보      | ADMIN |
| GET | `/api/staffs`         | 전체 조교 목록 조회         | ❌ 없음                               | 조교 리스트         | ALL   |
| GET | `/api/staffs/{id}`    | 특정 조교 정보 조회         | ❌ 없음                               | 조교 상세 정보       | ALL   |
| DELETE | `/api/staffs/{id}`    | 조교 삭제               | ❌ 없음                               | 없음 (`200 OK`)  | ADMIN |
| PUT | `/api/staffs/{id}/role` | 조교 역할(Role) 변경      | ✅ 필요 (`StaffRoleUpdateRequestDto`) | 변경된 조교 정보      | ADMIN |
| GET | `/api/staffs/me`    | 현재 로그인한 조교의 ROLE 조회 | ❌ 없음                               | 현재 유저의 ROLE 정보 | ALL   |

---

## 📥 Request Body

### 🟡 조교 생성 (StaffRequestDto)

```json
{
  "name": "홍길동",
  "userId": "hong123",
  "password": "securepw",
  "phoneNumber": "010-1234-5678",
  "role": "ADMIN"
}
````

| 필드명         | 타입     | 설명                     |
| ----------- | ------ | ---------------------- |
| name        | String | 조교 이름                  |
| userId      | String | 로그인 ID                 |
| password    | String | 로그인 비밀번호               |
| phoneNumber | String | 연락처                    |
| role        | Enum   | 역할 (`ADMIN` or `USER`) |

---

### 🟡 역할 변경 (StaffRoleUpdateRequestDto)

```json
{
  "role": "USER"
}
```

| 필드명  | 타입   | 설명                          |
| ---- | ---- | --------------------------- |
| role | Enum | 변경할 역할 (예: `USER`, `ADMIN`) |

---

## 📤 Response Body (StaffResponseDto)

```json
{
  "id": 3,
  "name": "홍길동",
  "userId": "hong123",
  "phoneNumber": "010-1234-5678",
  "role": "ADMIN"
}
```

| 필드명         | 타입     | 설명                   |
| ----------- | ------ | -------------------- |
| id          | Long   | 조교 ID                |
| name        | String | 조교 이름                |
| userId      | String | 로그인 ID               |
| phoneNumber | String | 연락처                  |
| role        | Enum   | 역할 (`USER`, `ADMIN`) |

---

## 📌 예시

### 생성 예시 (POST `/api/staffs`)

**요청**

```json
{
  "name": "이은정",
  "userId": "eunjung99",
  "password": "pw1234",
  "phoneNumber": "010-5678-1234",
  "role": "STAFF"
}
```

**응답**

```json
{
  "id": 5,
  "name": "이은정",
  "userId": "eunjung99",
  "phoneNumber": "010-5678-1234",
  "role": "STAFF"
}
```

📤 Response Body (StaffRoleResponseDto)
```json
{
"role": "ADMIN"
}
```

| 필드명  | 타입   | 설명                                |
| ---- | ---- |-----------------------------------|
| role | Enum | 현재 로그인한 유저의 역할 (`STAFF`, `ADMIN`) |


---

> ⚠️ **주의**:
>
> * `POST /api/staffs`, `PUT /api/staffs/{id}/role`, `DELETE /api/staffs/{id}`는 **ADMIN 권한을 가진 사용자만 호출 가능**합니다.
> * 역할(Role)은 서버 enum 값(`USER`, `ADMIN`) 기준으로 전달해야 합니다.
