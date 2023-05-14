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

| Java 표기 | Java 설명                | Kotlin 표기 | Kotlin 설명        |
|-----------|------------------------|-----------|------------------|
| public    | All opened             | public    | All opened       |
| protected | 같은 패키지 + 하위 클래스        | protected | 선언된 클래스 + 하위 클래스 |
| default   | 같은 패키지                 | internal  | 같은 모듈 내          |
| private   | 선언된 클래스 내 | private   | 선언된 클래스 내        |

- Java와 비슷하게 Kotlin에서도 접근 제어자(Visibility Modifier)를 제공해주고 있다. 차이점 위주로 살펴보자.
- `protected` 가 Java에서는 같은 패키지 내에서만 접근 가능했었는데, Kotlin에서는 패키지 내 접근이 불가능하게 변경됐다.
  - Kotlin에서는 같은 패키지니까 접근 허용하게 해준다는 개념을 받아들이지 않은 것 같다.
- `default` 역시 같은 패키지니까 접근을 허용해줬는데, 패키지 대신 모듈이라는 개념을 접근 제어자에 덧붙이며 `internal` 이라고 표기를 변경했다.
  - 여기서 말하는 모듈은 IDEA Module, Gradle source module 등을 의미한다.
- `public`과 `private`은 Java와 Kotlin 동일하다.
- Java에서는 접근 제어자를 붙여주지 않으면 `default` 였는데, Kotlin에서는 `public`이 기본이다.
- Kotlin의 `interanl`은 바이트코드 상 `public`이 된다. 때문에 Java 코드에서 Kotlin 모듈의 `internal`에 접근이 가능하다.
- Java는 같은 패키지의 Kotlin `protected`에 접근이 가능하다.

<br/>

```kotlin
open class FooKotlin protected constructor(
    val age: Int,
    val name: String,
)
```
- 단, 예외로 생성자에 접근 제어자를 붙여주기 위해서는 `constructor`를 명시적으로 작성해야 한다.
  - 접근 제어자가 `public`이 아니라 `protected`로 바꾸는 이유는 상속 받은 클래스에서 사용하기 위함이므로 `open` 또한 붙여준다.

<br/>

```java
/* Java Code */
public abstract class StringUtils {
    private StringUtils() {
    }

    public boolean isDirectoryPath(String path) {
        return path.endsWith("/");
    }
}
```
```kotlin
/* Kotlin Code */
fun isDirectoryPath(path: String): Boolean {
    return path.endsWith("/")
}
```
- Java에서는 유틸성 클래스를 인스턴스화 하지 못하게 하려고 private 기본 생성자를 만들어주곤 했다.
- Kotlin에서는 클래스 없이 파일 수준에서 메서드를 만들어서 사용하면 컴파일 후 static method 처럼 사용할 수 있다.

<br/>

```kotlin
class Car(
    internal val name: String,
    private var owner: String,
    _price: Int,
) {
    var price = _price
        private set
}
```
- `internal val name: String` : name의 getter가 `internal` 수준으로 만들어진다.
  - `val` 이기 때문에 setter는 만들어지지 않는다.
  - Java 로 Decompile 해보면 `public`으로 나온다. 이는 Java에서 `internal` 이라는 접근 제어자가 없고, `public`으로 대체되기 때문이다.
- `private var owner: String` : owner는 `private` 이기 때문에 getter와 setter가 만들어지지 않는다.
- `_price: Int` : price 에 값 할당은 아래에서 다시 진행된다. 
  - 프로퍼티 정의에 `var price` 만 있기 때문에 public getter와 setter가 생성된다.
  - 하지만 `private set` 으로 setter만 `private`으로 변경된다. 따라서 setter는 생성되지 않는다.

<br/>

## C-4. object 키워드 다루기

```java
/* Java Code */
public class JavaPerson {
    private static final int MIN_AGE = 1;

    public static JavaPerson newBaby(String name) {
        return new JavaPerson(name, MIN_AGE);
    }

    private String name;
    private int age;

    private JavaPerson(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```
```kotlin
/* Kotlin Code */
class Person private constructor(
    private val name: String,
    private val age: Int,
) {
    companion object {
        private const val MIN_AGE: Int = 1
        fun newBaby(name: String): Person {
            return Person(name, MIN_AGE)
        }
    }
}

fun main() {
    val person1 = Person.newBaby("js")
    val person2 = Person.Companion.newBaby("js")
}
```
- Java 에서는 `static`이 제공됐지만, Kotlin에서는 제공되지 않는다. 대신에 `companion object` 기능이 제공된다.
  - `static`: 클래스가 인스턴스화 될 때 새로운 값이 복제되는게 아니라 정적으로 인스턴스끼리의 값을 공유한다는 의미이다.
  - `companion object`: 클래스와 동행하는 유일한 오브젝트이다.
- `val MIN_AGE = 1` 대신에 `const val MIN_AGE = 1` 을 사용한 이유는,
  - `const` 를 사용하지 않으면 MIN_AGE에 1이 런타임에 할당된다.
  - `const` 를 사용하면 MIN_AGE에 1이 컴파일타임에 할당된다.
- 

<br/>

```kotlin
interface Log {
    fun log()
}

class Person2 private constructor(
    private val name: String,
    private val age: Int,
) {
    companion object Factory : Log {
        private const val MIN_AGE: Int = 1
        
        fun newBaby(name: String): Person2 {
            return Person2(name, MIN_AGE)
        }

        override fun log() {
            println("나는 Person2 클래스의 companion object 입니다~")
        }
    }
}

fun main() {
    val person1 = Person.newBaby("js")
    val person2 = Person.Factory.newBaby("js")
}
```
- Java와 달리, `companion object` 또한 하나의 객체로 간주된다.
  - 때문에 이름도 붙일 수 있고, interface 받아서 구현할 수도 있다.
  - 위 예시는 Log 라는 인터페이스를 상속받아서 구현한 것이다.
