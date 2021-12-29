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
	 * �ݵ�� �Ӽ��� fielddata:true �� �־�� �Ѵ�.
	 * �ش� ������ ���ؼ��� ex1 createIndex���� ���� ���� 
	 */
	
	public static void main(String[] args) throws IOException {

		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
		
		// �ε��� ��
		String INDEX_NAME = "movie_auto_java1";
		// �ݵ�� �Ӽ��� fielddata:true �� �־�� �Ѵ�.
		// �ش� ������ ���ؼ��� ex1 createIndex���� ���� ���� 
		
		// Ÿ�� ��
		String TYPE_NAME = "_doc";
		
		// �ʵ� ��
		String FIELD_NAME = "movieNm";
		
		
		// �˻� ���� ����
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchSourceBuilder.from(0);
		searchSourceBuilder.size(5);
		searchSourceBuilder.sort(new FieldSortBuilder(FIELD_NAME).order(SortOrder.DESC));
		
		
		// ��û
		SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
		searchRequest.types(TYPE_NAME);
		searchRequest.source(searchSourceBuilder);
		
		
		// ����
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
