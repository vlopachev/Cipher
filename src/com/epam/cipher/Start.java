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

        String enctiptText = cipher.encript(properties.getProperty("text"));

        System.out.println(enctiptText);

        String decriptText = cipher.decript(enctiptText);

        System.out.println(decriptText);
    }
}
