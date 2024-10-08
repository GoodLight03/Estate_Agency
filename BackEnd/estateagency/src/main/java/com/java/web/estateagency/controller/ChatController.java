package com.java.web.estateagency.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.web.estateagency.entity.Chat;
import com.java.web.estateagency.entity.Message;
import com.java.web.estateagency.exception.ChatAlreadyExistException;
import com.java.web.estateagency.exception.ChatNotFoundException;
import com.java.web.estateagency.exception.NoChatExistsInTheRepository;
import com.java.web.estateagency.model.request.ChatMessage;
import com.java.web.estateagency.model.request.CreateChatRoomRequest;
import com.java.web.estateagency.model.request.CreateCommentCustomerRequest;
import com.java.web.estateagency.model.request.CreateMessageRequest;
import com.java.web.estateagency.model.response.ContactDTO;
import com.java.web.estateagency.service.ChatService;

import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Tag(
        name = "REST APIs for Chat in Estateagency",
        description = "REST APIs in Estateagency to CREATE, UPDATE, FETCH AND DELETE for Chat"
)
@RestController
@RequestMapping("/api/chats")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class ChatController {
    
    @Autowired
    private ChatService chatService;

    @PostMapping("/create")
    @Operation(summary = "Create Chat",
            description = "REST API to Create Chat inside Estateagency")
    public ResponseEntity<Chat> createChat(@RequestParam("idfirstUserName") Long idfirstUserName, @RequestParam("idsecondUserName") Long idsecondUserName ) {

        CreateChatRoomRequest createChatRoomRequest=new CreateChatRoomRequest();
        createChatRoomRequest.setIdfirstUserName(idfirstUserName);
        createChatRoomRequest.setIdsecondUserName(idsecondUserName);
        log.info("Helo"+createChatRoomRequest.toString());
        
        try {
            return new ResponseEntity<Chat>(chatService.addChatV(createChatRoomRequest), HttpStatus.CREATED);
        } catch (ChatAlreadyExistException e) {
            return new ResponseEntity("Chat Already Exist", HttpStatus.CONFLICT);
        }
    }


    @GetMapping("/all")
    @Operation(summary = "Get All Chat")
    public ResponseEntity<List<Chat>> getAllChats() {
        try {
            return new ResponseEntity<List<Chat>>(chatService.findallchats(), HttpStatus.OK);
        } catch (NoChatExistsInTheRepository e) {
           return new ResponseEntity("List not found", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get By Id Chat")
    public ResponseEntity<Chat> getChatById(@PathVariable Long id) {
        try {
            return new ResponseEntity<Chat>(chatService.getById(id), HttpStatus.OK);
        } catch (ChatNotFoundException e) {
           return new ResponseEntity("Chat Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/firstUserName/{username}")
    @Operation(summary = "Get Chat By FirstUserName")
    public ResponseEntity<?> getChatByFirstUserName(@PathVariable String username) {
        try {
            HashSet<Chat> byChat = this.chatService.getChatByFirstUserName(username);
            return new ResponseEntity<>(byChat, HttpStatus.OK);
        } catch (ChatNotFoundException e) {
            return new ResponseEntity("Chat Not Exits", HttpStatus.CONFLICT);
        }
    }



    @GetMapping("/secondUserName/{username}")
    @Operation(summary = "Get Chat By SecondUserName")
    public ResponseEntity<?> getChatBySecondUserName(@PathVariable String username) {

        try {
            HashSet<Chat> byChat = this.chatService.getChatBySecondUserName(username);
            return new ResponseEntity<>(byChat, HttpStatus.OK);
        } catch (ChatNotFoundException e) {
            return new ResponseEntity("Chat Not Exits", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getChatByFirstUserNameOrSecondUserName/{username}")
    @Operation(summary = "Get By User FirstUserName or SecondUserName")
    public ResponseEntity<?> getChatByFirstUserNameOrSecondUserName(@PathVariable String username) {

        try {
            HashSet<Chat> byChat = this.chatService.getChatByFirstUserNameOrSecondUserName(username);
            return new ResponseEntity<>(byChat, HttpStatus.OK);
        } catch (ChatNotFoundException e) {
            return new ResponseEntity("Chat Not Exits", HttpStatus.CONFLICT);
        }
    }


    @GetMapping("/getChatByFirstUserNameAndSecondUserName")
    @Operation(summary = "Get By User SecondUserName or FirstUserName")
    public ResponseEntity<?> getChatByFirstUserNameAndSecondUserName(@RequestParam("firstUserName") String firstUserName, @RequestParam("secondUserName") String secondUserName){

        try {
            HashSet<Chat> chatByBothEmail = this.chatService.getChatByFirstUserNameAndSecondUserName(firstUserName, secondUserName);
            return new ResponseEntity<>(chatByBothEmail, HttpStatus.OK);
        } catch (ChatNotFoundException e) {
            return new ResponseEntity("Chat Not Exits", HttpStatus.NOT_FOUND);
        }
    }


    // @PutMapping("/message/{chatId}")
    // public ResponseEntity<Chat> addMessage(@RequestBody Message add , @PathVariable Long chatId) throws ChatNotFoundException {
    //     log.info(add.toString()+chatId);
    //     return new ResponseEntity<Chat>(chatService.addMessage(add,chatId), org.springframework.http.HttpStatus.OK);
    // }

    @PutMapping("/message/{chatId}")
    @Operation(summary = "Create Mesage")
    public ResponseEntity<Chat> addMessage(@RequestParam("message") String message ,@RequestParam("acount") String acount, @PathVariable Long chatId) throws ChatNotFoundException {
        log.info("Hello"+chatId.toString());
        CreateMessageRequest message2=new CreateMessageRequest();
        message2.setSenderEmail(acount);
        message2.setIdchat(chatId);
        message2.setReplymessage(message);
        log.info(message2.toString());
        return new ResponseEntity<Chat>(chatService.addMessageV(message2), org.springframework.http.HttpStatus.OK);
    }

    @GetMapping(path = "/loadcontact/{id}")
    public ResponseEntity<List<ContactDTO>> list(@PathVariable String id) throws ChatNotFoundException{
        return ResponseEntity.ok(chatService.findUserContacts(id));
    }

    @GetMapping(path = "/anonymous/{id}")
    public ResponseEntity <ContactDTO> getAnonymousContact(@PathVariable String id){
        return ResponseEntity.ok(chatService.anonymousContact(id));
    }

    @GetMapping("/getChatMessage/{id}")
    public List<ChatMessage> Getchat(@PathVariable Long id) throws ChatNotFoundException {
       
        try {
            
            Chat chat=chatService.getById(id);
            List<ChatMessage> lst=new ArrayList<>();
            for (Message message3 : chat.getMessageList()) {
                ChatMessage chatMessage=ChatMessage.builder()
                .message(message3.getReplymessage())
                .user(message3.getSenderEmail())
                .time(message3.getTime().toString())
                .build();

                lst.add(chatMessage);
            }
    
            return lst;
        } catch (ChatNotFoundException e) {
            // Xử lý ngoại lệ nếu cần
            e.printStackTrace();
            return null; // hoặc thông báo lỗi khác
        }

    }
}
