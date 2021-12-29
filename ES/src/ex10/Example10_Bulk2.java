package ex10;

import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;

public class Example10_Bulk2 {

	/*
	 * Bulk API
	 * 
	 * �ϴ� �ȵ� ���� 
	 */
	
	public static void main(String[] args) throws IOException {
		
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
		
		// �ε�����
		String INDEX_NAME = "movie_auto_java1";
		
		// Ÿ�Ը�
		String TYPE_NAME = "_doc";
		
		// �ʵ��
		String FIELD_NAME = "movieNm";
		
		
		BulkProcessor.Listener bulkListener = new BulkProcessor.Listener() {
			
			@Override
			public void beforeBulk(long executionId, BulkRequest request) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
				// TODO Auto-generated method stub
				
			}
		};
		
		
		BulkProcessor.Builder builder = BulkProcessor.builder((request, listener) -> client.bulkAsync(request, RequestOptions.DEFAULT, listener),
				bulkListener);

		// 500���� ������ ����Ǹ� Elasticsearch�� ���� ��û�� �Ѵ�.
		builder.setBulkActions(500);
		
		// 1MB�̻��� ������ ���̸� Elasticsearch�� ���� ��û�� �Ѵ�.
		builder.setBulkSize(new ByteSizeValue(1L, ByteSizeUnit.MB));
		
		// 10�ʰ� �Ǹ� ������ Elasticsearch�� ���� ��û�� �Ѵ�.
		builder.setFlushInterval(TimeValue.timeValueSeconds(10L));
		
		
		BulkRequest request = new BulkRequest();
		
		String _id = "7";
		IndexRequest one = new IndexRequest(INDEX_NAME, TYPE_NAME, _id).source(XContentType.JSON,FIELD_NAME, "��Ƴ��� ����2");
		
		_id = "8";
		IndexRequest two = new IndexRequest(INDEX_NAME,TYPE_NAME,_id).source(XContentType.JSON,FIELD_NAME,"������: ���ͼ��Ǻ��2");
		
		_id = "9";
		IndexRequest three = new IndexRequest(INDEX_NAME,TYPE_NAME,_id).source(XContentType.JSON,FIELD_NAME,"ĸƾ�Ƹ޸�ī �ú���2");
		
		builder.build().add(one);
		builder.build().add(two);
		builder.build().add(three);
		
		
		BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
		
		for(BulkItemResponse bulkItemResponse : bulkResponse) {
			
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
