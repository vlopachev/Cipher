package com.epam.cipher;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FourSquareCipher {
    public static final int SQUARES = 4;
    private PropertyManager propertyManager;
    private int[] sizeMatrix;
    private String[] key;
    private char[][] topLeftSquareMatrix;
    private char[][] bottomLeftSquareMatrix;
    private char[][] topRightSquareMatrix;
    private char[][] bottomRightSquareMatrix;

    public int[] getSizeMatrix() {
        return sizeMatrix;
    }

    public String[] getKey() {
        return key;
    }

    public void setKey(String[] key) {
        if (key != null && key.length == SQUARES) {
            this.key = key;
            setSizeMatrix();
            topLeftSquareMatrix = fillMatrixFromString(key[0]);
            bottomLeftSquareMatrix = fillMatrixFromString(key[1]);
            topRightSquareMatrix = fillMatrixFromString(key[2]);
            bottomRightSquareMatrix = fillMatrixFromString(key[3]);
        }
    }

    public String encrypt(String message) {
        return crypt(message, topLeftSquareMatrix, bottomRightSquareMatrix,
                bottomLeftSquareMatrix, topRightSquareMatrix);
    }

    public String decrypt(String message) {
        return crypt(message, bottomLeftSquareMatrix, topRightSquareMatrix,
                topLeftSquareMatrix, bottomRightSquareMatrix);
    }

    public List<Character> keygen() {
        List<Character> alphabet = new ArrayList<>();
        if (propertyManager != null){
            int startNumberLatinAlphabet = Integer.parseInt(propertyManager.getProperty("latin.alphabet.number.start"));
            int endNumberLatinAlphabet = Integer.parseInt(propertyManager.getProperty("latin.alphabet.number.end"));
            int startNumberCyrillicAlphabet = Integer.parseInt(propertyManager.getProperty("cyrillic.alphabet.number.start"));
            int endNumberCyrillicAlphabet = Integer.parseInt(propertyManager.getProperty("cyrillic.alphabet.number.end"));
            addCharsToAlphabet(alphabet, startNumberLatinAlphabet, endNumberLatinAlphabet);
            addCharsToAlphabet(alphabet, startNumberCyrillicAlphabet, endNumberCyrillicAlphabet);
            alphabet.add('ё');
            alphabet.add('Ё');
            Collections.shuffle(alphabet);
        }
        return alphabet;
    }

    public int[] getOptimalMatrixSize(int numberOfLetters) {
        int averageNumberOfRowsAndColumns = (int) Math.floor(Math.sqrt(numberOfLetters));
        while (numberOfLetters % averageNumberOfRowsAndColumns != 0) {
            averageNumberOfRowsAndColumns--;
        }
        return new int[] {averageNumberOfRowsAndColumns, numberOfLetters / averageNumberOfRowsAndColumns};
    }

    public void printMatrix(SquareLayout squareLayout) {
        char[][] matrix = null;
        switch (squareLayout) {
            case TOP_LEFT:
                matrix = topLeftSquareMatrix;
                break;
            case BOTTOM_LEFT:
                matrix = bottomLeftSquareMatrix;
                break;
            case TOP_RIGHT:
                matrix = topRightSquareMatrix;
                break;
            case BOTTOM_RIGHT:
                matrix = bottomRightSquareMatrix;
                break;
            default:
                throw new RuntimeException("NotSupportedSquareLayout");
        }
        for (int i = 0; i < sizeMatrix[0]; i++) {
            for (int j = 0; j < sizeMatrix[1]; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public void setPropertyManager(PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }

    private String crypt(String message, char[][] bottomLeftSquareMatrix, char[][] topRightSquareMatrix,
                         char[][] topLeftSquareMatrix, char[][] bottomRightSquareMatrix) {
        if (propertyManager == null){
            return "NotSetPropertyManager";
        }
        String[] pairsLetters = message.split(propertyManager.getProperty("split.by.two.chars"));
        StringBuilder sbCryptMessage = new StringBuilder();
        if (Thread.currentThread().getStackTrace()[2].getMethodName() == "encrypt"){
            String pair = pairsLetters[pairsLetters.length - 1];
            if (pair.length() == 1) {
                pairsLetters[pairsLetters.length - 1] = pair + pair;
            }
        }
        for (String pairLetters : pairsLetters) {
            int[] positionFistLetterInMatrix =
                    findPositionLetterInMatrix(pairLetters.charAt(0), bottomLeftSquareMatrix);
            int[] positionSecondLetterInMatrix =
                    findPositionLetterInMatrix(pairLetters.charAt(1), topRightSquareMatrix);
            sbCryptMessage.append(topLeftSquareMatrix[positionSecondLetterInMatrix[0]][positionFistLetterInMatrix[1]]);
            sbCryptMessage.append(bottomRightSquareMatrix[positionFistLetterInMatrix[0]][positionSecondLetterInMatrix[1]]);
        }
        return sbCryptMessage.toString();
    }

    private void addCharsToAlphabet(List<Character> alphabet, int i2, int i3) {
        for (int i = i2; i <= i3; i++) {
            alphabet.add((char) i);
        }
    }

    private int[] findPositionLetterInMatrix(char letter, char[][] matrix) {
        int[] positionLetter = new int[2];
        if (matrix != null) {
            for (int i = 0; i < sizeMatrix[0]; i++) {
                for (int j = 0; j < sizeMatrix[1]; j++) {
                    if (letter == matrix[i][j]) {
                        positionLetter[0] = i;
                        positionLetter[1] = j;
                        break;
                    }
                }
            }
        }
        return positionLetter;
    }

    private void setSizeMatrix() {
        this.sizeMatrix = getOptimalMatrixSize(key[0].length());
    }

    private char[][] fillMatrixFromString(String alphabet) {
        char[][] matrix = new char[sizeMatrix[0]][sizeMatrix[1]];
        char[] array = alphabet.toCharArray();
        int counter = 0;
        for (int i = 0; i < sizeMatrix[0]; i++) {
            for (int j = 0; j < sizeMatrix[1]; j++) {
                if (counter < array.length) {
                    matrix[i][j] = array[counter];
                    counter++;
                }
            }
        }
        return matrix;
    }
}
