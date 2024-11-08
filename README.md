# 위치 기반 동호회 및 동아리 매칭 서비스


## 상세 요구사항 정의서
---
### REQ-001~002 회원 가입 및 로그인

**사용 흐름**:

1. **회원 가입**
    - 사용자가 회원 가입 페이지로 이동.
    - 이메일과 비밀번호를 입력하고 "회원 가입" 버튼 클릭.
    - 이메일과 비밀번호 형식 유효성 검사 (이메일 형식, 비밀번호에 특수 문자 포함 여부).
    - 회원 가입 성공 시, 이메일 인증 링크 발송.
    - 사용자가 이메일 인증 후 로그인 가능.
2. **로그인**
    - 로그인 페이지에서 이메일과 비밀번호 입력 후 로그인.
    - 이메일과 비밀번호 일치 여부 유효성 검사.
3. **비밀번호 찾기**
    - 비밀번호를 잊은 경우, "비밀번호 찾기" 버튼 클릭 후 이메일 입력.
    - 이메일로 비밀번호 재설정 링크 발송.

**데이터 저장**:

- **회원 정보**: 이메일, 비밀번호(암호화), 닉네임 등.
- **로그인 세션**: 사용자 세션 정보 저장.

**구현 방법 (백엔드)**:

- **REST API**: 회원 가입, 로그인, 비밀번호 재설정 엔드포인트 생성.
- **데이터베이스**: 사용자 정보를 저장하는 테이블 생성.
- **이메일 서비스**: 이메일 인증 및 비밀번호 재설정 링크 발송.

**예외 처리**:

- 이메일 중복 체크.
- 비밀번호 형식 유효성 검사.
- 인증 이메일 유효 기간 설정.
- 잘못된 로그인 시도 시 오류 메시지 제공.

---

### REQ-003 메인 페이지 (지도 API)

**사용 흐름**:

1. 사용자가 메인 페이지로 이동.
2. 지도 API를 통해 지도가 표시됨 (네이버 지도 또는 카카오 지도).
3. 사용자가 지도에서 특정 위치를 선택하여 마커 생성.
4. 마커 클릭 시, 동아리 또는 동호회 생성 페이지로 이동하거나 네이버 지도처럼 사이드바에 동아리/동호회 생성 양식 표시.
5. 동아리/동호회 정보 입력 후 생성.

**데이터 저장**:

- **마커 정보**: 위치 정보, 생성자 정보, 생성 일시 등.
- **동아리/동호회 정보**: 이름, 설명, 위치 정보 등.

**구현 방법 (백엔드)**:

- **REST API**: 마커 생성, 조회, 동아리/동호회 생성 엔드포인트 생성.
- **지도 API 연동**: 네이버 지도 또는 카카오 지도 API 사용.
- **데이터베이스**: 마커 정보와 동아리/동호회 정보를 저장하는 테이블 생성.

**예외 처리**:

- 마커 생성 시 위치 유효성 검사.
- 동아리/동호회 정보 입력 유효성 검사.
- 마커 중복 생성 방지.

---

### REQ-004 글 작성, 조회, 삭제

**사용 흐름**:

1. 사용자가 글 작성 페이지로 이동.
2. 제목, 내용, 이미지 파일을 입력받아 글 작성.
3. 작성된 글은 리스트 형태로 페이징 처리되어 조회 가능.
4. 사용자가 글을 삭제할 수 있음.

**데이터 저장**:

- **글 정보**: 제목, 내용, 작성자 정보, 작성 일시 등.
- **이미지 파일 정보**: 파일 경로, 파일 이름, 파일 크기, 업로드 일시 등.

**구현 방법 (백엔드)**:

- **REST API**: 글 작성, 조회, 삭제 엔드포인트 생성.
- **데이터베이스**: 글 정보와 이미지 파일 정보를 저장하는 테이블 생성.

**예외 처리**:

