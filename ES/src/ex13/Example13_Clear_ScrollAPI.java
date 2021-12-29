package ex13;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class Example13_Clear_ScrollAPI {

	/*
	 * CLEAR SCROLL API
	 */
	
	public static void main(String[] args) throws IOException {

		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
		
		String INDEX_NAME = "movie_auto_java";
		String FIELD_NAME = "movieNm";
		String QUERY_TEXT = "아이";
		
		SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
		
		
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery(FIELD_NAME, QUERY_TEXT));
		searchRequest.source(searchSourceBuilder);
		
		searchRequest.scroll(TimeValue.timeValueMinutes(1L));
		
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		String scrollId = searchResponse.getScrollId();
		
		ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
		clearScrollRequest.addScrollId(scrollId);
		
		// scroll ID가 여러개일 경우에는 setScrollIds 를 사용해서 조회 가능하다.
		// request.setScrollIds(scrollIds);
		ClearScrollResponse clearScrollResponse = client.clearScroll(clearScrollRequest,RequestOptions.DEFAULT);
		
		
		// 해당 scrollId 가 정상적으로 release 되었는지 확인 가능하다.
		
		boolean success = clearScrollResponse.isSucceeded();
		int released = clearScrollResponse.getNumFreed();
		
		if(success) {
			System.out.println("정상적으로 release 되었습니다.");
			System.out.println(released);
		}
		else {
			System.out.println("release 되지 않았습니다.");
		}
		
		client.close();
		
	}

}
