# QA Report

## 1. Moi truong kiem thu

- Ngay kiem thu: 2026-05-28
- Thu muc du an: `/Users/hoangluong/Documents/PTIT_CNTT1_IT211_Session13`
- Java: Java 17 toolchain theo `build.gradle`
- Build tool: Gradle wrapper
- Lenh test: `sh gradlew test`

## 2. Pham vi test

- Service layer: CRUD, auto-generate id, not found, validation salary.
- Controller layer: HTTP status cho GET/POST/PUT/DELETE, validation payload, not found response.
- Application context smoke test: `contextLoads`.
- AOP logging: da sua pointcut dung package `com.example.ss13`.

## 3. Ket qua test tu dong

```text
> Task :compileJava
> Task :processResources
> Task :classes
> Task :compileTestJava
> Task :processTestResources NO-SOURCE
> Task :testClasses
> Task :test

BUILD SUCCESSFUL in 5s
4 actionable tasks: 4 executed
```

Ket luan: PASS.

## 4. Test case chinh

| Ma | Chuc nang | Du lieu | Ket qua mong doi | Trang thai |
| --- | --- | --- | --- | --- |
| TC-01 | Lay danh sach nhan vien | `GET /api/employees` | `200 OK` | PASS |
| TC-02 | Lay nhan vien ton tai | `GET /api/employees/1` | `200 OK` | PASS |
| TC-03 | Lay nhan vien khong ton tai | `GET /api/employees/99` | `404 Not Found` | PASS |
| TC-04 | Them nhan vien hop le | Valid JSON | `201 Created` | PASS |
| TC-05 | Them nhan vien fullName rong | Invalid JSON | `400 Bad Request` | PASS |
| TC-06 | Cap nhat nhan vien | Valid JSON | `200 OK` | PASS |
| TC-07 | Xoa nhan vien | `DELETE /api/employees/1` | `204 No Content` | PASS |
| TC-08 | Xoa nhan vien khong ton tai | `DELETE /api/employees/99` | `404 Not Found` | PASS |

## 5. Kiem thu API thu cong

Ung dung duoc start tren port `18080` bang lenh:

```text
sh gradlew bootRun --args=--server.port=18080
```

Ket qua goi API:

```text
GET /api/employees                    -> HTTP/1.1 200
POST /api/employees                   -> HTTP/1.1 201
POST /api/employees invalid fullName  -> HTTP/1.1 400
GET /api/employees/1                  -> HTTP/1.1 200
GET /api/employees/999                -> HTTP/1.1 404
PUT /api/employees/1                  -> HTTP/1.1 200
DELETE /api/employees/1               -> HTTP/1.1 204
DELETE /api/employees/999             -> HTTP/1.1 404
```

## 6. Log console can chup anh

Khi chay ung dung va goi API, console can co cac log dang:

```text
09:51:47 [INFO] c.example.ss13.aspect.LoggingAspect -  [CONTROLLER] Method create is being called
09:51:47 [INFO] c.e.s.controller.EmployeeController - POST /api/employees called
09:51:47 [INFO] c.e.ss13.service.EmployeeServiceImpl - Added new employee: Employee(id=1, fullName=Le Van C, department=Finance, salary=1.3E7)
09:51:47 [DEBUG] c.example.ss13.aspect.LoggingAspect - [SERVICE] Method addEmployee returned: Employee(id=1, fullName=Le Van C, department=Finance, salary=1.3E7)
09:51:47 [INFO] c.example.ss13.aspect.LoggingAspect -  [PERFORMANCE] Method create executed in 1 ms
09:55:27 [WARN] c.e.ss13.service.EmployeeServiceImpl - Employee not found with id: 999
09:56:20 [INFO] c.e.ss13.service.EmployeeServiceImpl - Deleted employee with id: 1
```

Neu can anh minh chung, mo terminal chay ung dung bang `sh gradlew bootRun`, goi request tu Postman Collection, sau do chup man hinh console co cac dong log tren.

## 7. Ghi chu/rui ro

- Repository hien tai luu du lieu trong bo nho, nen du lieu mat khi restart ung dung.
- `application.properties` van giu cau hinh MySQL/JPA tu ban dau de tham khao, nhung da exclude datasource/JPA auto configuration vi repository thuc te dang luu in-memory.
- File `gradlew` hien chua co quyen executable tren may nay, nen dung `sh gradlew ...` thay cho `./gradlew ...`.
