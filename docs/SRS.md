# Software Requirements Specification

## 1. Thong tin chung

- Ten he thong: Employee Management API
- Nhom/chuc nang: Quan ly nhan vien bang REST API
- Cong nghe: Java 17, Spring Boot 4.0.6, Gradle, Lombok, Spring Validation, Spring AOP
- Base URL mac dinh: `http://localhost:8080`

## 2. Muc tieu

He thong cung cap API CRUD de quan ly danh sach nhan vien. Du lieu nhan vien duoc luu trong bo nho thong qua `EmployeeRepository`, phu hop cho bai tap thuc hanh API, validation, logging va unit/integration test.

## 3. Pham vi

### 3.1. Chuc nang trong pham vi

- Lay danh sach nhan vien.
- Lay thong tin nhan vien theo id.
- Them nhan vien moi.
- Cap nhat nhan vien theo id.
- Xoa nhan vien theo id.
- Kiem tra du lieu dau vao khi them/cap nhat.
- Ghi log request controller, thoi gian xu ly va ket qua service bang AOP.
- Tra ve HTTP status phu hop cho thanh cong va loi.

### 3.2. Ngoai pham vi

- Dang nhap/phan quyen.
- Luu tru database that su.
- Phan trang, sap xep, tim kiem nang cao.
- Giao dien nguoi dung.

## 4. Tac nhan

- API Client: Postman, frontend, curl hoac bat ky client HTTP nao.
- Developer/QA: Chay test tu dong va kiem thu API.

## 5. Yeu cau chuc nang

| Ma | Mo ta | API | Ket qua mong doi |
| --- | --- | --- | --- |
| FR-01 | Lay danh sach nhan vien | `GET /api/employees` | `200 OK` va mang nhan vien |
| FR-02 | Lay nhan vien theo id ton tai | `GET /api/employees/{id}` | `200 OK` va thong tin nhan vien |
| FR-03 | Lay nhan vien theo id khong ton tai | `GET /api/employees/{id}` | `404 Not Found` |
| FR-04 | Them nhan vien hop le | `POST /api/employees` | `201 Created` va nhan vien da co id |
| FR-05 | Them nhan vien khong hop le | `POST /api/employees` | `400 Bad Request` |
| FR-06 | Cap nhat nhan vien ton tai | `PUT /api/employees/{id}` | `200 OK` va du lieu moi |
| FR-07 | Cap nhat nhan vien khong ton tai | `PUT /api/employees/{id}` | `404 Not Found` |
| FR-08 | Xoa nhan vien ton tai | `DELETE /api/employees/{id}` | `204 No Content` |
| FR-09 | Xoa nhan vien khong ton tai | `DELETE /api/employees/{id}` | `404 Not Found` |

## 6. Yeu cau du lieu

### Employee

| Truong | Kieu | Bat buoc | Rang buoc |
| --- | --- | --- | --- |
| `id` | Long | Khong | Tu sinh neu POST khong truyen |
| `fullName` | String | Co | Khong duoc rong |
| `department` | String | Co | Khong duoc rong |
| `salary` | double | Co | Lon hon 0 |

### Vi du request tao nhan vien

```json
{
  "fullName": "Le Van C",
  "department": "Finance",
  "salary": 13000000
}
```

## 7. Yeu cau phi chuc nang

- API tra ve JSON.
- Validation loi tra ve `400 Bad Request`.
- Tai nguyen khong ton tai tra ve `404 Not Found`.
- Controller va service co log de ho tro debug.
- Test tu dong phai chay bang `sh gradlew test`.

## 8. Tieu chi chap nhan

- Tat ca endpoint CRUD hoat dong dung HTTP status.
- Payload khong hop le bi tu choi.
- Test service va controller pass.
- Co Postman Collection de kiem thu thu cong.
- Co bao cao QA ghi lai ket qua test.