- 제목과 내용의 유효성 검사.
- 이미지 파일 크기와 형식 유효성 검사.
- 삭제 권한 확인.

---

### REQ-005 댓글 작성 및 관리

**사용 흐름**:

1. 사용자가 마커를 클릭하여 댓글 작성 페이지로 이동.
2. 댓글 입력 후 "작성" 버튼 클릭.
3. 사용자는 작성한 댓글을 수정 또는 삭제 가능.
4. 다른 사용자의 댓글에 대댓글 작성 가능.

**데이터 저장**:

- **댓글 정보**: 작성자 정보, 내용, 작성 일시 등.
- **대댓글 정보**: 부모 댓글 ID, 작성자 정보, 내용, 작성 일시 등.

**구현 방법 (백엔드)**:

- **REST API**: 댓글 작성, 수정, 삭제, 대댓글 작성 엔드포인트 생성.
- **데이터베이스**: 댓글과 대댓글 정보를 저장하는 테이블 생성.

**예외 처리**:

- 댓글 내용 유효성 검사.
- 댓글 수정 및 삭제 권한 확인.

**보안 고려사항**:

- 댓글 작성 권한 관리.
- 부적절한 내용 필터링.

---

### REQ-006 마이 페이지

**사용 흐름**:

1. 사용자가 마이 페이지로 이동.
2. 사용자가 입력한 정보(아이디, 비밀번호, 이메일 등) 조회.
3. 사용자가 작성한 댓글 목록을 조회.
4. 특정 댓글을 클릭하면 해당 댓글이 작성된 페이지로 이동.

**데이터 저장**:

- **사용자 정보**: 아이디, 비밀번호, 이메일 등.
- **댓글 목록**: 작성한 댓글 목록과 해당 페이지 정보.

**구현 방법 (백엔드)**:

- **REST API**: 사용자 정보 조회, 댓글 목록 조회 엔드포인트 생성.
- **데이터베이스**: 사용자 정보와 댓글 정보를 연결하는 테이블 생성.

**예외 처리**:

- 로그인 상태 확인.
- 댓글 조회 시 페이지 존재 여부 확인.

**보안 고려사항**:

- 사용자 정보 보호.
- 댓글 접근 권한 관리.

---

### REQ-007 커뮤니티 파일 업로드

**사용 흐름**:

1. 사용자가 커뮤니티 페이지로 이동.
2. 글 작성 시, "파일 업로드" 버튼 클릭하여 사진 파일 선택.
3. 선택한 파일은 글 작성 폼에 추가됨.
4. 글 작성 완료 후 "게시" 버튼 클릭.
5. 글 내용과 업로드된 파일이 서버에 전송되어 데이터베이스와 파일 스토리지에 저장됨.

**데이터 저장**:

- **글 정보**: 제목, 내용, 작성자 ID, 작성 일시 등.
- **파일 정보**: 파일 경로, 파일 이름, 파일 크기, 업로드 일시 등.
- **연결 데이터**: 글 정보와 파일 정보를 연결.

**구현 방법 (백엔드)**:

- **REST API**: 글 작성과 파일 업로드를 처리하는 API 엔드포인트 생성.
- **파일 저장**: 업로드된 파일을 서버의 지정된 디렉토리에 저장하고, 파일 경로를 데이터베이스에 기록.
- **데이터베이스**: 글 정보와 파일 정보를 저장하는 테이블 생성 및 연관 관계 설정.

**예외 처리**:

- 파일 크기 제한: 설정된 최대 파일 크기를 초과하는 파일 업로드 시 경고 메시지 표시.
- 파일 형식 제한: 허용된 파일 형식이 아닌 경우 경고 메시지 표시.
- 업로드 실패: 파일 업로드 중 오류 발생 시 경고 메시지 표시.

**보안 고려사항**:

