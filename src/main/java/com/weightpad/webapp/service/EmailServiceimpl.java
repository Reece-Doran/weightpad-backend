package com.weightpad.webapp.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceimpl implements EmailService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
	
	private final JavaMailSender mailSender;
	
	public EmailServiceimpl(JavaMailSender mailSender) {
		super();
		this.mailSender = mailSender;
	}
	
	
	@Override
	@Async
	public void send(String recipient, String contents) {
		try {
			MimeMessage mimeMsg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, "utf-8");
			helper.setText(contents, true);
			helper.setTo(recipient);
			helper.setSubject("Confirm your email");
			helper.setFrom("weightpad@gmail.com");
			mailSender.send(mimeMsg);
			
		}catch (MessagingException e) {
			LOGGER.error("failed to send " + e);
			throw new IllegalStateException("failed to send email");
		}
	}




}
