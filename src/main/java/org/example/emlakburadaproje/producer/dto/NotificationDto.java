package org.example.emlakburadaproje.producer.dto;

import lombok.*;
import org.example.emlakburadaproje.producer.dto.enums.NotificationType;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class NotificationDto {

    private NotificationType notificationType;

}
