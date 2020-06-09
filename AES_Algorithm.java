/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steganography;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author phong
 */
public class AES_Algorithm {
     public  String encrypt(String strToEncrypt, String myKey) {
      try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] key = myKey.getBytes("UTF-8");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
      } catch (Exception e) {
            System.out.println(e.toString());
      }
      return null;
} 
    public  String decrypt(String strToDecrypt, String myKey) {
      try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] key = myKey.getBytes("UTF-8");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
      } catch (Exception e) {
            System.out.println(e.toString());
      }
      return null;
}
    
//    public static void main(String[] args) throws UnsupportedEncodingException, IOException {
//        // TODO code application logic here
//        System.out.println("=================");
//        Scanner sc = new Scanner(System.in);
//        BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "ISO-8859-1"));
//        System.out.println("Nhap texxt:");
//        String text = in.readLine();
//        System.out.println("Nhap key: ");
//        String key = in.readLine();
//        System.out.println("out="+text);
//        String enString =encrypt(text, key);
//        System.out.println("En="+enString);
//        String deString=decrypt(enString, key);
//        System.out.println("de= "+deString);
//        
//    }
}
