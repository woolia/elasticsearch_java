package ex2;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class Example02_deleteIndex {

	/*
	 * �ε��� ����
	 */
	
	public static void main(String[] args) throws IOException {
		
		// ����
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1" , 9200 , "http")));
		
		
		// Index ��
		String INDEX_NAME = "movie_auto_java1";
		
		
		// �ε��� ����
		DeleteIndexRequest request = new DeleteIndexRequest(INDEX_NAME);
		
		DeleteIndexResponse deleteIndexResponse = client.indices().delete(request, RequestOptions.DEFAULT);
		// maven ���̺귯���� 6.4 ���������� �ε����� �����ϴ� API�� ����(DeleteIndexResponse)
		// ������ 7 �������ʹ� �ش� DeleteIndexResponse API �� �����Ǿ  6.4 ������ ���
		
		boolean acknowledged = deleteIndexResponse.isAcknowledged();
		
		client.close();
		
		
		
		
	}

}
