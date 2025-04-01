package com.ecwid.uniqueipcounter.container;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;

class DualBitSetContainerTest {

    @ParameterizedTest
    @MethodSource("provideTestCases")
    void shouldCountUniqueNumbers(int[] numbers, int expected) {
        DualBitSetContainer container = new DualBitSetContainer();

        assertEquals(0, container.countUnique(), "Empty container should have 0 unique numbers");

        for (int num : numbers) {
            container.add(num);
        }

        assertEquals(expected, container.countUnique(), "Unique number count mismatch");
    }

    static Stream<Arguments> provideTestCases() {
        return Stream.of(
                Arguments.of(new int[]{}, 0),
                Arguments.of(new int[]{0}, 1),
                Arguments.of(new int[]{12}, 1),
                Arguments.of(new int[]{1, 1, 2}, 2),
                Arguments.of(new int[]{1, 1}, 1),
                Arguments.of(new int[]{-1, 0, 1}, 3),
                Arguments.of(new int[]{-9, -7, -3, -9, 0, 1, -3, 0, 5, 9, 5}, 7),
                Arguments.of(new int[]{-128984, -2098321032, 143, 0, 213219872}, 5),
                Arguments.of(new int[]{3, 7, 3, 7, 3, 7, 7, 7, 3, 3, 3, 3, 7, 7}, 2),
                Arguments.of(new int[]{-1, 0, 1, 3, 4, 6, 7, 8, 9, 10, 11, 12}, 12),
                Arguments.of(new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE}, 3)
        );
    }
}
