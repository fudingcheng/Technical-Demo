package sitelog;

import java.util.HashMap;
import java.util.Map;

import com.huasheng.sitelog.SiteLogUtil;
import com.mongodb.util.JSON;

public class Test4 {

	public static void main(String[] args) {
		Map<String, Object> map=new HashMap();
		map.put("goodsid", "123456");
		Map<String, Object> m = SiteLogUtil.listPage("browseLog", map, 2, 10);
		String json = JSON.serialize(m);
		System.out.println(json);
	}

}
