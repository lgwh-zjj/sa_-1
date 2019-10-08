package project1;
import java.util.List;
import java.util.Map.Entry;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
public class main {
	public static void main(String args[])
	{
		 AWSCredentialsProvider credentials = null;
		 		try {
		 			credentials = new ProfileCredentialsProvider();
		 		} 
		  catch (Exception e) {
		 		}
		 		
		AmazonSQS sqs = AmazonSQSClient.builder().withRegion("us-east-1").withCredentials(credentials).build();
		ListQueuesResult lq_result = sqs.listQueues();
		System.out.println("Your SQS Queue URLs:");
		String myurl=null;
		for (String url : lq_result.getQueueUrls()) {
		    myurl=url;
		    System.out.println(myurl);
		}

	
		send mysend=new send(myurl, sqs);
		receive myreceive=new receive(myurl, sqs);
		
		mysend.start();
		myreceive.start();
	}
}
