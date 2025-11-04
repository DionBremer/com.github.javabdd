//////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2025 Contributors to the Eclipse Foundation
//
// See the NOTICE file(s) distributed with this work for additional
// information regarding copyright ownership.
//
// This program and the accompanying materials are made available
// under the terms of the MIT License which is available at
// https://opensource.org/licenses/MIT
//
// SPDX-License-Identifier: MIT
//////////////////////////////////////////////////////////////////////////////

package com.github.javabdd;

/**
 * Utilities for high-quality hashing of 64-bit values. The provided hash functions are based on
 * <a href="https://github.com/Nicoshev/rapidhash">rapidhash</a> but tailored towards decision diagram processing.
 */
public final class HashUtils {
    /** The default seed. */
    private static final long DEFAULT_SEED = 0xBDD89AA982704029L;

    /** The default secret. */
    private static final long[] DEFAULT_SECRET = {0x2D358DCCAA6C78A5L, 0x8BB84B93962EACC9L, 0x4B33A62ED433d4A3L};

    /** Constructor for the {@link HashUtils} class. */
    private HashUtils() {
        // Static class.
    }

    /**
     * Converts a 64-bit hash to a 32-bit hash.
     *
     * @param hash The 64-bit hash to convert.
     * @return The converted 32-bit hash.
     */
    public static int toInt(long hash) {
        return (int)(hash >>> 32) ^ (int)hash;
    }

    /**
     * Hashes the given 64-bit value, using a default seed and default secrets.
     *
     * @param first The 64-bit value to hash.
     * @return The computed hash.
     */
    public static long hash(long first) {
        return hash(first, DEFAULT_SEED, DEFAULT_SECRET);
    }

    /**
     * Hashes the given 64-bit value, using the specified seed and secrets.
     *
     * @param first The 64-bit value to hash.
     * @param seed The seed to use for hashing.
     * @param secret The array secrets to use for hashing, which must contain three 64-bit values.
     * @return The computed hash.
     */
    public static long hash(long first, long seed, long[] secret) {
        // The length of the (combined) key to hash is 8 bytes, i.e., 1 * 64 bits.
        long len = 8;

        seed ^= mix(seed ^ secret[0], secret[1]) ^ len;

        long a = (first & 0xFFFFFFFF00000000L) | (first & 0x00000000FFFFFFFFL);
        long b = ((first & 0x00000000FFFFFFFFL) << 32) | ((first & 0xFFFFFFFF00000000L) >>> 32);

        a ^= secret[1];
        b ^= seed;

        long low = unsignedMultiplyLow(a, b);
        long high = unsignedMultiplyHigh(a, b);

        a ^= low;
        b ^= high;

        return mix(a ^ secret[0] ^ len, b ^ secret[1]);
    }

    /**
     * Hashes the given 64-bit values, using a default seed and default secrets.
     *
     * @param first The first 64-bit value to hash.
     * @param second The second 64-bit value to hash.
     * @return The computed hash.
     */
    public static long hash(long first, long second) {
        return hash(first, second, DEFAULT_SEED, DEFAULT_SECRET);
    }

    /**
     * Hashes the given 64-bit values, using the specified seed and secrets.
     *
     * @param first The first 64-bit value to hash.
     * @param second The second 64-bit value to hash.
     * @param seed The seed to use for hashing.
     * @param secret The array secrets to use for hashing, which must contain three 64-bit values.
     * @return The computed hash.
     */
    public static long hash(long first, long second, long seed, long[] secret) {
        // The length of the (combined) key to hash is 16 bytes, i.e., 2 * 64 bits.
        long len = 16;

        seed ^= mix(seed ^ secret[0], secret[1]) ^ len;

        long a = (first & 0xFFFFFFFF00000000L) | (second & 0x00000000FFFFFFFFL);
        long b = ((first & 0x00000000FFFFFFFFL) << 32) | ((second & 0xFFFFFFFF00000000L) >>> 32);

        a ^= secret[1];
        b ^= seed;

        long low = unsignedMultiplyLow(a, b);
        long high = unsignedMultiplyHigh(a, b);

        a ^= low;
        b ^= high;

        return mix(a ^ secret[0] ^ len, b ^ secret[1]);
    }

