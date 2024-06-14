package com.java.web.estateagency.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.web.estateagency.entity.History;
import com.java.web.estateagency.repository.HistoryRepository;
import com.java.web.estateagency.service.HistoryService;

@Service
public class HistoryServiceImpl implements HistoryService{

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public List<History> getByCusumer(Long id) {
        List<History> histories=historyRepository.getByCustomer(id);
        return histories;
    }
    
}
