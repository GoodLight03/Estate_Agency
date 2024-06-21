package com.java.web.estateagency.service.impl;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.java.web.estateagency.entity.Contact;
import com.java.web.estateagency.entity.Contract;
import com.java.web.estateagency.entity.Maintenance;
import com.java.web.estateagency.model.request.CreateMaintenanceRequest;
import com.java.web.estateagency.repository.MaintenanceRepository;
import com.java.web.estateagency.repository.RoomRepository;
import com.java.web.estateagency.service.MaintenanceService;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Maintenance save(CreateMaintenanceRequest createMaintenanceRequest) {
        Maintenance maintenance = new Maintenance();
        maintenance.setName(createMaintenanceRequest.getName());
        maintenance.setDate(new Date());
        maintenance.setPrice(createMaintenanceRequest.getPrice());
        maintenance.setRoom(roomRepository.findById(createMaintenanceRequest.getIdroom()).get());
        return maintenanceRepository.save(maintenance);

    }

    @Override
    public List<Maintenance> getbtIdRoom(Long id) {
        return maintenanceRepository.getListByRoon(id);
    }

    @Override
    public List<Maintenance> getAll() {
        return maintenanceRepository.findAll();
    }

    @Override
    public List<Maintenance> getbtIdAgent(Long id) {
        return maintenanceRepository.getListAgent(id);
    }

    @Override
    public Maintenance upFile(Long id, MultipartFile file) {
        Maintenance maintenance = maintenanceRepository.findById(id).get();
        try {

            maintenance.setFile(Base64.getEncoder().encodeToString(file.getBytes()));

        } catch (Exception e) {
            // TODO: handle exception
        }
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public byte[] getFIle(Long id) {
        Maintenance contract = maintenanceRepository.findById(id).get();
        byte[] decodedBytes = Base64.getDecoder().decode(contract.getFile());
        return decodedBytes;
    }

}
