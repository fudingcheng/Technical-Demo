import org.csource.fastdfs.*;
import org.junit.Test;

/**
 * Created by fudingcheng on 2018-10-07.
 */
public class Demo1 {


    @Test
    public void test1() {

        try {
        // 1、加载配置文件，配置文件中的内容就是 tracker 服务的地址。
            ClientGlobal.init("C:\\work\\workspace\\idea\\pyg\\fastDFS\\src\\main\\resources\\fdfs_client.conf");
        // 2、创建一个 TrackerClient 对象。直接 new 一个。
            TrackerClient trackerClient = new TrackerClient();
        // 3、使用 TrackerClient 对象创建连接，获得一个 TrackerServer 对象。
            TrackerServer trackerServer = trackerClient.getConnection();
        // 4、创建一个 StorageServer 的引用，值为 null
            StorageServer storageServer = null;
        // 5、创建一个 StorageClient 对象，需要两个参数 TrackerServer 对象、StorageServer的引用
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        // 6、使用 StorageClient 对象上传图片。
        //扩展名不带“.”
            String[] strings = storageClient.upload_file(
                    "C:\\work\\workspace\\idea\\pyg\\fastDFS\\src\\main\\resources\\fast.jpg",
                    "jpg",
                    null);


        // 7、返回数组。包含组名和图片的路径。
            for (String string : strings) {
                System.out.println(string);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
