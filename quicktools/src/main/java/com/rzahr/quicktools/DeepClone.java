package com.rzahr.quicktools;


import java.lang.reflect.Array;
import java.util.*;

/**
 * this JAVA class is used to create a duplicate independent array list. it is needed since simply setting two arrays equal to each other will not
 * create two independent arrays and editing one of them will automatically edit the second
 */
@SuppressWarnings("ALL")
public class DeepClone {

    private DeepClone(){}

    public static <X> X deepClone(final X input) {
        if (input == null) {
            return input;
        } else if (input instanceof Map<?, ?>) {
            return (X) deepCloneMap((Map<?, ?>) input);
        } else if (input instanceof Collection<?>) {
            return (X) deepCloneCollection((Collection<?>) input);
        } else if (input instanceof Object[]) {
            return (X) deepCloneObjectArray((Object[]) input);
        } else if (input.getClass().isArray()) {
            return (X) clonePrimitiveArray(input);
        }

        return input;
    }

    private static Object clonePrimitiveArray(final Object input) {
        final int length = Array.getLength(input);
        final Object copy = Array.newInstance(input.getClass().getComponentType(), length);
        // deep clone not necessary, primitives are immutable
        System.arraycopy(input, 0, copy, 0, length);
        return copy;
    }

    private static <E> E[] deepCloneObjectArray(final E[] input) {
        final E[] clone = (E[]) Array.newInstance(input.getClass().getComponentType(), input.length);
        for (int i = 0; i < input.length; i++) {
            clone[i] = deepClone(input[i]);
        }

        return clone;
    }

    private static <E> Collection<E> deepCloneCollection(final Collection<E> input) {
        Collection<E> clone;
        // this is of course far from comprehensive. extend this as needed
        if (input instanceof LinkedList<?>) {
            clone = new LinkedList<E>();
        } else if (input instanceof SortedSet<?>) {
            clone = new TreeSet<E>();
        } else if (input instanceof Set) {
            clone = new HashSet<E>();
        } else {
            clone = new ArrayList<E>();
        }

        for (E item : input) {
            clone.add(deepClone(item));
        }

        return clone;
    }

    private static <K, V> Map<K, V> deepCloneMap(final Map<K, V> map) {
        Map<K, V> clone;
        // this is of course far from comprehensive. extend this as needed
        if (map instanceof LinkedHashMap<?, ?>) {
            clone = new LinkedHashMap<>();
        } else if (map instanceof TreeMap<?, ?>) {
            clone = new TreeMap<>();
        } else {
            clone = new HashMap<>();
        }

        for (Map.Entry<K, V> entry : map.entrySet()) {
            clone.put(deepClone(entry.getKey()), deepClone(entry.getValue()));
        }

        return clone;
    }
}
