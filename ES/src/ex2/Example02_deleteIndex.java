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
	 * 인덱스 삭제
	 */
	
	public static void main(String[] args) throws IOException {
		
		// 연결
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1" , 9200 , "http")));
		
		
		// Index 명
		String INDEX_NAME = "movie_auto_java1";
		
		
		// 인덱스 삭제
		DeleteIndexRequest request = new DeleteIndexRequest(INDEX_NAME);
		
		DeleteIndexResponse deleteIndexResponse = client.indices().delete(request, RequestOptions.DEFAULT);
		// maven 라이브러리가 6.4 버전에서는 인덱스를 삭제하는 API가 존재(DeleteIndexResponse)
		// 하지만 7 버전부터는 해당 DeleteIndexResponse API 가 삭제되어서  6.4 버전을 사용
		
		boolean acknowledged = deleteIndexResponse.isAcknowledged();
		
		client.close();
		
		
		
		
	}

}
