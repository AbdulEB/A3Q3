package com.company;

/* Author: Abdul El Badaoui
 * Student Number: 5745716
 * Description: This programs is the RSA cipher problem. Given n, it finds its prime factors and uses prime factors to
 * solve for phi of n, inorder to solve for a, for given b. These parameters are used in to solve for x from the given
 * equation on the assignment page which x is used to solve for the c0, c1 and c2 values
 * */

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    //parameters
    static int a, b, p, q, n;
    static String plainText;

    public static void main(String[] args) throws FileNotFoundException {
        //given parameters
        n = 18209;
        b = 3001;
        //initialize p and q
        p = 3;
        q = n/p;

        //while loop that will find the a p and q are prime factors of n
        while(n%p != 0){
            p +=2;
            q = n/p;
        }

        int phi_of_n = (p-1)*(q-1);//initialize phi of n

        a = multiplicativeInverse(phi_of_n);


        //read in the cipher text
        Scanner fileInput = new Scanner(new FileInputStream(new File("a3q3in.txt")));

        ArrayList<BigInteger> cipherText = new ArrayList<>();

        while(fileInput.hasNext()){
            cipherText.add(BigInteger.valueOf(fileInput.nextLong()));
        }

        plainText = "";

        //fecipher the message by finding x and passing it to a method
        for(BigInteger cipherCharacter: cipherText){
            int plainCharactersValue = cipherCharacter.modPow(BigInteger.valueOf(a), BigInteger.valueOf(n)).intValue();
            String plainCharacters = getCharactersString(plainCharactersValue);
            plainText += plainCharacters;
        }
        printOut();


    }
    //method that solves the plain text by segments of 3 characters
    private static String getCharactersString(int x){
        String characters = "";
        int c0, c1, c2;

        c2 = x%26;
        x = (x - c2)/26;

        c1 = x%26;
        c0 = (x - c1)/26;

        characters+=(char)(c0+65);
        characters+=(char)(c1+65);
        characters+=(char)(c2+65);
        return characters;
    }

    //method that prints out the decrypted message in a text file
    public static void printOut(){
        try {
            PrintWriter fileOutput = new PrintWriter("a3q3out.txt", "UTF-8");// output file creation
            fileOutput.println("The secret message (plaintext) is: ");
            fileOutput.println();
            fileOutput.println(plainText);
            fileOutput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //method multiplicative inverse to assist in solving for a
    public static int multiplicativeInverse(int phi_of_n){

        int phi_of_n0 = phi_of_n;
        int b0 = b;
        int a0 = 0;
        int a = 1;

        int q = phi_of_n0/b0;
        int r = phi_of_n0 - q * b0;

        while(r > 0){
            int temp = (a0 - q * a)%phi_of_n;
            a0 = a;
            a = temp;
            phi_of_n0 = b0;
            b0 = r;
            q = phi_of_n0/b0;
            r = phi_of_n0 - q * b0;
        }

        if(b0 != 1)
            return 0;
        else
            return a;
    }

}
