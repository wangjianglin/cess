package lin.client.tcp;

import lin.client.tcp.annotation.ProtocolParserType;
import lin.util.JsonUtil;

@ProtocolParserType(0)
public class ErrorPackageParser extends AbstractProtocolParser {
	public TcpPackage getPackage() {

		String s = new String(buffer,0,count);

		ErrorPackage.Data data = JsonUtil.deserialize(s,ErrorPackage.Data.class);

		return new ErrorPackage(data);
	}
}
