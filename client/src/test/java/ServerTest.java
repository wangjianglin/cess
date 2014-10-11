import java.net.ServerSocket;
import java.net.Socket;


public class ServerTest {

	public static void main(String[] args) throws Exception{
		ServerSocket server = new ServerSocket(8080);
		Socket client = null;
		byte[] bs = new byte[4096];
		while(true){
			client = server.accept();
			client.getOutputStream().write("Hello World!".getBytes());
			try{client.getInputStream().read(bs);
				System.out.println("str:"+new String(bs));
			}catch(Exception e){
				e.printStackTrace();
			}
			System.out.println("---"+client.getInetAddress().getHostAddress());
		}
	}
}
