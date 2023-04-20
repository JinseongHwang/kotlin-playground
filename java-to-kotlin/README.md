# 자바 개발자를 위한 코틀린 입문

> 최태현님의 [자바 개발자를 위한 코틀린 입문](https://www.inflearn.com/course/java-to-kotlin/dashboard) 강의를 보고 실습한 내용입니다.  
> README 에는 강의에서 설명한 내용, 여러 문서들, 제 생각이 섞여 있습니다.  
> ~저도 사람이기 때문에~ 정확하지 않은 정보가 있을 수 있습니다.  

## 코틀린에서 변수와 타입, 연산자를 다루는 방법

```kotlin
var number1: Long = 10L
var number2 = 10L
```
- 변수 선언 시 `var number: Long = 10L` 과 같은 형식으로 작성할 수 있다.
  - 단, 타입 추론 기능이 있기 때문에 타입을 생략할 수도 있다.

<br/>

```kotlin
var number1: Long = 10L // Variable: non-final
val number2: Long = 10L // Value: final
```
- 변수 선언하는 방법으로 `var`과 `val`이 있다.
  - var: Variable의 약자로 가변 변수이다.
  - val: Value의 약자로 불변 변수이다.
    - Java에서 `final`을 붙인 것과 동일한데, 선언할 때 값을 부여하지 않았다면 최초 1회까지만 값을 부여할 수 있다.
  - 모든 변수는 우선 불변으로 `val`로 선언하는 것을 원칙으로 하자. 꼭 필요한 경우에만 `var`을 사용하는 것이 안전하다.

<br/>

```kotlin
var number1: Long = 10L // Variable: non-final
val number2: Long = 10L // Value: final
```
- Kotlin에서는 숫자, 문자, 불리언과 같은 몇몇 타입들은 코드 작성 시 Reference type으로 보여지지만, 실행 시에는 Primitive type으로 표현된다.
  - Java에서도 숫자 연산 시에는 Reference type을 사용하지 말고 Primitive Type을 사용하라고 권장한다.
    - 불필요한 Boxing과 Unboxing이 진행되는 것을 막기 위해서이다.
  - 하지만 Kotlin에서는 똑똑하게 알아서 처리해준다. 개발자는 그저 Reference type으로 작성하면 된다.

<br/>

```kotlin
val number1: Long = 10L // not null
val number2: Long? = 10L // nullable

println(number1.javaClass) // -> long
println(number2.javaClass) // -> class java.lang.Long
```
- nullable한 변수로 만들기 위해서는 타입 뒤에 `?` 를 붙여줘야 한다.
  - Kotlin에서는 기본적으로 모든 변수가 not null type이다.
- nullable 변수로 활용하기 위해서 `?` 를 붙이게 되면 아예 다른 타입으로 간주된다.
  - nullable 타입의 변수의 멤버에 직접적으로 접근하면 컴파일 에러가 발생한다.
  - 하지만 `?`를 뺀 not null 타입 변수에는 멤버에 직접적으로 접근해도 컴파일 에러가 발생하지 않는다.

<br/>

```kotlin
val str1: String? = "ABC"
val str2: String? = null

println(str1?.length) // -> 3
println(str2?.length) // -> null
```
- Kotlin에서는 nullable 변수의 멤버에 접근하는 것과 관련해서 Safe Call 이라는 기능을 제공한다.
  - 변수가 null이 아니라면 실행하고, null이라면 실행하지 않고 null을 반환한다.

<br/>

```kotlin
val str: String? = null
println(str?.length ?: 0) // -> 0

str?.startsWith("A") ?: throw IllegalArgumentException("str is null")
str?.length ?: return 0
```
- Safe Call 과 함께 쓰이는 기능으로, `?:`를 Elvis 연산자라고 부른다.
  - `?:` 앞의 연산 결과가 null 이면 뒤의 값을 사용한다.
- 단순한 값 반환 뿐만 아니라, 예외를 발생시키거나 early return도 가능하다.

<br/>

```kotlin
val str1: String? = "ABC"
val str2: String? = null

println(str1!!.startsWith("A")) // -> true 
println(str2!!.startsWith("A")) // -> NPE 발생
```
- 개발 과정에서 어떤 변수에 null이 들어갈 일이 없지만, nullable이 되어야 하는 경우가 있다.
- 이 경우에는 해당 변수가 nullable 타입이지만, 절대 null이 아니라는 것을 알려주기 위해 변수 뒤에 `!!`을 붙여준다.
  - null 이 아니라면 정상적으로 동작한다.
  - null이라면 NPE가 발생하기 때문에 확실한 경우에만 사용할 수 있도록 주의해야 한다.
- 널 아님 단언(not-null assertion) 연산자라고 부른다.

<br/>

```java
private final Person person = new Person("jinseonghwang");
```
```kotlin
val person = Person("jinseonghwang")
```
- 객체를 인스턴스화 할 때 `new`를 붙이지 않는다.
  - 생성자를 함수 호출하듯 호출하면 된다. Java와 달리 `new`를 사용하지 않는다.
  - Python에서 객체를 생성할 때와 유사하다.

<br/>

```java
/* Java code */
import org.jetbrains.annotations.Nullable;

public class Person {
    private final String name;
    
    public Person(String name) { this.name = name; }

    @Nullable // [A] null 추론
    public String getName() { return name; }
}
```
```kotlin
/* Kotlin code */
fun main() {
    val person = Person(null)
    startsWithA(person.name)
}

fun startsWithA(str: String): Boolean { // str에 null을 허용하지 않음
    return str.startsWith("A")
}
```
- Java 코드를 Kotlin에서 가져다 사용할 때 null 추론을 하기 힘들다.
  - Kotlin에서 null 관련해서 알 수 없는 타입을 플랫폼 타입이라고 부른다.
- [A] 지점의 어노테이션으로 Kotlin의 null 추론을 도와줄 수 있다.
  - _Case1: 어노테이션이 없는 경우_
    - null 추론이 불가능하기 때문에 일단 컴파일 에러를 발생시키지 않는다.
    - person.name 에 null이 들어있지 않다면 정상적으로 동작한다.
    - person.name 에 null이 들어있다면 런타임 때 `getName()` 메서드 호출 시 NPE가 발생한다.
  - _Case2: @NotNull 어노테이션이 있는 경우_
    - person.name 에 null이 들어있지 않다면 정상적으로 동작한다.
    - person.name 에 null이 들어있다면 런타임 때 IllegalStateException 가 발생한다.
  - _Case3: @Nullable 어노테이션이 있는 경우_
    - person.name 라고 코드 작성 시 컴파일 에러가 발생한다.
- 컴파일 에러를 내기 위해서는 Kotlin 코드에서 Java 코드 접근 시 Java 코드에 null 추론 가능한 어노테이션을 붙여주자.
  - 아래 3개의 패키지에 있는 null 관련 어노테이션을 사용하면 된다.
    - `javax.annotation` 패키지
    - `android.support.annotation` 패키지
    - `org.jetbrains.annotation` 패키지
  - ~컴파일 에러 최고!~
- 만약 Kotlin으로 개발 시 플랫폼 타입이 존재하는 Java 라이브러리를 가져와서 사용할 일이 있을 수 있다. 이런 경우에는 Kotlin에서 Java 코드를 호출하는 최초 지점을 하나로 Wrapping해서, 추후 이슈가 생겼을 때 해당 단일 지점만 수정하면 되도록 개발하는 것이 좋다.
  - 단, SPoF가 되지 않도록 고려하는 것도 매우 중요하다.

<br/>




