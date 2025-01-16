# runBlocking vs runTest

코루틴 테스트에서 `runBlocking`과 `runTest`의 차이점을 알아보겠습니다.

## 주요 차이점

### 1. 가상 시간 스케줄러

- `runTest`는 내부적으로 `TestCoroutineScheduler`를 사용하여 가상 시간을 제어할 수 있습니다.
- `runBlocking`은 실제 시간이 흐르기 때문에, `delay()`와 같은 지연이 발생하면 실제로 해당 시간만큼 대기합니다.

예시:
```kotlin
// runTest 사용
runTest {
    delay(10_000L) // 10초 대기처럼 보이지만 실제로는 거의 즉시 실행됨
    scheduler.currentTime shouldBe 10_000L // 가상 스케줄러의 현재 시각은 10초
}

// runBlocking 사용
runBlocking {
    delay(10_000L) // 실제로 10초 대기
}
```

### 2. 테스트 실행 시간

- `runTest`는 `delay()`와 같은 지연을 가상 시간으로 처리하기 때문에 테스트 실행 시간이 매우 빠릅니다.
- `runBlocking`은 실제 시간이 흐르기 때문에 테스트 실행 시간이 길어질 수 있습니다.

## 언제 무엇을 사용해야 할까?

### runTest를 사용해야 하는 경우

1. 시간 기반 테스트가 필요한 경우
```kotlin
class FakeRepository {
    suspend fun getData(): String {
        delay(1000L) // DB 조회 시간을 시뮬레이션
        return "data"
    }
}

// 테스트 코드
runTest {
    val repository = FakeRepository()
    val result = repository.getData()
    // delay(1000L)가 있지만 실제로는 거의 즉시 실행됨
}
```

2. 여러 코루틴의 타이밍을 정확하게 제어해야 하는 경우
```kotlin
runTest {
    var result = ""
    
    launch {
        delay(2000L)
        result += "World"
    }
    
    launch {
        delay(1000L)
        result += "Hello "
    }
    
    advanceTimeBy(2000L)
    result shouldBe "Hello World"
}
```

### runBlocking을 사용해도 되는 경우

1. 시간 기반 테스트가 필요없는 경우
```kotlin
class SimpleCalculator {
    suspend fun add(a: Int, b: Int): Int {
        return a + b
    }
}

// 테스트 코드
runBlocking {
    val calculator = SimpleCalculator()
    val result = calculator.add(1, 2)
    result shouldBe 3
}
```

2. 실제 시간이 필요한 테스트를 할 경우
```kotlin
runBlocking {
    val startTime = System.currentTimeMillis()
    delay(100L)
    val endTime = System.currentTimeMillis()
    
    (endTime - startTime) shouldBeGreaterThan 100L
}
```

## 결론

- `delay()`나 시간 기반 테스트가 필요없다면 `runBlocking`을 사용해도 무방합니다.
- 하지만 테스트에서 `delay()`를 사용하는 경우가 생각보다 많을 수 있습니다:
  - 외부 서비스 호출 시뮬레이션
  - DB 조회 시간 시뮬레이션
  - 비동기 작업의 타이밍 테스트
  - 등등...
- 따라서 테스트의 일관성과 실행 시간을 위해 가능하면 `runTest`를 사용하는 것이 좋습니다.

## 참고 자료

- https://github.com/Kotlin/kotlinx.coroutines/blob/master/kotlinx-coroutines-test/common/src/TestBuilders.kt#L304
- https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/run-test.html
