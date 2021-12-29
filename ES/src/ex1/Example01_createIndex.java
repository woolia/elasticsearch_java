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

// elastic 7 �������� index�� �����ϴµ� ������ ���ܼ� 6.8.22 �� �׽�Ʈ
// elastic ������ 7.16.2 ���� 6.8.22 �� �ٲپ����� ������ �ذ�

// ���� ����� https://www.inflearn.com/questions/12385
// _doc �� ���ְ� url �� include_type_name=true �� �ؾ��ϴµ� ���⼭�� url�� �������� ��� �ϴ� �� ���� ������ �ٿ�׷��̵� �Ͽ���.
// �׷��� ������� �����Ͽ����� index�� ������ �� �ְ� �Ǿ���.



// ���� �Ȱ��� �ε����� �����ϸ� ���� �̸��� �ε����� �� ���� �� ����.

public class Example01_createIndex {
	
	/*
	 *  �ε��� ���� �� ���λ���
	 */
	
	public static void main(String[] args) throws IOException {
		
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1" , 9200 , "http")));
		
		// Index ��
		String INDEX_NAME = "movie_auto_java3";
		
		// Ÿ�Ը�
		String TYPE_NAME = "_doc";
		
		// ��������
		XContentBuilder indexBuilder = XContentFactory.jsonBuilder()
				.startObject()
					.startObject(TYPE_NAME)
						.startObject("properties")
							.startObject("movieCd")
								.field("type","keyword")
								.field("store","true")
								.field("index_options","docs")
								//  match ������ ����ϱ� ���ؼ��� fielddata:true �Ӽ��� �ʿ��ϴ�
								// fielddata:true �� type�� keyword �� ������ �������� ���ϰ� text �ϋ��� ���������ϴ�.
								// field Ÿ���� keyword �̸� fielddata=true �Ӽ��� ���� ���ϴ� �� ����.
								// movieCd �� .field("fielddata",true) �� ����� �ٷ� �ε��� ������ 
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
		
		
		// ���� ����
		CreateIndexRequest request = new CreateIndexRequest(INDEX_NAME);
		request.mapping(TYPE_NAME, indexBuilder);
		
		// Alias ����
		String ALIAS_NAME = "movie_auto_alias";
		request.alias(new Alias(ALIAS_NAME));
		// import org.elasticsearch.action.admin.indices.alias.Alias;
		
		
		// �ε��� ����
		CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
		
		boolean acknowledged = createIndexResponse.isAcknowledged();
		
		client.close();
	}

}
