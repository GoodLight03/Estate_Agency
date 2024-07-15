package com.java.web.estateagency.model.request;

import lombok.*;

@Data
@AllArgsConstructor
public class ChatMessage {
    String message;
    String user;
}