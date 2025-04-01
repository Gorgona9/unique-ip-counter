package com.ecwid.uniqueipcounter.container;

import java.util.Arrays;

/**
 * An implementation of {@link IntContainer} optimized to store a huge amount of int numbers.
 * <p>
 * Directly allocate an array of long values as a bitset.
 * The numbers to be added are split into two parts: the first 26 bits determine the index in the array,
 * while the remaining 6 bits (ranging from 0 to 63) specify the position of the bit to be set
 * within the corresponding long value.
 */
public class LongArrayContainer implements IntContainer {
    private static final int VALUE_SIZE = 6;
    private static final int VALUE_MASK = 0x3F;
    private static final int STORAGE_SIZE = 1 << (Integer.SIZE - VALUE_SIZE + 1);

    private final long[] storage = new long[STORAGE_SIZE];

    @Override
    public void add(final int number) {
        final var index = number >>> VALUE_SIZE;
        final var value = number & VALUE_MASK;
        storage[index] |= 1L << value;
    }

    @Override
    public long countUnique() {
        return Arrays.stream(storage).map(Long::bitCount).sum();
    }
}