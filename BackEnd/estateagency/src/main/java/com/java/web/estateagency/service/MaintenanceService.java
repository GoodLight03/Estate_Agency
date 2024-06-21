package com.java.web.estateagency.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.java.web.estateagency.entity.Contact;
import com.java.web.estateagency.entity.Contract;
import com.java.web.estateagency.entity.Maintenance;
import com.java.web.estateagency.model.request.CreateMaintenanceRequest;

public interface MaintenanceService {
    Maintenance save(CreateMaintenanceRequest createMaintenanceRequest);
    List<Maintenance> getbtIdRoom(Long id);
    List<Maintenance> getbtIdAgent(Long id);
    List<Maintenance> getAll();
   Maintenance upFile(Long id, MultipartFile file);

    byte[] getFIle(Long id);
}
