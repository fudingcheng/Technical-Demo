package cn.itcast.demo;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class QueueConsumer {

	public static void main(String[] args) throws JMSException, IOException {
		//1.创建连接工厂
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://192.168.25.135:61616");
		//2.创建连接
		Connection connection = connectionFactory.createConnection();
		//3.启动连接
		connection.start();
		//4.获取session(会话对象)  参数1：是否启动事务  参数2：消息确认方式
		final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		//5.创建队列对象
		Queue queue = session.createQueue("test-queue");

		//6.创建消息消费者对象
		MessageConsumer consumer = session.createConsumer(queue);

		//7.设置监听
		consumer.setMessageListener(new MessageListener() {

			public void onMessage(Message message) {
				TextMessage textMessage=(TextMessage)message;
				try {
					System.out.println("提取的消息："+ textMessage.getText() );
					textMessage.acknowledge();
				} catch (JMSException e) {
					e.printStackTrace();
				}

			}
		});
		//8.等待键盘输入
		//System.in.read();
		while (true){

		}

		//9.关闭资源

		//session.close();
		//connection.close();
	}

}
