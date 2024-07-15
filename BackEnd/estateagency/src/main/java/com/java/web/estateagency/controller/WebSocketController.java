package com.java.web.estateagency.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.java.web.estateagency.model.request.ChatMessage;


@Controller
public class WebSocketController {

    /**
     * @DestinationVariable, anotación que indica que un parámetro de método debe estar
     * vinculado a una variable de plantilla en una cadena de plantilla de destino.
     * Compatible con métodos de manejo de mensajes como @MessageMapping.
     * <p>
     * Siempre se requiere una variable de plantilla @DestinationVariable.
     */
    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}") // A dónde vamos a redireccionar. Canal de envío de los mensajes.
    public ChatMessage chat(@DestinationVariable String roomId, ChatMessage message) {
        System.out.println(message);
        return new ChatMessage(message.getMessage(), message.getUser());
    }
}
