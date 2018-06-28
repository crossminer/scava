package com.javasampleapproach.sendingmail;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
 
import com.javasampleapproach.sendingmail.mail.MailSender;
 
@SpringBootApplication
public class SpringBootSendingMailApplication implements CommandLineRunner {
	
	@Autowired
    @Qualifier("javasampleapproachMailSender")
	public MailSender mailSender;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootSendingMailApplication.class, args);
	}
 
	@Override
	public void run(String... arg0) throws Exception {
		
		String from = "crossminer.softeam@gmail.com";
		String to = "aboudeffa@gmail.com";
		String subject = "JavaMailSender";
		String body = "Just-Testing!";
		
		mailSender.sendMail(from, to, subject, body);
	}
 
}