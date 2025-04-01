package com.ecwid.uniqueipcounter.container;

import java.util.BitSet;

/**
 * An implementation of {@link IntContainer} that uses two {@link BitSet} for storing of int numbers.
 * <p>
 * This implementation is a slightly optimized version of a special case
 * of the more general {@link BitSetContainer} and is equivalent to {@code new BitSetContainer(1)}.
 */
public class DualBitSetContainer implements IntContainer {
    private final BitSet positive = new BitSet(Integer.MAX_VALUE);
    private final BitSet negative = new BitSet(Integer.MAX_VALUE);

    @Override
    public void add(int i) {
        if (i >= 0) {
            positive.set(i);
        } else {
            negative.set(~i);
        }
    }

    @Override
    public long countUnique() {
        return (long) positive.cardinality() + negative.cardinality();
    }
}