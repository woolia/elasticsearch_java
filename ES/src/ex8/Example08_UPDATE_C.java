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
	 * ���� 3 : UPSERT �� �̿��� ������Ʈ , UPDATE �� INSERT �� �����ؼ� UPSERT ��� ��
	 */

	public static void main(String[] args) throws IOException {		
		
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
		
		// �ε��� ��
		String INDEX_NAME = "movie_auto_java";
		
		// Ÿ�� ��
		String TYPE_NAME = "_doc";
		
		// ���� Ű��
		String _id = "1";
		
		
		/*
		 * [������Ʈ ��û3]
		 * UPSERT�� Ȱ���� ������Ʈ
		 */
		
		_id = "2";
		IndexRequest indexRequest = new IndexRequest(INDEX_NAME, TYPE_NAME, _id)
				.source(XContentFactory.jsonBuilder()
						.startObject()
							.field("movieCd" , "20173732")
							.field("movieNm","��Ƴ��� ����")
							.field("movieNmEn" , "Last Child")
							.field("openDt" , "")
							.field("typeNm" , "����")
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
				System.out.println("������Ʈ ����� �������� �ʽ��ϴ�.");
			}
		}
		client.close();
		
	}

}
