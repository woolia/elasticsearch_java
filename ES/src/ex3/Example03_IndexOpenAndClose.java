package ex3;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.close.CloseIndexResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class Example03_IndexOpenAndClose {

	/*
	 * 인덱스 오픈 및 종료
	 * 해당 인덱스가 존재해야한다. 그래서 example1 에서 movie_auto 인덱스를 생성후 example03 실행
	 */
	
	public static void main(String[] args) throws IOException {
	
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
		
		// Index 명
		String INDEX_NAME = "movie_auto";
		
		// Index 오픈
		OpenIndexRequest requestOpen = new OpenIndexRequest(INDEX_NAME);
		
		OpenIndexResponse openIndexResponse = client.indices().open(requestOpen,RequestOptions.DEFAULT);
		boolean ackOpen = openIndexResponse.isAcknowledged();
		
		
		// Index 닫기
		CloseIndexRequest requestClose = new CloseIndexRequest(INDEX_NAME);
		
		CloseIndexResponse closeIndexResponse = client.indices().close(requestClose, RequestOptions.DEFAULT);
		boolean ackClose = closeIndexResponse.isAcknowledged();
		
		client.close();
	}

}
