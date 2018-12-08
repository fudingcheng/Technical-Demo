package cn.itcast.demo;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TestFindAll {

	public static void main(String[] args) {
		//获取连接
		MongoClient client=new MongoClient();
		//得到数据库
		MongoDatabase database = client.getDatabase("itcastdb");
		//得到集合封装对象
		MongoCollection<Document> collection = database.getCollection("student");
		//得到查询结果
		FindIterable<Document> find = collection.find();
		//遍历查询结果
		for(Document doc:find ){
			System.out.println("name:"+ doc.getString("name") );
			System.out.println("sex:"+doc.getString("sex"));
			System.out.println("age:"+doc.getDouble("age"));
			System.out.println("address:"+doc.getString("address"));
		}

		
		
	}

}
