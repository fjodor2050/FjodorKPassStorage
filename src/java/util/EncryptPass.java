/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class EncryptPass {
    public String getEncryptPass(String password, String salts){
        password = password + salts;
        MessageDigest m;
        try {
            m = MessageDigest.getInstance("SHA-256");
            m.update(password.getBytes(),0,password.length());
            return new BigInteger(1,m.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EncryptPass.class.getName()).log(Level.SEVERE, "Не найден алгоритм шифрования", ex);
            return null;
        }
    }
    public String getSalts(){
        Date date = new Date();
        String s = Long.toString(date.getTime());
        MessageDigest m;
        try {
            m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(),0,s.length());
            return new BigInteger(1,m.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EncryptPass.class.getName()).log(Level.SEVERE, "Не найден алгоритм шифрования", ex);
            return null;
        }
    }
}
