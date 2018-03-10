package io.cess.cloud.config;

/**
 * @author lin
 * @date 28/06/2017.
 */
public class Util {

    public static String openId(String openidKey,String username,String clientId){
        String openId = "oid:"+username+"-"+clientId;

        try {
            openId = new io.cess.util.AES.B256(openidKey).encrypt(openId);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return openId;
    }
}
