package sample.cafekiosk.spring.client.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailSendClient {

    public boolean sendEmail(String fromEmail, String toEemail, String subject, String content) {
        log.info("메일 전송");
        throw new IllegalArgumentException("메일전송");
    }

    public void printA() {
        log.info("A");
    }

    public void printB() {
        log.info("B");
    }

    public void printC() {
        log.info("C");
    }
}
