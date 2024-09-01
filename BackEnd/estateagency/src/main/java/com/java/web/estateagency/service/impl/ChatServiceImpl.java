package com.java.web.estateagency.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.web.estateagency.entity.Chat;
import com.java.web.estateagency.entity.Message;
import com.java.web.estateagency.entity.User;
import com.java.web.estateagency.exception.ChatAlreadyExistException;
import com.java.web.estateagency.exception.ChatNotFoundException;
import com.java.web.estateagency.exception.NoChatExistsInTheRepository;
import com.java.web.estateagency.model.request.CreateChatRoomRequest;
import com.java.web.estateagency.model.request.CreateMessageRequest;
import com.java.web.estateagency.model.response.ContactDTO;
import com.java.web.estateagency.repository.ChatRepository;
import com.java.web.estateagency.repository.UserRepository;
import com.java.web.estateagency.service.ChatService;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    public Chat addChat(Chat chat) {
        return chatRepository.save(chat);
    }

    @Override
    public List<Chat> findallchats() throws NoChatExistsInTheRepository {
        if (chatRepository.findAll().isEmpty()) {
            throw new NoChatExistsInTheRepository();
        } else {
            return chatRepository.findAll();
        }

    }

    @Override
    public Chat getById(Long id) throws ChatNotFoundException {
        Optional<Chat> chatid = chatRepository.findById(id);
        if (chatid.isPresent()) {
            return chatid.get();
        } else {
            throw new ChatNotFoundException();
        }
    }

    @Override
    public HashSet<Chat> getChatByFirstUserName(String username) throws ChatNotFoundException {
        HashSet<Chat> chat = chatRepository.getChatByFirstUserName(username);

        if (chat.isEmpty()) {
            throw new ChatNotFoundException();
        } else {
            return chat;
        }
    }

    @Override
    public HashSet<Chat> getChatBySecondUserName(String username) throws ChatNotFoundException {
        HashSet<Chat> chat = chatRepository.getChatBySecondUserName(username);
        if (chat.isEmpty()) {
            throw new ChatNotFoundException();
        } else {
            return chat;
        }
    }

    @Override
    public HashSet<Chat> getChatByFirstUserNameOrSecondUserName(String username) throws ChatNotFoundException {
        HashSet<Chat> chat = chatRepository.getChatByFirstUserName(username);
        HashSet<Chat> chat1 = chatRepository.getChatBySecondUserName(username);

        chat1.addAll(chat);

        if (chat.isEmpty() && chat1.isEmpty()) {
            throw new ChatNotFoundException();
        } else if (chat1.isEmpty()) {
            return chat;
        } else {
            return chat1;
        }
    }

    @Override
    public HashSet<Chat> getChatByFirstUserNameAndSecondUserName(String firstUserName, String secondUserName)
            throws ChatNotFoundException {
        HashSet<Chat> chat = chatRepository.getChatByFirstUserNameAndSecondUserName(firstUserName, secondUserName);
        HashSet<Chat> chat1 = chatRepository.getChatBySecondUserNameAndFirstUserName(firstUserName, secondUserName);
        if (chat.isEmpty() && chat1.isEmpty()) {
            throw new ChatNotFoundException();
        } else if (chat.isEmpty()) {
            return chat1;
        } else {
            return chat;
        }
    }

    @Override
    public Chat addMessage(Message add, Long chatId) throws ChatNotFoundException {
        Optional<Chat> chat = chatRepository.findById(chatId);
        Chat abc = chat.get();

        if (abc.getMessageList() == null) {
            List<Message> msg = new ArrayList<>();
            msg.add(add);
            abc.setMessageList(msg);
            return chatRepository.save(abc);
        } else {
            List<Message> rates = abc.getMessageList();
            rates.add(add);
            abc.setMessageList(rates);
            return chatRepository.save(abc);
        }
    }

    @Override
    public Chat addChatV(CreateChatRoomRequest chat) {
        Chat chats = new Chat();
        chats.setFirstUserName(userRepository.findById(chat.getIdfirstUserName()).get());
        chats.setSecondUserName(userRepository.findById(chat.getIdsecondUserName()).get());
        chats.setMessageList(new ArrayList<>());
        chatRepository.save(chats);

        HashSet<Chat> chatby=new HashSet<>();
        try {
            chatby = getChatByFirstUserNameAndSecondUserName(userRepository.findById(chat.getIdfirstUserName()).get().getUsername(), userRepository.findById(chat.getIdsecondUserName()).get().getUsername());
        } catch (ChatNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        if(chatby.size()==1){
            Iterator<Chat> iterator = chatby.iterator();
            chats=iterator.next();
        }
        
        Message message = new Message();
        message.setSenderEmail(userRepository.findById(chat.getIdsecondUserName()).get().getUsername());
        message.setReplymessage("Tôi đang quan tâm tới phòng của bạn!");
        message.setChat(chats);
        
        chats.getMessageList().add(message);

        return chatRepository.save(chats);

    }

    @Override
    public Chat addMessageV(CreateMessageRequest createMessageRequest) throws ChatNotFoundException {
        Optional<Chat> chat = chatRepository.findById(createMessageRequest.getIdchat());
        Chat abc = chat.get();

        Message message = new Message();
        message.setSenderEmail(createMessageRequest.getSenderEmail());
        message.setReplymessage(createMessageRequest.getReplymessage());
        message.setChat(abc);

        if (abc.getMessageList() == null) {
            List<Message> msg = new ArrayList<>();
            msg.add(message);
            abc.setMessageList(msg);
            return chatRepository.save(abc);
        } else {
            List<Message> rates = abc.getMessageList();
            rates.add(message);
            abc.setMessageList(rates);
            return chatRepository.save(abc);
        }
    }

    @Override
    public List<ContactDTO> findUserContacts(String username) throws ChatNotFoundException {
        HashSet<Chat> chats = getChatByFirstUserNameOrSecondUserName(username);
        if (chats.isEmpty()) {
            return null;
        }
        List<ContactDTO> contactDTOList = new ArrayList<>();
        for (Chat chat : chats) {
            if (chat.getFirstUserName().getUsername().equals(username)) {
                ContactDTO contactDTO = ContactDTO.builder()
                        .id(chat.getId())
                        .name(chat.getSecondUserName().getUsername())
                        .photo(chat.getSecondUserName().getImg())
                        .build();

                contactDTOList.add(contactDTO);
            }else{
                ContactDTO contactDTO = ContactDTO.builder()
                        .id(chat.getId())
                        .name(chat.getFirstUserName().getUsername())
                        .photo(chat.getFirstUserName().getImg())
                        .build();

                contactDTOList.add(contactDTO);
            }

        }

        return contactDTOList;

    }

    @Override
    public ContactDTO anonymousContact(String telephone) {
        User user = userRepository.findByUsername(telephone).get();
        ContactDTO contactDTO = ContactDTO.builder()
                .id(0L)
                .name(user.getUsername())
                .build();
        return contactDTO;
    }
}
