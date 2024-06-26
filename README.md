# Expand + Window = Expandow

## 프로젝트 개요
이 프로젝트는 간단한 탄막슈팅게임으로 windowkill 게임을 클론코딩한 프로젝트입니다.

## DEMO
https://github.com/he4rtbleed/Expandow/assets/29333455/9d03e66a-1335-4544-b706-67adb618e60a


## 주요 기능
- **플레이어 이동**: 방향키와 마우스를 사용하여 플레이어를 이동시킬 수 있습니다.
- **적 생성 및 관리**: 다양한 적 캐릭터들이 주기적으로 생성되며 플레이어와 충돌 시 공격합니다.
- **미사일 발사**: 플레이어가 미사일을 발사하여 적을 공격할 수 있습니다.
- **충돌 감지**: 플레이어와 적, 미사일 간의 충돌을 감지하고 처리합니다.
- **상점 시스템**: 플레이어는 상점에서 업그레이드를 구매하여 강해질 수 있습니다.

## 플레이
- 게임을 실행하면 플레이어는 wasd 키로 이동할 수 있습니다.
- 마우스를 클릭하여 미사일을 발사할 수 있습니다.
- 스페이스바키를 통해 상점을 연뒤 업그레이드 항목을 구매하여 플레이어를 강화할 수 있습니다.

## 주요 클래스 구조
- **Game.java**: 게임의 메인 엔트리 포인트로, 게임 루프와 초기화를 담당합니다.
- **Area 패키지**
  - `Area`: 게임 영역의 추상 클래스.
  - `MainArea`: 메인 게임 영역을 나타내는 클래스.
  - `ShopArea`: 상점 영역을 나타내는 클래스, 업그레이드를 처리합니다.
- **Entity 패키지**
  - `Entity`: 모든 게임 개체의 기본 추상 클래스.
  - `Movable`: 이동 가능한 개체를 위한 인터페이스.
  - `Player`: 플레이어를 나타내는 클래스.
  - `PlayerMissile`: 플레이어가 발사하는 미사일을 나타내는 클래스.
  - `Projectiles`: 모든 투사체의 추상 클래스.
  - `EntityManager`: 게임 내 모든 개체를 관리하는 클래스.
  - `SpawnManager`: 적을 생성하는 클래스.
- **Entity.Mobs 패키지**
  - `Mobs`: 적의 기본 추상 클래스.
  - `Circle`, `Triangle`, `Octagon`: 다양한 적 클래스.
- **Utils 패키지**
  - `CoordinateConvertHelper`: 좌표 변환 유틸리티 클래스.
  - `GameConditions`: 게임 상태를 관리하는 클래스.
  - `IntersectHelpers`: 충돌 감지를 위한 유틸리티 클래스.
  - `Enums/Edge`: 상수 정의 클래스.
