package com.java.web.estateagency.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.web.estateagency.entity.Contact;
import com.java.web.estateagency.model.request.CreateContactRequest;
import com.java.web.estateagency.repository.ContactRepository;
import com.java.web.estateagency.repository.UserRepository;
import com.java.web.estateagency.service.ContactService;
import com.java.web.estateagency.utils.EmailUlti;

@Service
public class ContactServiceImpl implements ContactService{

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailUlti emailUlti;

    @Override
    public Contact save(CreateContactRequest createContactRequest) {
        Contact contact=new Contact();
        contact.setTitle(createContactRequest.getTitle());
        contact.setContent(createContactRequest.getContent());
        contact.setDayContact(new Date());
        contact.setUser(userRepository.findById(createContactRequest.getIduser()).get());
        contact.setStatus("Chờ phản hồi");
        contact.setEmail(userRepository.findById(createContactRequest.getIduser()).get().getEmail());
        return contactRepository.save(contact);
    }

    @Override
    public List<Contact> findAll() {
       return contactRepository.findAll();
    }

    @Override
    public Contact update(String reply, Long id) {
        Contact contact=contactRepository.findById(id).get();
        emailUlti.sendEmail(contact.getEmail(), contact.getTitle(), reply);
        contact.setReply(reply);
        contact.setDayReply(new Date());
        contact.setStatus("Đã phản hồi");
        return contactRepository.save(contact);
        
    }
    
}
