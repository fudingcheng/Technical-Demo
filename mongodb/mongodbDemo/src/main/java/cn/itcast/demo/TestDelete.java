package cn.itcast.demo;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class TestDelete {

	public static void main(String[] args) {
		
		//获取连接
		MongoClient client=new MongoClient();
		//得到数据库
		MongoDatabase database = client.getDatabase("itcastdb");
		//得到集合封装对象
		MongoCollection<Document> collection = database.getCollection("student");
		
		BasicDBObject bson=new BasicDBObject("name", "铁扇公主");
				
		collection.deleteOne(bson);//删除记录（符合条件的第一条记录）
		//collection.deleteMany(bson);//删除符合条件的全部记录
	}
}
