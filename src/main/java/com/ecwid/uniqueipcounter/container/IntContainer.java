package com.ecwid.uniqueipcounter.container;

/**
 * Collection for storing a set of int numbers.
 * <p>
 * Implemented only the essential methods required to solve the problem of counting unique numbers.
 */
public interface IntContainer {
    /**
     * Add an int number
     *
     * @param number - integer number
     */
    void add(int number);

    /**
     * Count the unique int numbers
     *
     * @return the count of unique numbers
     */
    long countUnique();

    /**
     * Adds all elements to this container if they're not already present.
     *
     * @param other int container
     */
    default void addAll(IntContainer other) {
        throw new UnsupportedOperationException();
    }
}