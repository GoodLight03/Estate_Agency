package com.java.web.estateagency.service;

import java.util.List;

import com.java.web.estateagency.entity.Bill;
import com.java.web.estateagency.model.request.CreateBillRequest;

public interface BillService {
    Bill save(CreateBillRequest createBillRequest);

    List<Bill> getByIdContract(Long id);

    byte[] generateReport(long id) ;

    Bill detail(long id);

    Bill updatePayment(long id, String user);
}
