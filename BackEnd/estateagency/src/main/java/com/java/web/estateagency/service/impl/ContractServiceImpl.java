package com.java.web.estateagency.service.impl;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    @Override
    public Contract upFile(Long id, MultipartFile file) {
       Contract contract=contractsRepository.findById(id).get();
       try {
            
                contract.setFile(Base64.getEncoder().encodeToString(file.getBytes()));
            
        } catch (Exception e) {
            // TODO: handle exception
        }
        return contractsRepository.save(contract);
    }
    @Override
    public byte[] getFIle(Long id) {
        Contract contract=contractsRepository.findById(id).get();
        byte[] decodedBytes = Base64.getDecoder().decode(contract.getFile());
        return decodedBytes;
    }
    
}
