package com.epam.cipher;

public class Start {
    public static void main(String[] args) {
        PropertyManager propertyManager = new PropertyManager();
        propertyManager.setProperties("cipher.properties");
        String [] fourMixedAlphabetForSquares = new String[FourSquareCipher.SQUARES];
        fourMixedAlphabetForSquares[0] = propertyManager.getProperty("top.left.square");
        fourMixedAlphabetForSquares[1] = propertyManager.getProperty("bottom.left.square");
        fourMixedAlphabetForSquares[2] = propertyManager.getProperty("top.right.square");
        fourMixedAlphabetForSquares[3] = propertyManager.getProperty("bottom.right.square");
        FourSquareCipher cipher = new FourSquareCipher();
        cipher.setPropertyManager(propertyManager);
        cipher.setKey(fourMixedAlphabetForSquares);
        String encrypt = cipher.encrypt(propertyManager.getProperty("text"));
        System.out.println(encrypt);
        String decrypt = cipher.decrypt(encrypt);
        System.out.println(decrypt);
    }
}
