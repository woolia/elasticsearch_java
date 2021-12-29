package ex8;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;

public class Example08_UPDATE_C {

	/*
	 * UPDATE API
	 * 버전 3 : UPSERT 를 이용한 업데이트 , UPDATE 와 INSERT 를 같이해서 UPSERT 라고 함
	 */

	public static void main(String[] args) throws IOException {		
		
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
		
		// 인덱스 명
		String INDEX_NAME = "movie_auto_java";
		
		// 타입 명
		String TYPE_NAME = "_doc";
		
		// 문서 키값
		String _id = "1";
		
		
		/*
		 * [업데이트 요청3]
		 * UPSERT를 활용한 업데이트
		 */
		
		_id = "2";
		IndexRequest indexRequest = new IndexRequest(INDEX_NAME, TYPE_NAME, _id)
				.source(XContentFactory.jsonBuilder()
						.startObject()
							.field("movieCd" , "20173732")
							.field("movieNm","살아남은 아이")
							.field("movieNmEn" , "Last Child")
							.field("openDt" , "")
							.field("typeNm" , "장편")
						.endObject()
						);
		
		XContentBuilder upsertBuilder = XContentFactory.jsonBuilder();
		upsertBuilder.startObject();
		upsertBuilder.field("createAt" , new Date());
		upsertBuilder.endObject();
				
		
		UpdateRequest upsertRequest = new UpdateRequest(INDEX_NAME, TYPE_NAME, _id).doc(upsertBuilder).upsert(indexRequest);
		
		try {
			UpdateResponse updateResponse = client.update(upsertRequest, RequestOptions.DEFAULT);
		}catch(ElasticsearchException e) {
			if(e.status() == RestStatus.NOT_FOUND) {
				System.out.println("업데이트 대상이 존재하지 않습니다.");
			}
		}
		client.close();
		
	}

}
