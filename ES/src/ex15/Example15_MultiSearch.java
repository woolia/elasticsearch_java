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
	 * �˻�� �������϶� MultiSearch
	 */

	public static void main(String[] args) throws IOException {
		
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
		
		String INDEX_NAME = "movie_auto_java1";
		String TYPE_NAME = "_doc";
		String FIELD_NAME = "movieNm";
		String QUERY_TEXT = "����";
		String QUERY_TEXT2 = "ĸƾ�Ƹ޸�ī";
		
		
		MultiSearchRequest request = new MultiSearchRequest();
		
		// ù��° �˻� ��û ž��
		SearchRequest firstSearchRequest = new SearchRequest(INDEX_NAME);
		//firstSearchRequest.types(TYPE_NAME);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery(FIELD_NAME, QUERY_TEXT));
		firstSearchRequest.source(searchSourceBuilder);
		request.add(firstSearchRequest);

		
		// �ι�° �˻� ��û ž��
		SearchRequest secondSearchRequest = new SearchRequest(INDEX_NAME);
		//secondSearchRequest.types(TYPE_NAME);
		// ������ ����� ���� searchSourceBuilder ����
		
		// ������ ��
		searchSourceBuilder = new SearchSourceBuilder();
		// �ʱ�ȭ�� ����� �Ѵ�. �ȱ׷��� builder�� ������ ù���� ������ �ι��� �������� ������ �ڲ�  QUERY_TEXT2 �� ��µǴ°� 
		// new SearchSourceBuilder(); �� �ʱ�ȭ�� ����� �Ѵ�.
		
		searchSourceBuilder.query(QueryBuilders.matchQuery(FIELD_NAME, QUERY_TEXT2));
		secondSearchRequest.source(searchSourceBuilder);
		request.add(secondSearchRequest);
		
		MultiSearchResponse multiSearchResponse = client.msearch(request, RequestOptions.DEFAULT);
		// multisearch ������ MultiSearchResponse , msearch ��� 
		
		
		// listener ���
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
