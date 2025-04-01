package com.ecwid.uniqueipcounter.container;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.stream.Stream;

class BitSetContainerTest {

    @ParameterizedTest
    @MethodSource("provideTestCases")
    void shouldCountUniqueNumbers(int level, int[] numbers, int expected) {
        BitSetContainer container = new BitSetContainer(level);

        assertEquals(0, container.countUnique(), "Empty container should have 0 unique numbers");

        for (int num : numbers) {
            container.add(num);
        }

        assertEquals(expected, container.countUnique(), "Unique number count mismatch");
    }

    static Stream<Arguments> provideTestCases() {
        return Stream.of(
                Arguments.of(1, new int[]{}, 0),
                Arguments.of(1, new int[]{0}, 1),
                Arguments.of(1, new int[]{1}, 1),
                Arguments.of(1, new int[]{1, 1, 2}, 2),
                Arguments.of(1, new int[]{1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 5, 5, 5, 5}, 3),
                Arguments.of(1, new int[]{-1, 0, 1}, 3),
                Arguments.of(1, new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE}, 3),
                Arguments.of(1, new int[]{Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE}, 5),
                Arguments.of(1, new int[]{-9, -7, -3, -9, 0, 1, -3, 0, 5, 9, 5}, 7),
                Arguments.of(2, new int[]{}, 0),
                Arguments.of(2, new int[]{0}, 1),
                Arguments.of(2, new int[]{1}, 1),
                Arguments.of(2, new int[]{-1, 0, 1}, 3),
                Arguments.of(2, new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE}, 3),
                Arguments.of(2, new int[]{Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE}, 5),
                Arguments.of(4, new int[]{}, 0),
                Arguments.of(4, new int[]{0}, 1),
                Arguments.of(4, new int[]{1}, 1),
                Arguments.of(4, new int[]{-1, 0, 1}, 3),
                Arguments.of(4, new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE}, 3),
                Arguments.of(4, new int[]{Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE}, 5),
                Arguments.of(8, new int[]{}, 0),
                Arguments.of(8, new int[]{0}, 1),
                Arguments.of(8, new int[]{1}, 1),
                Arguments.of(8, new int[]{-1, 0, 1}, 3),
                Arguments.of(8, new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE}, 3),
                Arguments.of(8, new int[]{Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE}, 5)
        );
    }
}
