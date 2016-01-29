package lin.client.http;


import lin.client.http.annotation.HttpPackageMethod;
import lin.client.http.annotation.HttpPackageReturnType;
import lin.client.http.annotation.HttpPackageUrl;
import lin.client.http.annotation.HttpParamName;
import lin.client.http.PageResult;

/**
 * 
 * @author liuxiang
 * @date 2015年8月6日 上午10:03:18
 *
 */
@HttpPackageUrl("goods/goodslist.action")
@HttpPackageMethod(HttpMethod.POST)
@HttpPackageReturnType(value = PageResult.class, parameterizedType = { GoodsGerneral.class })
public class GoodsPackage extends HttpPackage {
	
	
	@HttpParamName
	private int currentPage;
	@HttpParamName
	private int recommand;


	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public int getRecommand() {
		return recommand;
	}

	public void setRecommand(int recommandType) {
		this.recommand = recommandType;
	}

}
