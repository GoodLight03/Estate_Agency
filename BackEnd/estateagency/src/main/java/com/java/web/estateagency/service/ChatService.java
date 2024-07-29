package com.java.web.estateagency.service;

import java.util.HashSet;
import java.util.List;

import com.java.web.estateagency.entity.Chat;
import com.java.web.estateagency.entity.Message;
import com.java.web.estateagency.exception.ChatAlreadyExistException;
import com.java.web.estateagency.exception.ChatNotFoundException;
import com.java.web.estateagency.exception.NoChatExistsInTheRepository;
import com.java.web.estateagency.model.request.CreateChatRoomRequest;
import com.java.web.estateagency.model.request.CreateMessageRequest;
import com.java.web.estateagency.model.response.ContactDTO;

public interface ChatService {
    public Chat addChat(Chat chat) throws ChatAlreadyExistException;

    public Chat addChatV(CreateChatRoomRequest chat) throws ChatAlreadyExistException;

    List<Chat> findallchats() throws NoChatExistsInTheRepository;

    Chat getById(Long id)  throws ChatNotFoundException;

    HashSet<Chat> getChatByFirstUserName(String username)  throws ChatNotFoundException;

    HashSet<Chat> getChatBySecondUserName(String username)  throws ChatNotFoundException;

    HashSet<Chat> getChatByFirstUserNameOrSecondUserName(String username)  throws ChatNotFoundException;

    HashSet<Chat> getChatByFirstUserNameAndSecondUserName(String firstUserName, String secondUserName)  throws ChatNotFoundException;

    Chat addMessage(Message add, Long chatId)  throws ChatNotFoundException;

    Chat addMessageV(CreateMessageRequest createMessageRequest)  throws ChatNotFoundException;

    List<ContactDTO> findUserContacts(String username) throws ChatNotFoundException;

    ContactDTO anonymousContact(String telephone);
}
