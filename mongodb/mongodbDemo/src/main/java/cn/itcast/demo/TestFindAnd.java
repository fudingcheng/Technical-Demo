package cn.itcast.demo;

import java.util.Arrays;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TestFindAnd {

	public static void main(String[] args) {
		//获取连接
		MongoClient client=new MongoClient();
		//得到数据库
		MongoDatabase database = client.getDatabase("itcastdb");
		//得到集合封装对象
		MongoCollection<Document> collection = database.getCollection("student");
		
		BasicDBObject bson1=new BasicDBObject("age", new BasicDBObject("$gte",20));
		BasicDBObject bson2=new BasicDBObject("age", new BasicDBObject("$lt",30));
		//构建查询条件
		BasicDBObject bson=new BasicDBObject("$and", Arrays.asList( bson1, bson2 )  );
		
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
