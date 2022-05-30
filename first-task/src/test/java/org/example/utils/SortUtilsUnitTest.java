package org.example.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SortUtilsUnitTest {

    @Test
    void shouldReturnNullInInputIsNull() {
        assertNull(SortUtils.sort(null, List.of('x', 'b', 'f')));
    }

    @Test
    void shouldReturnEmptyInInputIsEmpty() {
        assertEquals("", SortUtils.sort("", List.of('a', 'b')));
    }

    @Test
    void shouldReturnInputIfInputLengthIsOne() {
        assertEquals("x", SortUtils.sort("x", List.of('x', 'b', 'f')));
    }

    @Test
    void shouldReturnInputIfAlphabetIsEmpty() {
        String expected = "aabcde";
        assertEquals(expected, SortUtils.sort(expected, List.of()));
    }

    @Test
    void shouldSortByAlphabetAndAppendNonAlphabeticSymbols() {
        String expected = "aabcde";
        assertEquals(expected + "z", SortUtils.sort("bazcaed", List.of('a', 'b', 'c', 'd', 'e')));
        assertEquals("xbbaacaa", SortUtils.sort("abacabax", List.of('x', 'b', 'f')));
    }

    @Test
    void shouldSortByAlphabet() {
        String input = "bacaed";
        String expected = "aabcde";
        assertEquals(expected, SortUtils.sort(input, List.of('a', 'b', 'c', 'd', 'e')));
    }
}