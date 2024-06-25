package com.java.web.estateagency.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.java.web.estateagency.config.vnpay.VNPayService;
import com.java.web.estateagency.entity.Bill;
import com.java.web.estateagency.entity.Chat;
import com.java.web.estateagency.exception.ChatNotFoundException;
import com.java.web.estateagency.model.request.CreateBillRequest;
import com.java.web.estateagency.model.request.CreateCommentCustomerRequest;
import com.java.web.estateagency.model.response.MessageResponse;
import com.java.web.estateagency.service.BillService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/bill")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class BillController {
    @Autowired
    private BillService billService;

    @Autowired
    private VNPayService vnPayService;

    @GetMapping("/{id}")
    @Operation(summary = "Get By Id Chat")
    public ResponseEntity<List<Bill>> getChatById(@PathVariable Long id) {
        try {
            return new ResponseEntity<List<Bill>>(billService.getByIdContract(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Chat Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    @Operation(summary = "Tạo Phòng")
    public ResponseEntity<?> create(@Valid @RequestBody CreateBillRequest commentRequest) {
        log.info(commentRequest.toString());
        billService.save(commentRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/bill/{id}")
    public ResponseEntity<byte[]> getBill(@PathVariable("id") long id) {

        byte[] pdfContent = billService.generateReport(id);
        
        return new ResponseEntity<>(pdfContent, HttpStatus.OK);

    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> dowBill(@PathVariable("id") long id) {

        log.info("Hello" + id);
        // return ResponseEntity.ok(contractServices.generateReport(id));
        byte[] pdfContent = billService.generateReport(id);
        // Chuẩn bị HttpHeaders
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "OK" + ".pdf");
        headers.setContentLength(pdfContent.length);

        // Trả về phản hồi HTTP chứa tệp PDF
        return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);

    }

    @GetMapping("/payment/{id}/{money}/{infor}/{user}")
    public String getMethodName(@PathVariable("id") int id,@PathVariable("money") int money,@PathVariable("infor") String infor,@PathVariable("user") String user, RedirectAttributes attributes,
            HttpServletRequest request) {
                log.info(id+"-"+infor+"-"+user);
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder((int) money, id+"-"+infor+"-"+user, baseUrl);
        // return "redirect:" + vnpayUrl;
        return  vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public ResponseEntity<String> GetMapping(HttpServletRequest request,
            RedirectAttributes attributes) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        log.info(orderInfo);
        String[] result = orderInfo.split("-");
        Long id= Long.parseLong(result[0]);
        String name= result[2];
        
        // model.addAttribute("orderId", orderInfo);
        // model.addAttribute("totalPrice", totalPrice);
        // model.addAttribute("paymentTime", paymentTime);
        // model.addAttribute("transactionId", transactionId);
        String angularSuccessUrl = "http://localhost:4200/history";
        if (paymentStatus == 1) {
            // ShoppingCart cart = cartService.FindById(Long.parseLong(orderInfo));
            // Order order = orderService.save(cart);
            // session.removeAttribute("totalItems");
            billService.updatePayment(id, name);
            
          return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, angularSuccessUrl).build();
        }
        return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, angularSuccessUrl).body(null);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Bill> getMethodName(@PathVariable("id") long id) {
        return ResponseEntity.ok(billService.detail(id));
    }

    @GetMapping("/report/{id}")
    public ResponseEntity<List<Object>> report(@PathVariable("id") long id) {
        return ResponseEntity.ok(billService.getReport(id));
    }

    @GetMapping("/reportcontractmaintain/{id}")
    public ResponseEntity<List<Integer>> reportcontractmaintain(@PathVariable("id") long id) {
        return ResponseEntity.ok(billService.getReportRoomandMaintain(id));
    }

    @GetMapping("/reportagent/{id}")
    public ResponseEntity<Map<String,Integer>> reportAgent(@PathVariable("id") long id) {
        return ResponseEntity.ok(billService.getReportAgent(id));
    }

    @GetMapping("/reportadmin")
    public ResponseEntity<Map<String,Integer>> reportAdmin() {
        return ResponseEntity.ok(billService.getReportAdmin());
    }

}
