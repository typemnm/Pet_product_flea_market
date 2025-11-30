# Pet_product_flea_market
안드로이드 반려동물 용품 중고거래 어플리케이션
## Convention
모든 사용자들은 아래의 브랜치 전략을 따릅니다.
### 1. 커밋 메시지 컨벤션

커밋 메시지는 다음 형식을 따릅니다:

```
[<타입>] <제목>

- <본문> (선택사항)

- <꼬리말> (선택사항)
```
한글을 사용해도 좋습니다.

#### 타입 (Type)
- **feat**: 새로운 기능 추가
- **fix**: 버그 수정
- **docs**: 문서 수정
- **style**: 코드 포맷팅, 세미콜론 누락 등 (기능 변경 없음)
- **refactor**: 코드 리팩토링
- **test**: 테스트 코드 추가 또는 수정
- **chore**: 빌드 업무 수정, 패키지 매니저 수정 등

#### 제목 작성 규칙
- 50자 이내로 작성
- 첫 글자는 대문자로 작성
- 마침표를 붙이지 않음
- 명령문으로 작성 (과거형 사용 금지)

#### 예시
```
feat: 상품 구매 로직 구현

- 상품 구매 시 팝업 및 이력 DB 갱신 추가
```

### 2. 코드 컨벤션

#### 네이밍 규칙
- **Activity**: 하단의 규칙을 따름
  ```java
  Activity	Activity 접미사 사용	LoginActivity
  Fragment	Fragment 접미사	ProductListFragment
  Adapter	Adapter 접미사	ChatAdapter
  ViewModel	ViewModel 접미사	MainViewModel
  데이터 모델	명사	Product, User
  Util/Helper	역할 명시	DateUtil, StringHelper
  ```
- **함수명**: camelCase, 동사+목적어 사용
  ```java
  loadProducts();
  validateInput();
  moveToLoginPage();
  showErrorMessage();
  sendChatMessage();
  ```
- **변수명**: camelCase 사용
  ```java
  int productId;
  String userName;
  boolean isLoggedIn;
  ```
- **상수명**: UPPER_SNAKE_CASE 사용
  ```java
  public static final int MAX_PRODUCT_COUNT = 100;
  public static final String KEY_PRODUCT_ID = "KEY_PRODUCT_ID";
  ```
- **UI 컴포넌트**: 접두사 사용
  ```java
  Button	btn	btnLogin
  TextView	txt	txtTitle
  EditText	et	etSearch
  ImageView	img	imgProfile
  RecyclerView	rv	rvChatList
  CheckBox	cb	cbAgree
  Switch	sw	swNotification
  ```

#### 들여쓰기 및 포맷팅
- 들여쓰기: 4칸 스페이스 사용
- 중괄호 스타일: BSD 스타일
  ```java
  if (condition) {
      // code
  } else {
      // code
  }
  ```
#### Adapter 규칙
- ViewHolder 클래스는 static class 사용
  ```java
  static class ViewHolder extends RecyclerView.ViewHolder {
    ImageView img;
    TextView txt;

    ViewHolder(View itemView) {
        super(itemView);
        img = itemView.findViewById(R.id.imgProduct);
        txt = itemView.findViewById(R.id.txtName);
    }
  }
  ```
#### 주석
- 함수 상단에 간단한 설명 주석 작성
- 복잡한 알고리즘의 경우 핵심 로직에 주석 추가
- 한글 또는 영어로 작성 가능

브랜치 전략
  - main: 안정된 코드
  - feature/<기능명>/<name>: 새로운 기능 개발 브랜치
  - issue/<이슈 번호>/<name>: 이슈 수정 브랜치
