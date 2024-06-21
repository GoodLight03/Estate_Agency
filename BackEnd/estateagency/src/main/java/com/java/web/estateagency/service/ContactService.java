package com.java.web.estateagency.service;



import java.util.List;

import com.java.web.estateagency.entity.Contact;
import com.java.web.estateagency.model.request.CreateContactRequest;

public interface ContactService {
    Contact save(CreateContactRequest createContactRequest);
    List<Contact> findAll();
    Contact update(String reply,Long id);
}
