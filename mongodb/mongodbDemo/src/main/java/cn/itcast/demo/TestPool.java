package cn.itcast.demo;

import java.util.Date;

public class TestPool {

	public static void main(String[] args) {
		long startTime = new Date().getTime();//开始时间
		
		StudentDao studentDao=new StudentDao();
		for(int i=0;i<20000;i++){
			studentDao.save("测试"+i, "男", 25.0, "测试地址"+i);
		}
		long endTime = new Date().getTime();//完成时间
		System.out.println("完成时间："+(endTime-startTime)+"毫秒");
	}

}