- companion object의 이름을 Factory로 설정했다.
  - 이 경우에는 `log()` 를 반드시 구현해야 한다.

<br/>

```java
/* Java Code */
public static void main(String[] args) {
    Person.Companion.newBaby("ABC"); // without @JvmStatic
    Person.newBaby("ABC"); // with @JvmStatic
}
```
```kotlin
/* Kotlin Code */
class Person private constructor(
    private val name: String,
    private val age: Int,
) {
    companion object {
        private const val MIN_AGE: Int = 1

        @JvmStatic
        fun newBaby(name: String): Person {
            return Person(name, MIN_AGE)
        }
    }
}
```
- Java 코드에서 Kotlin의 `companion object` 내부 프로퍼티에 `static` 처럼 `클래스.프로퍼티` 식으로 접근하고자 한다면 Kotlin 코드 쪽에 `@JvmStatic` 어노테이션을 붙여줘야 한다.

<br/>

```kotlin
object Singleton {
    var a: Int = 0
}

fun main() {
    println(Singleton.a) // 0
    Singleton.a = 10
    println(Singleton.a) // 10
}
```
- Java 에서는 싱글턴을 만들기 위해 여러 기법이 있었다.
  - 상속 받을 일이 없다면 enum class 를 활용해서 싱글턴을 만드는 것이 최선의 방법이있다.
- Kotlin 에서는 단순히 class가 아닌 object 로 클래스를 만들면 해당 객체 자체가 싱글턴이 된다.

<br/>

```java
public interface Movable {
    void move();
    void fly();
}
```
```kotlin
private fun moveSomething(movable: Movable) {
    movable.move()
    movable.fly()
}

fun main() {
    moveSomething(object : Movable {
        override fun move() {
            println("무빙 슈슈슉")
        }
        override fun fly() {
            println("플라잉 파닥파닥")
        }
    })
}
```
- Java 였다면 `new Movable() {...}` 처럼 구현했을텐데, Kotlin 에서는 `object : Movable` 처럼 구현한다.

<br/>

## C-5. 중첩 클래스 다루기

```java
/* Java Code */
public class JavaHouse {
    private String address;
    // ...

    public class LivingRoom {
        // ...
        public String getAddress() {
            return JavaHouse.this.address;
        }
    }
}
```
- 잠깐 Java 얘기를 해보자.
- Java에서는 중첩 클래스 구조를 사용할 경우, 내부 클래스에서 `JavaHouse.this.address` 로 직접 접근이 가능하다.
  - 이는 내부 클래스인 LivingRoom 에서 외부 클래스인 JavaHouse 를 눈에 보이지 않게 참조하고 있다.
    - Effective Java 3/E 의 Item 24, 86에 보면,
      1. 내부 클래스는 숨겨진 외부 클래스 정보를 가지고 있어, 참조를 해지하지 못하는 경우 메모리 누수가 발생할 수 있고, 이를 디버깅 하기 어렵다.
      2. 내부 클래스의 직렬화 형태가 명확하게 정의되지 않아 직렬화에 있어 제한이 있다.
    - 따라서 클래스 안에 클래스를 만들 때는 static 클래스를 사용해야 한다고 조언하고 있다.

<br/>

```kotlin
class House(
    private val address: String,
    private val livingRoom: LivingRoom
) {
    class LivingRoom(
        private val area: Double
    )
}
```
- Kotlin 에서 권장하는 중첩 클래스의 모습은 그냥 `class` 를 사용하는 것이다.
  - 기본적으로 외부 클래스에 대한 연결이 없는 내부 클래스가 중첩으로 만들어진다.

<br/>

```kotlin
class House2(
    private val address: String,
    private val livingRoom: LivingRoom
) {
    inner class LivingRoom(
        private val area: Double
    ) {
        val address: String
            get() = this@House2.address
    }
}
```
- 바깥 클래스에 대한 참조를 명시적으로 가지고 있는 클래스를 만들기 위해서는 `inner class` 라고 명시적으로 알려줘야 한다.
- 바깥 클래스의 프로퍼티에 참조하고 싶으면, `this@타입.프로퍼티` 형태로 참조할 수 있다.
- 마찬가지로 권장되지 않는다.

<br/>

## C-6. 다양한 클래스 다루기

```kotlin
data class PersonDto(
    val name: String,
    val age: Int,
)
```
- Java에서 DTO를 만들면 필드 뿐만 아니라 부가적으로 생성자, getter, `equals()`, `hashCode()`, `toString()` 등을 함깨 구현해야 한다.
  - Lombok의 도움을 받거나 IDE의 도움을 받아서 처리할 수도 있지만, 불편한 것임에는 틀림 없다.
