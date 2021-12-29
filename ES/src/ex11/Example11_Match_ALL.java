package ex11;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

public class Example11_Match_ALL {

	/*
	 * Match All API
	 * 반드시 속성에 fielddata:true 가 있어야 한다.
	 * 해당 설정을 위해서는 ex1 createIndex에서 설정 가능 
	 */
	
	public static void main(String[] args) throws IOException {

		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
		
		// 인덱스 명
		String INDEX_NAME = "movie_auto_java1";
		// 반드시 속성에 fielddata:true 가 있어야 한다.
		// 해당 설정을 위해서는 ex1 createIndex에서 설정 가능 
		
		// 타입 명
		String TYPE_NAME = "_doc";
		
		// 필드 명
		String FIELD_NAME = "movieNm";
		
		
		// 검색 쿼리 설정
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchSourceBuilder.from(0);
		searchSourceBuilder.size(5);
		searchSourceBuilder.sort(new FieldSortBuilder(FIELD_NAME).order(SortOrder.DESC));
		
		
		// 요청
		SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
		searchRequest.types(TYPE_NAME);
		searchRequest.source(searchSourceBuilder);
		
		
		// 응답
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHits searchHits = searchResponse.getHits();
		
		Map<String, Object> sourceMap;
		for(SearchHit hit : searchHits) {
			sourceMap = hit.getSourceAsMap();
			
			System.out.println(sourceMap.toString());
		}
		
		client.close();
		
	}

}
