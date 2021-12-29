package ex4;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;

public class Example04_CreateDataToIndex {

	/*
	 * �ε����� ������ �߰�
	 * ���⼭�� �ε����� �������� �ʾƵ� �ڵ����� ����
	 */
	
	public static void main(String[] args) throws IOException {
	
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
		
		// �ε�����
		String INDEX_NAME = "movie_auto_java1";
		
		// Ÿ�Ը�
		String TYPE_NAME = "_doc";
		
		// ���� Ű��
		String _id = "1";
		
		// ������ �߰�
		IndexRequest request = new IndexRequest(INDEX_NAME,TYPE_NAME,_id);
		
		
		// jsonBuilder() �� XContentFactory �� �Լ� �̹Ƿ� XContentFactory.jsonBuilder() �� �����Ѵ�. 
		request.source(XContentFactory.jsonBuilder()
				.startObject()
					.field("movieCd","20173732")
					.field("movieNm","��Ƴ��� ����")
					.field("movieNmEn","Last Child")
				.endObject()
			);
		
		
		// ��� ��ȸ
		try {
			IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		}
		catch (ElasticsearchException e) {
			if(e.status() == RestStatus.CONFLICT)
				System.out.println("���� ������ �����Ͽ����ϴ�.");
		}
		
		client.close();
	}

}
