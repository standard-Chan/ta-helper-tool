# 문제점

## 연관된 데이터인 경우 삭제가 안되는 문제
연관관계에 있는 데이터를 어떻게 처리해야할지에 대해 난해함을 겪음.
연관이 없고 독립적인 테이블 (academy, staff, class_type) 을 우선하여 처리하였다.
### class, extra-class
위 데이터는 모든 주간 기록 데이터의 핵심이므로
데이터의 삭제가 많이 일어나지 않을 것이라고 판단하여 연관관계가 하나라도 있는 경우
삭제가 불가능하도록 구현하였다.

### Student
Student는 주간기록과 많은 연관관계가 맺어져 있지만,
업무 중에 실수로 다른 수업 주차 기록에 해당 학생 정보를 입력하여 처리하는 실수 가능성이 있으므로
사용자의 편의를 위해서 삭제가 가능하고 연관관계를 일괄적으로 삭제하는 방향으로 구현하였다.

3개의 테이블과 연관관계를 끊고, 삭제처리를 진행하였다.

---

## 여러개의 데이터를 INSERT+UPDATE 할때 문제
> 여러개의 데이터를 받아서 INSERT + UPDATE (데이터가 있으면 수정, 없으면 생성)하는데 많은 시간이 소요되는 문제가 발생함.

### UPSERT Query
기존에는 INSERT할 데이터와 UPDATE할 데이터를 Java 내에서 분류하여 INSERT 따로, UPDATE 따로 쿼리를 전송하였다.

아래는 기존 코드이다.
```java
    @Transactional
    public WeeklyClassRecordResponseDto upsert(WeeklyClassRecordRequestDto dto, Staff staff) {
        Student student = studentRepository.findById(dto.getStudentId()).orElseThrow(() -> new StudentException(ErrorCode.STUDENT_NOT_FOUND));
        // 데이터 존재 확인
        Optional<WeeklyClassRecord> record = recordRepository.findByStudentAndWeekNo(student, dto.getWeekNo());

        // 이미 존재하는 경우
        if (record.isPresent()) { // UPDATE
            return update(record.get().getId(), dto, staff);
        } else { // INSERT
            return create(dto, staff);
        }
    }
```
기존 코드는 굉장히 비효율적인 과정을 거쳤다.
1. 데이터의 존재 확인을 위해 SELECT Query 전송
2. 데이터를 INSERT or UPDATE 하기 위한 Query 전송
3. 1-2 과정을 N번만큼 반복

총 2N개의 쿼리를 생성하여 처리하는 방식이고, 데이터가 1000개라면 2000개의 쿼리가 발생된다.

#### UPSERT
하지만 SQL에서 UPSERT 쿼리의 존재를 알게되어 해당 방식으로 전환하고자 하였다.
```sql
INSERT INTO table_name
( column1 , column2 , … )
VALUES
( v1 , v2 , … )
ON DUPLICATE KEY UPDATE
column1 = v1, … ;
```

위 방식을 사용하면 SELECT Query를 전송할 필요가 없으므로 총 N개의 쿼리가 발생된다.


### 벌크연산 - JDBC TEMPLATE 와 Batch를 이용하여 해결
JdbcTemplate와 Batch를 이용하여 기존 N개의 쿼리를 1개의 쿼리를 통해 UPSERT 할 수 있도록 구현하였다.

```java
    @Transactional
    public void bulkUpsertRecords(List<WeeklyClassRecordRequestDto> dtos, Staff staff) {
        if (dtos.isEmpty()) return;

        // sql
        // weekly_class_record는 student_id, class_id, week_no 를 기준으로 UNIQUE 제약조건이 걸려있다.
        String sql = """
                        INSERT INTO weekly_class_record
                        (student_id, class_entity_id, week_no, attended, test_score, homework_score, note, created_by, updated_by)
                        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                        ON DUPLICATE KEY UPDATE
                        attended = VALUES(attended),
                        test_score = VALUES(test_score),
                        homework_score = VALUES(homework_score),
                        note = VALUES(note),
                        updated_by = VALUES(updated_by)
                    """;

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                WeeklyClassRecordRequestDto dto = dtos.get(i);

                ps.setLong(1, dto.getStudentId());
                ps.setLong(2, dto.getClassId());
                ps.setInt(3, dto.getWeekNo());
                ps.setBoolean(4, dto.isAttended());
                ps.setInt(5, dto.getTestScore());
                ps.setInt(6, dto.getHomeworkScore());
                ps.setString(7, dto.getNote());
                ps.setLong(8, staff.getId());
                ps.setLong(9, staff.getId());
            }

            public int getBatchSize() {
                return dtos.size();
            }
        });
    }
```

JDBC와 Batch를 통해서 1개의 쿼리만으로 N개의 데이터를 처리할 수 있게 되었다.

Batch Size는 보통 한 수업당 최대 150명 정도이므로 제한을 두지 않았다.

### 성능 차이
#### 100개의 데이터를 UPSERT 하는데 소요된 시간(ms)

- 기존 방식 : 약 1576 ms ~ 1913 ms
- 벌크연산 방식 : 약 30 ms ~ 42 ms

---
## UPSERT 시 락
JdbcTemplate 방식은 UPSERT 시에 각 row에 대한 배타락을 자동으로 획득하기 때문에 충돌로
데이터가 반영이 안되는 문제가 발생하지는 않는다.
하지만 사용자가 해당값이 덮어써졌다는 사실을 인지할 필요가 있으므로 현재 로그인중인
모든 사용자에게 '새로운 값이 저장되었다' 라는 알림을 보낼 필요가 있다.


##그밖의 실수

## 403 Forbidden

Spring Security를 사용하면 가능한 URL이 아닌 경우 404 에러가 아니라 403 에러가 발생한다.
이는 Security 필터 우선순위 때문에 그런것.

만약 다른 URL은 정상적으로 작동하는데, 특정 URL에 403에러 Forbidden 이 발생한다면 URL이 옳바른지 확인하도록 하자.

---

## Security requestMatchers 적용 순서

> authorizeHttpRequests의 requestMatcher 는 위치에 따라서 적용 순서가 달라진다.

```java
http.~.authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/login/**").permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                )
```

```java
http.~.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/login/**").permitAll()
                        .anyRequest().permitAll()
                )
```

첫번째 코드는 /api/login/** 을 먼저 허용해주고 두번째 코드는 /api/** 를 먼저 차단한다. 

Spring Security에서는 먼저 적용한 requestMatchers 부터 차례대로 필터링을 진행한다. 
따라서 두번째 코드의 아래 `/api/login/**` 은 위의 `/api/**`에 차단되어 허가되지 않는다.
