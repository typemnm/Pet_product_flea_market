# [최종 프로젝트 결과 보고서]
## 주제: 반려동물 용품 중고거래 플랫폼 'Pet Product Flea Market'

---

## 1. 프로젝트 개요 및 기획 의도

### 1.1 프로젝트 개요
본 프로젝트는 반려동물 가구의 증가에 따라 수요가 늘고 있는 '반려동물 용품'을 전문으로 하는 중고거래 안드로이드 애플리케이션입니다. 불필요한 카테고리를 제거하고 반려동물 용품에만 집중하여, 사용자에게 직관적인 UI와 편리한 거래 경험을 제공하는 것을 목표로 합니다.

### 1.2 개발 환경
* **OS**: Android (Min SDK 24, Target SDK 36)
* **Language**: Java (JDK 11)
* **IDE**: Android Studio
* **Libraries**: Volley (네트워크 통신), ViewPager2 (이미지 슬라이드), RecyclerView (리스트 뷰) 등

---

## 2. 평가 기준 반영 내역 (핵심 구현 기술)

본 프로젝트는 과제 평가 기준인 4가지 항목을 모두 충족하도록 설계 및 구현되었습니다.

### 2.1 액티비티 5개 이상 및 데이터 연동
총 **14개의 Activity**를 구현하였으며, 각 화면은 유기적으로 연결되어 있습니다.
* **주요 흐름**: Splash → Login → ProductList → ProductDetail → Order → OrderResult → MyPage
* **데이터 송수신**:
    * `Intent.putExtra()`: 로그인 시점부터 `USER_ID`를 모든 액티비티에 전달하여 세션을 유지합니다.
    * `Serializable`: `Product` 객체를 직렬화하여 상세 화면 이동 시 상품 데이터 전체를 효율적으로 전달합니다.

### 2.2 메뉴 사용
* **PopupMenu**: `OrderActivity`에서 결제 수단 선택 시 팝업 메뉴를 띄워 삼성페이, 네이버페이 등을 선택하도록 구현했습니다.
* **BottomNavigationView**: `ProductListActivity`와 `MyPageActivity` 하단에 배치하여 주요 메뉴 간 이동 편의성을 제공합니다.

### 2.3 어댑터뷰 및 커스텀 뷰 사용
* **RecyclerView**: 상품 목록과 이미지 슬라이더에 적용하여 대량의 데이터를 효율적으로 관리하고 스크롤 성능을 최적화했습니다.
* **Custom View (ZoomImageView)**: `AppCompatImageView`를 상속받아 직접 구현했습니다. `ScaleGestureDetector`를 활용해 핀치 줌(Pinch Zoom) 기능을 제공합니다.

### 2.4 데이터베이스 혹은 파일 사용
* **SharedPreferences**: 별도의 DB 서버 없이 로그인 정보(ID/PW)와 구매 내역을 안드로이드 로컬 파일에 저장하여 영구 보존합니다.
* **Network (Volley)**: 공지사항과 의견 보내기 기능에서는 PHP 서버와 JSON 데이터를 주고받습니다.

---

## 3. 상세 기능 명세 및 구현 로직

### 3.1 로컬 데이터 기반 회원 인증 시스템
* **파일**: `LoginActivity.java`
* **로직**: `SharedPreferences`를 이용해 회원가입 시 ID/PW를 Key-Value로 저장합니다. 로그인 시 입력된 ID로 저장된 PW를 조회(`getString`)하여 일치 여부를 검증합니다.

### 3.2 RecyclerView와 ViewHolder 패턴
* **파일**: `ProductListActivity.java`, `ProductAdapter.java`
* **로직**: `ViewHolder` 패턴을 적용하여 스크롤 시 뷰 생성 비용을 최소화했습니다. 어댑터 내부에서 클릭 이벤트를 처리하지 않고 `OnProductClickListener` 인터페이스를 통해 액티비티로 콜백(Callback)하여 결합도를 낮췄습니다.

### 3.3 객체 직렬화(Serialization) 데이터 전달
* **파일**: `Product.java`, `ProductDetailActivity.java`
* **로직**: `Product` 클래스가 `Serializable`을 구현하도록 하여, 인텐트로 객체를 통째로 넘깁니다. 이를 통해 이름, 가격, 이미지 등을 개별적으로 보내는 번거로움을 없앴습니다.

### 3.4 Matrix 연산을 이용한 커스텀 줌 뷰
* **파일**: `ZoomImageView.java`
* **로직**: `ScaleGestureDetector`로 두 손가락의 거리 변화를 감지하고, 이를 `Matrix`의 `postScale` 메서드에 적용하여 이미지를 확대/축소합니다. 이미지가 처음 로드될 때 `onGlobalLayout`을 통해 화면 중앙에 맞춤 정렬(Fit Center)됩니다.

