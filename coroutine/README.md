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