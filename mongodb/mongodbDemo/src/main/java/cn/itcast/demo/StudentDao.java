package cn.itcast.demo;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * 学员数据访问层
 * @author Administrator
 *
 */
public class StudentDao {
	
	public void save( String name,String sex,Double age,String address  ){
		MongoDatabase database = MongoManager.getDatabase();//获得数据库
		MongoCollection<Document> collection = database.getCollection("student2");
	
		Document doc=new Document();
		doc.put("name", name);
		doc.put("sex", sex);
		doc.put("age", age);
		doc.put("address", address);
			
		collection.insertOne(doc);		
	}

}
