package com.epam.cipher;

public class FourSquareCipher {
    public static final int SQUARES = 4;
    private int sizeMatrix;
    private String[] key;
    private char[][] topLeftSquareMatrix;
    private char[][] bottomLeftSquareMatrix;
    private char[][] topRightSquareMatrix;
    private char[][] bottomRightSquareMatrix;

    public int getSizeMatrix() {
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
            char firstLetter = pairLetters.charAt(0);
            char secondLetter = pairLetters.charAt(1);
            int[] positionFistLetterInMatrix =
                    findPositionLetterInMatrix(Character.toLowerCase(firstLetter),topLeftSquareMatrix);
            int[] positionSecondLetterInMatrix =
                    findPositionLetterInMatrix(Character.toLowerCase(secondLetter),bottomRightSquareMatrix);
            char firstEncryptLetter =
                    bottomLeftSquareMatrix[positionSecondLetterInMatrix[0]][positionFistLetterInMatrix[1]];
            char secondEncryptLetter =
                    topRightSquareMatrix[positionFistLetterInMatrix[0]][positionSecondLetterInMatrix[1]];
            if (Character.isUpperCase(firstLetter)){
                firstEncryptLetter = Character.toUpperCase(firstEncryptLetter);
            }
            if (Character.isUpperCase(secondLetter)){
                secondEncryptLetter = Character.toUpperCase(secondEncryptLetter);
            }
            encriptMessage += String.valueOf(firstEncryptLetter) + String.valueOf(secondEncryptLetter);
        }
        return encriptMessage;
    }

    public String decript(String encriptMessage){
        String message = "";







        return message;
    }



    private int[] findPositionLetterInMatrix(char letter, char[][] matrix) {
        int[] positionLetter = new int[2];
        if (matrix != null){
            for (int i = 0; i < sizeMatrix; i++) {
                for (int j = 0; j < sizeMatrix; j++) {
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
        for (int i = 0; i < sizeMatrix; i++) {
            for (int j = 0; j < sizeMatrix; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private void setSizeMatrix() {
        this.sizeMatrix = (int) Math.ceil(Math.sqrt(key[0].length()));
    }

    private char[][] fillMatrixFromString(String alphabet) {
        char[][] matrix = new char[sizeMatrix][sizeMatrix];
        char[] array = alphabet.toCharArray();
        int counter = 0;
        for (int i = 0; i < sizeMatrix; i++) {
            for (int j = 0; j < sizeMatrix; j++) {
                if (counter < array.length) {
                    matrix[i][j] = array[counter];
                    counter++;
                }
            }
        }
        return matrix;
    }
}
