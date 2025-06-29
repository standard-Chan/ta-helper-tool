# 📘 WebSocket 사용 가이드 (classRecord 실시간 공유용)

## 접속 방법

WebSocket 서버에 접속할 때는 다음 URL로 접속하면 된다.

```
ws://${domain}/ws/classRecord?classId={id}&weekNo={no}
```

#### 프론트 코드

```ts
const socket = new WebSocket("ws://localhost:8080/ws/classRecord?classId=1&weekNo=3");
```

이렇게 하면 특정 수업(`classId`)의 특정 주차(`weekNo`)에 대한 실시간 방에 참여

---

## 메시지 구조 및 수신 처리

### 📥 서버에서 오는 메시지 형식

서버에서는 다음 3가지 유형의 메시지를 보냅니다.

| `type`        | 설명               | 예시                                                             |
| ------------- | ---------------- | -------------------------------------------------------------- |
| `EDIT`        | 다른 사용자의 수정 반영    | `{ type: "EDIT", studentId: 12, field: "note", value: "수정함" }` |
| `SAVE_NOTICE` | 누군가 저장 버튼을 누름 알림 | `{ type: "SAVE_NOTICE", user: "석찬" }`                          |
| `USER_COUNT`  | 현재 접속자 수 전달      | `{ type: "USER_COUNT", count: 4 }`                             |

### 📥 수신 처리 예시

```ts
socket.onmessage = (event) => {
  const data = JSON.parse(event.data);

  switch (data.type) {
    case "EDIT":
      // studentId와 field, value로 local state를 실시간으로 반영
      break;

    case "SAVE_NOTICE":
      alert(`${data.user}님이 저장을 완료했습니다.`);
      break;

    case "USER_COUNT":
      setUserCount(data.count);
      break;
  }
};
```

---

## 메시지 전송 방법

### ✏️ 실시간 편집 내용 전송

사용자가 값을 수정하면 다음 형태로 전송합니다:

```ts
socket.send(JSON.stringify({
  type: "EDIT",
  studentId: 12,
  field: "note",
  value: "오늘 숙제 안함"
}));
```

### 💾 저장 완료 전송

저장 버튼을 누를 때 다음 메시지를 전송:

```ts
socket.send(JSON.stringify({
  type: "SAVE_NOTICE",
  user: "석찬"
}));
```