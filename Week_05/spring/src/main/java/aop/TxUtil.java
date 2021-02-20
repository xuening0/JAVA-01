package aop;

public class TxUtil {
	//|拦截器：模拟事务开始
	public void BeginTx() {
		System.out.println("==模拟开始事务==");
	}

	//|拦截器：模拟事务结束
	public void EndTx() {
		System.out.println("==模拟结束事务==");
	}
}
