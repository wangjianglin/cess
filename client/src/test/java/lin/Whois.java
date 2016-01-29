package lin;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Whois {
	private static final int DEFAULT_PORT = 43;  
    
    public String query(String domain) throws Exception {  
        String server = "";  
        String tld = getTLD(domain);  
        if ("com".equals(tld)) {  
            server = "whois.verisign-grs.com";  
        } else if ("net".equals(tld)) {  
            server = "whois.verisign-grs.com";  
        } else if ("org".equals(tld)) {  
            server = "whois.pir.org";  
        } else if ("cn".equals(tld)) {  
            server = "whois.cnnic.cn";  
        } else if ("jp".equals(tld)) {  
            server = "whois.jprs.jp";  
        } else if ("kr".equals(tld)) {  
            server = "whois.kr";  
        }  
        return query(domain, server);  
    }  
      
    public String query(String domain, String server) throws Exception {  
        Socket socket = new Socket(server, DEFAULT_PORT);  
//        String lineSeparator = "\r\n";  
  
        PrintWriter out = new PrintWriter(socket.getOutputStream());  
        out.println(domain);  
        out.flush();  
  
//        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));  
//        StringBuilder ret = new StringBuilder();  
//        String line;  
//        while ((line = in.readLine()) != null) {  
//            ret.append(line + lineSeparator);  
//        }  
        
        ByteArrayOutputStream ret = new ByteArrayOutputStream();
        InputStream _in = socket.getInputStream();
        byte[] bs = new byte[1024];
        int count = 0;
        while((count = _in.read(bs)) != -1){
        	ret.write(bs, 0, count);
        }
        
        socket.close();  
        return ret.toString();  
    }  
      
    private String getTLD(String domain) {  
        final int index;  
        return (domain == null || (index = domain.lastIndexOf('.') + 1) < 1) ? domain  
                : (index < (domain.length())) ? domain.substring(index) : "";  
    }  
       static Whois w = new Whois();
     private static  long start = 0;
     private static  long count = 0;
    public static void main(String[] args) throws Exception {  
         
 
//        String whois = w.query("a-gx.com");
//        System.out.println("w:"+whois);
//        System.out.println("ok:" + whois.contains("No match for "));
//        for()
//    	print = new PrintStream(new FileOutputStream(new File("log.txt"), true),true);
//    	printError = new PrintStream(new FileOutputStream(new File("log-error.txt"), true),true);
    	print = System.out;
    	printError = System.out;
//        whois(Integer.parseInt(args[0]));
    	whois(2);
//        System.out.println("-------------------------");
//        pool.shutdown();
    }  
    
    private static void whois(int l){
    	char[] ch = new char[l];
    	for(int n=0;n<l;n++){
    		ch[n] = 'a';
    	}
    	whoisImpl(ch,0);
    	while(threadSize != 0){
    		try {
				Thread.sleep(1000);
//				print.println("thead size:"+threadSize);
			} catch (InterruptedException e) {
			}
    	}
    }
//    private static String whois = null;
    private static void whoisImpl(char[] ch,int index){
    	if(index == ch.length){
    		if(start <= count++){
//    		printA(ch);
    			if(count % 1000 == 0){
//    				System.out.println("count:"+count);
    				print.println("count:"+count);
    			}
    			addQuery(new String(ch));
    		}
    		return;
    	}
//    	for(int n=0;n<26;n++){
    	for(int n=0;n<chars.length - ((index == 0 || index == ch.length-1)?1:0);n++){
    		ch[index] = chars[n];
    		whoisImpl(ch,index+1);
    	}
    }
    private static final char[] chars = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','1','2','3','4','5','6','7','8','9','0','-'};
    //private static final char[] chars = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','-'};
    private static final int THREAD_SIZE = 100;
    private static ExecutorService pool = Executors.newFixedThreadPool(THREAD_SIZE);
    private static volatile int threadSize = 0;
    private static volatile Object threadLock = new Object();
//    private static 
    
    private static java.io.PrintStream print = null;// = new PrintWriter(new File("log.txt"));
    private static java.io.PrintStream printError = null;// = new PrintWriter(new File("log.txt"));
    
//    private static int ccc = 0;
    private static void addQuery(final String str){
    	
    	while(threadSize >= THREAD_SIZE-3){
    		try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
			}
    	}
    	try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
		}
    	
    		synchronized (threadLock) {
				threadSize++;
			}
    	pool.execute(new Runnable(){
    		
			@Override
			public void run() {
				try {
					String whois = w.query(str+".com");
//					print.println("*************   " + c);
	    			if(whois.contains("No match for ")){
//	    		System.out.println(str + ".com:" + whois.contains("No match for "));
	    				print.println(str + ".com:" + whois.contains("No match for "));
	    			}
				} catch (Throwable e) {
//					e.printStackTrace();
					printError.println("error:"+str);
//					return;
				}
	    		try {
//					Thread.sleep(1000);
				} catch (Throwable e) {
//					e.printStackTrace();
				}

//				print.println("============   threadSize 1:" + threadSize);
	    		synchronized (threadLock) {
					threadSize--;

//					print.println("============   threadSize 2:" + threadSize);
				}
			}
    	});
//    	pool.
    }
    
//    private static void printA(char[] ch){
////    	String s = "";
//    	for(int n=0;n<ch.length;n++){
//    		System.out.print(ch[n]);
//    	}
//    	System.out.println();
//    }
}
