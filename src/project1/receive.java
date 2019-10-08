package project1;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

public class receive extends Thread{
	public String myurl;
	public AmazonSQS sqs;
	public receive(String myurl,AmazonSQS sqs)
	{
		this.myurl=myurl;
		this.sqs=sqs;
	}
	
	public void run() {
	
	JFrame frame=new JFrame();
	JPanel panel1=new JPanel();
	JPanel panel2=new JPanel();
	JTextArea text=new JTextArea(40, 20);
	JButton button=new JButton("Ω” ’");
	
	panel1.add(text);
	panel2.add(button);

	frame.setLayout(new GridLayout(2, 1));
	frame.add(panel1);
	frame.add(panel2);
	frame.setSize(300, 300);
	frame.show();
	
	button.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// Receive messages
			System.out.println("Receiving messages from MyFifoQueue.fifo.\n");
			final ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(myurl);
	
			// Uncomment the following to provide the ReceiveRequestDeduplicationId
			//receiveMessageRequest.setReceiveRequestAttemptId("1");
			final List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
			for (final Message message : messages) {
			    System.out.println("Message");
			    System.out.println("  Body:          " + message.getBody());
			    text.append(message.getBody()+"\n");
			}
			System.out.println();
			// Delete the message
			System.out.println("Deleting the message.\n");
			final String messageReceiptHandle = messages.get(0).getReceiptHandle();
			sqs.deleteMessage(new DeleteMessageRequest(myurl, messageReceiptHandle));	
		}
	});
	
	}
	
}