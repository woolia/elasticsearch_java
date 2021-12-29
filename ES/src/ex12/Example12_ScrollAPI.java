package ex12;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpHead;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MatchQuery;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class Example12_ScrollAPI {

	/*
	 * SCROLL API
	 * search 요청에서 많은 수의 결과 리턴을 가능하게 해준다.
	 * Scrolling 은 실시간으로 유저의 요청을 처리하기 위한 것이 아니라 대량의 데이터를 처리하기 위한것(ex 한 인덱스를 다른 구성으로 된 새로운 인덱스로 리인덱스 해주기 위함)
	 */
	
	public static void main(String[] args) throws IOException {
		
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
		
		String INDEX_NAME = "movie_auto_java1";
		String FIELD_NAME = "movieNm";
		String QUERY_TEXT = "캡틴아메리카";
		
		// 검색 쿼리 설정
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery(FIELD_NAME, QUERY_TEXT));
		
		// 스크롤 생성
		final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
		
		
		// 최초 요청
		SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
		searchRequest.source(searchSourceBuilder);
		searchRequest.scroll(scroll);
		
		
		// 최초 응답
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		String scrollId = searchResponse.getScrollId();
		SearchHit[] searchHits = searchResponse.getHits().getHits(); 
		
		for(int i =0 ; i<searchHits.length; i++) {
			System.out.println("search : " + searchHits[i]);
		}
		
		// 아래 로직은 현재로써는 필요 없는 것 같다. 아니면 엄청나게 많은 데이터가 존재할떄는 1분동안 스크롤을 내려도 다 못가져 올 수 있기 때문에
		// 아래 다음 요청을 부르는 로직은 방대한 데이터가 존재할 때 사용 
		/*
		while(searchHits != null && searchHits.length >0) {
			
			// 다음 요청
			SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
			scrollRequest.scroll(scroll);
			
			// 다음 응답
			searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
			scrollId = searchResponse.getScrollId();
			searchHits = searchResponse.getHits().getHits();
		}
		*/ 
		
		client.close();
	}

}
