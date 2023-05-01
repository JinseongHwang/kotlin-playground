package lec14

fun handleCountry(country: Country) {
    when (country) {
        Country.KOREA -> TODO()
        Country.AMERICA -> TODO()
        Country.JAPAN -> TODO()
    }
}

fun handleCar(car: HyundaiCar) {
    when (car) {
        is Avante -> TODO()
        is Grandeur -> TODO()
        is Sonata -> TODO()
    }
}

fun main() {
    val country = Country.KOREA
    handleCountry(country)
}