## 코루틴 디버깅

### Add to VM options
```plaintext
-Dkotlinx.coroutines.debug
```

### 스레드 이름을 출력하면
```plaintext
main @coroutine#2
```
- main: 스레드 이름
- @: 구분자
- coroutine: 코루틴 이름 (중복 가능)
- 2: 코루틴 고유 번호 (중복 불가능)

## 코루틴 디스패처

### Dispatchers.IO

- 네트워크 요청이나 DB 읽기 쓰기 같은 입출력(I/O) 작업을 실행하는 디스패처
- 코루틴 v1.8.1 기준, 가용한 스레드 수는 = Max(64, JVM에서 사용할 수 있는 프로세서 수)
  - DefaultIoScheduler 객체에서 확인 가능하다.
  - 일반적인 컴퓨터에서는 프로세서 수가 64개 이하이므로 64개의 스레드를 사용 가능하다고 볼 수 있다.
  - M2 Pro 기준 SystemProps.AVAILABLE_PROCESSORS = 12

### Dispatchers.Default

- CPU 바운드 작업을 실행하는 디스패처
- 코루틴 v1.8.1 기준, 가용한 스레드 수는 = Max(2, JVM에서 사용할 수 있는 프로세서 수)
  - 요즘(?) 컴퓨터에서는 프로세서 수가 2개 이상이므로 프로세서 수만큼 스레드가 생성된다고 볼 수 있다.

### Dispatchers.Main

- 메인 스레드에서의 작업을 실행하는 디스패처
- 기본 코루틴 라이브러리에는 구현체가 없다. 사용을 위해서는 안드로이드 코루틴 라이브러리를 추가해야 한다.
- Dispatchers.Main.immediate 옵션도 제공하는데, 이 옵션은 디스패처 내 작업 대기열과 무관하게 메인 스레드에서 즉시 실행되도록 보장한다.

## 코루틴 디스패처와 스레드 풀

