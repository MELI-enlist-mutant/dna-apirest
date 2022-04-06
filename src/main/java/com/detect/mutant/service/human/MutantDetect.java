package com.detect.mutant.service.human;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class MutantDetect {

    private static final int SEQUENCE_LEN = 4;

    public boolean isMutant(List<String> array) {
        int sequenceCounter = 0;

        if (array.size() < 4) {
            return false;
        }
        for (int i = 0; i < array.size(); i++) {
            for (int j = 0; j < array.get(i).length(); j++) {

                sequenceCounter += getNumberOfSequences(i, j, array);

                if (sequenceCounter > 1) {
                    return true;
                }
            }

        }
        return false;
    }

    private int getNumberOfSequences(int row, int column, List<String> array) {

        int internalCounter = 0;

        internalCounter += hasRightSequence(row, column, array) ? 1 : 0;
        internalCounter += hasDownSequence(row, column, array) ? 1 : 0;
        internalCounter += hasDiagonalSequence(row, column, array) ? 1 : 0;
        internalCounter += hasDiagonalInverseSequence(row, column, array) ? 1 : 0;

        return internalCounter;
    }

    private boolean hasRightSequence(int row, int column, List<String> array) {
        if (column >= array.size() - (SEQUENCE_LEN - 1)) {
            return false;
        }
        for (int k = 1; k < SEQUENCE_LEN; k++) {
            if (array.get(row).charAt(column) != array.get(row).charAt(column + k)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasDownSequence(int row, int column, List<String> array) {
        if (row >= array.size() - (SEQUENCE_LEN - 1)) {
            return false;
        }
        for (int k = 1; k < SEQUENCE_LEN; k++) {
            if (array.get(row).charAt(column) != array.get(row + k).charAt(column)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasDiagonalSequence(int row, int column, List<String> array) {
        if (row >= array.size() - (SEQUENCE_LEN - 1) || column >= array.size() - (SEQUENCE_LEN - 1)) {
            return false;
        }
        for (int k = 1; k < SEQUENCE_LEN; k++) {
            if (array.get(row).charAt(column) != array.get(row + k).charAt(column + k)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasDiagonalInverseSequence(int row, int column, List<String> array) {
        if (row >= array.size() - (SEQUENCE_LEN - 1) || column < SEQUENCE_LEN - 1) {
            return false;
        }
        for (int k = 1; k < SEQUENCE_LEN; k++) {
            if (array.get(row).charAt(column) != array.get(row + k).charAt(column - k)) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasCorrectElements(List<String> dna) {
        List<Character> validChars = Arrays.asList('A', 'C', 'G', 'T');
        long counter = dna.stream()
                .filter(elementList -> elementList.length() == dna.size())
                .map(String::chars)
                .flatMap(IntStream::boxed)
                .map(elementChar -> (char) elementChar.intValue())
                .filter(validChars::contains)
                .count();

        return counter == ((long) dna.size() * dna.size());
    }

}
