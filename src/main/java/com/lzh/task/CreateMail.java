package com.lzh.task;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

/**
 * @author lizhenhao
 */
public class CreateMail {

    public static void main(String[] args) throws UnsupportedEncodingException, MessagingException {


        Properties properties = new Properties();

        //通过session获取
        Session session = Session.getInstance(properties);

        //通过session获取邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);


        //设置发送人
        mimeMessage.setFrom(new InternetAddress("lzhskyocean@126.com","豪懱榊","UTF-8"));

        //设置收件人
        mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress("lzhskyocean@126.com"));

        //设置主题
        mimeMessage.setSubject("测试邮件","UTF-8");

        //设置内容

        mimeMessage.setContent("<h1>这是一封测试邮件</h1>","text/html;charset=utf-8");

        //设置发送时间
        mimeMessage.setSentDate(new Date());

        //保存信息
        mimeMessage.saveChanges();


    }
}
