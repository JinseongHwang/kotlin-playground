package lec22

/**
 * 기본적으로 제네릭은 JDK 1.5부터 도입됐는데, 하위 호한성을 위해 제네릭 타입을 소거하는 방향으로 설계됐다.
 * 타입 소거는 컴파일 타임에 진행되며, List<String>을 List 로 변환(소거)하는 것을 말한다.
 *
 * 따라서 코드에서 Any 타입 파라미터를 `param is List<String>`과 같이 검증하는 것은 런타임에 불가능하다.
 * 그렇지만 최소한 List 인지 검증하고 싶다면 `param is List<*>`과 같이 Star projection을 사용해 검증 가능하다.
 *
 * 만약 파라미터가 Collection<String> 타입이라면, `param is MutableList<String>` 는 가능하긴 하다. (코틀린 컴파일러의 똑똑함)
 */
class TypeErase {
}

fun main() {
    "123".printSuperString() // String:123
    123.printSuperString() // Int:123
}

/**
 * inline으로 메서드가 아니라 붙여넣기 방식으로 동작하도록 함. (제네릭을 사용하지 않는 것처럼 해 타입 소거가 발생하지 않음)
 *
 * reified는 '실체화하다', '구체화하다'라는 사전적 의미를 가지며, Kotlin에서 제네릭 타입에 사용되어 런타임에도 타입 정보를 유지하도록 돕는 키워드이다.
 * reified는 inline과 함께 쓸 수 있는 특수 키워드인데, reified type과 함께 인라인 함수가 호출되면 컴파일러는 type argument로 사용된 실제 타입을 알고 만들어진 바이트코드를 직접 클래스에 대응되도록 바꿔준다.
 *
 * 단, reified를 사용한다고 해서 T의 인스턴스를 만들거나 T의 companion object에 접근은 불가능하다.
 */
inline fun <reified T> T.printSuperString() {
    println("${T::class.simpleName}:$this")
}