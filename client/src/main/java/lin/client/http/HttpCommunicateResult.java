package lin.client.http;

import lin.util.thread.AutoResetEvent;


/**
 * 
 * @author 王江林
 * @date 2013-7-16 下午12:02:52
 *
 */
public class HttpCommunicateResult {
	public static final long ABORT_CODE = 0x2001000;
    //private AutoResetEvent are;
    //private System.Action abort;

    //HttpCommunicateResult(AutoResetEvent are, System.Action abort)
//	ReentrantLock lock = new ReentrantLock();
//	Condition condition = lock.newCondition();
	HttpRequest request ;
	HttpCommunicateResult()
    {
		//this.lock = lock;
        //this.are = are;
        //this.abort = abort;
    }

    private Boolean _result = null;

	public AutoResetEvent set;

    public void Abort()
    {
    	request.abort();	
//        if (abort != null)
//        {
//            abort();
//        }
    }

    public void WaitForEnd()
    {
    	set.waitOne();
//    	lock.lock();
//    	if(this._result == null){
//	    	try {
//				condition.await();
//			} catch (InterruptedException e) {
//			}
//	    }
//    	lock.unlock();
    }
    void setResult(boolean result){
//    	this.lock.lock();
    	this._result = result;
//    	this.condition.signalAll();
//    	this.lock.unlock();
    }
    public boolean getResult()
    {
    	this.WaitForEnd();
    	return this._result;
//        get
//        {
//            if (are != null)
//            {
//                are.WaitOne();
//            }
//            return _result;
//        }
//        internal set
//        {
//            _result = value;
//        }
    }
}
