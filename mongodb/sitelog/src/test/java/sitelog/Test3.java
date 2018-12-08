package sitelog;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.huasheng.sitelog.SiteLogUtil;

public class Test3 {

	public static void main(String[] args) {
		
		for(int i=0;i<1000;i++){
			
			Map<String, Object> map=new HashMap();
			map.put("userid", "900"+i);//用户ID
			map.put("ip", "121.211.112.212");
			map.put("browseTime", new Date());//浏览时间
			map.put("model", "大众"+i);//型号
			map.put("goodsid", "123456");//商品ID
			map.put("price", 11.8);//价格
			map.put("remark", "八成新，快来买吧");
			
			SiteLogUtil.save("browseLog", map);
		}

	}

}
