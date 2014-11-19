package lin.client.tcp;

import org.junit.Test;

public class CommTest {

	@Test
	public void testComm() throws InterruptedException{
		 Communicate server = new Communicate((session,pack,response) ->
         {
             if (pack instanceof CommandPackage)
             {
                 CommandPackage p = (CommandPackage) pack;
                 System.out.println("pack:" + p.getCommand());
             }
             if (pack instanceof JsonPackage)
             {
                 JsonPackage json = (JsonPackage) pack;
            	 System.out.println("json:" + json.getPath());
             }
             //response(pack);
         }, 7890,new SessionListener(){

			@Override
			public void Create(Session session) {
				
			}

			@Override
			public void Destory(Session session) {
				
			}});

         Communicate client = new Communicate((session, pack,response) ->
         {

         }, "127.0.0.1", 7890);

         DetectPackage detect = new DetectPackage();
         Package r = client.send(detect).waitForEnd();
         System.out.println("----------------------------------"+r.getSequeue()+r);

         JsonTestPackage jsonPack = new JsonTestPackage();
         jsonPack.setData("test.");
         r = client.send(jsonPack).waitForEnd();
         System.out.println("----------------------------------" + r.getSequeue()+r);

         //Thread.Sleep(1000);
         client.close();

         server.close();
         //Thread.Sleep(1000);
         Thread.sleep(1000);
         System.out.println("end .");
         Thread.sleep(1000);
	}
	
	
	@Test
	public void testClientComm() throws InterruptedException{
//		 Communicate server = new Communicate((session,pack,response) ->
//         {
//             if (pack instanceof CommandPackage)
//             {
//                 CommandPackage p = (CommandPackage) pack;
//                 System.out.println("pack:" + p.getCommand());
//             }
//             if (pack instanceof JsonPackage)
//             {
//                 JsonPackage json = (JsonPackage) pack;
//            	 System.out.println("json:" + json.getPath());
//             }
//             //response(pack);
//         }, 7890,new SessionListener(){
//
//			@Override
//			public void Create(Session session) {
//				
//			}
//
//			@Override
//			public void Destory(Session session) {
//				
//			}});
//
         Communicate client = new Communicate((session, pack,response) ->
         {

         }, "127.0.0.1", 7890);

         DetectPackage detect = new DetectPackage();
         Package r = client.send(detect).waitForEnd();
         System.out.println("----------------------------------"+r.getSequeue()+r);

         JsonTestPackage jsonPack = new JsonTestPackage();
         jsonPack.setData("test.");
         r = client.send(jsonPack).waitForEnd();
         System.out.println("----------------------------------" + r.getSequeue()+r);

         //Thread.Sleep(1000);
         client.close();

//         server.close();
         //Thread.Sleep(1000);
         Thread.sleep(1000);
         System.out.println("end .");
         Thread.sleep(1000);
	}
}
