package cn.itcast.demo;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * 按条件查询-匹配查询
 * @author Administrator
 *
 */
public class TestFindEq {

	public static void main(String[] args) {
		//获取连接
		MongoClient client=new MongoClient();
		//得到数据库
		MongoDatabase database = client.getDatabase("itcastdb");
		//得到集合封装对象
		MongoCollection<Document> collection = database.getCollection("student");
		
		//构建查询条件
		BasicDBObject bson=new BasicDBObject("name", "猪八戒");
		
		//得到查询结果
		FindIterable<Document> find = collection.find(bson);
		//遍历查询结果
		for(Document doc:find ){
			System.out.println("name:"+ doc.getString("name") );
			System.out.println("sex:"+doc.getString("sex"));
			System.out.println("age:"+doc.getDouble("age"));
			System.out.println("address:"+doc.getString("address"));
		}

		
		

	}

}
