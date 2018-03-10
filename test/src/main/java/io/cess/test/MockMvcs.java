package io.cess.test;

import io.cess.util.JsonUtil;
import org.junit.Assert;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class MockMvcs {

    /**
     * 存储 oauth 2 认证信息
     */
    private static ThreadLocal<AuthInfo> authInfo = new ThreadLocal<>();

    private static ThreadLocal<Map<String,String>> httpHeader = new ThreadLocal<Map<String, String>>();

    private static ThreadLocal<Type> resultType = new ThreadLocal<>();

    private static ThreadLocal<int[]> errors = new ThreadLocal<>();

    static void setAuthInfo(AuthInfo info){
        authInfo.set(info);
    }

    public static AuthInfo getAuthInfo(){
        return authInfo.get();
    }

    static void setHttpHeader(Map<String,String> map){
        httpHeader.set(map);
    }

    public static Map<String,String> getHttpHeader(){
        return httpHeader.get();
    }

    static void setResultType(Type type){
        resultType.set(type);
    }

    public static Type getResultType(){
        return resultType.get();
    }

    static void setErrors(int[] es){
        errors.set(es);
    }

    public static int[] getErrors(){
        return errors.get();
    }

    public static <T> T result(MvcResult result) throws UnsupportedEncodingException {
        return result(result,getResultType(),getErrors());
    }

    public static <T> T result(MvcResult result, Type type, int[] errors) throws UnsupportedEncodingException {

        String str = result.getResponse().getContentAsString();

        if(errors != null && errors.length > 0){
            for(int error : errors){
                Assert.assertEquals("错误码不对！",error,result.getResponse().getStatus());
            }
        }else{
            Assert.assertTrue(str,result.getResponse().getStatus()>=0);
        }

        T r = JsonUtil.deserialize(str,type);

        return r;
    }
}
