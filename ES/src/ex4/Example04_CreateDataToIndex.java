package ex4;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;

public class Example04_CreateDataToIndex {

	/*
	 * 인덱스에 데이터 추가
	 * 여기서는 인덱스를 생성하지 않아도 자동으로 생성
	 */
	
	public static void main(String[] args) throws IOException {
	
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
		
		// 인덱스명
		String INDEX_NAME = "movie_auto_java1";
		
		// 타입명
		String TYPE_NAME = "_doc";
		
		// 문서 키값
		String _id = "1";
		
		// 데이터 추가
		IndexRequest request = new IndexRequest(INDEX_NAME,TYPE_NAME,_id);
		
		
		// jsonBuilder() 는 XContentFactory 의 함수 이므로 XContentFactory.jsonBuilder() 로 실행한다. 
		request.source(XContentFactory.jsonBuilder()
				.startObject()
					.field("movieCd","20173732")
					.field("movieNm","살아남은 아이")
					.field("movieNmEn","Last Child")
				.endObject()
			);
		
		
		// 결과 조회
		try {
			IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		}
		catch (ElasticsearchException e) {
			if(e.status() == RestStatus.CONFLICT)
				System.out.println("문서 생성에 실패하였습니다.");
		}
		
		client.close();
	}

}