    /**
     * Hashes the given 64-bit values, using a default seed and default secrets.
     *
     * @param first The first 64-bit value to hash.
     * @param second The second 64-bit value to hash.
     * @param third The third 64-bit value to hash.
     * @return The computed hash.
     */
    public static long hash(long first, long second, long third) {
        return hash(first, second, third, DEFAULT_SEED, DEFAULT_SECRET);
    }

    /**
     * Hashes the given 64-bit values, using the specified seed and secrets.
     *
     * @param first The first 64-bit value to hash.
     * @param second The second 64-bit value to hash.
     * @param third The third 64-bit value to hash.
     * @param seed The seed to use for hashing.
     * @param secret The array secrets to use for hashing, which must contain three 64-bit values.
     * @return The computed hash.
     */
    public static long hash(long first, long second, long third, long seed, long[] secret) {
        // The length of the (combined) key to hash is 24 bytes, i.e., 3 * 64 bits.
        long len = 24;

        seed ^= mix(seed ^ secret[0], secret[1]) ^ len;
        seed = mix(first ^ secret[2], second ^ seed ^ secret[1]);

        long a = second;
        long b = third;

        a ^= secret[1];
        b ^= seed;

        long low = unsignedMultiplyLow(a, b);
        long high = unsignedMultiplyHigh(a, b);

        a ^= low;
        b ^= high;

        return mix(a ^ secret[0] ^ len, b ^ secret[1]);
    }

    /**
     * Hashes the given 64-bit values, using a default seed and default secrets.
     *
     * @param first The first 64-bit value to hash.
     * @param second The second 64-bit value to hash.
     * @param third The third 64-bit value to hash.
     * @param fourth The fourth 64-bit value to hash.
     * @return The computed hash.
     */
    public static long hash(long first, long second, long third, long fourth) {
        return hash(first, second, third, fourth, DEFAULT_SEED, DEFAULT_SECRET);
    }

    /**
     * Hashes the given 64-bit values, using the specified seed and secrets.
     *
     * @param first The first 64-bit value to hash.
     * @param second The second 64-bit value to hash.
     * @param third The third 64-bit value to hash.
     * @param fourth The fourth 64-bit value to hash.
     * @param seed The seed to use for hashing.
     * @param secret The array secrets to use for hashing, which must contain three 64-bit values.
     * @return The computed hash.
     */
    public static long hash(long first, long second, long third, long fourth, long seed, long[] secret) {
        // The length of the (combined) key to hash is 32 bytes, i.e., 4 * 64 bits.
        long len = 32;

        seed ^= mix(seed ^ secret[0], secret[1]) ^ len;
        seed = mix(first ^ secret[2], second ^ seed ^ secret[1]);

        long a = third;
        long b = fourth;

        a ^= secret[1];
        b ^= seed;

        long low = unsignedMultiplyLow(a, b);
        long high = unsignedMultiplyHigh(a, b);

        a ^= low;
        b ^= high;

        return mix(a ^ secret[0] ^ len, b ^ secret[1]);
    }

    /**
     * Hashes the given 64-bit values, using a default seed and default secrets.
     *
     * @param first The first 64-bit value to hash.
     * @param second The second 64-bit value to hash.
     * @param third The third 64-bit value to hash.
     * @param fourth The fourth 64-bit value to hash.
     * @param fifth The fifth 64-bit value to hash.
     * @return The computed hash.
     */
    public static long hash(long first, long second, long third, long fourth, long fifth) {
        return hash(first, second, third, fourth, fifth, DEFAULT_SEED, DEFAULT_SECRET);
    }

    /**
     * Hashes the given 64-bit values, using the specified seed and secrets.
     *
     * @param first The first 64-bit value to hash.
     * @param second The second 64-bit value to hash.
     * @param third The third 64-bit value to hash.
     * @param fourth The fourth 64-bit value to hash.
     * @param fifth The fifth 64-bit value to hash.
     * @param seed The seed to use for hashing.
     * @param secret The array secrets to use for hashing, which must contain three 64-bit values.
     * @return The computed hash.
     */
    public static long hash(long first, long second, long third, long fourth, long fifth, long seed, long[] secret) {
        // The length of the (combined) key to hash is 40 bytes, i.e., 5 * 64 bits.
        long len = 40;

        seed ^= mix(seed ^ secret[0], secret[1]) ^ len;
        seed = mix(first ^ secret[2], second ^ seed ^ secret[1]);
        seed = mix(third ^ secret[2], fourth ^ seed);

        long a = fourth;
        long b = fifth;

        a ^= secret[1];
        b ^= seed;

        long low = unsignedMultiplyLow(a, b);
        long high = unsignedMultiplyHigh(a, b);

        a ^= low;
        b ^= high;

        return mix(a ^ secret[0] ^ len, b ^ secret[1]);
    }

