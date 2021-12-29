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
	 * 데이터가 존재하는지 boolean
	 */
	
	public static void main(String[] args) throws IOException {
		
		
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
		
		// 인덱스 명
		String INDEX_NAME = "movie_auto_java";
		
		// 타입 명
		String TYPE_NAME = "_doc";
		
		// 문서 키값
		String _id = "1";
		
		GetRequest getRequest = new GetRequest(INDEX_NAME,TYPE_NAME,_id);
		boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
		
		System.out.println("데이터가 존재하나요 ? " + exists);
		
		client.close();
	}

}