- 파일 검증: 업로드된 파일에 대한 바이러스 검사를 수행.
- 파일 경로 보호: 업로드된 파일에 접근할 수 있는 경로를 안전하게 보호.
- 권한 관리: 파일 업로드 및 삭제 권한을 사용자 권한에 따라 제한.

---

## ERD

![image](https://github.com/user-attachments/assets/20169f28-f48f-4a95-93d9-e2490b784021)

## API 설계 (요구사항 기반) 

## **REQ-001~002 회원 가입 및 로그인**

### 회원 가입 API

**회원 API**
| 기능 | Method | URL | Input (JSON 형식) | Response | HTTP 상태 코드 |
| --- | --- | --- | --- | --- | --- |
| 회원가입 | POST | `/api/members/signup` | `json { "loginId": "testUser", "password": "password1!", "name": "Test User", "email": "test@example.com" }` | `{"memberId": 1}` | 201 Created |
| 로그인 | POST | `/api/members/login` | `json { "loginId": "testUser", "password": "password1!" }` | `{"memberId": 1}` | 200 OK |
| 회원 정보 조회 | GET | `/api/members/findMember` | 없음 | `{"id": 1, "name": "Test User", "email": "test@example.com", "address": "Test Address"}` | 200 OK |
| 회원 정보 업데이트 | PATCH | `/api/members/updateMember` | `json { "name": "Updated User", "email": "newEmail@example.com", "address": "newAddress" }` | `{"id": 1, "name": "Updated User", "email": "newEmail@example.com", "address": "newAddress"}` | 200 OK |
| 회원 삭제 | DELETE | `/api/members/deleteMember` | 없음 | `204 No Content` | 204 No Content |
## REQ-003 메인 페이지 (지도 API)

지도 표시 API

| 기능 | Method | URL | Input (JSON 형식) | Response | HTTP 상태 코드 |
| --- | --- | --- | --- | --- | --- |
| **마커 작성** | POST | `/api/markers` | **Form-Data** 
markerDTO: {   "title": "string",  "content": "string" }, files: List of Files (multipart files) | `{ <br> &nbsp;"title": "string", <br> &nbsp;"content": "string", <br> &nbsp;"address": { "x": double, "y": double }, <br> &nbsp;"files": [ { "base64Data": "string" }, ... ] <br>}` | 201 Created |
| **마커 단일 조회** | GET | `/api/markers/{markerId}` | N/A | `{ <br> &nbsp;"title": "string", <br> &nbsp;"content": "string", <br> &nbsp;"address": { "x": double, "y": double }, <br> &nbsp;"files": [ { "base64Data": "string" }, ... ] <br>}` | 200 OK |
| **특정 사용자 마커 조회** | GET | `/api/markers/member/{memberId}` | N/A | `[ { <br> &nbsp;"title": "string", <br> &nbsp;"content": "string", <br> &nbsp;"address": { "x": double, "y": double }, <br> &nbsp;"files": [ { "base64Data": "string" }, ... ] <br>}, ... ]` | 200 OK |
| **전체 마커 조회** | GET | `/api/markers/allMarkers` | N/A | `[ { <br> &nbsp;"title": "string", <br> &nbsp;"content": "string", <br> &nbsp;"address": { "x": double, "y": double }, <br> &nbsp;"files": [ { "base64Data": "string" }, ... ] <br>}, ... ]` | 200 OK |
| **마커 삭제** | DELETE | `/api/markers/{markerId}` | N/A | N/A | 204 No Content |

![image (2)](https://github.com/user-attachments/assets/14036a96-ab2e-42c1-a4dc-f094792cb48e)

### 설명

- **마커 작성**: 클라이언트에서 마커 정보를 생성하고 파일들을 첨부하여 `Form-Data` 형식으로 전송합니다. 서버는 생성된 마커 정보를 반환합니다.
- **마커 단일 조회**: `marker_id`에 해당하는 마커 정보를 반환합니다. 반환 시 관련된 파일 정보도 함께 포함됩니다.
- **특정 사용자 마커 조회**: 특정 `member_id`의 모든 마커 목록을 반환하며, 각 마커는 파일 정보를 포함합니다.
- **전체 마커 조회**: 모든 마커와 관련된 파일 정보를 포함한 목록을 반환합니다.
- **마커 삭제**: 특정 `marker_id`의 마커를 삭제합니다.

## REQ-004 글 작성, 조회, 삭제

글 작성 폼 표시

글 작성 API

글 조회 API

글 상세 조회 API

글 삭제 API

![image](https://github.com/user-attachments/assets/9d360aba-c559-48b9-86d3-1147116f7a34)

## REQ-005 댓글 작성 및 관리

댓글 작성 API

대댓글 작성 API

댓글 수정 API

댓글 삭제 API

![image](https://github.com/user-attachments/assets/dc4f6f67-10db-480a-b15e-1ac76143428a)

## REQ-006 마이 페이지

회원 정보 조회 API

회원 정보 수정 API

사용자 댓글 목록 조회 API

회원 탈퇴 API
![image](https://github.com/user-attachments/assets/46441ad4-c3c0-4cd2-8619-9622aca43fe3)


## REQ-007 커뮤니티 파일 업로드

파일 업로드 폼 표시

파일 업로드 API
| 기능 | Method | URL | Input | Response | Return Page | HTTP 상태코드 |
| --- | --- | --- | --- | --- | --- | --- |
| 파일 업로드 | POST | `/api/files/upload` | `file`: MultipartFile (업로드할 파일) | `FileDTO` (파일 정보) | 없음 | 201 Created |
| 파일 수정 | PUT | `/api/files/update/{fileId}` | `file`: MultipartFile (수정할 파일) | `File` (수정된 파일 정보) | 없음 | 200 OK |
| 파일 조회 | GET | `/api/files/{fileId}` | `fileId`: Long (파일 ID) | `File` (파일 정보) | 없음 | 200 OK |
| 파일 삭제 | DELETE | `/api/files/delete/{fileId}` | `fileId`: Long (파일 ID) | 없음 | 없음 | 204 No Content |

### 각 기능 설명

1. **파일 업로드**
    - **Method:** POST
    - **URL:** `/api/files/upload`
    - **Input:**
        - `file`: MultipartFile (사용자가 업로드할 파일)
    - **Response:**
        - `FileDTO` 객체로, 업로드된 파일의 정보(원본 파일 이름, 저장된 파일 이름, 파일 유형 등)를 포함.
    - **Return Page:** 없음
    - **HTTP 상태코드:** 201 Created (파일이 성공적으로 생성되었음을 나타냄)
2. **파일 수정**
    - **Method:** PUT
    - **URL:** `/api/files/update/{fileId}`
    - **Input:**
        - `fileId`: Long (수정할 파일의 ID)
        - `file`: MultipartFile (수정할 파일)
    - **Response:**
        - `File` 객체로, 수정된 파일의 정보.
    - **Return Page:** 없음
    - **HTTP 상태코드:** 200 OK (파일이 성공적으로 수정되었음을 나타냄)
3. **파일 조회**
    - **Method:** GET
    - **URL:** `/api/files/{fileId}`
    - **Input:**
        - `fileId`: Long (조회할 파일의 ID)
    - **Response:**
        - `File` 객체로, 요청된 파일의 정보.
    - **Return Page:** 없음
    - **HTTP 상태코드:** 200 OK (파일이 성공적으로 조회되었음을 나타냄)
4. **파일 삭제**
    - **Method:** DELETE
    - **URL:** `/api/files/delete/{fileId}`
    - **Input:**
        - `fileId`: Long (삭제할 파일의 ID)
    - **Response:** 없음 (삭제 성공 시)
    - **Return Page:** 없음
    - **HTTP 상태코드:** 204 No Content (파일이 성공적으로 삭제되었음을 나타냄)
