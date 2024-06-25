package com.java.web.estateagency.service;

import java.util.List;
import java.util.Map;

import com.java.web.estateagency.entity.Bill;
import com.java.web.estateagency.model.request.CreateBillRequest;

public interface BillService {
    Bill save(CreateBillRequest createBillRequest);

    List<Bill> getByIdContract(Long id);

    byte[] generateReport(long id) ;

    Bill detail(long id);

    Bill updatePayment(long id, String user);

    List<Object> getReport(Long idAgent);

    List<Integer> getReportRoomandMaintain(Long idAgent);

    Map<String,Integer> getReportAgent(Long id);

    Map<String, Integer> getReportAdmin();
}
