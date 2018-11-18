package com.epam.cipher;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FourSquareCipher {
    public static final int SQUARES = 4;
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

    public String encript(String message){
        String[] pairsLetters = message.split("(?<=\\G.{2})");
        String pair;
        if ((pair = pairsLetters[pairsLetters.length -1]).length() == 1) {
            pairsLetters[pairsLetters.length -1] = pair + pair;
        }
        String encriptMessage = "";
        for (String pairLetters: pairsLetters){
            int[] positionFistLetterInMatrix =
                    findPositionLetterInMatrix(pairLetters.charAt(0),topLeftSquareMatrix);
            int[] positionSecondLetterInMatrix =
                    findPositionLetterInMatrix(pairLetters.charAt(1),bottomRightSquareMatrix);
            char firstEncryptLetter =
                    bottomLeftSquareMatrix[positionSecondLetterInMatrix[0]][positionFistLetterInMatrix[1]];
            char secondEncryptLetter =
                    topRightSquareMatrix[positionFistLetterInMatrix[0]][positionSecondLetterInMatrix[1]];
            encriptMessage += String.valueOf(firstEncryptLetter) + String.valueOf(secondEncryptLetter);
        }
        return encriptMessage;
    }

    public String decript(String encriptMessage){
        String message = "";
        return message;
    }

    public List<Character> keygen (TypeAlphabet typeAlphabet){
        List<Character> alphabet = new ArrayList<>();
        switch (typeAlphabet){
            case LATIN:
                addCharsToAlphabet(alphabet, 32, 126);
                break;
            case CYRILLIC:
                addCharsToAlphabet(alphabet, 1040, 1103);
                alphabet.add('ё');
                alphabet.add('Ё');
                addCharsToAlphabet(alphabet, 32, 64);
                addCharsToAlphabet(alphabet, 91, 96);
                addCharsToAlphabet(alphabet, 123, 125);
                break;
            default:
                throw new RuntimeException("NotSupportedTypeAlphabet");
        }
        Collections.shuffle(alphabet);
        return alphabet;
    }

    private void addCharsToAlphabet(List<Character> alphabet, int i2, int i3) {
        for (int i = i2; i <= i3; i++) {
            alphabet.add((char) i);
        }
    }

    public int[] getOptimalMatrixSize(int numberOfLetters){
        int averageNumberOfRowsAndColumns = (int) Math.floor(Math.sqrt(numberOfLetters));
        while (numberOfLetters % averageNumberOfRowsAndColumns != 0){
            averageNumberOfRowsAndColumns --;
        }
        return new int[] {averageNumberOfRowsAndColumns, numberOfLetters / averageNumberOfRowsAndColumns};
    }


    private int[] findPositionLetterInMatrix(char letter, char[][] matrix) {
        int[] positionLetter = new int[2];
        if (matrix != null){
            for (int i = 0; i < sizeMatrix[0]; i++) {
                for (int j = 0; j < sizeMatrix[1]; j++) {
                    if (letter == matrix[i][j]){
                        positionLetter[0] = i;
                        positionLetter[1] = j;
                        break;
                    }
                }
            }
        }
        return positionLetter;
    }

    public void printMatrix(SquareLayout squareLayout) {
        char[][] matrix = null;
        switch (squareLayout){
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
            for (int j = 0; j < sizeMatrix[0]; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private void setSizeMatrix() {
        this.sizeMatrix = getOptimalMatrixSize(key[0].length());
    }

    private char[][] fillMatrixFromString(String alphabet) {
        char[][] matrix = new char[sizeMatrix[0]][sizeMatrix[1]];
        char[] array = alphabet.toCharArray();
        int counter = 0;
        for (int i = 0; i < sizeMatrix[0]; i++) {
            for (int j = 0; j < sizeMatrix[0]; j++) {
                if (counter < array.length) {
                    matrix[i][j] = array[counter];
                    counter++;
                }
            }
        }
        return matrix;
    }
}
