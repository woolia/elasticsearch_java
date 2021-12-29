package ex8;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;

public class Example08_UPDATE_B {

	/*
	 * UPDATE API
	 * ���� 2 : ������ �κ��� ������Ʈ �ϴ� ���
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
		 * [������Ʈ ��û2]
		 * ���� �κ��� ������Ʈ
		 */
		
		XContentBuilder builder = XContentFactory.jsonBuilder();
		builder.startObject();
		builder.field("createAt" , new Date()); // import java.util.Date;
		builder.field("prdtYear" , "2021");
		builder.field("typeNm", "����");
		builder.endObject();
		
		UpdateRequest request2 = new UpdateRequest(INDEX_NAME,TYPE_NAME,_id).doc(builder);
				
		
		try {
			UpdateResponse updateResponse = client.update(request2, RequestOptions.DEFAULT);
		}catch(ElasticsearchException e) {
			if(e.status() == RestStatus.NOT_FOUND) {
				System.out.println("������Ʈ ����� �������� �ʽ��ϴ�.");
			}
		}
		client.close();
		
	}

}
