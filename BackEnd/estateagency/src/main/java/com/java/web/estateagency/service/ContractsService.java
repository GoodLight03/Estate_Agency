package com.java.web.estateagency.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.java.web.estateagency.entity.Contract;

public interface ContractsService {
    List<Contract> getbyAgent(Long id);

    Contract upFile(Long id, MultipartFile file);

    byte[] getFIle(Long id);
}
