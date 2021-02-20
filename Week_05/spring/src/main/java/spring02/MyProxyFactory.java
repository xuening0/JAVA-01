package spring02;

import java.lang.reflect.Proxy;

public class MyProxyFactory {
	public static Object getProxy(Object target){
		MyInvokationHandler handler=new MyInvokationHandler();
		handler.setTarget(target);
		//|创建并且返回一个动态代理
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), handler);
	}
}