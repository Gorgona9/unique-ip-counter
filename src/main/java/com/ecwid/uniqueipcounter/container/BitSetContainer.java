package com.ecwid.uniqueipcounter.container;

import java.util.BitSet;

import static java.util.Arrays.stream;
import static java.util.Objects.checkIndex;
import static java.util.stream.Stream.generate;

/**
 * An implementation of {@link IntContainer} that created
 * an array of {@link BitSet} for storing set of numbers of type int.
 * <p>
 * This approach dynamically allocates memory as required.
 * As a result, if the IP addresses belong to just one or a few specific subnets,
 * it can significantly reduce memory consumption.
 */
public class BitSetContainer implements IntContainer {
    private final BitSet[] storage;
    private final int mask;
    private final int shift;

    /**
     * Create a new container with the necessary configuration.
     *
     * @param level - The number of leading bits in the number
     *              that will be used to identify the index of the cell containing the bitset.
     *              Valid values are from 1 to 16 ({@code Byte.SIZE * 2}).
     * @throws IndexOutOfBoundsException if level outside the range 1..16
     */
    public BitSetContainer(int level) {
        checkIndex(level - 1, Byte.SIZE * 2);

        mask = 0xFFFF_FFFF >>> level;
        shift = Integer.SIZE - level;
        storage = generate(BitSet::new).limit(1L << level).toArray(BitSet[]::new);
    }

    @Override
    public void add(int ip) {
        storage[ip >>> shift].set(ip & mask);
    }

    @Override
    public long countUnique() {
        return stream(storage).mapToLong(BitSet::cardinality).sum();
    }
}
