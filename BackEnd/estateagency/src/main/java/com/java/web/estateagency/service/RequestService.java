package com.java.web.estateagency.service;

import java.util.List;

import com.java.web.estateagency.entity.Request;
import com.java.web.estateagency.model.request.CreateRequestRequest;

public interface RequestService {
    List<Request> findAllByIdUser(Long id, String filter);

    Request save(CreateRequestRequest createRequestRequest);

    Request updateStatus(long id, String status);
}
