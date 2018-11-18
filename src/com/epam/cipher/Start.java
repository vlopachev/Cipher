package com.epam.cipher;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;


public class Start {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try (
                FileInputStream fis = new FileInputStream("resources/cipher.properties")){
            properties.load(fis);
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        String [] fourMixedAlphabetForSquares = new String[FourSquareCipher.SQUARES];
        fourMixedAlphabetForSquares[0] = properties.getProperty("top.left.square");
        fourMixedAlphabetForSquares[1] = properties.getProperty("bottom.left.square");
        fourMixedAlphabetForSquares[2] = properties.getProperty("top.right.square");
        fourMixedAlphabetForSquares[3] = properties.getProperty("bottom.right.square");

        FourSquareCipher cipher = new FourSquareCipher();
        cipher.setKey(fourMixedAlphabetForSquares);
        cipher.printMatrix(SquareLayout.BOTTOM_LEFT);

        System.out.println(cipher.encript("Всем привет"));

        System.out.println((int)' ');


        for (char c = '0'; c <= '9'; c++){
            System.out.println((int)c);
        }

        System.out.println((int)' ');

        System.out.println(cipher.getOptimalMatrixSize(108)[0]);
        System.out.println(cipher.getOptimalMatrixSize(108)[1]);

        System.out.println(cipher.getSizeMatrix()[0]);
        System.out.println(cipher.getSizeMatrix()[1]);





        //System.out.println(cipher.keygen(TypeAlphabet.CYRILLIC));

        for (char ch:cipher.keygen(TypeAlphabet.CYRILLIC)) {
            System.out.print(ch);
        }

        System.out.println();

        for (char ch:cipher.keygen(TypeAlphabet.LATIN)) {
            System.out.print(ch);
        }

        System.out.println();

        System.out.println(cipher.keygen(TypeAlphabet.CYRILLIC).size());
        System.out.println(cipher.keygen(TypeAlphabet.LATIN).size());

    }
}
