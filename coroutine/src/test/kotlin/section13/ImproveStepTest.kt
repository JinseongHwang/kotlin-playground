package section13

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*

class ImproveStepTest : FunSpec({
    test("[1] TestCoroutineScheduler, TestDispatcher, CoroutineScope을 직접 만드는 테스트") {
        // Given
        val testCoroutineScheduler = TestCoroutineScheduler() // 가상 시간 스케줄러 생성
        val testDispatcher: TestDispatcher = StandardTestDispatcher(scheduler = testCoroutineScheduler) // 가상 시간 스케줄러를 사용하는 Dispatcher 생성
        val testCoroutineScope = CoroutineScope(context = testDispatcher) // 가상 시간 스케줄러를 사용하는 CoroutineScope 생성

        var result = 0

        // When
        testCoroutineScope.launch {
            delay(10000L)
            result = 1
            delay(10000L)
            result = 2
        }
        testDispatcher.scheduler.advanceUntilIdle() // testCoroutineScope 하위의 모든 코루틴이 완료될 때까지 가상 시간을 흐르게 만듦

        // Then
        result shouldBe 2
    }

    /**
     * StandardTestDispatcher 생성 시 이미 만들어진 TestCoroutineScheduler 가 있으면 그것을 활용하고 없으면 새로운 TestCoroutineScheduler를 생성해서 사용한다.
     * 따라서 TestCoroutineScheduler를 직접 생성할 필요가 없다.
     */
    test("[2] TestDispatcher, CoroutineScope을 직접 만드는 테스트") {
        // Given
        val testDispatcher: TestDispatcher = StandardTestDispatcher() // 이렇게 써도 내부적으로 TestCoroutineScheduler를 생성해서 사용한다.
        val testCoroutineScope = CoroutineScope(context = testDispatcher)

        var result = 0

        // When
        testCoroutineScope.launch {
            delay(10000L)
            result = 1
            delay(10000L)
            result = 2
        }
        testDispatcher.scheduler.advanceUntilIdle() // 대신 testDispatcher.scheduler 로 스케줄러에 접근함

        // Then
        result shouldBe 2
    }

    /**
     * TestScope 함수를 호출하면 내부에 TestDispatcher를 가진 TestScope 객체가 반환된다.
     * TestScope은 확장 함수를 통해 advanceUntilIdle, advanceTimeBy 등의 함수를 직접 호출 할 수 있도록 한다.
     *
     * 즉, TestScope이 내부에 StandardTestDispatcher를 가지고, StandardTestDispatcher가 내부에 TestCoroutineScheduler를 가지고 있는 구조이다.
     * TestScope > StandardTestDispatcher > TestCoroutineScheduler
     */
    test("[3] TestScope을 사용하는 테스트") {
        // Given
        val testCoroutineScope = TestScope()

        var result = 0

        // When
        testCoroutineScope.launch {
            delay(10000L)
            result = 1
            delay(10000L)
            result = 2
        }
        testCoroutineScope.advanceUntilIdle()

        // Then
        result shouldBe 2
    }

    /**
     * runTest 함수는 수신 객체로 TestScope를 받아서 람다 함수를 실행한다. runTest 블럭에서 this(:TestScope)로 접근 가능하다.
     * runTest 자체로 TestScope 안의 코루틴을 새롭게 생성하고, 생성된 코루틴 내부에서 일시 중단 함수가 실행되더라도 가상 시간을 흐르게 해 곧바로 실행 완료 될 수 있도록 한다.
     * 즉, advanceUntilIdle을 호출하지 않아도 가상 시간이 흐른다.
     */
    test("[4] runTest 사용하는 테스트") {
        // Given
        var result = 0

        // When
        runTest {
            delay(10000L)
            result = 1
            delay(10000L)
            result = 2
        }

        // Then
        result shouldBe 2
    }

    /**
     * runTest 함수는 runTest 함수로 생성된 코루틴 내부에서 실행된 코루틴의 시간만 흐르게 만든다.
     * runTest 함수를 호출해 생성된 TestScope을 사용해 새로운 코루틴이 실행된다면, 이 코루틴은 자동으로 시간이 흐르지 않는다.
     * -> advanceUntilIdle을 호출해야만 가상 시간이 흐른다.
     *
     * 출력 결과:
     * 현재 가상 시간: 0ms, result: 0
     * 현재 가상 시간: 10000ms, result: 1
     */
    test("runTest 주의사항") {
        runTest {
            var result = 0

            launch {
                delay(10000L)
                result = 1
            }

            println("현재 가상 시간: ${this.currentTime}ms, result: $result")
            advanceUntilIdle()
            println("현재 가상 시간: ${this.currentTime}ms, result: $result")
        }
    }
})