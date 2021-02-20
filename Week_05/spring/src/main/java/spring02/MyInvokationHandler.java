package spring02;

import aop.TxUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvokationHandler  implements InvocationHandler {
	//|需要被代理的对象
	private Object target;
	public void setTarget(Object target) {
		this.target = target;
	}
	//|执行动态代理的所有方法时 都会被替换成如下的invoke方法(重点)
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		TxUtil tx=new TxUtil();
		tx.BeginTx();
		//|调用 target的 method方法 传入args参数（重点）(返回method方法的返回结果)
		Object result=method.invoke(target, args);
		tx.EndTx();
		return result;
	}
 
}