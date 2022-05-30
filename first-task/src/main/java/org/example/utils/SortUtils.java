package org.example.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toMap;

public final class SortUtils {

    private SortUtils() {
    }

    public static String sort(String input, List<Character> alphabet) {
        if (isEmpty(input) || input.length() == 1
                || isEmpty(alphabet)) {
            return input;
        }
        Map<Character, Integer> symbolToPosition = mapSymbolToPosition(alphabet);
        Map<Boolean, List<Character>> containsInAlphabetToChars = mapContainedInAlphabetToChars(input, symbolToPosition);

        var containsInAlphabet = true;
        List<Character> containedInAlphabetInput = containsInAlphabetToChars.get(containsInAlphabet);

        var containedInputLength = containedInAlphabetInput.size();
        var alphabetLength = alphabet.size();

        int[] charCounts = new int[alphabetLength];
        Arrays.fill(charCounts, 0);

        storeSymbolsCount(symbolToPosition, containedInAlphabetInput, containedInputLength, charCounts);

        changeSymbolCountsToStartIndexes(alphabetLength, charCounts);

        char[] containedOutput = new char[containedInputLength];

        intStreamIterateInReversedOrder(containedInputLength)
                .forEach(index -> {
                    var currentSymbol = containedInAlphabetInput.get(index);
                    var symbolPosition = symbolToPosition.get(currentSymbol);
                    containedOutput[charCounts[symbolPosition] - 1] = currentSymbol;
                    --charCounts[symbolPosition];
                });
        return appendNotAlphabetSymbolsAndCreateResult(containsInAlphabetToChars, containsInAlphabet, containedOutput);
    }

    private static String appendNotAlphabetSymbolsAndCreateResult(Map<Boolean, List<Character>> containsInAlphabetToChars, boolean containsInAlphabet, char[] containedOutput) {
        return Stream.concat(createCharStream(containedOutput), containsInAlphabetToChars.get(!containsInAlphabet).stream())
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private static Stream<Character> createCharStream(char[] containedOutput) {
        return IntStream.range(0, containedOutput.length).mapToObj(i -> containedOutput[i]);
    }

    private static IntStream intStreamIterateInReversedOrder(int containedInputLength) {
        return IntStream.iterate(containedInputLength - 1, i -> --i)
                .limit(containedInputLength);
    }

    private static void changeSymbolCountsToStartIndexes(int alphabetLength, int[] charCounts) {
        IntStream.range(1, alphabetLength)
                .forEach(index -> charCounts[index] += charCounts[index - 1]);
    }

    private static void storeSymbolsCount(Map<Character, Integer> symbolToPosition, List<Character> containedIsAlphabetInput, int containedInputLength, int[] charCounts) {
        IntStream.range(0, containedInputLength)
                .forEach(index -> ++charCounts[symbolToPosition.get(containedIsAlphabetInput.get(index))]);
    }

    private static Map<Boolean, List<Character>> mapContainedInAlphabetToChars(String input, Map<Character, Integer> symbolToPosition) {
        return input.chars()
                .mapToObj(character -> (char) character)
                .collect(partitioningBy(symbolToPosition::containsKey));
    }

    private static Map<Character, Integer> mapSymbolToPosition(List<Character> alphabet) {
        return IntStream.range(0, alphabet.size())
                .boxed()
                .collect(toMap(alphabet::get, Function.identity(), (oldValue, newValue) -> oldValue));
    }

    private static boolean isEmpty(String input) {
        return input == null || input.length() == 0;
    }

    private static boolean isEmpty(List<Character> alphabet) {
        return alphabet == null || alphabet.isEmpty();
    }
}