### 3.5 구매 내역 인덱싱 저장 (Indexing Strategy)
* **파일**: `OrderResultActivity.java`, `PurchaseListActivity.java`
* **로직**: 단일 파일에 여러 구매 내역을 저장하기 위해 `COUNT` 키를 사용합니다. 새로운 구매 시 `COUNT`를 증가시키고, 이를 인덱스로 삼아 `NAME_1`, `PRICE_1` 형태로 데이터를 누적 저장합니다.

### 3.6 비동기 서버 통신 (Volley)
* **파일**: `NoticeActivity.java`, `FeedbackActivity.java`
* **로직**: `JsonObjectRequest` (GET)로 공지사항 목록을 받아오고, `StringRequest` (POST)로 사용자 의견을 서버에 전송합니다. 메인 스레드를 차단하지 않는 비동기 방식으로 구현되었습니다.

---

## 4. 부록: 전체 클래스 기능 명세

### 4.1 화면 및 로직 제어 (Activity)
| 클래스명 | 기능 설명 |
| :--- | :--- |
| `SplashActivity` | 앱 실행 시 로고를 1.5초간 보여준 후 로그인 화면으로 자동 이동 |
| `LoginActivity` | `SharedPreferences`를 이용한 회원가입 및 로그인 인증 처리 |
| `ProductListActivity` | 메인 홈 화면. `RecyclerView`로 상품 목록 표시 및 하단 탭 이동 |
| `ProductDetailActivity` | 상품 상세 정보 표시, `ViewPager2` 이미지 슬라이드, 구매 버튼 기능 |
| `OrderActivity` | 배송지 입력 및 `PopupMenu`를 이용한 결제 수단 선택 |
| `OrderResultActivity` | 주문 결과 영수증 표시 및 구매 내역 파일 저장 |
| `MyPageActivity` | 사용자 정보, 구매 내역, 공지사항 등으로 이동하는 허브 화면 |
| `PurchaseListActivity` | 파일에 저장된 과거 구매 내역을 불러와 리스트로 표시 |
| `ProfileActivity` | 로그인한 사용자의 ID 및 프로필 정보 표시 |
| `ImageZoomActivity` | 상품 이미지를 전체 화면으로 확대해서 보기 (커스텀 뷰 사용) |
| `NoticeActivity` | 서버(PHP)에서 공지사항 JSON 데이터를 받아와 목록 표시 |
| `FeedbackActivity` | 별점과 의견을 작성하여 서버 DB로 전송 (POST) |
| `FeedbackListActivity` | 다른 사용자들이 남긴 의견 목록을 서버에서 받아와 표시 |
| `MainActivity` | 앱의 기본 진입점 혹은 공지사항 테스트용 베이스 액티비티 |

### 4.2 데이터 연결 (Adapter)
| 클래스명 | 기능 설명 |
| :--- | :--- |
| `ProductAdapter` | 상품 목록(`RecyclerView`)에 데이터를 바인딩하고 클릭 이벤트 처리 |
| `ProductImageAdapter` | 상세 화면의 이미지 슬라이더(`ViewPager2`) 데이터 바인딩 |
| `NoticeListAdapter` | 공지사항 데이터(`ListView`) 바인딩 |
| `FeedbackListAdapter` | 의견 목록 데이터(`ListView`) 바인딩 |
| `PurchaseListAdapter` | 구매 내역 데이터(`ListView`) 바인딩 |

### 4.3 데이터 모델 (Model)
| 클래스명 | 기능 설명 |
| :--- | :--- |
| `Product` | 상품 정보(ID, 이름, 가격, 이미지 등) 저장 및 직렬화(`Serializable`) |
| `Notice` | 공지사항 정보(번호, 작성자, 날짜, 내용) 저장 |
| `Feedback` | 의견 정보(번호, 아이디, 별점, 내용, 날짜) 저장 |
| `Purchase` | 구매 내역 정보(상품명, 가격, 주소, 결제수단) 저장 |

### 4.4 커스텀 뷰 (Custom View)
| 클래스명 | 기능 설명 |
| :--- | :--- |
| `ZoomImageView` | `ScaleGestureDetector`와 `Matrix`를 이용해 핀치 줌 기능을 직접 구현한 이미지 뷰 |

---

## 5. 결론 및 기대 효과

본 프로젝트는 안드로이드의 4대 컴포넌트 중 **Activity**의 생명주기와 데이터 전달 메커니즘을 충실히 구현하였으며, **RecyclerView**와 **Adapter** 패턴을 통해 데이터를 효율적으로 관리했습니다. 또한 **Custom View**를 직접 제작하여 모바일 환경에 최적화된 UX를 제공하고, **SharedPreferences**와 **Volley**를 통해 로컬과 서버 데이터를 모두 다루는 경험을 쌓았습니다. 이 앱은 기본적인 중고거래 기능을 완벽히 수행하며, 추후 DB 고도화를 통해 실제 서비스로 확장 가능한 구조를 갖추고 있습니다.
