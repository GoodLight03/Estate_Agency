package com.java.web.estateagency.service.impl;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.java.web.estateagency.entity.Bill;
import com.java.web.estateagency.entity.Contract;
import com.java.web.estateagency.entity.Maintenance;
import com.java.web.estateagency.entity.Room;
import com.java.web.estateagency.model.request.CreateBillRequest;
import com.java.web.estateagency.repository.BillRepository;
import com.java.web.estateagency.repository.ContactRepository;
import com.java.web.estateagency.repository.ContractsRepository;
import com.java.web.estateagency.repository.MaintenanceRepository;
import com.java.web.estateagency.repository.RoomRepository;
import com.java.web.estateagency.repository.UserRepository;
import com.java.web.estateagency.service.BillService;

import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BillServiceImpl implements BillService {

    @Autowired
    private ContractsRepository contractsRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Override
    public Bill save(CreateBillRequest createBillRequest) {
        Bill bill = new Bill();
        bill.setName(createBillRequest.getName());
        //bill.setDate(new Date());
        bill.setDatestart(createBillRequest.getStart());
        bill.setDateend(createBillRequest.getEnd());
        bill.setContract(contractsRepository.findById(createBillRequest.getIdcontact()).get());
        bill.setStatus("Chưa thanh toán");

        Contract contract = contractsRepository.findById(createBillRequest.getIdcontact()).get();
        Room room = contract.getRoom();

        List<Maintenance> maintenances = room.getMaintenances();
        //List<Maintenance> maintenances2 = getByDay(maintenances, LocalDate.now());
        List<Maintenance> maintenances2 = getByDayV2(maintenances, createBillRequest.getStart(), createBillRequest.getEnd());
        long total = 0;
        total += room.getPrice();
        for (Maintenance maintenance : maintenances2) {
            total += maintenance.getPrice();
            log.info(total+"");
        }

        bill.setTotal(total);
        return billRepository.save(bill);
    }

    private List<Maintenance> getByDayV2(List<Maintenance> list, Date start, Date end) {

        // LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        // LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());

        List<Maintenance> maintenances2 = new ArrayList<>();
        for (Maintenance maintenance : list) {
            if (convertDate(maintenance.getDate()).isAfter(convertDate(start))
                            && convertDate(maintenance.getDate()).isBefore(convertDate(end))) {
                maintenances2.add(maintenance);
            }
        }
        log.info(maintenances2.size()+"hellokiii");
        return maintenances2;
    }

    private LocalDate convertDate(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

        // Chuyển đổi từ java.time.LocalDateTime sang java.time.LocalDate
        LocalDate localDate = localDateTime.toLocalDate();

        return localDate;
    }

    @Override
    public List<Bill> getByIdContract(Long id) {
        return billRepository.getByContractID(id);
    }

    @Override
    public byte[] generateReport(long id) {
        try {
            // Bill bill = billRepository.findById(id).get();
            Optional<Bill> optionalBill = billRepository.findById(id);

            if (optionalBill.isPresent()) {
                Bill bill = optionalBill.get();
                Contract contract = bill.getContract();

                Room room = contract.getRoom();
              

                String data = "Name: " + room.getName() + "\n" + "Adress:" + room.getAddress() +
                        "\n" + "Price: " + Math.round(room.getPrice()) + "\n" + "Payment Method: Cart";
                Date date = new Date();
                String fileName = room.getName();

                Document document = new Document();
                // String filePath = "D:\\Angular\\" + fileName + ".pdf";
                // PdfWriter.getInstance(document, new FileOutputStream(filePath));
                // Tạo một ByteArrayOutputStream để ghi dữ liệu PDF vào bộ đệm
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PdfWriter.getInstance(document, baos);
                document.open();
                setRactangleInPdf(document);

                Paragraph chunk = new Paragraph("Bill In Month", getFont("Header"));
                chunk.setAlignment(Element.ALIGN_CENTER);
                document.add(chunk);

                Paragraph paragraph = new Paragraph(data + "\n \n", getFont("Data"));
                document.add(paragraph);

                PdfPTable table = new PdfPTable(3);
                table.setWidthPercentage(100);
                addTableHeader(table);

                // JSONArray
                // jsonArray=CafeUtils.getJsonArrayfromString((String)requestMap.get("productDetails"));
                // log.info(jsonArray+"");
                // for(int i=0; i<jsonArray.length();i++){
                // log.info("Helloj"+jsonArray.getJSONObject(i));
                // addRows(table,
                // CafeUtils.getMapFormJson(jsonArray.getJSONObject(i).toString()));
                // }
                long total=0;
                total+=room.getPrice();
                List<Maintenance> maintenances = maintenanceRepository.getListByRoon(room.getId());
                if (maintenances.size() > 0) {
                    List<Maintenance> maintenances2 = getByDayV2(maintenances, bill.getDatestart(),bill.getDateend());
                    for (Maintenance maintenance : maintenances2) {
                        addRows(table, maintenance);
                        total+=maintenance.getPrice();
                    }
                    document.add(table);
                }

                Paragraph footer = new Paragraph("Total :" + total + "\n"
                        + "Thank you for visiting. Please visit again!!", getFont("Data"));
                document.add(footer);

                document.close();

                // // Chuẩn bị HTTP response
                // response.setContentType("application/pdf");
                // response.addHeader("Content-Disposition", "attachment; filename=" + fileName
                // + ".pdf");
                // // Gửi dữ liệu PDF từ bộ đệm dưới dạng phản hồi HTTP
                // response.setContentLength(baos.size());
                // ServletOutputStream outputStream = response.getOutputStream();
                // baos.writeTo(outputStream);
                // outputStream.flush();

                // // Đảm bảo rằng dữ liệu đã được gửi đi và không còn trong bộ đệm
                // outputStream.close();
                byte[] pdfContent = baos.toByteArray();
                baos.close();
                return pdfContent;
                // return fileName;
            } else {
                throw new IllegalArgumentException("Bill not found for id: " + id);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new IOException("Error generating report: " + e.getMessage());
        }
        // return "";

    }

    private void addRows(PdfPTable table, Maintenance maintenance) {
        table.addCell((String) maintenance.getName());
        table.addCell((String) maintenance.getDate().toString());
        table.addCell((String) maintenance.getPrice().toString());
        // table.addCell(Double.toString((Double)data.get("price")));
        // table.addCell(Double.toString((Double)data.get("total")));
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Name", "Date", "Price")
                .forEach(columTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setBorderColor(BaseColor.BLACK);
                    header.setPhrase(new Phrase(columTitle));
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }

    private Font getFont(String type) {
        switch (type) {
            case "Header":
                Font heaFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
                heaFont.setStyle(Font.BOLD);
                return heaFont;
            // break;
            case "Data":
                Font heaFontDT = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
                heaFontDT.setStyle(Font.BOLD);
                return heaFontDT;
            default:
                return new Font();
        }
    }

    private void setRactangleInPdf(Document document) throws DocumentException {
        Rectangle rect = new Rectangle(577, 825, 18, 15);
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);
        document.add(rect);
    }

    @Override
    public Bill detail(long id) {
        return billRepository.findById(id).get();
    }

    @Override
    public Bill updatePayment(long id, String user) {
        Bill bill = billRepository.findById(id).get();
        bill.setStatus("Đã thanh toán");
        bill.setDatepay(new Date());
        bill.setUser(user);
        return billRepository.save(bill);
    }

    @Override
    public List<Object> getReport(Long idAgent) {
        List<Contract> contracts = contractsRepository.getByAgent(idAgent);
        List<Long> list = new ArrayList<>();
        for (Contract contract : contracts) {
            list.add(contract.getId());
        }

        return billRepository.getTotalByMonthvsYear(list);

    }

    @Override
    public List<Integer> getReportRoomandMaintain(Long idAgent) {
        int totalbill = 0;
        int totalmaintaince = 0;

        List<Long> list = new ArrayList<>();
        List<Contract> contracts = contractsRepository.getByAgent(idAgent);
        for (Contract contract : contracts) {
            list.add(contract.getId());
        }
        
        List<Bill> bills = billRepository.getByPaymented(list);
        for (Bill bill : bills) {
            totalbill += bill.getContract().getRoom().getPrice();
            for (Maintenance maintenance : bill.getContract().getRoom().getMaintenances()) {
                totalmaintaince += maintenance.getPrice();
            }

        }
        List<Integer> result = new ArrayList<>();
        result.add(totalbill);
        result.add(totalmaintaince);
        return result;

    }

    private Integer getTotalAll(long id) {
        int totalmaintaince = 0;

        List<Long> list = new ArrayList<>();
        List<Contract> contracts = contractsRepository.getByAgent(id);
        for (Contract contract : contracts) {
            list.add(contract.getId());
        }
        List<Bill> bills = billRepository.getByPaymented(list);
        for (Bill bill : bills) {
            totalmaintaince += bill.getTotal();
            // for (Maintenance maintenance : bill.getContract().getRoom().getMaintenances()) {
            //     totalmaintaince += maintenance.getPrice();
            // }

        }
        return totalmaintaince;
    }

    @Override
    public Map<String, Integer> getReportAgent(Long id) {
        Map<String, Integer> map = new HashMap<>();
        map.put("room", roomRepository.getByAgent(id).size());
        map.put("roomrented", roomRepository.getByAgentAngented(id).size());
        map.put("notpayment", billRepository.getByContractIDAndNotPayment(id).size());
        map.put("total", getTotalAll(id));
        return map;

    }

    @Override
    public Map<String, Integer> getReportAdmin() {
        Map<String, Integer> map = new HashMap<>();
        map.put("acount", userRepository.findAll().size());
        map.put("room", roomRepository.findAll().size());
        map.put("roomnot", roomRepository.getByRoomReportAdmin().size());
        map.put("contact", contactRepository.findAll().size());
        return map;
    }

}
