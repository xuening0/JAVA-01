package spring02;

import interfaceTest.Dog;

import java.lang.reflect.Proxy;

public class Test {
	public static void main(String []args)
	{
		Dog target=new GunDog();
		Dog dog=(Dog)MyProxyFactory.getProxy(target);
		dog.info();
		dog.run();
		System.out.println("isProxyClass: "+Proxy.isProxyClass(dog.getClass()));
	}
	
}