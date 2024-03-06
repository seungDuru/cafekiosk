package sample.cafekiosk.spring.api.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;

@RequiredArgsConstructor
@Service
public class MailService {

    private final MailSendClient mainSendClient;
    private final MailSendHistoryRepository mailSendHistoryRepository;

    public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {

        boolean isSuccess = mainSendClient.sendEmail(fromEmail, toEmail, subject, content);
        if (isSuccess) {
            mailSendHistoryRepository.save(
                    MailSendHistory.builder()
                            .fromEmail(fromEmail)
                            .toEmail(toEmail)
                            .subject(subject)
                            .content(content)
                            .build());

            mainSendClient.printA();
            mainSendClient.printB();
            mainSendClient.printC();

            return true;
        }

        return false;
    }
}
