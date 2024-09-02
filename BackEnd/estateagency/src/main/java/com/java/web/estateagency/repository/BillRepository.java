package com.java.web.estateagency.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.java.web.estateagency.entity.Bill;
import com.java.web.estateagency.entity.History;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query("select p from Bill p where p.contract.id = ?1 ")
    List<Bill> getByContractID(Long id);

    @Query("select p from Bill p where p.contract.room.user.id = ?1 and p.status = 'Chưa thanh toán'")
    List<Bill> getByContractIDAndNotPayment(Long id);

    @Query("SELECT MONTH(o.datepay) AS month, YEAR(o.datepay) AS year, SUM(o.total) AS total " +
            "FROM Bill o WHERE o.status = 'Đã thanh toán' AND o.contract.id IN :idList " +
            "GROUP BY YEAR(o.datepay), MONTH(o.datepay) " +
            "ORDER BY YEAR(o.datepay), MONTH(o.datepay)")
    public List<Object> getTotalByMonthvsYear(@Param("idList") List<Long> idList);

    @Query("select p from Bill p where p.status = 'Đã thanh toán' and p.contract.id IN :idList ")
    List<Bill> getByPaymented(@Param("idList") List<Long> idList);

    @Query("select sum(b.total)-sum(r.price) from Contract c join Room r on c.room.id=r.id "+
            "join Bill b on b.contract.id=c.id " +
            "where b.status='Đã thanh toán' and r.user.id = ?1")
     int getTotalMaintain(Long idAgent);
}
