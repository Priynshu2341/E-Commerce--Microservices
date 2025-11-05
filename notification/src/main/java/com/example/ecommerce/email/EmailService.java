package com.example.ecommerce.email;

import com.example.ecommerce.entity.EmailTemplates;
import com.example.ecommerce.kafka.order.OrderConfirmation;
import com.example.ecommerce.kafka.payment.PaymentNotificationRequest;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine engine;

    @Async
    public void sendPaymentSuccessEmail(PaymentNotificationRequest pr) {
        final String templateName = EmailTemplates.PAYMENT_CONFIRMATION.getTemplate();
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());

            helper.setFrom("contact@venomcoding.com");
            helper.setTo(pr.customerEmail());
            helper.setSubject(EmailTemplates.PAYMENT_CONFIRMATION.getSubject());

            Context ctx = new Context();
            ctx.setVariable("paymentNotificationRequest", pr); // matches template usage

            String html = engine.process(templateName, ctx);
            helper.setText(html, true);

            mailSender.send(message);
            log.info("PaymentSuccessEmail sent to {} with template {}", pr.customerEmail(), templateName);

        } catch (org.springframework.mail.MailException e) {
            log.error("Mail send failed (SMTP/auth). to={} template={} cause={}",
                    pr.customerEmail(), templateName, e.getMessage(), e);
        } catch (org.thymeleaf.exceptions.TemplateInputException e) {
            log.error("Template not found/invalid. template={} to={} cause={}",
                    templateName, pr.customerEmail(), e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error while sending payment email. to={} template={} cause={}",
                    pr.customerEmail(), templateName, e.getMessage(), e);
        }
    }

    @Async
    public void sendOrderSuccessEmail(OrderConfirmation oc) {
        final String templateName = EmailTemplates.ORDER_CONFIRMATION.getTemplate();
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());

            helper.setFrom("contact@venomcoding.com");
            helper.setTo(oc.customer().email());
            helper.setSubject(EmailTemplates.ORDER_CONFIRMATION.getSubject());

            Context ctx = new Context();
            ctx.setVariable("orderConfirmation", oc); // matches template usage

            String html = engine.process(templateName, ctx);
            helper.setText(html, true);

            mailSender.send(message);
            log.info("Order Confirmation Email sent to {} with template {}", oc.customer().email(), templateName);

        } catch (org.springframework.mail.MailException e) {
            log.error("Mail send failed. to={} template={} cause={}",
                    oc.customer().email(), templateName, e.getMessage(), e);
        } catch (org.thymeleaf.exceptions.TemplateInputException e) {
            log.error("Template not found/invalid. template={} to={} cause={}",
                    templateName, oc.customer().email(), e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error while sending order email. to={} template={} cause={}",
                    oc.customer().email(), templateName, e.getMessage(), e);
        }
    }
}
