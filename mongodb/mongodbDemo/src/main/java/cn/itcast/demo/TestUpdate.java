package cn.itcast.demo;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * 修改文档： 将红孩儿的address 改为南海
 * @author Administrator
 *
 */
public class TestUpdate {

	public static void main(String[] args) {
		
		//获取连接
		MongoClient client=new MongoClient();
		//得到数据库
		MongoDatabase database = client.getDatabase("itcastdb");
		//得到集合封装对象
		MongoCollection<Document> collection = database.getCollection("student");
		
		//修改的条件
		BasicDBObject bson= new BasicDBObject("name", "红孩儿");
		//修改后的值
		BasicDBObject bson2 = new BasicDBObject("$set",  new BasicDBObject("address", "南海"));
		//参数1：修改条件  参数2：修改后的值
		collection.updateOne(bson, bson2);
		//collection.updateMany(filter, update);//修改符合条件的所有记录
	}

}
