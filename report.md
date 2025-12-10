# [최종 프로젝트 결과 보고서]
## 주제: 반려동물 용품 중고거래 플랫폼 'Pet Product Flea Market'

---

## 1. 프로젝트 개요 및 기획 의도

### 1.1 프로젝트 개요
본 프로젝트는 반려동물 가구의 증가에 따라 수요가 늘고 있는 '반려동물 용품'을 전문으로 하는 중고거래 안드로이드 애플리케이션입니다. 일반적인 중고거래 앱과 달리 반려동물 용품이라는 특정 카테고리에 집중하여 사용자에게 직관적인 UI와 편리한 거래 경험을 제공하는 것을 목표로 합니다.

### 1.2 기획 의도
* **타겟 사용자**: 사용하지 않는 캣타워, 이동장, 사료 등을 처분하고 싶은 판매자와 저렴하게 물품을 구매하고 싶은 구매자.
* **차별점**: 불필요한 카테고리를 제거하고, 상품 상세 이미지 확대(Zoom) 및 직관적인 주문 프로세스를 도입하여 모바일 환경에 최적화된 사용자 경험(UX)을 제공합니다.

---

## 2. 시스템 구조 및 개발 환경

### 2.1 개발 환경
* **OS**: Android (Min SDK 24, Target SDK 35)
* **Language**: Java (JDK 11)
* **IDE**: Android Studio
* **Library**: Volley (네트워크 통신), ViewPager2 (이미지 슬라이드), RecyclerView (리스트 뷰)

### 2.2 앱 아키텍처 및 데이터 흐름
* **MVC 패턴 적용**: 데이터(Model)와 화면(View), 그리고 이를 제어하는 컨트롤러(Activity/Adapter)를 분리하여 유지보수성을 높였습니다.
* **데이터 흐름**:
    1.  `LoginActivity`: `SharedPreferences`를 이용한 로컬 데이터 입출력.
    2.  `ProductListActivity`: `ArrayList<Product>`에 담긴 데이터를 `RecyclerView Adapter`를 통해 바인딩.
    3.  `ProductDetailActivity` & `OrderActivity`: `Intent`의 `Serializable` 기능을 통해 `Product` 객체 자체를 액티비티 간에 전달.

---

## 3. 핵심 기능 및 상세 구현 내용 (평가 기준 충족)

본 프로젝트는 총 10개의 액티비티로 구성되어 있으며, 각 화면은 유기적으로 연결되어 데이터를 주고받습니다.

### 3.1 회원 관리 시스템 (File I/O)
* **구현 파일**: `LoginActivity.java`, `activity_login.xml`
* **기능 상세**:
    * **자동 로그인 프로세스**: `SplashActivity`에서 1.5초 대기 후 로그인 화면으로 전환됩니다.
    * **데이터 저장 방식**: 별도의 DB 서버 구축 없이 가볍게 동작하도록 안드로이드 내부 파일 저장소인 `SharedPreferences`를 활용했습니다.
    * **로직**: 사용자가 입력한 ID를 Key로, PW를 Value로 저장하며, 로그인 시 `prefs.getString(id)`로 저장된 PW와 입력된 PW를 대조하여 인증을 수행합니다. 인증 성공 시 `ProductListActivity`로 이동하며, 이때 `Intent`로 사용자 ID를 넘겨주어 앱 전반에서 로그인 정보를 유지합니다.

### 3.2 메인 상품 목록 (RecyclerView & Adapter)
* **구현 파일**: `ProductListActivity.java`, `ProductAdapter.java`, `item_product.xml`
* **핵심 기술**: **RecyclerView**와 **ViewHolder 패턴**
* **구현 상세**:
    * 대량의 상품 데이터를 효율적으로 처리하기 위해 `ListView` 대신 `RecyclerView`를 사용했습니다.
    * `ProductAdapter` 클래스는 `RecyclerView.Adapter`를 상속받아 구현했습니다. `onCreateViewHolder`에서 뷰를 생성하고, `onBindViewHolder`에서 데이터를 연결합니다.
    * **클릭 이벤트 처리**: 어댑터 내부 인터페이스 `OnProductClickListener`를 정의하여, 아이템 클릭 시 메인 액티비티에서 이를 감지하고 상세 페이지로 이동하도록 구현했습니다. 이를 통해 뷰와 로직의 결합도를 낮췄습니다.

### 3.3 상품 상세 정보 및 이미지 슬라이더 (ViewPager2)
* **구현 파일**: `ProductDetailActivity.java`, `ProductImageAdapter.java`
* **핵심 기술**: **ViewPager2**
* **구현 상세**:
    * 상품의 여러 이미지를 좌우 스와이프로 넘겨볼 수 있도록 `ViewPager2`를 적용했습니다.
    * `ProductImageAdapter`를 통해 이미지 리소스를 바인딩하며, 이미지를 클릭할 경우 확대 화면(`ImageZoomActivity`)으로 전환되는 콜백 메서드를 구현했습니다.
    * 이전 화면에서 받은 `Product` 객체(`Serializable`)에서 데이터를 추출하여 제목, 가격, 설명을 동적으로 셋팅합니다.

