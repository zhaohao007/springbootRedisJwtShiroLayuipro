package com.hinmu.lims.util.common;



import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

/**
 * 密码加密生成器
 *
 * @author hao
 * */
public class GeneratePass {

    public static void main(String[] args) {
        String password = "admin";
        String saltstr = createSalt();

        String pwd = SHA512(password + saltstr);

        System.out.println(saltstr);
        System.out.println(pwd);


        String pwd2 = SHA512(password+saltstr);
        System.out.println(pwd2);

    }



    //生成盐(128个字符)  盐的长度应该等同于加密后字符串的长度
    public static String createSalt() {
        char[] chars="0123456789abcdefghijklmnopqrwtuvzxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] saltchars=new char[128];
        Random RANDOM = new SecureRandom();
        for(int i=0;i<128;i++)
        {
            int n=RANDOM.nextInt(62);
            saltchars[i]=chars[n];
        }
        String salt=new String(saltchars);
        return salt;
    }

    //SHA512加密(128个字符)
    public static String SHA512(String pwd) {
        String shaPwd = null;
        if (pwd != null && pwd.length() > 0) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
                messageDigest.update(pwd.getBytes());
                byte byteBuffer[] = messageDigest.digest();
                StringBuffer strHexString = new StringBuffer();
                for (int i = 0; i < byteBuffer.length; i++) {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                shaPwd = strHexString.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return shaPwd;
    }


}
