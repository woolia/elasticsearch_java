package ex7;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class Example07_deleteAPI {

	/*
	 * DELETE API
	 * 데이터 삭제
	 */
	
	public static void main(String[] args) throws IOException {

		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
		
		// 인덱스 명
		String INDEX_NAME = "movie_auto_java1";
		
		// 타입 명
		String TYPE_NAME = "_doc";
		
		// 문서 키값
		String _id = "1";
		
		DeleteRequest request = new DeleteRequest(INDEX_NAME,TYPE_NAME,_id);
		DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
		
		client.close();
		
	}

}
