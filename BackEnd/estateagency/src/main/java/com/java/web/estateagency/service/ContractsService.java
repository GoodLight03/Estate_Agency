package com.java.web.estateagency.service;

import java.util.List;

import com.java.web.estateagency.entity.Contract;

public interface ContractsService {
    List<Contract> getbyAgent(Long id);
}
