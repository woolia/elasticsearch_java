package ex5;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class Example05_GetAPI {

	/*
	 * GET API
	 */
	
	public static void main(String[] args) throws IOException {
	
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
		
		// �ε��� ��
		String INDEX_NAME = "movie_auto_java";
		
		// Ÿ�Ը�
		String TYPE_NAME = "_doc";
		
		// ���� Ű��
		String _id = "1";
		
		// ��û
		GetRequest request = new GetRequest(INDEX_NAME,TYPE_NAME,_id);
		
		// ����
		GetResponse response = client.get(request, RequestOptions.DEFAULT);
		
		// ������ ����� Map ���·� �����޴´�.
		if(response.isExists()) {
			long version = response.getVersion();
			Map<String,Object> sourceMap = response.getSourceAsMap(); // ���� �����͸� Map ���·� �ް� ��
			for(String keyset : sourceMap.keySet()) {
				String key = keyset;
				String value = (String)sourceMap.get(keyset);
				
				System.out.println(" key : " + key + " value : " + value);
			}
		}
		else {
			System.out.println("����� �������� �ʽ��ϴ�.");
		}

		client.close();
	}

}
