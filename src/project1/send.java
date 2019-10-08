package project1;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

public class send extends Thread{
	public String myurl;
	public AmazonSQS sqs;
	public send(String myurl,AmazonSQS sqs)
	{
		this.myurl=myurl;
		this.sqs=sqs;
	}
	
	public void run() {
	
	JFrame frame=new JFrame();
	JPanel panel1=new JPanel();
	JPanel panel2=new JPanel();
	JTextArea text=new JTextArea(40, 20);
	JButton button=new JButton("·¢ËÍ");
	
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
			//System.out.println("Sending a message to MyFifoQueue.fifo.\n");
			String sendMessage=text.getText();
			final SendMessageRequest sendMessageRequest = new SendMessageRequest(myurl, sendMessage);
			sendMessageRequest.setMessageGroupId("messageGroup1");
			final SendMessageResult sendMessageResult = sqs.sendMessage(sendMessageRequest);
			final String sequenceNumber = sendMessageResult.getSequenceNumber();
			final String messageId = sendMessageResult.getMessageId();
			System.out.println("SendMessage succeed with messageId " + messageId + ", sequence number " + sequenceNumber + "\n");
		}
	});
	
	}
	
}
