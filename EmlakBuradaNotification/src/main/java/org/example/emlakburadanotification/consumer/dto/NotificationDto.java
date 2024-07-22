package org.example.emlakburadanotification.consumer.dto;

import lombok.*;
import org.example.emlakburadanotification.consumer.dto.enums.NotificationType;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class NotificationDto {

    private NotificationType notificationType;


}
