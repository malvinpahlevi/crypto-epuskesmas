/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.util;

import com.je.util.AES256;

/**
 *
 * @author AJ.P
 */
public class testenkripsi {
    
    
    public static void main (String []args){
         String B = "test";
        String A = AES256.encrypt(B);
        String C = AES256.decrypt(A);
        
        System.out.println("Hasil Encrypt : " + A);
//        System.out.println("hasil decrypt " + C);
    }
    
}
