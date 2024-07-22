package org.example.emlakburadanotification.consumer;


import lombok.extern.slf4j.Slf4j;
import org.example.emlakburadanotification.consumer.dto.NotificationDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationConsumer {

    @RabbitListener(queues = "${notification.queue}")
    public void sendNotification(NotificationDto notificationDto) {

        log.info("notification :{}", notificationDto.toString());

    }

}
