# ComposeRouteMap

**ComposeRouteMap**은 Android Jetpack Compose와 MVVM 아키텍처를 활용하여, 사용자 위치에서 선택한 **N개 장소**을 최적 경로로 방문하고, 네이버 지도를 통해 시각화하는 샘플 애플리케이션입니다.

---

## 🌟 주요 기능

- **장소 검색 & 추가**: 키워드(음식점·카페·상호 등)를 바탕으로 장소 검색  
- **여러 장소 관리**: 검색된 장소를 리스트에 추가·삭제 가능  
- **최적 경로 계산**: 사용자 위치를 시작점으로, 선택한 장소를 모두 방문하는 **최적 순서** 산출  
- **네이버 지도 연동**  
  - **마커 표시**: 현재 위치 · 목표 장소들을 마커로 표시  
  - **경로 그리기**: 선에 화살표 패턴이 들어간 `PathOverlay`로 경로 시각화  
  - **카메라 자동 조정**: 모든 마커가 보이도록 카메라 바운드 이동  
- **요약 정보 배너**: 총 **거리**(m/km) 및 **예상 소요 시간**(분/시간) 상단 또는 하단 배너로 표시  
- **위치 권한 처리**: 앱 실행 시 위치 권한 요청 및 허용 시 내 위치로 카메라 이동  
- **햅틱 피드백 및 로딩 인디케이터**  
- **Compose Navigation**을 통한 화면 전환

---

## 📂 프로젝트 구조 (주요 디렉터리)

```
app/src/main/
├─ java/com/example/composeroutemap/
│  ├─ data/
│  │   ├─ Dimens.kt
│  │   ├─ LocationStore.kt
│  │   ├─ Place.kt
│  │   └─ remote/ (DirectionsDto, RouteSearchResult)
│  ├─ ui/
│  │   ├─ map/     (NaverMapScreen.kt, NaverMapViewModel.kt)
│  │   ├─ search/  (SearchScreen.kt, SearchViewModel.kt)
│  │   └─ customwidget/ (Map buttons, haptics)
│  └─ utils/      (MapUtils, permission helpers)
└─ res/           (layouts, drawables)
```

