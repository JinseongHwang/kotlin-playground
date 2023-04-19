# from java to kotlin

> 최태현님의 [자바 개발자를 위한 코틀린 입문](https://www.inflearn.com/course/java-to-kotlin/dashboard) 강의를 보고 실습한 내용입니다.  
> README 에는 강의에서 설명한 내용, 여러 문서들, 제 생각이 섞여 있습니다.  
> ~저도 사람이기 때문에~ 정확하지 않은 정보가 있을 수 있습니다.  

## 코틀린에서 변수와 타입, 연산자를 다루는 방법

```kotlin
var number1: Long = 10L
var number2 = 10L
```
- 변수 선언 시 var number: Long = 10L 과 같은 형식으로 작성할 수 있다.
  - 단, 타입 추론 기능이 있기 때문에 타입을 생략할 수도 있다.

```kotlin
var number1: Long = 10L // Variable: non-final
val number2: Long = 10L // Value: final
```
- 변수 선언하는 방법으로 var과 val이 있다.
  - var: Variable의 약자로 가변 변수이다.
  - val: Value의 약자로 불변 변수이다.
    - Java에서 final을 붙인 것과 동일한데, 선언할 때 값을 부여하지 않았다면 최초 1회까지만 값을 부여할 수 있다.
  - 모든 변수는 우선 불변으로 val로 선언하는 것을 원칙으로 하자. 꼭 필요한 경우에만 var을 사용하는 것이 안전하다.

```kotlin
var number1: Long = 10L // Variable: non-final
val number2: Long = 10L // Value: final
```
- Kotlin에서는 숫자, 문자, 불리언과 같은 몇몇 타입들은 코드 작성 시 Reference type으로 보여지지만, 실행 시에는 Primitive type으로 표현된다.
  - Java에서도 숫자 연산 시에는 Reference type을 사용하지 말고 Primitive Type을 사용하라고 권장한다.
    - 불필요한 Boxing과 Unboxing이 진행되는 것을 막기 위해서이다.
  - 하지만 Kotlin에서는 똑똑하게 알아서 처리해준다. 개발자는 그저 Reference type으로 작성하면 된다.

```kotlin
val number1: Long = 10L // not null
val number2: Long? = 10L // nullable

println(number1.javaClass) // -> long
println(number2.javaClass) // -> class java.lang.Long
```
- nullable한 변수로 만들기 위해서는 타입 뒤에 "?" 를 붙여줘야 한다.
  - Kotlin에서는 기본적으로 모든 변수가 not null type이다.
  - nullable 변수로 활용하기 위해서 "?" 를 붙이게 되면 아예 다른 타입으로 간주된다.

```kotlin
val person = Person("jinseonghwang")
```
- 객체를 인스턴스화 할 때 new를 붙이지 않는다.
  - 생성자를 함수 호출하듯 호출하면 된다. Java와 달리 new를 사용하지 않는다.
  - Python에서 객체를 생성할 때와 유사하다.



