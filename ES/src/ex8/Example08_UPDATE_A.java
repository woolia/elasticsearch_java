package ex8;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

public class Example08_UPDATE_A {

	/*
	 * UPDATE API
	 * ���� 1 : ��ũ��Ʈ �̿�
	 */
	
	// �ϴ� �ȵ� 
	
	public static void main(String[] args) throws IOException {
		
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
		
		// �ε��� ��
		String INDEX_NAME = "movie_auto_java";
		
		// Ÿ�� ��
		String TYPE_NAME = "_doc";
		
		// ���� Ű��
		String _id = "1";
		
		
		/*
		 * [������Ʈ ��û1]
		 * ��ũ��Ʈ�� �̿��� ������Ʈ ���
		 */
		
		UpdateRequest request1 = new UpdateRequest(INDEX_NAME,TYPE_NAME,_id);
		
		// ���������� singletonMap("count", 10); �� ���� ������ Collections.singletonMap("count", 10); 
		Map<String , Object> parameters = Collections.singletonMap("count", 10);
		
		// import : import org.elasticsearch.script.Script;
		Script inline = new Script(ScriptType.INLINE , "painless" , "ctx._source.prdtYear += params.count" , parameters);
		request1.script(inline);
		
		try {
			UpdateResponse updateResponse = client.update(request1, RequestOptions.DEFAULT);
		}catch(ElasticsearchException e) {
			if(e.status() == RestStatus.NOT_FOUND) {
				System.out.println("������Ʈ ����� �������� �ʽ��ϴ�.");
			}
		}
		client.close();

	}

}