    /**
     * Hashes the given 64-bit values, using a default seed and default secrets.
     *
     * @param first The first 64-bit value to hash.
     * @param second The second 64-bit value to hash.
     * @param third The third 64-bit value to hash.
     * @param fourth The fourth 64-bit value to hash.
     * @param fifth The fifth 64-bit value to hash.
     * @param sixth The sixth 64-bit value to hash.
     * @return The computed hash.
     */
    public static long hash(long first, long second, long third, long fourth, long fifth, long sixth) {
        return hash(first, second, third, fourth, fifth, sixth, DEFAULT_SEED, DEFAULT_SECRET);
    }

    /**
     * Hashes the given 64-bit values, using the specified seed and secrets.
     *
     * @param first The first 64-bit value to hash.
     * @param second The second 64-bit value to hash.
     * @param third The third 64-bit value to hash.
     * @param fourth The fourth 64-bit value to hash.
     * @param fifth The fifth 64-bit value to hash.
     * @param sixth The sixth 64-bit value to hash.
     * @param seed The seed to use for hashing.
     * @param secret The array secrets to use for hashing, which must contain three 64-bit values.
     * @return The computed hash.
     */
    public static long hash(long first, long second, long third, long fourth, long fifth, long sixth, long seed,
            long[] secret)
    {
        // The length of the (combined) key to hash is 48 bytes, i.e., 6 * 64 bits.
        long len = 48;

        seed ^= mix(seed ^ secret[0], secret[1]) ^ len;
        seed = mix(first ^ secret[2], second ^ seed ^ secret[1]);
        seed = mix(third ^ secret[2], fourth ^ seed);

        long a = fifth;
        long b = sixth;

        a ^= secret[1];
        b ^= seed;

        long low = unsignedMultiplyLow(a, b);
        long high = unsignedMultiplyHigh(a, b);

        a ^= low;
        b ^= high;

        return mix(a ^ secret[0] ^ len, b ^ secret[1]);
    }

    /**
     * Hashes the given 64-bit values, using a default seed and default secrets.
     *
     * @param first The first 64-bit value to hash.
     * @param second The second 64-bit value to hash.
     * @param third The third 64-bit value to hash.
     * @param fourth The fourth 64-bit value to hash.
     * @param fifth The fifth 64-bit value to hash.
     * @param sixth The sixth 64-bit value to hash.
     * @param seventh The seventh 64-bit value to hash.
     * @return The computed hash.
     */
    public static long hash(long first, long second, long third, long fourth, long fifth, long sixth, long seventh) {
        return hash(first, second, third, fourth, fifth, sixth, seventh, DEFAULT_SEED, DEFAULT_SECRET);
    }

    /**
     * Hashes the given 64-bit values, using the specified seed and secrets.
     *
     * @param first The first 64-bit value to hash.
     * @param second The second 64-bit value to hash.
     * @param third The third 64-bit value to hash.
     * @param fourth The fourth 64-bit value to hash.
     * @param fifth The fifth 64-bit value to hash.
     * @param sixth The sixth 64-bit value to hash.
     * @param seventh The seventh 64-bit value to hash.
     * @param seed The seed to use for hashing.
     * @param secret The array secrets to use for hashing, which must contain three 64-bit values.
     * @return The computed hash.
     */
    public static long hash(long first, long second, long third, long fourth, long fifth, long sixth, long seventh,
            long seed, long[] secret)
    {
        // The length of the (combined) key to hash is 56 bytes, i.e., 7 * 64 bits.
        long len = 56;

        seed ^= mix(seed ^ secret[0], secret[1]) ^ len;

        long see1 = seed, see2 = seed;
        seed = mix(first ^ secret[0], second ^ seed);
        see1 = mix(third ^ secret[1], fourth ^ see1);
        see2 = mix(fifth ^ secret[2], sixth ^ see2);
        seed ^= see1 ^ see2;

        long a = sixth;
        long b = seventh;

        a ^= secret[1];
        b ^= seed;

        long low = unsignedMultiplyLow(a, b);
        long high = unsignedMultiplyHigh(a, b);

        a ^= low;
        b ^= high;

        return mix(a ^ secret[0] ^ len, b ^ secret[1]);
    }

