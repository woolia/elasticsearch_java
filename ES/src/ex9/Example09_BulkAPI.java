package ex9;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

public class Example09_BulkAPI {

	/*
	 * Bulk API
	 * ��뷮 ó��
	 */
	
	public static void main(String[] args) throws IOException {
		
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
		
		// �ε�����
		String INDEX_NAME = "movie_auto_java1";
		
		// Ÿ�Ը�
		String TYPE_NAME = "_doc";
		
		// �ʵ��
		String FIELD_NAME = "movieNm";
		
		
		// ������ ����
		BulkRequest request = new BulkRequest();
		
		request
			.add(new IndexRequest(INDEX_NAME,TYPE_NAME,"4")
			.source(XContentType.JSON,FIELD_NAME,"��Ƴ��� ����"));
		
		request
			.add(new IndexRequest(INDEX_NAME,TYPE_NAME,"5")
			.source(XContentType.JSON,FIELD_NAME,"������ : ���ͼ��� ���"));
		
		request
			.add(new IndexRequest(INDEX_NAME,TYPE_NAME,"6")
			.source(XContentType.JSON, FIELD_NAME, "ĸƾ�Ƹ޸�ī �ú���"));
		
		
		// ��� ��ȸ
		
		BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
		
		for(BulkItemResponse bulkItemResponse : response) {
			// bulk�� response �ϳ��� ��ü��  BulkItemResponse Ŭ�����̴�.
			
			DocWriteResponse itemResponse = bulkItemResponse.getResponse();
			
			if(bulkItemResponse.getOpType() == DocWriteRequest.OpType.INDEX || bulkItemResponse.getOpType() == DocWriteRequest.OpType.CREATE) {
				IndexResponse indexResponse = (IndexResponse) itemResponse;
			}
			else if(bulkItemResponse.getOpType() == DocWriteRequest.OpType.UPDATE) {
				UpdateResponse updateResponse = (UpdateResponse) itemResponse; 
			}
			else if(bulkItemResponse.getOpType() == DocWriteRequest.OpType.DELETE) {
				DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
			}
		}
		
		client.close();
		
	}
}
