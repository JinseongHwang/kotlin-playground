package section13

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher

class TestCoroutineSchedulerTest : FunSpec({

    /**
     * TestCoroutineScheduler의 advanceTimeBy 함수를 사용하면 가상 시간을 흐르게 만들 수 있다.
     * TestCoroutineScheduler의 currentTime 프로퍼티를 사용하면 현재 시간(가상 시간)을 확인할 수 있다.
     */
    test("가상 시간 조절 테스트") {
        val testCoroutineScheduler = TestCoroutineScheduler() // 생성 시점에 가상 시간은 0초라고 가정

        testCoroutineScheduler.advanceTimeBy(5000L) // 5초를 흐르게 만듦
        testCoroutineScheduler.currentTime shouldBe 5000L // 현재 시간이 5초임을 단언
        testCoroutineScheduler.advanceTimeBy(6000L) // 6초를 더 흐르게 만듦
        testCoroutineScheduler.currentTime shouldBe 11000L // 현재 시간이 11초임을 단언
        testCoroutineScheduler.advanceTimeBy(10000L) // 10초를 더 흐르게 만듦
        testCoroutineScheduler.currentTime shouldBe 21000L // 현재 시간이 21초임을 단언
    }

    /**
     * TestCoroutineScheduler를 사용하는 Dispatcher와, 그 Dispatcher를 사용하는 CoroutineScope를 생성하기
     */
    test("가상 시간 위에서 테스트 진행") {
        // Given
        val testCoroutineScheduler = TestCoroutineScheduler() // 가상 시간 스케줄러 생성
        val testDispatcher: TestDispatcher = StandardTestDispatcher(scheduler = testCoroutineScheduler) // 가상 시간 스케줄러를 사용하는 Dispatcher 생성
        val testCoroutineScope = CoroutineScope(context = testDispatcher) // 가상 시간 스케줄러를 사용하는 CoroutineScope 생성

        var result = 0

        // When
        testCoroutineScope.launch {
            delay(10000L) // 10초를 기다림
            result = 1
            delay(10000L) // 10초를 더 기다림
            result = 2
        }

        // Then
        testCoroutineScheduler.advanceTimeBy(5000L) // 5초를 흐르게 만듦
        result shouldBe 0
        testCoroutineScheduler.advanceTimeBy(6000L) // 6초를 더 흐르게 만듦
        result shouldBe 1
        testCoroutineScheduler.advanceTimeBy(10000L) // 10초를 더 흐르게 만듦
        result shouldBe 2
    }

    /**
     * TestCoroutineScheduler의 advanceUntilIdle 함수를 사용하면 가상 시간 스케줄러를 사용하는 모든 코루틴이 완료될 때까지 시간이 흐른다
     */
    test("advanceUtilIdle 동작 살펴보기") {
        // Given
        // StandardTestDispatcher 생성 시 이미 만들어진 TestCoroutineScheduler 가 있으면 그것을 활용하고 없으면 새로운 TestCoroutineScheduler를 생성해서 사용한다.
        // 따라서 TestCoroutineScheduler를 직접 생성할 필요가 없다.
        val testDispatcher: TestDispatcher = StandardTestDispatcher() // 이렇게 써도 내부적으로 TestCoroutineScheduler를 생성해서 사용한다.
        val testCoroutineScope = CoroutineScope(context = testDispatcher)

        var result = 0

        // When
        testCoroutineScope.launch {
            delay(10000L) // 10초를 기다림
            result = 1
            delay(10000L) // 10초를 더 기다림
            result = 2
        }
        testDispatcher.scheduler.advanceUntilIdle() // testCoroutineScope 하위의 모든 코루틴이 완료될 때까지 가상 시간을 흐르게 만듦

        // Then
        result shouldBe 2
    }
})