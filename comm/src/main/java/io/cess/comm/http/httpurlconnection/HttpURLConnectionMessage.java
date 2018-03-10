//package io.cess.comm.http.httpurlconnection;
//
//import java.net.HttpURLConnection;
//
//import io.cess.comm.http.HttpMessage;
//
///**
// * @author lin
// * @date 1/11/16.
// */
//public class HttpURLConnectionMessage implements HttpMessage {
//
//    private final HttpURLConnection conn;
//
//    HttpURLConnectionMessage(HttpURLConnection conn){
//        this.conn = conn;
//    }
//    @Override
//    public void addHeader(String name, String value) {
//        conn.setRequestProperty(name,value);
//    }
//}