![image](https://github.com/user-attachments/assets/c165fdb5-3bbf-45d4-be9a-28b335d58265)

### 동작 흐름

1. launch 등으로 생성된 코루틴은 디스패처에 의해 실행할 스레드에 할당된다.
2. 만약 Dispatchers.IO 혹은 Dispatchers.Default를 사용하면, 최초 사용 시점에 스레드 풀이 생성된다. 그리고 생성된 스레드 풀을 IO, Default 디스패처가 공유한다.
   - CommonPool은 더 이상 사용되지 않으며 semi-LIFO 스케줄링을 사용한다.
3. 공유하기 때문에 스레드 이름이 똑같이 출력된다. 하지만 스레드 풀 내부에서 IO 전용 스레드 풀과 Default 전용 스레드 풀이 구분되어 있다.
   - 스레드 풀에서 #1 ~ #64 로 만들어서 사용한다면, IO 디스패처는 #1~30, Default 디스패처는 #50~64를 사용하는 식으로 구분되어 있다. (예시임)

### limitedParallelism

- limitedParallelism 함수를 적용하면, 병렬 처리 시 스레드 풀에서 최대 몇개의 스레드를 해당 작업에 할당할지 결정할 수 있다. 
- limitedParallelism 함수를 IO 디스패처에 적용했을 때는 기존 IO 스레드의 개수에 영향을 받지 않는 별도의 스레드 집합을 사용하는 디스패쳐가 만들어집니다. 이런 디스패처는 다른 작업에 영향을 받지 않아야 하는 작업을 할 때 사용돼야 합니다. 
- limitedParallelism 함수를 Default 디스패처에 적용했을 때는 기존 Default 디스패처의 스레드들 중 일부를 제한적으로 사용하는 디스패처가 만들어집니다. 이런 디스패처는 특정 작업이 모든 스레드를 사용하는 것을 방지해야 할 때 사용됩니다. 
- 즉 limitedParallism 함수는 위와 같이 매우 특별한 상황에만 사용되며, 대부분의 경우에는 Dispatcher.IO나 Dispatchers.Default만 사용해도 문제가 없습니다.
  
### 스레드 생성?

- IO 디스패처에 코루틴이 실행 요청되면 스레드가 부족하다고 판단될 경우 스레드가 생성되는데요. 
- 이때 부족한 개수 만큼만 생성되는 것이 아니라 일부 여유를 두고 생성한 다음 해당 스레드들 중 하나에 코루틴을 보내는 것으로 알고 있습니다. 
- 따라서 코루틴을 3개만 실행 요청하더라도 5개가 생성될 수 있고, 4개만 실행 요청하더라도 7개가 생성될 수 있습니다.

### 참고

- https://www.inflearn.com/community/questions/1407720
- https://discuss.kotlinlang.org/t/commonpool-default-for-coroutines/11965

## 코루틴 취소 확인

- job.cancel()을 호출하면 즉시 취소되는 것이 아니라 취소 요청이 들어간 것 뿐이다.
- 취소 요청에 대한 확인은 아래와 같은 경우에 수행된다.
  - 일시 중단 시점
  - 코루틴이 실행을 대기하는 시점
- 취소 확인 시점을 만드는 3가지 방법
  - delay 함수를 사용해 취소 확인 시점 만들기
  - yield 함수를 사용해 취소 확인 시점 만들기
  - CoroutineScope.isActive 속성을 사용해 취소 확인

## Job 객체의 상태 변수

- Job 객체는 isActive, isCompleted, isCancelled 세 가지 상태 변수를 가지고 있다.
  - isActive: Job 객체가 실행 중인지 여부 (Active 상태)
  - isCancelled: Job 객체가 취소 요청되었는지 여부 (Cancelling, Cancelled 상태)
  - isCompleted: Job 객체가 완료되었는지 여부. 취소 여부와는 무관하게 끝났는지 확인한다 (Cancelled, Completed 상태)

## 구조화된 동시성

### 구조화된 동시성의 원칙
- 구조화된 동시성의 원칙이란 비동기 작업을 구조화해 비동기 프로그래밍을 보다 안정적이고, 예측 가능하게 만드는 원칙입니다.
- 코루틴은 구조화된 동시성의 원칙을 사용해 비동기 작업인 코루틴을 부모-자식 관계로 구조화해 코루틴이 보다 안전하게 관리되고 제어될 수 있도록 합니다.
- 코루틴을 부모-자식 관계로 구조화 하기 위해서는 코루틴 빌더 함수의 람다식 안에서 새로운 코루틴 빌더 함수를 호출하기만 하면 됩니다.

### CoroutineContext 상속
- 부모 코루틴은 실행 환경(CoroutineContext)을 자식에게 상속합니다.
- 자식 코루틴 생성 시 CoroutineContext를 지정하지 않으면 부모 코루틴의 CoroutineContext를 상속받습니다.
- 자식 코루틴 생성 시 다른 Context를 넣어주면 상속받은 부모 코루틴의 Context에 덮어쓰기 됩니다.
  - 예를 들어, 부모 코루틴의 Context에 CoroutineDispatcher와 CoroutineName이 있고, 자식 코루틴에 CoroutineName만 지정하면 CoroutineDispatcher는 부모를 따라가고 CoroutineName만 자식의 것으로 변경됩니다.

### Job 객체의 계층 구조
- 모든 코루틴 빌더 함수(launch, async 등)는 호출 시마다 코루틴 추상체인 Job 객체를 새롭게 생성합니다.
- 코루틴 제어에 Job 객체가 필요하기 때문에, Job 객체는 부모 코루틴으로부터 상속 받지 않습니다.
- 즉, 코루틴 빌더를 통해 생성된 코루틴들은 서로 다른 Job을 가집니다.
- CoroutineContext를 통해서 Job 객체를 얻어올 수 있습니다: `coroutineContext.job` 또는 `coroutineContext[Job]`

### Job의 계층 프로퍼티
- parent: Job?
  - 코루틴은 부모 코루틴이 없을 수 있고, 부모 코루틴이 있더라도 최대 하나입니다.
  - 최상위 코루틴은 부모가 없습니다(null).
- children: Sequence<Job>
  - 하나의 코루틴이 여러 자식 코루틴을 가질 수 있습니다.
- 부모 코루틴의 Job은 children 접근자로 자식 코루틴의 Job에 접근 가능하고, 자식 코루틴의 Job은 parent 접근자로 부모 코루틴의 Job에 접근 가능합니다.
- 이러한 Job의 계층 구조는 코루틴 구조화에 핵심적인 역할을 합니다.

## 궁금한 점

- Continuation에서 label이 정확히 뭘 의미하는지? (decomplie code 기준으로)
- runBlocking을 거의 사용하지 않는 이유는? 그 대안은?
- Sprinb MVC > Controller 메서드를 suspend로 작성하면 부모 코루틴은 어디서 빌드해주는지? (suspend fun main 과 동일한건지?)
  - 내부적으로 Dispatchers.Unconfined를 쓰기 때문에 스레드 1개만 할당된다. 따라서 중단 발생 시 다른 스레드에 할당되지 않는다. 스레드 할당받지 못한 코루틴을 처리하는 내부의 싱글 스레드 이벤트 루프에서 처리한다? 이 부분에 대해 자세히 알아보자.
- 코루틴 Job의 상태는 왜 외부에서 접근 불가능하고, isActive, isCancelled, isCompleted로만 접근 가능한지?
- launch와 async의 차이점은 무엇인지? 예외의 관점에서? (+ SupervisorJob)
- 