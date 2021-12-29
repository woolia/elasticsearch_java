package ex6;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class Example06_Exist {

	/*
	 * Exisgts API
	 * �����Ͱ� �����ϴ��� boolean
	 */
	
	public static void main(String[] args) throws IOException {
		
		
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
		
		// �ε��� ��
		String INDEX_NAME = "movie_auto_java";
		
		// Ÿ�� ��
		String TYPE_NAME = "_doc";
		
		// ���� Ű��
		String _id = "1";
		
		GetRequest getRequest = new GetRequest(INDEX_NAME,TYPE_NAME,_id);
		boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
		
		System.out.println("�����Ͱ� �����ϳ��� ? " + exists);
		
		client.close();
	}

}
