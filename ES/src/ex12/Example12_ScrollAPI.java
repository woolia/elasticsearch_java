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
	 * search ��û���� ���� ���� ��� ������ �����ϰ� ���ش�.
	 * Scrolling �� �ǽð����� ������ ��û�� ó���ϱ� ���� ���� �ƴ϶� �뷮�� �����͸� ó���ϱ� ���Ѱ�(ex �� �ε����� �ٸ� �������� �� ���ο� �ε����� ���ε��� ���ֱ� ����)
	 */
	
	public static void main(String[] args) throws IOException {
		
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
		
		String INDEX_NAME = "movie_auto_java1";
		String FIELD_NAME = "movieNm";
		String QUERY_TEXT = "ĸƾ�Ƹ޸�ī";
		
		// �˻� ���� ����
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery(FIELD_NAME, QUERY_TEXT));
		
		// ��ũ�� ����
		final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));
		
		
		// ���� ��û
		SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
		searchRequest.source(searchSourceBuilder);
		searchRequest.scroll(scroll);
		
		
		// ���� ����
		SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
		String scrollId = searchResponse.getScrollId();
		SearchHit[] searchHits = searchResponse.getHits().getHits(); 
		
		for(int i =0 ; i<searchHits.length; i++) {
			System.out.println("search : " + searchHits[i]);
		}
		
		// �Ʒ� ������ ����ν�� �ʿ� ���� �� ����. �ƴϸ� ��û���� ���� �����Ͱ� �����ҋ��� 1�е��� ��ũ���� ������ �� ������ �� �� �ֱ� ������
		// �Ʒ� ���� ��û�� �θ��� ������ ����� �����Ͱ� ������ �� ��� 
		/*
		while(searchHits != null && searchHits.length >0) {
			
			// ���� ��û
			SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
			scrollRequest.scroll(scroll);
			
			// ���� ����
			searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
			scrollId = searchResponse.getScrollId();
			searchHits = searchResponse.getHits().getHits();
		}
		*/ 
		
		client.close();
	}

}