    /**
     * Hashes the given 64-bit values, using a default seed and default secrets.
     *
     * @param first The first 64-bit value to hash.
     * @param second The second 64-bit value to hash.
     * @param third The third 64-bit value to hash.
     * @param fourth The fourth 64-bit value to hash.
     * @param fifth The fifth 64-bit value to hash.
     * @param sixth The sixth 64-bit value to hash.
     * @param seventh The seventh 64-bit value to hash.
     * @param eighth The eighth 64-bit value to hash.
     * @return The computed hash.
     */
    public static long hash(long first, long second, long third, long fourth, long fifth, long sixth, long seventh,
            long eighth)
    {
        return hash(first, second, third, fourth, fifth, sixth, seventh, eighth, DEFAULT_SEED, DEFAULT_SECRET);
    }

    /**
     * Hashes the given 64-bit values, using the specified seed and secrets.
     *
     * @param first The first 64-bit value to hash.
     * @param second The second 64-bit value to hash.
     * @param third The third 64-bit value to hash.
     * @param fourth The fourth 64-bit value to hash.
     * @param fifth The fifth 64-bit value to hash.
     * @param sixth The sixth 64-bit value to hash.
     * @param seventh The seventh 64-bit value to hash.
     * @param eighth The eighth 64-bit value to hash.
     * @param seed The seed to use for hashing.
     * @param secret The array secrets to use for hashing, which must contain three 64-bit values.
     * @return The computed hash.
     */
    public static long hash(long first, long second, long third, long fourth, long fifth, long sixth, long seventh,
            long eighth, long seed, long[] secret)
    {
        // The length of the (combined) key to hash is 64 bytes, i.e., 8 * 64 bits.
        long len = 64;

        seed ^= mix(seed ^ secret[0], secret[1]) ^ len;

        long see1 = seed, see2 = seed;
        seed = mix(first ^ secret[0], second ^ seed);
        see1 = mix(third ^ secret[1], fourth ^ see1);
        see2 = mix(fifth ^ secret[2], sixth ^ see2);
        seed ^= see1 ^ see2;

        long a = seventh;
        long b = eighth;

        a ^= secret[1];
        b ^= seed;

        long low = unsignedMultiplyLow(a, b);
        long high = unsignedMultiplyHigh(a, b);

        a ^= low;
        b ^= high;

        return mix(a ^ secret[0] ^ len, b ^ secret[1]);
    }

    /**
     * Gives the least significant 64 bits of the unsigned 128-bit product of the given two 64-bit factors.
     *
     * @param left The left 64-bit factor.
     * @param right The right 64-bit factor.
     * @return The least significant 64 bits of the 128-bit result of multiplying the given two 64-bit factors.
     */
    static long unsignedMultiplyLow(long left, long right) {
        return left * right;
    }

    /**
     * Gives the most significant 64 bits of the unsigned 128-bit product of the given two 64-bit factors.
     *
     * @param left The left 64-bit factor.
     * @param right The right 64-bit factor.
     * @return The most significant 64 bits of the 128-bit result of multiplying the given two 64-bit factors.
     */
    static long unsignedMultiplyHigh(long left, long right) {
        return Math.multiplyHigh(left, right) + ((left >> 63) & right) + ((right >> 63) & left);
    }

    /**
     * Mixes two given 64-bit values into a single 64-bit value, for hashing purposes.
     *
     * @param left The first 64-bit value.
     * @param right The second 64-bit value.
     * @return The mixed 64-bit value.
     */
    private static long mix(long left, long right) {
        long low = unsignedMultiplyLow(left, right);
        long high = unsignedMultiplyHigh(left, right);

        left ^= low;
        right ^= high;

        return left ^ right;
    }
}
