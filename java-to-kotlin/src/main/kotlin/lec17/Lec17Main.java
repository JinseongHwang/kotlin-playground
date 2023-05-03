package lec17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Lec17Main {

    public static void main(String[] args) {
        List<Fruit> fruits = Arrays.asList(
            new Fruit("사과", 1_000),
            new Fruit("사과", 1_200),
            new Fruit("사과", 1_200),
            new Fruit("사과", 1_500),
            new Fruit("바나나", 3_000),
            new Fruit("바나나", 3_200),
            new Fruit("바나나", 2_500),
            new Fruit("수박", 10_000)
        );

        List<Fruit> filtered1 = filterFruitsInterface(fruits, fruit -> fruit.getName().equals("사과"));
        for (Fruit fruit : filtered1) {
            System.out.println(fruit);
        }

        List<Fruit> filtered2 = filterFruitsStream(fruits, fruit -> fruit.getName().equals("사과"));
        for (Fruit fruit : filtered2) {
            System.out.println(fruit);
        }
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
}
