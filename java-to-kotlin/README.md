# 자바 개발자를 위한 코틀린 입문

> 최태현님의 [자바 개발자를 위한 코틀린 입문](https://www.inflearn.com/course/java-to-kotlin/dashboard) 강의를 보고 실습한 내용입니다.  
> README 에는 강의에서 설명한 내용, 여러 문서들, 제 생각이 섞여 있습니다.  
> ~저도 사람이기 때문에~ 정확하지 않은 정보가 있을 수 있습니다.  

# A. 코틀린에서 변수와 타입, 연산자를 다루는 방법

## A-1. var과 val 다루기

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

## A-2. null 다루기

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
/* Java code */
final Person person = new Person("jinseonghwang");
```
```kotlin
/* Kotlin code */
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

## A-3. 타입 다루기

```kotlin
val num1: Int = 3
val num2: Long = num1 // compile error: type mismatch
val num2: Long = num1.toLong() // Good!
```
- Kotlin에서는 타입 변환을 명시적으로 해줘야 한다.
- `toXXX()` 메서드가 잘 구현되어 있으니 적극 활용하자.

<br/>

```kotlin
val num1: Int? = 3
val num2: Long = num1?.toLong() ?: 0L
```
- 타입 변환 또한 마찬가지로, nullable인 경우에는 null 처리를 해줘야 한다.

<br/>

```java
/* Java code */
if (obj instanceof Person) { ... }
if (!(obj instanceof Person)) { ... }
```
```kotlin
/* Kotlin code */
if (obj is Person) { ... }
if (obj !is Person) { ... }
```
- Object 타입이 Person 타입인지 알고 싶다면 Java에서는 `instanceof` 키워드를 사용했다.
- Kotlin에서는 `is` 를 사용해서 타입 검증을 할 수 있다.
- 만약 Object 가 Person 타입이 아닌지 알고 싶다면 `!is` 로 작성하면 된다. 보다 간결해졌다.

<br/>

```java
/* Java code */
final Person person = (Person)obj;
```
```kotlin
/* Kotlin code */
val person = obj as Person
```
- Java에서 타입 캐스팅을 할 때는 괄호 안에 타입을 작성했어야 했다.
- Kotlin에서는 `as` 키워드를 사용해서 타입 캐스팅을 해준다.
  - 단, obj 가 Person 타입이 아니라면 ClassCaseException 이 발생한다.

<br/>

```kotlin
fun printAgeIfPerson(obj: Any?) {
    val person = obj as? Person
    println(person?.age)
}
```
- 만약 obj가 nullable 변수라면, `as?` 로 null 처리를 할 수 있다.
  - obj 에 실제 객체가 저장되어 있다면 정상적으로 `person.age` 를 출력한다.
  - obj 에 null이 들어있다면 person 변수에 null이 들어가게 되고, age에 접근하면 NPE가 발생한다.
- obj가 Person 타입이 아니라면 `as?` 가 아니라 `as` 일 때는 ClassCaseException이 발생했지만, `as?` 일 때는 null 이 반환된다.

<br/>

```kotlin
Any
```
- Java의 `Object` 역할을 Kotlin에서는 `Any`가 수행한다. 모든 객체의 최상위 타입이다.
- 모든 Primitive type의 최상위 타입도 `Any`이다. Primitive type과 Reference type을 구분하지 않고 모두 최상위에는 `Any`가 있다.
- `Any` 자체로는 null을 저장할 수 없기 때문에 null을 저장하고 싶다면 `Any?`로 표현해야 한다.
- Any에도 Object와 마찬가지로 `equals()`, `hashCode()`, `toString()`이 존재한다.

<br/>

```kotlin
Unit
```
- Java의 `void` 역할을 Kotlin에서는 `Unit`이 수행한다.
- `void`와 다르게 `Unit`은 그 자체로 타입 인자로 사용 가능하다.
  - Java에서는 제네릭 타입으로 `void`를 쓰고 싶다면 Wrapper class인 `Void` 를 사용해야 했다.
  - 하지만 Kotlin에서는 `Unit`을 그대로 사용해도 된다.

<br/>

```kotlin
Nothing
```
```kotlin
fun fail(message: String): Nothing {
  throw IllegalArgumentException(message)
}
```
- 함수가 정상적으로 끝나지 않았다는 사실을 표현하는 역할이다.
- 무조건 예외를 반환하는 함수 or 무한 루프 함수 등에는 반환 타입으로 `Nothing`을 사용하면 된다.

<br/>

```kotlin
val person = Person("황진성", 100)
val log = "이름: ${person.name} / 나이: ${person.age}"
```
- Java에서는 변수가 포함된 문자열을 만들 때 `String.format()` or `StringBuilder` 등을 사용해야 한다.
- Kotlin에서는 `${variable}` 방식으로 변수가 포함된 문자열을 만들 수 있다.
  - 단일 변수인 경우에는 중괄호를 생략할 수도 있지만, 가독성, 일괄변환, 정규식 활용 등 측면에서 좋기 때문에 중괄호를 생략하지 않는 것이 선호된다.
  - 하지만 팀 내 문화에 따라 규칙을 지키는 것이 더 중요하다.
- String interpolation 기능이라고 불린다.

<br/>

```kotlin
val str = """
        ABC
        123
        4567890
    """.trimIndent()
println(str.javaClass) // -> class java.lang.String
println(str.length) // -> 15
println(str.replace("\n", "#")) // -> ABC#123#4567890
```
- Java에서는 여러 줄 문자열을 만들기 위해서 `StringBuilder` 등을 사용해야 한다.
- Kotlin에서는 큰따옴표(") 3개를 연달아 쓰고 묶어주면 여러 줄 문자열을 만들 수 있다.
  - `trimIndent()` 메서드를 사용하면 불필요한 indent를 제거해준다.
- 줄 뒤에 자동으로 `\n`을 넣어준다.

<br/>

```java
/* Java code */
final String str = "ABCDE";
char ch = str.charAt(1);
```
```kotlin
/* Kotlin code */
val str = "ABCDE"
val ch = str[1]
```
- Java에서 String indexing을 하기 위해서는 `charAt()` 을 사용한다.
- Kotlin에서는 일반적인 Array 처럼 `[n]` 로 표기한다. ~드디어!~

<br/>

## A-4. 연산자 다루기

```java
/* Java code */
if (money1.compareTo(money2) > 0) { ... }
```
```kotlin
/* Kotlin code */
if (money1 > money2) { ... }
```
- Java에서 객체 간 비교를 하기 위해서 Comparable 인터페이스 상속 후 compareTo() 를 구현해서 비교가 가능하다.
  - 주어진 인자가 더 작으면 양수, 같으면 0, 더 크면 음수를 반환한다.
- Java 에서는 객체 간 비교를 위해서 `compareTo()` 를 명시적으로 호출해줘야 했지만, Kotlin에서는 부등호로 객체 비교 시 자동으로 `compareTo()` 를 호출해준다.

<br/>

```kotlin
val money1 = JavaMoney(1_000L)
val money2 = money1
val money3 = JavaMoney(1_000L)

println(money1 === money2) // true
println(money1 == money2) // true
println(money1 === money3) // false
println(money1 == money3) // true
```
- Java에서는 동일성 검증에서 `==` 을 사용하고, 동등성 검증에서 Object의 `equals()` 를 사용한다.
- Kotlin에서는 동일성 겅증에서 `===` 을 사용하고, 동등성 검증에서 `==` 를 사용한다.

<br/>

```kotlin
data class Money(
    val amount: Long
) {
    operator fun plus(other: Money): Money {
        return Money(this.amount + other.amount)
    }
}

fun main() {
    val money1 = Money(1_000L)
    val money2 = Money(2_000L)
    println(money1 + money2) // Money(amount=3000)
}
```
- Kotlin에서는 연산자 오버로딩이 가능하다.
- `+` 를 사용하면 자동으로 `plus()` 메서드 호출해준다.
- 연산자 별 지정된 메서드 이름이 궁금하다면 [문서](https://kotlinlang.org/docs/operator-overloading.html)에 자세히 나와있다.

<br/>

```kotlin
if (score !in 0..100) {
     throw IllegalArgumentException("${score}는 범위를 초과했습니다.")
}
```
- 범위를 표현할 때 `x..y` 을 사용 가능하다.
  - x와 y를 모두 포함하는 범위이다. 예시를 보면 0 이상 100 이하를 의미한다.
- `in`을 사용해서 해당 컬렉션 혹은 범위 내에 포함되는지 간단하게 확인 가능하다. `!`을 앞에 붙이면 `not in` 이 된다.

<br/>

# B. 코틀린에서 코드를 제어하는 방법

## B-1. 제어문 다루기

```kotlin
fun getPassOrFail(score: Int): String {
    return if (score >= 50) {
        "P"
    } else {
        "F"
    }
}
```
- Java에서 if-else는 Statement 지만, Kotlin에서는 Expression 이다.
  - Statement: 하나의 값으로 도출되지 않는 일반적인 프로그램의 문장 전체를 의미.
  - Expression: 하나의 값으로 도출되는 문장.
  - Statement 안에 Expression 이 포함되는 관계이다.
- 따라서 Java와 달리 Kotlin에서는 if-else 구문 전체를 하나의 값으로 사용 가능하다.

<br/>

```kotlin
fun getGradeWithSwitch(score: Int): String {
    return when (score) {
        in 90..100 -> "A"
        in 80..89 -> "B"
        in 70..79 -> "C"
        else -> "D"
    }
}
```
- Java의 switch-case 문의 구조가 변경됐다.
- Kotlin에서는 `when` 으로 작성한다.
  - `switch` 대신에 `when` 을 쓰고,
  - `case` 대신에 `조건 -> 반환값` 을 쓰고,
  - `default` 대신에 `else` 를 쓴다.

<br/>

```kotlin
fun judgeNumber(number: Int) {
    when {
        number == 0 -> println("주어진 숫자는 0입니다.")
        number % 2 == 0 -> println("주어진 숫자는 짝수입니다.")
        else -> println("주어진 숫자는 홀수입니다.")
    }
}
```
- when 뒤에 값을 생략할 수도 있다.
- 조건부에는 어떠한 Expression 이 들어갈 수 있다.
- when은 Enum class 혹은 Sealed class와 함께 사용하면 더욱 더 활용성이 높아진다. 뒤에서 알아보자.

<br/>

## B-2. 반복문 다루기

```kotlin
val numbers = listOf(1, 2, 3)
for (number in numbers) {
    println(number)
}
```
- for-each 문을 사용할 때 Java에서는 `:` 를 사용했다면, Kotlin에서는 `in` 을 사용한다.
- `in` 뒤에 있는 자료형은 `Iterable` 을 구현한 객체라면 뭐든 가능하다.

<br/>

```kotlin
// 1, 2, 3
for (i in 1..3) {
    println(i)
}

// 3, 2, 1
for (i in 3 downTo 1) {
     println(i)
}

// 1, 3, 5
for (i in 1..5 step 2) {
     println(i)
}

// 10, 7, 4, 1
for (i in 10 downTo 1 step 3) {
     println(i)
}
```
- `..`, `downTo`, `step` 등을 사용해서 다양한 반복문에서의 표현이 가능하다.
- `..` 은 `IntRange` 를 구현하는 방법 중 하나이다. `IntRange`는 `IntProgression`를 상속받는다.
  - `IntProgression` 의 의미는 정수 등차수열이다.
  - step의 기본 값은 1로 설정되어 있다.
  - 따라서 `1..3` 의 의미는 시작값=1, 끝값=3, 공차=1 인 등차수열을 만들어서 반환하라는 의미를 가진다.
  - `3 downTo 1` 의 의미는 시작값=3, 끝값=1, 공차=-1 인 등차수열을 만들어서 반환하라는 의미를 가진다.
  - 단, 공차가 -1 이라고 해서 `step`에 음수를 넣을 수 있는건 아니다. 공차보다는 말 그대로 간격이라는 의미로 생각하고 사용하면 된다.
- 여기서 활용한 `downTo`, `step` 또한 함수이다. 중위 호출 함수라고 불리는 기능이다. 함수 호출 방법만 다르게 한 것이다.
  - 일반적으로 `변수.함수이름(args)` 구조이지만, `변수 함수이름 args` 로 작성하는 것도 가능하게 한 것이다.

<br/>

## B-3. 예외 다루기

```kotlin
fun parseInt(str: String): Int? {
    return try {
        str.toInt()
    } catch (e: NumberFormatException) { 
        null
    }
}
```
- try-catch-finally 구문은 Java와 Kotlin 모두 동일하다.
- Kotlin 에서는 try-catch-finally 역시 Expression 으로 취급되기 때문에 그대로 return 하는 것이 가능하다.

<br/>

```kotlin
fun readFile() {
    val currentFile = File(".")
    val file = File(currentFile.absolutePath + "/a.txt")
    val reader = BufferedReader(FileReader(file))
    for (line in reader.lines()) {
        println(line)
    }
    reader.close()
}
```
- Java에서 파일 읽기 코드를 작성하게 되면 `IOException` 이 발생할 여지가 생긴다.
  - `IOException`는 Checked Exception 이기 때문에 메서드 레벨에 `throws IOException` 이라고 명시를 해줘야만 한다.
- Kotlin 에서는 Checked Exception과 Unchecked Exception을 따로 구분하지 않는다. 따라서 `throws` 구문을 작성하지 않아도 된다.
  - 꼭 구분하고자 한다면 Kotlin의 모든 예외는 Unchecked Exception 이라고 표현할 수 있다. 

<br/>

```java
/* Java code */
public void readFile(String path) throws IOException {
      try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            reader.lines().forEach(System.out::println);
      }
}
```
```kotlin
/* Kotlin code */
fun readFile(path: String) {
    BufferedReader(FileReader(path)).use { reader ->
        for (line in reader.lines()) {
            println(line)
        }
    }
}
```
- Java에서는 try-with-resources 구문으로 `Closeable` 객체를 안전하게 다뤘었다.
- Kotlin에서는 try-with-resources 구문이 사라지고, `Closeable`의 확장함수 `use()` 를 활용한다.

<br/>

## B-4. 함수 다루기

```kotlin
// Step 1: 코틀린스러운 함수
fun max(a: Int, b: Int): Int {
    return if (a > b) {
        a
    } else {
        b
    }
}

// Step 2: `=` 으로 수정하기
fun max(a: Int, b: Int): Int = if (a > b) a else b

// Step 3: Return type 생략하기
fun max(a: Int, b: Int) = if (a > b) a else b
```
- Step 1: 가장 일반적인 형태의 함수이다. return 을 하나로 모아서 표현했다.
- Step 2: 함수의 결과 값이 하나로 결정되어 있기 때문에 중괄호 블럭 대신 `=` 로 함수를 표현할 수 있다.
  - 더불어 한줄로 정리하고, 불필요한 중괄호를 제거할 수도 있다.
- Step 3: 반환하는 값 모두 a, b 둘 중 하나이기 때문에 Return type이 Int 라는 것을 생략할 수도 있다.
  - 단, 중괄호 블럭을 사용하는 경우에는 Unit(void) 타입이 아니라면 생략이 불가능하다. 명시적으로 Return type을 작성해야 한다.

<br/>

```kotlin
fun repeat(
    str: String,
    num: Int = 3,
    useNewLine: Boolean = true,
) {
    for (i in 1..num) {
        if (useNewLine) {
            println(str)
        } else {
            print(str)
        }
    }
}
```
- Java 에서는 함수에 기본 값을 주고 싶다면 기본 값이 포함된 함수 오버로딩을 했어야 했다.
- Kotlin 에서는 Default parameter 기능을 제공해준다.
- 마지막 파라미터 뒤에 `,` 를 추가해도 잘 동작한다.

<br/>

```kotlin
fun printNameAndGender(name: String, gender: String) {
    println("이름 = ${name}")
    println("성별 = ${gender}")
}

fun main() {
    printNameAndGender(
        name = "jinseong",
        gender = "male",
    )
}
```
- Java에서는 파라미터에 값을 넣어줄 때 어디에 어떤 값을 넣는지 명시적으로 표현하고 싶다면 빌더 패턴을 사용했어야 했다.
  - 주로 Lombok의 @Builder 를 쓰곤 했다.
- Kotlin에서는 Named Parameter 기능을 제공해준다. `파라미터명 = 값` 형태로 함수 호출이 가능하다.
  - 단, Java 코드의 메서드를 가져다 쓸 때는 이 기능이 동작하지 않는다.
  - 바이트코드에서 파라미터 이름을 그대로 보존하지 않기 때문이다.

<br/>

```kotlin
fun printAll(vararg strings: String) {
    for (str in strings) {
        println(str)
    }
}

fun main() {
    printAll("A", "B", "C")
    val array = arrayOf("D", "E", "F")
    printAll(*array) // Spread 연산자
}
```
- Java 에서는 가변 인자를 사용하기 위해 `...` 을 사용했었다.
- Kotlin 에서는 파라미터 앞에 `vararg` 를 사용해서 조금 더 명시적으로 가변인자 임을 표현할 수 있다.
- 호출하는 쪽에서는 Java 와 동일하게 `,` 를 사용하면 된다.
  - 단, 배열을 넣기 위해서는 마치 `,` 로 연결된 것 처럼 보이게 하는 Spread 연산자(`*`)를 사용해야 한다.

<br/>

# C. 코틀린에서 OOP

## C-1. 클래스 다루기

```kotlin
// Step 1: 기본적인 클래스 구조
class Person constructor(name: String, age: Int) {
    val name: String = name
    var age: Int = age
}

// Step 2: constructor 생략
class Person(name: String, age: Int) {
    val name: String = name
    var age: Int = age
}

// Step 3: 필드 선언 및 대입 생략
class Person(
    val name: String,
    var age: Int
)
```
- 3개 모두 동일한 역할을 수행하는 클래스이다.
- getter와 setter 모두 기본적으로 제공해준다.
  - `val`은 `final` 필드이기 때문에 setter 가 생성되지 않는다.
- `객체.필드` 형식으로 getter와 setter를 호출할 수 있다.
  - 이 방법은 Java 코드를 가져와서 쓸 때도 동일하게 사용 가능하다.

<br/>

```kotlin
class Person(
    val name: String,
    var age: Int,
) {
    init {
        if (age <= 0) {
            throw IllegalArgumentException("나이는 ${age} 일 수 없습니다.")
        }
    }
}
```
- 객체 생성 시점에 어떠한 로직을 수행하고 싶다면 init{} 블럭을 사용할 수 있다.
  - 생성자가 호출되는 시점에 1회만 실행된다.
- 값을 적절히 만들어 주거나, validation 역할로 사용할 수 있다.

<br/>

```kotlin
class Person(
    val name: String,
    var age: Int,
) {
    // 부생성자1
    constructor(name: String): this(name, 1)
  
    // 부생성자2
    constructor() : this("진송") {
      println("부생성자2 호출 -> 부생성자1 호출 -> 주생성자 호출")
    }
}
```
- 나이가 1로 설정되는 생성자를 하나 더 만들고 싶다면, 클래스 블럭 내에서 `constructor` 로 만들 수 있다.
  - 반환 타입에 `this()` 를 사용해서 작성하면 된다.
- 클래스 이름 뒤에 오는 생성자를 주생성자(Primary constructor)라고 하고, 아래에 constructor 키워드로 만든 생성자를 부생성자(Secondary constructor)라고 한다.
  - 주생성자는 반드시 있어야 한다. 단, 파라미터가 하나도 존재하지 않는 경우에는 생략이 가능하다.
  - 부생성자는 있어도 되고 없어도 된다. 있으려면 반드시 최종적으로 주생성자를 호출해야 한다.

<br/>

```kotlin
class Person(
    val name: String = "진송",
    var age: Int = 1,
) {
    init {
        if (age <= 0) {
            throw IllegalArgumentException("나이는 ${age} 일 수 없습니다.")
        }
    }
}
```
- 부생성자를 사용하는 것은 복잡하기 때문에 권장되지 않는다.
- Kotlin에서는 기본적으로 Default Parameter를 사용하는 것을 권장한다.
- 불가피하게 부생성자를 사용해야 한다면, 정적 팩터리 메서드로 해결하자.

<br/>

```kotlin
class Person(
    val name: String = "진송",
    var age: Int = 1,
) {
    fun isAdultFun(): Boolean {
        return this.age >= 20
    }

    val isAdultProp1: Boolean
        get() = this.age >= 20

    var myAge: Int
        get() {
            return this.age
        }
        set(value) {
            this.age += value * 10
        }
}

fun main() {
    val person = Person("진성", 100)
    println(person.isAdultFun()) // false
    println(person.isAdultProp1) // false

    println(person.myAge) // 100
    person.myAge = 3 // set: this.age += 3 * 10
    println(person.myAge) // 130
}
```
```java
/* De-complied Java Code */
public final boolean isAdultFun() {
    return this.age >= 20;
}

public final boolean isAdultProp1() {
    return this.age >= 20;
}

public final int getMyAge() {
    return this.age;
}

public final void setMyAge(int value) {
    this.age += value * 10;
}
```
- 20살 이상이면 성인으로 판단하는 `isAdult` 메서드를 2가지 방식으로 만들어보자.
  - 첫 번째 방식인 `isAdultFun()` 은 함수 형태로 만든 것이고, `isAdultProp` 은 프로퍼티 형태로 만든 것이다.
  - `isAdultFun()` 은 함수와 같으므로 `person.isAdultFun()` 처럼 호출해야 한다.
  - `isAdultProp` 은 프로퍼티와 같으므로 `person.isAdultProp` 처럼 호출해야 한다.
  - Decompile 된 Java 코드를 보면 생성된 메서드는 서로 동일한 것으로 확인된다. Kotlin에서 제공해주는 Syntax sugar라고 생각하자.
- 내 맘대로 age를 `get()`, `set()` 하는 메서드를 프로퍼티 형태로 만들어보자.
  - 프로퍼티 이름은 `myAge` 로 설정했다.
  - `get()` 을 호출하면 곧바로 현재 age 값을 반환하도록 했다.
  - `set(value)` 을 호출하면 value 에 10을 곱해서 age에 대입하도록 했다.
  - Decompile 된 Java 코드를 보면 getter와 setter를 만들어 준 것을 확인할 수 있다.
- 언제 함수 형태로 써야 하고, 프로퍼티 형태로 써야 할까?
  - 어떤 객체 인스턴스의 프로퍼티 처럼 접근해야 하는 명확한 형태라면 프로퍼티 형태로 작성하면 되고,
    - 예를 들어 기존 프로퍼티를 활용해서 새로운 프로퍼티를 만들어야 하는 경우 (age를 활용해서 10대인지 판별하는 `person.isTeenager`)
  - 그 외의 경우 함수 형태로 작성하면 된다.
    - 예를 들어 특정 행위, 동작 등을 정의해야 하는 경우

<br/>

```kotlin
class Person2(
    name: String = "jinseong",
    var age: Int = 1,
) {
    val name = name
        get() = field.uppercase()
}

fun main() {
    val person = Person2()
    println(person.name)
}
```
- Custom getter를 만들어서 사용하는 방법에 대해 알아보자.
- 클래스의 생성자에서 프로퍼티 정의하는 `val` 을 빼고 아래에서 따로 정의해줬다. 그 아래에서 getter를 정의해보자.
  - 1차원적으로 생각해보면 `get() = this.name.uppercase()` 를 하면 동작할 것만 같다.
  - 하지만 동작하지 않는다. 컴파일 에러가 발생한다.
  - `this.name` 자체가 getter 를 호출하게 되는데, `get()` 을 타고 들어가면 다시 `this.name` 을 호출하기 때문에 순환 참조가 발생한다.
  - 따라서 Kotlin에서는 `field` 라는 예약어를 제공해준다. Backing field 라고 불린다.

<br/>

```kotlin
class Person3(
    val name: String = "jinseong",
    var age: Int = 1,
) {
    val uppercaseName: String
        get() = this.name.uppercase()
}

fun main() {
    val person = Person3()
    println(person.uppercaseName)
}
```
- Backing field 를 사용하는 방식 또한 좋지만, 실제로는 잘 사용되지 않는다고 한다.
- 새로운 프로퍼티를 만드는 식으로 정의하면 더 간단하고 사용하기 쉽다.

<br/>

## C-2. 상속 다루기

```kotlin
abstract class Animal(
    protected val species: String,
    protected val legCount: Int,
) {
    abstract fun move()
}
```
- [Java code](https://github.com/JinseongHwang/kotlin-playground/blob/main/java-to-kotlin/src/main/kotlin/lec10/JavaAnimal.java) 와 큰 차이가 아직은 보이지 않는다.

<br/>

```kotlin
class Cat(
    species: String
) : Animal(species, 4) {
    override fun move() {
        println("고양이가 사뿐 사뿐 걸어가~")
    }
}
```
- [Java code](https://github.com/JinseongHwang/kotlin-playground/blob/main/java-to-kotlin/src/main/kotlin/lec10/JavaCat.java) 와 다른 점에 대해 살펴보자.
- 상속을 받을 때는 `extends` 가 아니라 ` : ` 을 사용한다.
  - 타입을 명시할 땐 앞에 띄어쓰기가 없는 `: ` 이었는데, 상속받을 때는 앞에 띄어쓰기가 있는 ` : ` 이 기본 컨벤션이다.
  - 앞에 띄어쓰기를 하지 않는다고 해서 에러가 발생하지는 않는다. 그냥 컨벤션이니 지키도록 하자.
- 상속 받음과 동시에 상위 클래스의 생성자를 호출해야 한다.
- `@Override` 어노테이션 대신에 `override` 라는 키워드를 명시적으로 사용한다.

<br/>

```kotlin
abstract class Animal(
    protected val species: String,
    protected open val legCount: Int,
) {
    abstract fun move()
}

class Penguin(
    species: String
) : Animal(species, 2) {

    private val wingCount: Int = 2

    override fun move() {
        println("펭귄이 움직인다~ 꽥꽥")
    }

    override val legCount: Int
        get() = super.legCount + this.wingCount
}
```
- [Java code](https://github.com/JinseongHwang/kotlin-playground/blob/main/java-to-kotlin/src/main/kotlin/lec10/JavaPenguin.java) 와 다른 점에 대해 살펴보자.
- abstract 프로퍼티가 아니라면 오버라이딩 할 때 `open` 을 붙여줘야 한다. 기본적으로 모든 클래스, 프로퍼티, 함수에 `final` 이 붙어있기 때문이다.
  - `override`와 custom getter를 활용해서 getter를 오버라이딩 할 수 있다.
- 상위 클래스에 접근하기 위해서 `super` 키워드를 사용하는 것은 Java 와 동일하다.
- Java, Kotlin 모두 abstract class 는 인스턴스화 할 수 없다.

<br/>

```java
/* Java Code */
public interface Flyable {
    default void act() {
        System.out.println("파닥 파닥");
    }
    
    void run();
}

```
```kotlin
/* Kotlin Code */
interface Flyable {
    fun act() {
        println("파닥 파닥")
    }
    fun run()
}
```
- 인터페이스 구현은 거의 비슷하다.
  - 접근지정자는 기본이 Public 이라서 생략 가능하다.
  - 인터페이스 내 디폴트 메서드는 별도 지정자 없이 구현하면 된다.
  - 추상 메서드는 구현하지 않으면 된다.

<br/>

```kotlin
class Penguin(
    species: String
) : Animal(species, 2), Swimable, Flyable {

    private val wingCount: Int = 2

    override fun move() {
        println("펭귄이 움직인다~ 꽥꽥")
    }

    override val legCount: Int
        get() = super.legCount + this.wingCount

    override fun act() {
        super<Swimable>.act()
        super<Flyable>.act()
    }
}
```
- Swimable, Flyable 두 인터페이스를 구현하는 Penguin 클래스를 살펴보자.
- 인터페이스 구현 또한 `implements` 키워드 대신 ` : ` 을 사용하며, 여러 개를 상속받을 경우에 `,` 로 연결한다.
- Swimable, Flyable 둘 다 `act()` 라는 디폴트 메서드를 가지고 있는데, `override` 키워드와 `super<TYPE>.act()` 등을 통해 자유롭게 사용 가능하다.
- Java, Kotlin 모두 interface 는 인스턴스화 할 수 없다.

<br/>

```kotlin
interface Swimable {
    val swimAbility: Int
    fun act() = println("어푸 어푸 내 수영 능력치는 ${swimAbility}야!")
}

class Penguin(
  species: String
) : Animal(species, 2), Swimable, Flyable {
    // ...
    override val swimAbility: Int
        get() = 5
}
```
- Kotlin 에서는 Backing field 없는 프로퍼티를 인터페이스에 생성이 가능하다.
- Swimable 인터페이스에서 상속 받는 클래스에서 swimAbility 의 getter에 대해 구현할 것이라고 믿고 사용하는 것이 가능하다.
  - 인터페이스에서 getter 에 대해 `get() = 5` 와 같은 식으로 디폴트 메서드를 만들어줄 수도 있다.

<br/>

```kotlin
open class Base(
    open val number: Int = 100
) {
    init {
        println("Base Class")
        println(number)
    }
}

class Derived(
    override val number: Int
): Base(number) {
    init {
        println("Derived Class")
        println(number)
    }
}

fun main() {
    val obj = Derived(3)
}
```
```text
Base Class
0
Derived Class
3
```
- 상위 클래스를 설계할 때 생성자 또는 초기화 블럭에서 사용되는 프로퍼티에서는 `open` 을 피해야 한다.
- 출력 결과에서 100도, 3도 아닌 0이 나온 이유는 다음과 같다.
  1. Derived 인스턴스를 생성하기 전, 상위 클래스인 Base의 인스턴스화를 먼저 시도한다.
  2. Base 의 초기화 블럭을 실행한다.
  3. number에 접근하고자 하는데, number는 하위 클래스인 Derived 에서 오버라이딩 예정이다.
  4. 따라서 Base의 number 에 접근하지 못하고 Derived의 number에 접근해야 하는데 Derived는 아직 인스턴스화 되지 않았다.
  5. Int의 기본 값인 0 이 출력되게 된다.
  6. 추후 Derived 에서 인스턴스화가 끝나면 비로소 3이 출력된다.

> 정리해보자면,  
> `final`: 기본적으로 모든 클래스, 프로퍼티, 함수에 붙어있다. 즉, 상속 또는 오버라이딩을 할 수 없다.  
> `open`: 상속, 오버라이딩을 하고 싶다면 open 을 명시적으로 붙여줘야 한다.  
> `abstract`: 반드시 오버라이드를 해야 한다는 의미를 가진다.  
> `override`: 상위 클래스의 프로퍼티, 메서드 등을 오버라이딩 한다는 의미이다. 어노테이션이 아니라 예약어이다.  

<br/>

## C-3. 접근 제어 다루기

```kotlin

```

<br/>

```kotlin

```

<br/>

```kotlin

```

<br/>

```kotlin

```

<br/>

```kotlin

```

<br/>

```kotlin

```

<br/>

```kotlin

```

<br/>

```kotlin

```

<br/>

```kotlin

```

<br/>

```kotlin

```

<br/>

## C-4. object 키워드 다루기
## C-5. 중첩 클래스 다루기
## C-6. 다양한 클래스 다루기