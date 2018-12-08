package cn.itcast.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SeckillTask {


	//秒	 分	时	日 月  星期
	@Scheduled(cron="* * * * * ?")
	public void refreshSeckillGoods() {
		System.out.println("执行了秒杀商品增量更新 任务调度" + new Date());
	}
}
