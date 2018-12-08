package cn.itcast.demo;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TestAdd {

	public static void main(String[] args) {
		//获取连接
		MongoClient client=new MongoClient();
		//得到数据库
		MongoDatabase database = client.getDatabase("itcastdb");
		//得到集合封装对象
		MongoCollection<Document> collection = database.getCollection("student");
		Map<String, Object> map=new HashMap();
		map.put("name", "铁扇公主");
		map.put("sex", "女");
		map.put("age", 35.0);
		map.put("address", "芭蕉洞");		
		Document doc=new Document(map);		
		collection.insertOne(doc);//插入一条记录
		//collection.insertMany(documents);//一次性插入多条文档
		
	}

}
