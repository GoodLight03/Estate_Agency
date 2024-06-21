package com.java.web.estateagency.service.impl;

import java.io.FileOutputStream;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
import com.java.web.estateagency.entity.Contract;
import com.java.web.estateagency.entity.Maintenance;
import com.java.web.estateagency.entity.Room;
import com.java.web.estateagency.repository.ContractsRepository;
import com.java.web.estateagency.service.ContractsService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContractServiceImpl implements ContractsService {

    @Autowired
    private ContractsRepository contractsRepository;

    @Override
    public List<Contract> getbyAgent(Long id) {
        return contractsRepository.getByAgent(id);
    }

    @Override
    public Contract upFile(Long id, MultipartFile file) {
        Contract contract = contractsRepository.findById(id).get();
        try {

            contract.setFile(Base64.getEncoder().encodeToString(file.getBytes()));

        } catch (Exception e) {
            // TODO: handle exception
        }
        return contractsRepository.save(contract);
    }

    @Override
    public byte[] getFIle(Long id) {
        Contract contract = contractsRepository.findById(id).get();
        byte[] decodedBytes = Base64.getDecoder().decode(contract.getFile());
        return decodedBytes;
    }

    @Override
    public String generateReport(long id) {
        try {
            log.info("Hello");
            Contract contract = contractsRepository.findById(id).get();
            //log.info("Contact:"+contract.toString());
            Room room = contract.getRoom();
            //log.info("Room: "+room.toString());
            String data = "Name: " + room.getName() + "\n" + "Adress:" + room.getAddress() +
                    "\n" + "Price: " + room.getPrice() + "\n" + "Payment Method: Cart";
            Date date = new Date();
            String fileName = room.getName();
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("D:\\Angular\\" + fileName + ".pdf"));
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
    
            List<Maintenance> maintenances = room.getMaintenances();
            for (Maintenance maintenance : maintenances) {
                addRows(table, maintenance);
            }
            document.add(table);
    
            Paragraph footer = new Paragraph("Total :" + getTotal(id).toString() + "\n"
                    + "Thank you for visiting. Please visit again!!", getFont("Data"));
            document.add(footer);
    
            document.close();

            return fileName;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
       
    }

    private void addRows(PdfPTable table, Maintenance maintenance) {
        table.addCell((String) maintenance.getName());
        table.addCell((String) maintenance.getDate().toString());
        table.addCell((String) maintenance.getPrice().toString());
        // table.addCell(Double.toString((Double)data.get("price")));
        // table.addCell(Double.toString((Double)data.get("total")));
    }

    private Long getTotal(long id) {
        long total = 0;
        Contract contract = contractsRepository.findById(id).get();
        Room room = contract.getRoom();
        total += room.getPrice();
        List<Maintenance> maintenances = room.getMaintenances();
        for (Maintenance maintenance : maintenances) {
           total+=maintenance.getPrice();
        }
        return total;
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
}
