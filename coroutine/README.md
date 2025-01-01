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


## 코루틴 디스패처와 스레드 풀

![image](https://github.com/user-attachments/assets/c165fdb5-3bbf-45d4-be9a-28b335d58265)

동작 흐름

1. launch 등으로 생성된 코루틴은 디스패처에 의해 실행할 스레드에 할당된다.
2. 만약 Dispatchers.IO 혹은 Dispatchers.Default를 사용하면, 최초 사용 시점에 스레드 풀이 생성된다. 그리고 생성된 스레드 풀을 IO, Default 디스패처가 공유한다.
   - CommonPool은 더 이상 사용되지 않으며 semi-LIFO 스케줄링을 사용한다.
3. 공유하기 때문에 스레드 이름이 똑같이 출력된다. 하지만 스레드 풀 내부에서 IO 전용 스레드 풀과 Default 전용 스레드 풀이 구분되어 있다.
   - 스레드 풀에서 #1 ~ #64 로 만들어서 사용한다면, IO 디스패처는 #1~30, Default 디스패처는 #50~64를 사용하는 식으로 구분되어 있다. (예시임)

limitedParallelism
- limitedParallelism 함수를 적용하면, 병렬 처리 시 스레드 풀에서 최대 몇개의 스레드를 해당 작업에 할당할지 결정할 수 있다. 
- limitedParallelism 함수를 IO 디스패처에 적용했을 때는 기존 IO 스레드의 개수에 영향을 받지 않는 별도의 스레드 집합을 사용하는 디스패쳐가 만들어집니다. 이런 디스패처는 다른 작업에 영향을 받지 않아야 하는 작업을 할 때 사용돼야 합니다. 
- limitedParallelism 함수를 Default 디스패처에 적용했을 때는 기존 Default 디스패처의 스레드들 중 일부를 제한적으로 사용하는 디스패처가 만들어집니다. 이런 디스패처는 특정 작업이 모든 스레드를 사용하는 것을 방지해야 할 때 사용됩니다. 
- 즉 limitedParallism 함수는 위와 같이 매우 특별한 상황에만 사용되며, 대부분의 경우에는 Dispatcher.IO나 Dispatchers.Default만 사용해도 문제가 없습니다.
  
스레드 생성?
- IO 디스패처에 코루틴이 실행 요청되면 스레드가 부족하다고 판단될 경우 스레드가 생성되는데요. 
- 이때 부족한 개수 만큼만 생성되는 것이 아니라 일부 여유를 두고 생성한 다음 해당 스레드들 중 하나에 코루틴을 보내는 것으로 알고 있습니다. 
- 따라서 코루틴을 3개만 실행 요청하더라도 5개가 생성될 수 있고, 4개만 실행 요청하더라도 7개가 생성될 수 있습니다.
