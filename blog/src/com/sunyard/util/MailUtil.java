package com.sunyard.util;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * 邮件工具类
 * @author jinhui.z
 *
 */
public final class MailUtil {
	
	private MailUtil() {}

	/**
	 * 发送邮件，普通文本方式
	 * @param host 相关邮箱的主机smtp.163.com
	 * @param user 发送方用户邮箱
	 * @param password 发送方用户邮箱密码
	 * @param subject 邮件标题
	 * @param contents 邮件内容
	 * @param to 接收人邮箱
	 * @throws MessagingException
	 * @throws GeneralSecurityException
	 */
	public static void sendEmail(String host,String user,String password,String subject,String contents,String to) throws MessagingException, GeneralSecurityException{
		Properties props = new Properties();
        // 开启debug调试
        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", host);
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);

        Session session = Session.getInstance(props);

        Message msg = new MimeMessage(session);
        msg.setSubject(subject);
        StringBuilder builder = new StringBuilder();
        builder.append(contents);
        msg.setText(builder.toString());
        msg.setFrom(new InternetAddress(user));
        Transport transport = session.getTransport();
        transport.connect(host, user, password);
        transport.sendMessage(msg, new Address[] { new InternetAddress(to) });
        if(transport!=null)transport.close();
	}
}
