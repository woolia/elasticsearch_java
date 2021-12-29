package ex15;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.util.Asserts;
import org.elasticsearch.Assertions;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.MultiSearchResponse.Item;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

public class Example15_MultiSearch {
	
	/*
	 * 검색어가 여러개일때 MultiSearch
	 */

	public static void main(String[] args) throws IOException {
		
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
		
		String INDEX_NAME = "movie_auto_java1";
		String TYPE_NAME = "_doc";
		String FIELD_NAME = "movieNm";
		String QUERY_TEXT = "아이";
		String QUERY_TEXT2 = "캡틴아메리카";
		
		
		MultiSearchRequest request = new MultiSearchRequest();
		
		// 첫번째 검색 요청 탑재
		SearchRequest firstSearchRequest = new SearchRequest(INDEX_NAME);
		//firstSearchRequest.types(TYPE_NAME);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery(FIELD_NAME, QUERY_TEXT));
		firstSearchRequest.source(searchSourceBuilder);
		request.add(firstSearchRequest);

		
		// 두번째 검색 요청 탑재
		SearchRequest secondSearchRequest = new SearchRequest(INDEX_NAME);
		//secondSearchRequest.types(TYPE_NAME);
		// 기존에 만들어 놓은 searchSourceBuilder 재사용
		
		// 막혔던 곳
		searchSourceBuilder = new SearchSourceBuilder();
		// 초기화를 해줘야 한다. 안그러면 builder가 덮여서 첫번쨰 조건이 두번쨰 조건으로 덮여서 자꾸  QUERY_TEXT2 가 출력되는것 
		// new SearchSourceBuilder(); 로 초기화를 해줘야 한다.
		
		searchSourceBuilder.query(QueryBuilders.matchQuery(FIELD_NAME, QUERY_TEXT2));
		secondSearchRequest.source(searchSourceBuilder);
		request.add(secondSearchRequest);
		
		MultiSearchResponse multiSearchResponse = client.msearch(request, RequestOptions.DEFAULT);
		// multisearch 에서는 MultiSearchResponse , msearch 사용 
		
		
		// listener 사용
		/*
		ActionListener<MultiSearchResponse> listener = new ActionListener<MultiSearchResponse>() {
			
			@Override
			public void onResponse(MultiSearchResponse response) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(Exception e) {
				// TODO Auto-generated method stub
				
			}
		};
		
		
		MultiSearchResponse.Item firstResponse = multiSearchResponse.getResponses()[0];
		SearchResponse searchResponse = firstResponse.getResponse();
		SearchHit[] hit1 = searchResponse.getHits().getHits();
		for(int i =0; i<hit1.length ; i++) {
			System.out.println(hit1[i]);
		}
		
		MultiSearchResponse.Item secondResponse = multiSearchResponse.getResponses()[1];
		searchResponse = secondResponse.getResponse();
		SearchHit[] hit2 = searchResponse.getHits().getHits();
		for(int i =0; i<hit2.length ; i++) {
			System.out.println(hit2[i]);
		}
		*/

		
		List<SearchResponse> searchResponses = new ArrayList<>();
		for(MultiSearchResponse.Item multiSearchResponseItem : multiSearchResponse.getResponses()) {
			searchResponses.add(multiSearchResponseItem.getResponse());
		}
		
		int count = 1;
		List <Map<String, Object>> arrList = new ArrayList<>();
		
		for(SearchResponse searchSourceResponse : searchResponses) {
			
			SearchHit[] hits = searchSourceResponse.getHits().getHits();
			//Map<String, ProfileShardResult> profileResult =  searchSourceResponse.getProfileResults();
			for(SearchHit s : hits)
			  {
				  Map<String, Object> sourceMap = s.getSourceAsMap();
				  arrList.add(sourceMap);
			  }
		}
		
		for(Map<String,Object> source : arrList) {
			
			for(String _key : source.keySet()) {
				String key = _key;
				String value = (String) source.get(key);
				System.out.println("key : " + key + " value : " + value);	
			}
		}
		
		
		client.close();
		
	}

}
