package com.java.web.estateagency.model.request;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class ChatMessage {
    Long id;
    String time;
    String message;
    String user;
}