package com.kh.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

// 양방향 암호화 지원하는 클래스
// java.api 기본적으로 제공하고 있음.
// javax.crypto, java.security패키지에 구성되어 있음

// 저희만의 key를 사용할 것임
// key가 진짜 중요함. Key 프로젝트에 한개만 있어야한다.
public class AESCryptor {
    
    private static SecretKey key;   // 암호화를 위한 키
    private String path;    // 파일로 저장된 암호화키의 위치
    
    public AESCryptor() {
        // 클래스가 인스턴스화 될때(생성) 기본설정을 해줘야함
        // 1. key파일이 있다면 Key파일에서 SecrectKey 객체를 불러오고 없다면 SecrectKey객체를 생성하여 파일로 저장
        
        this.path = AESCryptor.class.getResource("/").getPath();
        this.path = this.path.substring(0, this.path.indexOf("classes"));

        System.out.println(path);   // 서버시작할 때마다 이 파일이 어디에 위취되어있는지 나온다.
        
        File f = new File(this.path + "/bslove.bs");
        // key 를 저장하고 있는 파일 이름이 bsloive.bs -> Secretkey객체가 저장되어 있음
        if(f.exists()) {
            // key를 저장하는 파일이 있으면
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));){
                
                try {
                    this.key = (SecretKey) ois.readObject();
                    
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        } else {
            // SecreKey 객체를 생성
            if(key == null) {
                getGenerator(); // key값을 생성하는 메소드
            }
        }
    }

    private void getGenerator() {
        // secretkey를 생성하는 메서드
        SecureRandom ser = new SecureRandom();
        //key를 생성해주는 클래스
        KeyGenerator keygen = null;
        
        // 1. 적용할 알고리즘 AES => 키가 한개, RSA => 키가 두개 (공개키, 개인키)
        try {
            keygen = KeyGenerator.getInstance("AES");
            
            // 키값 생성
            keygen.init(128, ser);
            this.key = keygen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        // 생성된 키객체를 정해진 경로에 파일로 저장해서 관리하기
        File f = new File(this.path+"/bslove.bs");
        
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));) {
            oos.writeObject(this.key);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
            
  
    }
    
    public static String encrypt(String str) {
        
        // key를 가지고 암호화처리하는 클래스가 있음.
        // Cipher
        
        String resultValue = "";
        
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, AESCryptor.key);
            
            // 매개변수로 넘어온 값 암호화 처리하기
            byte[] encrpt =  str.getBytes(Charset.forName("UTF-8"));
            
            byte[] result =  cipher.doFinal(encrpt);
            
            resultValue =  Base64.getEncoder().encodeToString(result);
            
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        
        
        return resultValue;
    }
    
    // 생성된 키를 가지고 복호화 (원본값으로 변경)하는 메소드
    public static String decrypt(String encryptedStr) {
        
        String oriVal ="";
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, AESCryptor.key);
            
            byte[] decodeStr = Base64.getDecoder().decode(encryptedStr.getBytes(Charset.forName("UTF-8")));
            oriVal = new String(cipher.doFinal(decodeStr));
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        
        return oriVal;
    }
    
    
}
