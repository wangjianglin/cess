package lin.client.tcp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(value = { ElementType.TYPE })
public @interface Command {
	public int commaand();
}