- Kotlin에서는 `data class` 를 사용하면 부가적인 코드 없이 위 필요한 모든 것들이 자동으로 생성된다.
  - 위 예시 코드는 장황한 [Java 코드](https://github.com/JinseongHwang/kotlin-playground/blob/main/java-to-kotlin/src/main/kotlin/lec14/JavaPersonDto.java) 와 완전 동일하다.
- `data class` 에 named parameter 기능까지 사용한다면 builder, equals, hashCode, toString 모두 생성된 것 처럼 누릴 수 있다.

<br/>

```kotlin
enum class Country(
    private val code: String
) {
    KOREA("KO"),
    AMERICA("US"),
    ;
}
```
- `enum class` 구현 방법은 Java와 거의 동일하다.

<br/>

```java
/* Java Code */
private static void handleCountry(JavaCountry country) {
    if (country == JavaCountry.KOREA) {
        // 로직 처리
    }
    if (country == JavaCountry.AMERICA) {
        // 로직 처리
    }
}
```
```kotlin
/* Kotlin Code */
fun handleCountry(country: Country) {
    when (country) {
        Country.KOREA -> TODO()
        Country.AMERICA -> TODO()
    }
}
```
- `enum class`의 필드에 따라 분기하는 로직이 있다고 가정하자.
- Country 라는 `enum class` 에 KOREA, AMERICA 이외의 다른 필드가 생긴다면, `when` 구문에서 컴파일 에러가 발생한다.
- `enum class` 와 `when` 을 조합하면 Java에서는 잡아내지 못하는 오류를 컴파일 타임에 잡아낼 수 있다.

<br/>

```kotlin
sealed class HyundaiCar(
    val name: String,
    val price: Long,
)

class Avante : HyundaiCar("아반떼", 1_000L)
class Sonata : HyundaiCar("소나타", 2_000L)
class Grandeur : HyundaiCar("그랜져", 3_000L)
```
- 봉인된 클래스 라는 기능을 가진 `sealed class`가 Kotlin에 추가되었다. (`sealed interface`도 있다!)
- 상속이 가능하도록 추상 클래스를 만들고 싶은데, 외부에서는 이 클래스를 상속받지 못하게 할 때 `sealed class`를 사용한다.
- 3가지 특징이 있다. 
  1. 컴파일 타임에 하위 클래스의 타입을 모두 기억한다. 즉, 런타임에 클래스 타입이 추가될 수 없다.
  2. 하위 클래스는 같은 패키지 안에 있어야 한다. 
  3. `enum class`와 달리, 클래스를 상속받을 수 있고, 하위 클래스는 멀티 인스턴스가 가능하다.

<br/>

```kotlin
fun handleCar(car: HyundaiCar) {
    when (car) {
        is Avante -> TODO()
        is Grandeur -> TODO()
        is Sonata -> TODO()
    }
}
```
- `sealed class` 역시 `enum class` 처럼 컴파일 타임에 타입을 모두 알고 있다.
- 따라서 컴파일 타임에 특정한 하위 클래스가 빠졌다면, 컴파일 에러를 발생시키며 개발자의 실수를 줄일 수 있다.
  - 예를 들어 위 코드에서 `is Sonata -> TODO()` 를 추가했다가 실수로 작성하지 않았다면, 컴파일 에러가 발생한다.

<br/>

# D. 코틀린에서 FP

## D-1. 배열 다루기

```java
/* Java Code */
int[] array = {100, 200};
for (int i = 0; i < array.length; i++) {
    System.out.printf("%s %s\n", i, array[i]);
}
```
```kotlin
val array = arrayOf(100, 200)
for (i in array.indices) {
    println("${i} ${array[i]}")
}
```
- `arrayOf` 를 사용해서 배열을 생성할 수 있다.
- `array.indices` 를 사용하면 배열의 index range를 반환한다.
  - `0..1` 값을 가지는 IntRange 객체를 반환한다.

<br/>

```kotlin
val array = arrayOf(100, 200)
for ((idx, value) in array.withIndex()) {
    println("${idx} ${value}")
}
```
- 배열을 순회하며 index와 value를 한번에 꺼내올 수 있는 방법이 있다.
- `array.withIndex()` 를 쓰면 되는데, `IndexingIterable` 객체를 반환한다.

<br/>

## D-2. 컬렉션 다루기

- Kotlin에서는 Collection을 선언하기 전 가변(Mutable)인지 불변(Immutable)인지 먼저 결정해야 한다.
  - Mutable collection : collection에 element를 추가,삭제 할 수 있다.
  - Immutable collection : collection에 element를 추가,삭제 할 수 없다.
- 기본적으로 `List`, `Set`, `Map` 을 만들어서 사용하면 모두 Immutable collection이다.
  - Mutable collection을 사용하려면 `MutableList`, `MutableSet`, `MutableMap` 을 사용해야 한다.
  - 일반적으로 Immutable collection을 사용하도록 하고, 꼭 필요한 경우에만 Mutable collection을 사용하도록 하자.  
- Java 에서는 Mutable과 Immutable을 구분하지 않는다. 따라서 고려해야 할 점은,
  - Kotlin에서 Immutable collection으로 작성된 자료구조가 Java 코드 쪽에 가서 수정될 수도 있다.
  - Immutable 임에도 Java 코드와 함께 쓰인다면 수정될 가능성을 열어두고 개발하자.
- Java 에서는 nullable과 non-nullable을 구분하지 않는다. 따라서 고려해야 할 점은,
  - Kotlin에서 non-nullable로 작성된 자료구조가 Java 코드 쪽에 가서 null이 들어갈 수도 있다.
  - non-nullable 임에도 Java 코드와 함께 쓰인다면 null이 저장될 가능성을 열어두고 개발하자.
- Kotlin 쪽의 컬렉션이 Java에서 호출되면 컬렉션 내용이 변할 수 있음을 감안해야 한다.
  - Kotlin 쪽에서 Java 코드와 주고받을 일이 있다면 `Collections.unmodifiableXXX()` 를 활용하면 변경 자체를 막을 수 있다.
  - 그리고 플랫폼 타입과 마찬가지로 Java와 주고받는 지점을 단 하나로 만들어서 오류 발생 시 영향 범위를 최소화 하는 것이 좋다.

```kotlin
val numbers = listOf(100, 200) // Int 라고 적지 않아도 추론 가능하기 때문에 생략함.
val emptyList = emptyList<Int>() // 어떤 값이 들어갈지 모르기 때문에 타입 명시 해줘야 함.
val mutableNumbers = mutableListOf(300, 400) // Mutable List 생성
mutableNumbers.add(500) // mutable 이기 때문에 add 가능

for (number in numbers) {
    println(number)
}

for ((idx, value) in numbers.withIndex()) {
    println("$idx : $value")
}
```
- `listOf<>()`, `mutableListOf<>()` 를 사용해서 List를 생성할 수 있다.
- Mutable List에서만 `add()` 등의 연산이 가능하다.
- 순회 하는 방법은 배열과 동일하다.

<br/>

```kotlin
val numbers = setOf(100, 200)
val mutableNumbers = mutableSetOf(300, 400) // LinkedHashSet

for (number in numbers) {
    println(number)
}

for ((idx, number) in numbers.withIndex()) {
    println("$idx : $number")
}
```
- `setOf<>()`, `mutableSetOf<>()` 를 사용해서 Set을 생성할 수 있다.

<br/>

```kotlin
val oldMap = mutableMapOf<Int, String>()
oldMap.put(1, "Monday") // 방법1
oldMap[2] = "Tuesday" // 방법2

val map = mapOf(
    1 to "Monday", 
    2 to "Tuesday",
)

for (key in map.keys) {
    println("$key : ${map[key]}")
}

for ((k, v) in map.entries) {
    println("$k : $v")
}
```
- `mapOf<>()`, `mutableMapOf<>()` 를 사용해서 Map을 생성할 수 있다.
- `mutableMapOf<>()` 을 사용하는 경우에는 `put()` 또는 `[]` 연산자를 통해 값을 저장할 수 있다.
- `mapOf<>()` 을 사용하는 경우에는 중위 호출 `to`를 사용해서(Pair 객체) 깔끔하게 값을 미리 저장할 수 있다.
- `map.keys` 혹은 `map.entries` 를 활용해서 Map을 순회할 수 있다.

<br/>

```kotlin
val list1: List<Int?>
val list2: List<Int>?
val list3: List<Int?>?
```
- `List<Int?>` : List에 null이 들어갈 수 있지만 List 자체는 절대 null이 아님.
- `List<Int>?` : List에 절대 null이 들어갈 수 없지만 List 자체는 null 일 수 있음.
- `List<Int?>?` : List에 null이 들어갈 수 있고, List 자체도 null일 수 있음.

<br/>

## D-3. 다양한 함수 다루기

```kotlin
fun String.lastChar(): Char {
    return this[this.length - 1]
}

fun main() {
    val str = "ABC"
    val lastChar = str.lastChar()
    println(lastChar) // C
}
```
- `fun 확장하려는클래스.함수이름(파라미터)` 를 활용해서 기존에 존재하는 클래스에 확장해서 메서드를 만들 수 있다.
- 확장함수는 기존 클래스에 존재하는 멤버처럼 보이게 하는 기능이다. 하지만 이 기능을 활용해서 기존 클래스의 캡슐화를 깨뜨릴 수는 없다.
  - 기존 함수에 있던 private, protected 멤버는 확장 함수에서 접근이 불가능하다는 의미이다.
- 기존 클래스의 함수 이름과 외부에서 구현된 확장 함수의 이름이 같다면 호출 시 어떤 걸 호출하게 될까?
  - 기존 클래스에 있는 함수가 더 높은 우선순위를 갖는다.
  - 하지만 확장 함수가 먼저 구현되어 있었는데, 기존 클래스에 함수를 추가하는 경우에는 오작동을 유발할 수 있으므로 조심해야 한다.

<br/>

```kotlin
open class Train(
    val name: String = "새마을기차",
    val price: Int = 5_000,
)

fun Train.isExpensive(): Boolean {
    println("Train의 확장함수")
    return this.price >= 10_000
}

class Srt : Train("SRT", 40_000)

fun Srt.isExpensive(): Boolean {
    println("Srt의 확장함수")
    return this.price >= 10_000
}

fun main() {
    val train : Train = Train()
    train.isExpensive() // Train의 확장함수

    val srt1 : Train = Srt()
    srt1.isExpensive() // Train의 확장함수

    val srt2 : Srt = Srt()
    srt2.isExpensive() // Srt의 확장함수
}
```
- 확장 함수가 오버라이딩 되면 정적인 타입에 의존된다.
- 실제 런타임에 어떤 타입이 들어가냐 보단, 코드 상으로 어떤 타입으로 결정되는지가 어떤 함수를 부를지 결정한다.
- 이 방식은 오해를 불러 일으킬 여지가 있어 보이기 때문에 실제로 사용하는 것은 좋아보이지 않는다.

<br/>

```kotlin
fun String.lastChar(): Char {
    return this[this.length - 1]
}

val String.lastChar: Char
    get() = this[this.length - 1]
```
- 확장 함수 역시 확장 프로퍼티 형태로도 사용 가능하다.

<br/>

```java
String str = "Hello World";
System.out.println(StringUtilsKt.lastChar(str)); // d
```
- Java 에서 Kotlin의 확장 함수를 가져다 사용할 경우 static 함수처럼 가져다 사용할 수 있다.

<br/>

```kotlin
fun Int.add(other: Int): Int {
    return this + other
}

infix fun Int.add2(other: Int): Int {
    return this + other
}

fun main() {
    println(3.add(4))
    println(3.add2(4))
    println(3 add2 4)
}
```
- `변수.함수이름(파라미터)` 와 같이 함수를 호출하지만, `infix` 가 붙은 함수는 `변수 함수이름 파라미터` 식으로 함수 호출도 허용한다.

<br/>

```kotlin
inline fun Int.add3(other: Int): Int {
    return this + other
}

fun main() {
    println(3.add3(4))
}
```
- 함수가 호출되는 대신, 함수를 호출한 지점에 함수 본문을 그대로 복붙하고 싶은 경우에 `inline` 함수를 사용한다.
- 함수를 파라미터로 전달할 때 오버헤드를 줄일 수 있다.
- 하지만 너무 많이 쓰면 좋지 않을 수 있다. 성능 측정과 함께 신중하게 사용해야 한다.

<br/>

## D-4. 람다 다루기

```java
public static void main(String[] args) {
    List<Fruit> fruits = Arrays.asList(...);
    List<Fruit> filtered1 = filterFruitsInterface(fruits, fruit -> fruit.getName().equals("사과"));
    List<Fruit> filtered2 = filterFruitsStream(fruits, fruit -> fruit.getName().equals("사과"));
    List<Fruit> filtered3 = filterFruitsStream(fruits, Fruit::isApple);
}

private static List<Fruit> filterFruitsInterface(List<Fruit> fruits, Predicate<Fruit> fruitFilter) {
    List<Fruit> results = new ArrayList<>();
    for (Fruit fruit : fruits) {
        if (fruitFilter.test(fruit)) {
            results.add(fruit);
        }
    }
    return results;
}

private static List<Fruit> filterFruitsStream(List<Fruit> fruits, Predicate<Fruit> fruitFilter) {
    return fruits.stream()
                 .filter(fruitFilter)
                 .collect(Collectors.toList());
}
```
- 잠깐 Java 얘기를 먼저 하고 넘어가자.
- Java에서는 현실의 복잡한 요구사항을 깔끔하게 해결하기 위해 람다와 같은 개념이 등장했다.
- JDK 8 이후부터 람다, 스트림이 도입되며 코드를 요구사항에 맞게 깔끔하게 수정하는 것이 가능하다.
- `filtered3` 처럼 `Predicate`와 같은 함수형 인터페이스와 메서드 레퍼런스 기능을 활용하면 마치 함수를 매개변수로 넘기는 것 처럼 작성하는 것이 가능하다.
- Java에서는 함수가 2급시민으로 취급되기 때문에 함수 자체를 변수에 할당하거나 파라미터로 전달하는 것이 언어 차원에서 불가능했다.
- 하지만 Kotlin에서는 함수가 1급시민, Expression으로 취급되기 때문에 변수에 할당하거나 파라미터로 전달하는 것이 가능해졌다.

<br/>

```kotlin
val isApple1 = fun(fruit: KoFruit): Boolean {
    return fruit.name == "사과"
}
val isApple2 = { fruit: KoFruit -> fruit.name == "사과" }

println(isApple1(fruits[0]))
println(isApple1.invoke(fruits[0]))
println(isApple2(fruits[0]))
println(isApple2.invoke(fruits[0]))
```
- Kotlin에서는 이름 없는 함수인 람다 함수를 만드는 방법이 2가지가 있다.
  1. 변수 자체에 fun 키워드로 함수를 할당하는 방법이다. 함수 이름은 없고, 나머진 일반 함수와 동일하다.
  2. 중괄호와 화살표 연산자로 파라미터와 본문을 구분해서 람다 함수를 작성 후 변수에 할당하는 방법이다.
- 호출할 때는 일반 함수와 동일하게 호출 가능하며, `.invoke()`를 통해서도 호출 가능하다.

<br/>

```kotlin
val isApple1: (KoFruit) -> Boolean = fun(fruit: KoFruit): Boolean {
    return fruit.name == "사과"
}
val isApple2: (KoFruit) -> Boolean = { fruit: KoFruit -> fruit.name == "사과" }
```
- 함수의 파라미터 타입과 리턴 타입을 넣고 싶으면 위 예시처럼 넣을 수 있다.

<br/>

```kotlin
fun main() {
    val fruits = listOf(...)

    val isApple1: (KoFruit) -> Boolean = fun(fruit: KoFruit): Boolean {
        return fruit.name == "사과"
    }
    val isApple2: (KoFruit) -> Boolean = { fruit: KoFruit -> fruit.name == "사과" }

    val filtered1 = filterFruits(fruits, isApple1)
    val filtered2 = filterFruits(fruits, isApple2)
    val filtered3 = filterFruits(fruits, { fruit: KoFruit -> fruit.name == "사과" })
    val filtered4 = filterFruits(fruits) { fruit: KoFruit -> fruit.name == "사과" }
    val filtered5 = filterFruits(fruits) { fruit -> fruit.name == "사과" }
    val filtered6 = filterFruits(fruits) { it.name == "사과" }
}

private fun filterFruits(
    fruits: List<KoFruit>, filter: (KoFruit) -> Boolean
): List<KoFruit> {
    val results = mutableListOf<KoFruit>()
    for (fruit in fruits) {
        if (filter(fruit)) {
            results.add(fruit)
        }
    }
    return results
}
```
- `filterFruits()` 함수에서 볼 수 있듯, 함수 자체를 파라미터로 넘겨주는 것이 가능하다.
- `filtered1 ~ 6` 까지 모두 동일한 기능을 한다. 어떻게 가능한지 하나씩 알아보자.
  1. `filtered1`: 가장 일반적인 방법이다.
  2. `filtered2`: 화살표 연산자를 활용한 람다 함수도 파라미터로 사용이 가능하다.
  3. `filtered3`: 변수 할당 전, 함수 그 자체를 담아도 잘 동작한다.
  4. `filtered4`: 호출하는 함수의 마지막 파라미터가 함수 타입이라면 소괄호 밖에서 중괄호를 작성해도 잘 동작한다.
  5. `filtered5`: 호출하는 함수 쪽의 파라미터 타입이 명시되어 있기 때문에 타입 추론이 가능하다. 전달하는 파라미터 함수 안의 타입 제거가 가능하다.
  6. `filtered6`: 전달하는 함수의 파라미터가 단 하나라면 `it` 라는 예약어로 간소화 가능하다. 예를 들어 `a -> a.name` 와 같은 매핑 함수가 있다고 했을 때 `it.name` 으로 간소화 가능하다.

<br/>

```kotlin
// Closeable.kt 파일 일부
public inline fun <T : Closeable?, R> T.use(block: (T) -> R): R { ... }
```
```kotlin
fun readFile(path: String) {
    BufferedReader(FileReader(path)).use { reader ->
        for (line in reader.lines()) {
            println(line)
        }
    }
}
```
- `Closeable.kt` 파일 구현 일부를 보며 아래 `readFile()`의 `use()`가 어떻게 가능한지 알아보자.
1. `BufferedReader`는 `Closeable`을 상속받고 있다.
2. `<T : Closeable?, R> T.use` 를 보면 알 수 있듯, `use()`는 `Closeable`을 구현한 `T`의 확장 함수이다.
3. `use(block: (T) -> R)` 를 보면 알 수 있듯, 받는 파라미터가 `(T) -> R` 타입의 함수이다.
    - 람다 함수를 받도록 만들어진 함수이다.
    - 단 하나의 파라미터, 즉, 마지막 파라미터가 함수 타입이기 때문에 소괄호 밖에서 중괄호로 전달할 함수 작성이 가능하다.
4. T=`FileReader`, R=`Unit` 이란 것을 추론할 수 있다.

<br/>

## D-5. Closure 알아보기

```java
/* Java Code */
String targetFruitName = "바나나";
targetFruitName = "수박";
filterFruits(fruits, (fruit) -> targetFruitName.equals(fruit,getName()));
```
```kotlin
/* Kotlin Code */
var targetFruitName = "바나나"
targetFruitName = "수박"
filterFruits(fruits) { it.name == targetFruitName }
```
- 위 Java 코드를 실행하면 컴파일 시점에 아래와 같은 오류가 발생한다.
  - Variable used in lambda expression should be final or effectively final.
  - final 변수거나 실질적으로 final 인 변수만 람다 내부에서 참조 가능하다는 의미이다.
- 하지만 Kotlin 코드는 실행하면 잘 동작한다.
  - Kotlin은 람다 함수가 시작하는 시점에 참조하고 있는 변수들을 모두 포획하여 그 정보를 가지고 있기 때문이다.
  - 즉, 임시적으로 사용할 복사본을 만들어서 내부적으로 저장해두고 참조한다는 의미와 같다.
  - 이렇게 동작하는 방식의 데이터 구조를 Closure라고 한다.

<br/>

## D-6. 컬렉션을 함수형으로 다루기

```kotlin
data class Fruit(
    val id: Long,
    val name: String,
    var factoryPrice: Long?, // 원가
    var currentPrice: Long? = (factoryPrice?.times(1.0)?.toLong()) // 원가 + VAT
)

val fruits = listOf(
    Fruit(1, "사과", 1_000L),
    Fruit(2, "사과", 1_200L),
    Fruit(3, "사과", 1_200L),
    Fruit(4, "사과", 1_500L),
    Fruit(5, "사과", null),
    Fruit(6, "바나나", 3_000L),
    Fruit(7, "바나나", 3_200L),
    Fruit(8, "바나나", 2_500L),
    Fruit(9, "수박", 10_000L),
)
```
```kotlin
// 이름이 사과인 것만 필터링
val apples1 = fruits.filter { fruit: Fruit -> fruit.name == "사과" }

// 이름이 사과인 것만 필터링 + 인덱스 접근
val apples2 = fruits.filterIndexed { idx, fruit ->
    println(idx)
    fruit.name == "사과"
}

// 이름이 사과인 것들의 가격만 가져옴
val applePrices1 = fruits.filter { fruit: Fruit -> fruit.name == "사과" }
    .map { fruit: Fruit -> fruit.currentPrice }


// 이름이 사과인 것들의 가격만 가져옴 + 인덱스 접근
val applePrices2 = fruits.filter { fruit: Fruit -> fruit.name == "사과" }
    .mapIndexed { idx, fruit ->
        println(idx)
        fruit.currentPrice
    }

// 이름이 사과인 것들의 가격만 가져옴. 근데 가격이 null인 것은 가져오지 않음.
val values = fruits.filter { fruit: Fruit -> fruit.name == "사과" }
    .mapNotNull { fruit: Fruit -> fruit.currentPrice }

// fruits 안에 있는 것들이 모두 사과인가요?
val isAllApple = fruits.all { fruit: Fruit -> fruit.name == "사과" }

// fruits 안에 사과가 단 하나도 없나요?
val isNoApple = fruits.none { fruit: Fruit -> fruit.name == "사과" }

// fruits 안에 사과가 단 하나라도 있나요?
val isAnyApple = fruits.any { fruit: Fruit -> fruit.name == "사과" }

// fruits 안에 element 가 총 몇개인가요? (== list.size)
val count = fruits.count()

// 과일 가격 순으로 오름차순 정렬해서 보여주세요.
// null 은 가장 작게 취급된다.
val fruitOrderByPriceAsc = fruits.sortedBy { fruit: Fruit -> fruit.currentPrice }

// 과일 가격 순으로 내림차순 정렬해서 보여주세요.
// 마찬가지로 null 은 가장 작게 취급된다.
val fruitOrderByPriceDesc = fruits.sortedByDescending { fruit: Fruit -> fruit.currentPrice }

// 과일 이름으로 중복 제거해서 보여주세요.
val distinctFruitNames = fruits.distinctBy { fruit: Fruit -> fruit.name }
    .map { fruit: Fruit -> fruit.name }

val firstFruit1 = fruits.first() // 첫 값 (List가 비어있으면 NoSuchElementException 발생)
val firstFruit2 = fruits.firstOrNull() // 첫 값 (List가 비어있으면 null 반환)
val lastFruit1 = fruits.last() // 끝 값 (상동)
val lastFruit2 = fruits.lastOrNull()  // 끝 값 (상동)

// 과일 이름으로 그룹핑해주세요.
val map1: Map<String, List<Fruit>> = fruits.groupBy { fruit: Fruit -> fruit.name }

// 중복되지 않는 key를 가지고 맵을 만들고 싶어요.
// 중복되지 않는 프로퍼티를 key로 가져가야 한다는 것이 핵심이다.
val map2: Map<Long, Fruit> = fruits.associateBy { fruit: Fruit -> fruit.id }

// 과일 이름으로 그룹핑하고 원가만 필요해요.
// groupBy() 안에서 Key와 Value를 동시에 정의할 수 있다.
val map3: Map<String, List<Long>> = fruits
    .groupBy({ fruit: Fruit -> fruit.name}, {fruit: Fruit -> fruit.factoryPrice ?: -1 })
```
- 주곧내 ~(주석이 곧 내용)~
- 람다 함수에서 인자가 1개일 경우 `it` 예약어를 사용해서 축약할 수  있지만 굳이 축약형으로 작성하지 않았다.

<br/>

# E. 코틀린에서 코루틴(Coroutine)

작성중...

<br/>

# F. 번외

## F-1. Type Alias

```kotlin
typealias FruitFilter = (Fruit) -> Boolean

fun filterFruits(fruits: List<Fruit>, filter: FruitFilter) {
    // ...
}
```
- `(Fruit) -> Boolean` 가 너무 길어서 `FruitFilter` 라는 타입으로 별칭을 지정했다.
  - 만약 파라미터 개수가 크게 늘어난다면 `typealias` 기능은 유용하게 사용될 것이다.

<br/>

```kotlin
data class UltraSuperGuardianTribe(
    val name: String,
)

typealias USGTMap = Map<String, UltraSuperGuardianTribe>
```
- 이름이 긴 클래스를 collection에서 사용할 때도 축약하는 용도로 사용하면 좋다.

<br/

## F-2. as import

```kotlin
package lec19.a

fun printHello() {
    println("Hello! AA")
}
```
```kotlin
package lec19.b

fun printHello() {
    println("Hello! BB")
}
```
```kotlin
package lec19

import lec19.a.printHello as printHelloA
import lec19.b.printHello as printHelloB

fun main() {
    printHelloA() // Hello! AA
    printHelloB() // Hello! BB
}
```
- 다른 패키지에 속해있지만, 같은 이름을 가진 함수를 동시에 가져오고 싶다면 `as import`를 사용해야 한다.
- `as import`를 사용해서 어떤 클래스나 함수를 가져오는 시점에 이름을 바꾸는 기능이다.

<br/>

## F-3. 구조분해와 componentN 함수

```kotlin
data class Person(
    val name: String,
    val age: Int,
)

fun main() {
    val person = Person("jinseong", 20)
    val (name, age) = person
    println("이름 = $name / 나이 = $age") // 이름 = jinseong / 나이 = 20
}
```
- 마법같이 `name`과 `age`에 적절한 값이 분해되어 할당된다. 이런 문법을 보고 구조분해 문법이라고 부른다.
- `data class` 에는 componentN 함수도 자동으로 생성해주는데, componentN 함수를 사용해서 동작한다.
- 프로퍼티 순서에 의존해서 동작하기 때문에, 추후 변경 가능성이 있는 클래스라면 조심해서 사용하자.

<br/>

```kotlin
// val (name, age) = person
val name = person.component1() 
val age = person.component2()
```
- person 객체에 가지고 있는 첫번째 프로퍼티가 `component1()`을 호출하면 반환된다. N번째 프로퍼티는 `componentN()` 을 호출하면 된다.

<br/>

```kotlin
class Person2(
    val name: String,
    val age: Int,
) {
    operator fun component1(): String {
        return this.name
    }

    operator fun component2(): Int {
        return this.age
    }
}

fun main() {
    val person = Person2("jinseong", 1000)
    val (name, age) = person
    println("이름 = $name / 나이 = $age") // 이름 = jinseong / 나이 = 1000
}
```
- `data class`가 아니라면 직접 componentN 함수를 구현해서 사용할 수 있다.
- 단, 연산자 오버로딩으로 간주되기 때문에 앞에 `operator` 키워드를 붙여줘야 한다.

<br/>

## F-4. Jump와 Label

```kotlin
val numbers = listOf(1, 2, 3, 4)
    numbers.map { it + 1 }
        .forEach { println(it) }
```
- 일반적으로 코드의 흐름을 제어할 때, `return`, `break`, `continue` 를 사용한다.
- 하지만 Kotlin의 `forEach()`를 사용하는 도중에는 `break`과 `continue`를 사용할 수 없다.

<br/>

```kotlin
val numbers = listOf(1, 2, 3, 4)
run {
    numbers.forEach {number ->
        if (number == 2) {
            println("continue")
            return@forEach
        }
        if (number == 3) {
            println("break")
            return@run
        }
        println(number)
    }
}
```
```text
1
continue
break
```
- `forEach()`를 사용하는 도중에 `break`과 `continue`를 사용하고 싶다면 `run{}` 블럭으로 감싸줘야 한다.
- `return@run` 이 `break` 역할을 수행하고, `return@forEach` 이 `continue` 역할을 수행한다.
- 복잡하고 가독성이 떨어지기 때문에 break 혹은 continue를 사용해야 할 일이 있다면 일반적인 for 문을 사용하는 것을 추천한다.

<br/>

```kotlin
out_loop@ for (i in 1..10) {
    for (j in 1..3) {
        if (j == 2) {
            break@out_loop
        }
        println("$i $j")
    }
}
```
```text
1 1
```
- Kotlin에서는 Label 기능을 제공하는데, 특정 expression에 `LABEL_NAME@` 을 붙여서 하나의 Label로 간주하고, `break`, `continue`, `return` 등을 사용하는 기능이다.
- C 언어의 `GOTO` 와 매우 닮아있는데, 마찬가지로 사용하지 않는 것을 강력하게 추천한다.

<br/>

## F-5. TakeIf와 TakeUnless

```kotlin
fun getNumberOrNull(number: Int): Int? {
    return if (number <= 0) {
        null
    } else {
        number
    }
}

fun getNumberOrNullV2(number: Int): Int? {
    return number.takeIf { it > 0 }
}

fun getNumberOrNullV3(number: Int): Int? {
    return number.takeUnless { it <= 0 }
}

fun main() {
    // v1: original
    println(getNumberOrNull(-1)) // null
    println(getNumberOrNull(10)) // 10

    // v2: takeIf
    println(getNumberOrNullV2(-1)) // null
    println(getNumberOrNullV2(10)) // 10

    // v3: takeUnless
    println(getNumberOrNullV3(-1)) // null
    println(getNumberOrNullV3(10)) // 10
}
```
- 처음에 작성한 `getNumberOrNull()` 메서드를 Kotlin에서 제공하는 `takeIf{}`와 `takeUnless{}`를 활용해서 완벽히 동일한 함수로 바꿔보자.
- `takeIf {}` : 블럭의 람다함수 조건을 만족하면 그 값이, 만족하지 않으면 `null`을 반환한다.
- `takeUnless {}` : 블럭의 람다함수 조건을 만족하지 않으면 그 값이, 만족하면 `null`을 반환한다.

<br/>

## F-6. Scope function

```kotlin
fun printPerson(person: Person?) {
    if (person != null) {
        println(person.name)
        println(person.age)
    }
}

fun printPersonV2(person: Person?) {
    person?.let {
        println(it.name)
        println(it.age)
    }
}
```
- scope은 "영역"을 의미하고, function은 "함수"를 의마한다. 즉, scope function은 일시적인 영역을 형성하는 함수라는 의미이다.
- 위 `printPerson()` 과 `printPersonV2()` 는 동일한 기능을 수행하는 함수이다. 여기서 알아낼 수 있는 scope function의 정의는,
  - 람다 함수를 사용해 일시적인 영역을 만들고,
  - 코드를 더 간결하게 만들고,
  - Functional programming 할 때 method chaining에 활용하는 함수이다.

<br/>

```kotlin
fun main() {
    val person = Person("jinseong", 100)

    // let
    val value1 = person.let {
        it.age
    }
    println("value1 => ${value1}")

    // run
    val value2 = person.run {
        this.age
    }
    println("value2 => ${value2}")
  
    // also
    val value3 = person.also {
        it.age
    }
    println("value3 => ${value3}")

    // apply
    val value4 = person.apply {
        this.age
    }
    println("value4 => ${value4}")

    // with
    val value5 = with(person) {
        println("with => ${person} / 이름: ${name} / 나이: ${this.age}")
        "A"
    }
    println("value5 => ${value5}")
}
```
```text
value1 => 100
value2 => 100
value3 => Person(name=jinseong, age=100)
value4 => Person(name=jinseong, age=100)
with => Person(name=jinseong, age=100) / 이름: jinseong / 나이: 100
value5 => A
```

Scope function으로는 대표적으로 5가지가 존재한다. (`let{}`, `run{}`, `also{}`, `apply{}`, `with{}`)
- `let{}`, `run{}`, `also{}`, `apply{}` : 확장함수이다.
- `with{}` : 객체와 람다함수를 인자로 하는 일반함수이다.

|            | it으로 접근 | this로 접근 |
|------------|---------|----------|
| 람다 결과 반환   | let     | run      |
| 객체 그 자체 반환 | also    | apply    |

- `it` 는 람다 함수의 단일 인자를 가리키는 예약어이다. 만약 `it` 대신 다른 변수명을 사용하고 싶다면 재정의 가능하다. 하지만 생략은 불가능하다.
- `this`는 확장 함수에서 스스로를 `this`라고 칭할 수 있다. 다른 변수명으로 재정의는 불가능하지만, 생략이 가능하다.

<br/>

### `let` 활용 사례

```kotlin
val strings = listOf("APPLE", "CAR", "HI")
strings.map { it.length }
    .filter { it >= 3 }
    .let(::println)
```
```text
[5, 3]
```
- `let{}`은 하나 이상의 함수를 call chain 결과로 호출할 때 사용된다.
  - 여기서는 `let{}` 뒤에 람다함수를 넣은 것이 아니라 인자로 메서드 레퍼런스를 전달했다.
  - `filter{}`의 결과의 타입이 `List<Int>` 이기 때문에 받아서 출력하면 리스트 형태로 출력된다.

<br/>

```kotlin
val str: String? = "Hello"
val length = str?.let {
    println(it.uppercase())
    it.length
}
println(length)
```
```text
HELLO
5
```
- non-null 값에 대해서만 scope 안의 code block을 실행시키고 싶을 때 활용한다.

<br/>

```kotlin
val numbers = listOf("one", "two", "three", "four")
val modifiedFirstItem = numbers.first()
    .let { firstItem ->
        if (firstItem.length >= 5) {
            firstItem
        } else {
            "!$firstItem!"
        }.uppercase()
    }
println(modifiedFirstItem)
```
```text
!ONE!
```
- 일회성으로 제한된 영역에 변수를 만들어야 하는 경우에 활용된다.
- 하지만 이 경우에는 private method로 분리하는 것이 depth를 줄이고 코드를 깔끔하게 유지하는 데 도움되기 때문에 좋은 용례는 아니다.

<br/>

### `run` 활용 사례

```kotlin
class PersonRepository {
    fun save(person: Person): Person {
        println("Save successfully => ${person}")
        return person
    }
}

fun main() {
    val personRepository = PersonRepository()
    val person = Person("jinseong", 100).run(personRepository::save)
    println(person)
}
```
```text
Save successfully => Person(name=jinseong, age=100)
Person(name=jinseong, age=100)
```
- 객체 생성 후 바로 어떤 로직을 실행해야 하는 경우 활용된다.

<br/>

### `apply` 활용 사례

```kotlin
data class Person(
    val name: String,
    val age: Int,
    var hobby: String? = null,
)

fun createPerson(
    name: String,
    age: Int,
    hobby: String,
): Person {
    return Person(
        name = name,
        age = age,
    ).apply {
        this.hobby = hobby
    }
}

fun main() {
    val person = createPerson("jinseong", 100, "programming")
    println(person)
}
```
```text
Person(name=jinseong, age=100, hobby=programming)
```
- apply 는 객체 그 자체를 반환하는 특징이 있다.
- 객체 설정을 할 때 객체를 수정하는 로직이 call chain 중간에 필요할 때 활용된다.
  - Test fixture를 만들 때 활용할 수 있다.

<br/>

### `also` 활용 사례

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

