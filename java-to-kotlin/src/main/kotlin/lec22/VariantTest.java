package lec22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VariantTest {
    public static void main(String[] args) {
        // Effective Java Item28: 배열보다 리스트를 선호해야 하는 이유
        /** Co-variant (공변)
         * Object는 String의 상위 타입이다.
         * Array의 경우에는 Collection과 다르게 제네릭을 사용하지 않으며, 상위 타입 개념을 전파한다.
         * 즉, Object는 String의 상위 타입이기 때문에 Object[] 또한 String[]의 상위 타입으로 처리한다.
         * 하지만 이 경우에는 컴파일 타임에 문제가 잡히지 않고 런타임에 문제가 잡힌다.
         */
        String[] strs = { "A", "B", "C" };
        Object[] objs = strs; // 문제 없음
        objs[0] = 1; // 런타임에 ArrayStoreException 발생!

        /** Invariant (무공변)
         * Object는 String의 상위 타입이다.
         * Array와 달리 제네릭을 사용하는 Collection에서는,
         * List<String>과 List<Object>를 완전히 다른 타입으로 처리한다.
         */
        List<String> strList = new ArrayList<>(Arrays.asList(strs));
        // List<Object> objList = strList; // 서로 다른 타입이기 때문에 컴파일 실패
    }
}
