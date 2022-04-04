package com.detect.mutant.service.human;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class MutantDetect {

    public boolean isMutant(List<String> array) {
        int sequenceCounter = 0;
        int sequenceLen = 4;

        if (array.size() < 4) {
            return false;
        }
        for (int i = 0; i < array.size(); i++) {
            for (int j = 0; j < array.get(i).length(); j++) {
                if (hasRightSequence(sequenceLen, i, j, array)) {
                    sequenceCounter++;
                }
                if (hasDownSequence(sequenceLen, i, j, array)) {
                    sequenceCounter++;
                }
                if (hasDiagonalSequence(sequenceLen, i, j, array)) {
                    sequenceCounter++;
                }
                if (hasDiagonalInverseSequence(sequenceLen, i, j, array)) {
                    sequenceCounter++;
                }

                if (sequenceCounter > 1) {
                    System.out.println(sequenceCounter);
                    return true;
                }
            }

        }
        System.out.println(sequenceCounter);

        return false;
    }

    private boolean hasRightSequence(int sequenceLen, int row, int column, List<String> array) {
        if (column >= array.size() - (sequenceLen - 1)) {
            return false;
        }//crear sequenceLen=4
        for (int k = 1; k < sequenceLen; k++) {
            if (array.get(row).charAt(column) != array.get(row).charAt(column + k)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasDownSequence(int sequenceLen, int row, int column, List<String> array) {
        if (row >= array.size() - (sequenceLen - 1)) {
            return false;
        }
        for (int k = 1; k < sequenceLen; k++) {
            if (array.get(row).charAt(column) != array.get(row + k).charAt(column)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasDiagonalSequence(int sequenceLen, int row, int column, List<String> array) {
        if (row >= array.size() - (sequenceLen - 1) || column >= array.size() - (sequenceLen - 1)) {
            return false;
        }
        for (int k = 1; k < sequenceLen; k++) {
            if (array.get(row).charAt(column) != array.get(row + k).charAt(column + k)) {
                return false;
            }
        }
        return true;
    }

    private boolean hasDiagonalInverseSequence(int sequenceLen, int row, int column, List<String> array) {
        if (row >= array.size() - (sequenceLen - 1) || column < sequenceLen - 1) {
            return false;
        }
        for (int k = 1; k < sequenceLen; k++) {
            if (array.get(row).charAt(column) != array.get(row + k).charAt(column - k)) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasCorrectElements(List<String> dna) {
        List<Character> validChars = Arrays.asList('A', 'T', 'C', 'G');
        long count = dna.stream()
                .filter(s -> s.length() == dna.size())
                .map(String::chars)
                .flatMap(IntStream::boxed)
                .map(i -> (char) i.intValue())
                .filter(validChars::contains)
                .count();

        return count == ((long) dna.size() * dna.size());
    }

}
