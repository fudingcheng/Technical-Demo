package sitelog;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import com.huasheng.sitelog.SiteLogUtil;
import com.mongodb.client.FindIterable;
import com.mongodb.util.JSON;

public class Test2 {

	public static void main(String[] args) {
		Map<String, Object> map =new HashMap();
		map.put("userid", "8888");		
		FindIterable<Document> list = SiteLogUtil.list("browseLog", map);
		String json = JSON.serialize(list);
		System.out.println(json);
		
	}

}
