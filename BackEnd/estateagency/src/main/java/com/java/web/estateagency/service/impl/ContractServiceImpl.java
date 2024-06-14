package com.java.web.estateagency.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.web.estateagency.entity.Contract;
import com.java.web.estateagency.repository.ContractsRepository;
import com.java.web.estateagency.service.ContractsService;

@Service
public class ContractServiceImpl implements ContractsService{

    @Autowired
    private ContractsRepository contractsRepository;
    @Override
    public List<Contract> getbyAgent(Long id) {
        return contractsRepository.getByAgent(id);
    }
    
}
