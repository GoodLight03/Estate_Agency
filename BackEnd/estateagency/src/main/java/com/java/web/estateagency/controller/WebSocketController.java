package com.java.web.estateagency.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.java.web.estateagency.entity.Chat;
import com.java.web.estateagency.entity.Message;
import com.java.web.estateagency.entity.User;
import com.java.web.estateagency.exception.ChatNotFoundException;
import com.java.web.estateagency.model.request.ChatMessage;
import com.java.web.estateagency.model.request.CreateMessageRequest;
import com.java.web.estateagency.service.ChatService;
import com.java.web.estateagency.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class WebSocketController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserService userService;

    /**
     * @DestinationVariable, anotación que indica que un parámetro de método debe
     * estar
     * vinculado a una variable de plantilla en una cadena de plantilla de destino.
     * Compatible con métodos de manejo de mensajes como @MessageMapping.
     * <p>
     * Siempre se requiere una variable de plantilla @DestinationVariable.
     * 
     * @throws ChatNotFoundException
     */
    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessage chat(@DestinationVariable String roomId, ChatMessage message) throws ChatNotFoundException {
        System.out.println(message);

        CreateMessageRequest message2 = new CreateMessageRequest();
        message2.setSenderEmail(message.getUser());
        message2.setIdchat(message.getId());
        message2.setReplymessage(message.getMessage());
        log.info(message2.toString());
        chatService.addMessageV(message2);

        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        outputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String outputTime = outputFormat.format(new Date(System.currentTimeMillis()));

        return new ChatMessage(0L, outputTime, message.getMessage(),
                message.getUser());
    }

    // @MessageMapping("/chat/{roomId}")
    // @SendTo("/topic/{roomId}") // A dónde vamos a redireccionar. Canal de envío
    // de los mensajes.
    // public List<ChatMessage> chatSave(@DestinationVariable String roomId,
    // ChatMessage message) throws ChatNotFoundException {
    // System.out.println(message);
    // System.out.println("Hello"+roomId);
    // try {
    // CreateMessageRequest message2 = new CreateMessageRequest();
    // //Long userId = Long.parseLong(message.getUser());
    // //User user = userService.getUsserId(userId);
    // message2.setSenderEmail(message.getUser());
    // message2.setIdchat(message.getId());
    // message2.setReplymessage(message.getMessage());
    // log.info(message2.toString());
    // chatService.addMessageV(message2);

    // Chat chat=chatService.getById(message.getId());
    // List<ChatMessage> lst=new ArrayList<>();
    // for (Message message3 : chat.getMessageList()) {
    // ChatMessage chatMessage=ChatMessage.builder()
    // .message(message3.getReplymessage())
    // .user(message3.getSenderEmail())
    // .time(message3.getTime().toString())
    // .build();

    // lst.add(chatMessage);
    // }

    // return lst;
    // } catch (ChatNotFoundException e) {
    // // Xử lý ngoại lệ nếu cần
    // e.printStackTrace();
    // return null; // hoặc thông báo lỗi khác
    // }

    // }

    // @MessageMapping("/chat/{roomId}")
    // @SendTo("/topic/{roomId}") // A dónde vamos a redireccionar. Canal de envío
    // de los mensajes.
    // public Chat chatSave(@DestinationVariable String roomId, ChatMessage message)
    // throws ChatNotFoundException {
    // System.out.println(message);
    // System.out.println("Hello"+roomId);
    // try {
    // CreateMessageRequest message2 = new CreateMessageRequest();
    // Long userId = Long.parseLong(message.getUser());
    // User user = userService.getUsserId(userId);
    // message2.setSenderEmail(user.getUsername());
    // message2.setIdchat(Long.parseLong(roomId));
    // message2.setReplymessage(message.getMessage());
    // log.info(message2.toString());
    // chatService.addMessageV(message2);

    // return chatService.getById(Long.parseLong(roomId));
    // } catch (ChatNotFoundException e) {
    // // Xử lý ngoại lệ nếu cần
    // e.printStackTrace();
    // return null; // hoặc thông báo lỗi khác
    // }

    // }
}
