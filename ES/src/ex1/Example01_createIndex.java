package ex1;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

// elastic 7 버전부터 index를 생성하는데 문제가 생겨서 6.8.22 로 테스트
// elastic 버전을 7.16.2 에서 6.8.22 로 바꾸었더니 에러가 해결

// 에러 내용들 https://www.inflearn.com/questions/12385
// _doc 를 없애고 url 에 include_type_name=true 로 해야하는데 여기서는 url를 보낼수가 없어서 하는 수 없이 버전을 다운그레이드 하였다.
// 그래서 예제대로 실행하였더니 index를 생성할 수 있게 되었다.



// 또한 똑같은 인덱스가 존재하면 같은 이름의 인덱스를 또 만들 수 없다.

public class Example01_createIndex {
	
	/*
	 *  인덱스 생성 및 매핑생성
	 */
	
	public static void main(String[] args) throws IOException {
		
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1" , 9200 , "http")));
		
		// Index 명
		String INDEX_NAME = "movie_auto_java3";
		
		// 타입명
		String TYPE_NAME = "_doc";
		
		// 매핑정보
		XContentBuilder indexBuilder = XContentFactory.jsonBuilder()
				.startObject()
					.startObject(TYPE_NAME)
						.startObject("properties")
							.startObject("movieCd")
								.field("type","keyword")
								.field("store","true")
								.field("index_options","docs")
								//  match 쿼리를 사용하기 위해서는 fielddata:true 속성이 필요하다
								// fielddata:true 는 type이 keyword 인 곳에서 생성하지 못하고 text 일떄는 생성가능하다.
								// field 타입이 keyword 이면 fielddata=true 속성을 지정 못하는 것 같다.
								// movieCd 만 .field("fielddata",true) 를 지우니 바로 인덱스 생성함 
							.endObject()
							.startObject("movieNm")
								.field("type","text")
								.field("store","true")
								.field("index_options","docs")
								.field("fielddata","true")
							.endObject()
							.startObject("movieNmEn")
								.field("type","text")
								.field("store","true")
								.field("index_options","docs")
								.field("fielddata","true")
							.endObject()
						.endObject()
					.endObject()
				.endObject();
		
		
		// 매핑 설정
		CreateIndexRequest request = new CreateIndexRequest(INDEX_NAME);
		request.mapping(TYPE_NAME, indexBuilder);
		
		// Alias 설정
		String ALIAS_NAME = "movie_auto_alias";
		request.alias(new Alias(ALIAS_NAME));
		// import org.elasticsearch.action.admin.indices.alias.Alias;
		
		
		// 인덱스 생성
		CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
		
		boolean acknowledged = createIndexResponse.isAcknowledged();
		
		client.close();
	}

}