### 3.4 이미지 핀치 줌 (Custom View)
* **구현 파일**: `ZoomImageView.java`, `ImageZoomActivity.java`
* **핵심 기술**: **Custom View**, **Matrix**, **ScaleGestureDetector**
* **구현 상세**:
    * 기존 `ImageView`에는 줌 기능이 없으므로, 직접 `AppCompatImageView`를 상속받은 커스텀 뷰 `ZoomImageView`를 제작했습니다.
    * `ScaleGestureDetector`를 사용하여 두 손가락의 거리 변화(Pinch)를 감지하고, 이를 `Matrix` 객체의 `postScale` 메서드에 적용하여 이미지를 확대/축소합니다.
    * `onGlobalLayoutListener`를 통해 이미지가 처음 로드될 때 화면 중앙에 맞춤(Fit Center) 정렬되도록 초기화 로직을 추가했습니다.

### 3.5 주문 및 결제 시스템 (Menu & Popup)
* **구현 파일**: `OrderActivity.java`, `OrderResultActivity.java`, `menu.xml`
* **핵심 기술**: **Popup Menu**
* **구현 상세**:
    * 상품 정보와 사용자가 입력한 주소를 바탕으로 주문을 진행합니다.
    * '결제 수단' 버튼 클릭 시 `PopupMenu`를 띄워 '삼성페이, 네이버페이, 계좌이체, 신용카드' 중 하나를 선택하게 합니다. 이는 안드로이드 메뉴 리소스(`res/menu/menu.xml`)를 활용하여 구현했습니다.
    * 선택된 데이터는 다시 `Intent`에 담겨 결과 화면(`OrderResultActivity`)으로 전송되어 최종 주문 영수증 형태로 출력됩니다.

### 3.6 공지사항 (네트워크 통신)
* **구현 파일**: `MainActivity.java`, `NoticeListAdapter.java`
* **핵심 기술**: **Volley Library**, **JSON Parsing**
* **구현 상세**:
    * 앱 내 공지사항 탭에서는 외부 웹 서버(PHP)와 통신하여 데이터를 가져옵니다.
    * `Volley` 라이브러리의 `JsonObjectRequest`를 사용하여 `GET` 방식으로 데이터를 요청하고, 응답받은 JSON 배열을 파싱하여 `Notice` 객체 리스트로 변환합니다.
    * 변환된 데이터는 `BaseAdapter`를 상속받은 `NoticeListAdapter`를 통해 `ListView`에 뿌려집니다.

### 3.7 마이페이지 및 하단 내비게이션
* **구현 파일**: `MyPageActivity.java`, `ProfileActivity.java`
* **핵심 기술**: **BottomNavigationView**
* **구현 상세**:
    * `ProductListActivity`, `MainActivity`, `MyPageActivity` 하단에 공통적으로 `BottomNavigationView`를 배치하여 화면 간 이동 편의성을 제공합니다.
    * 각 메뉴 아이템 클릭 시 `Intent` 플래그를 관리하여 불필요한 액티비티 스택이 쌓이는 것을 방지했습니다.

---

## 4. 프로젝트의 차별성 및 기술적 도전

### 4.1 데이터 전달의 효율성 (Serializable)
단순히 `String` 데이터를 하나씩 `putExtra`로 넘기는 비효율적인 방식을 지양하고, `Product` 클래스 자체를 직렬화(`implements Serializable`)하여 객체 단위로 데이터를 주고받았습니다. 이를 통해 코드의 가독성을 높이고 유지보수를 용이하게 만들었습니다.

### 4.2 사용자 경험(UX)을 고려한 커스텀 뷰
오픈소스 라이브러리를 사용할 수도 있었으나, 모바일 프로그래밍 학습 목표에 맞춰 직접 터치 이벤트를 처리하는 `ZoomImageView`를 구현했습니다. 이를 통해 안드로이드의 터치 이벤트 시스템(`MotionEvent`, `ScaleGestureDetector`)에 대한 이해도를 높였습니다.

---

## 5. 향후 개선 계획

1.  **데이터베이스 고도화**: 현재 파일(`SharedPreferences`)과 더미 데이터에 의존하는 방식을 개선하여, **Room Database** 또는 **Firebase Firestore**를 도입해 데이터의 영속성과 실시간 동기화를 구현할 예정입니다.
2.  **이미지 처리 라이브러리 도입**: 앱 용량 최적화를 위해 **Glide** 라이브러리를 도입하여 서버 URL 이미지를 비동기로 로딩하고 캐싱하는 기능을 추가하겠습니다.
3.  **예외 처리 강화**: 네트워크 통신 실패나 입력값 오류 등에 대한 예외 처리를 보강하여 앱의 안정성을 높일 것입니다.

---

## 6. 결론
본 프로젝트를 통해 안드로이드의 4대 컴포넌트 중 하나인 **Activity**의 생명주기와 상호작용을 깊이 이해할 수 있었습니다. 또한 **RecyclerView**와 **Adapter** 패턴을 활용하여 데이터를 효율적으로 관리하고, **Custom View**를 통해 사용자 친화적인 UI를 직접 구현해 보는 경험을 쌓았습니다. 이 앱은 기본적인 중고거래 기능을 모두 갖추고 있으며, 추후 DB 연동을 통해 실제 서비스 가능한 수준으로 발전시킬 수 있는 확장성을 가지고 있습니다.
