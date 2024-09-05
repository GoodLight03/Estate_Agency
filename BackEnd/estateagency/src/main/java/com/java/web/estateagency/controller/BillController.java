package com.java.web.estateagency.controller;

import java.util.List;
import java.util.Map;

import com.java.web.estateagency.model.response.ErrorResponseDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(
        name = "CRUD REST APIs for Bill in Estateagency",
        description = "CRUD REST APIs in Estateagency to CREATE, UPDATE, FETCH AND DELETE for Bill"
)
@RestController
@RequestMapping("/api/bill")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class BillController {
    @Autowired
    private BillService billService;

    @Autowired
    private VNPayService vnPayService;

    @Operation(
            summary = "Get Bill by Contact Id REST API",
            description = "REST API to Get Bill By Contact Id inside Estateagency"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/{id}")
    public ResponseEntity<List<Bill>> getChatById(@PathVariable Long id) {
        try {
            return new ResponseEntity<List<Bill>>(billService.getByIdContract(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Chat Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(
            summary = "Create Bill REST API",
            description = "REST API to Create Bill inside Estateagency"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody CreateBillRequest commentRequest) {
        log.info(commentRequest.toString());
        billService.save(commentRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Operation(
            summary = "Get Bill to PDF REST API",
            description = "REST API to Get Bill to PDF inside Estateagency"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/billid/{id}")
    public ResponseEntity<byte[]> getBill(@PathVariable("id") long id) {

        byte[] pdfContent = billService.generateReport(id);
        
        return new ResponseEntity<>(pdfContent, HttpStatus.OK);

    }

    @Operation(
            summary = "Download Bill PDF REST API",
            description = "REST API to Download Bill PDF inside Estateagency"
    )
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

    @Operation(
            summary = "Payment Bill",
            description = "REST API to Payment Bill inside Estateagency"
    )
    @GetMapping("/payment/{id}/{money}/{infor}/{user}")
    public String getMethodName(@PathVariable("id") int id,@PathVariable("money") int money,@PathVariable("infor") String infor,@PathVariable("user") String user, RedirectAttributes attributes,
            HttpServletRequest request) {
                log.info(id+"-"+infor+"-"+user);
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.payment((int) money*100, id+"-"+infor+"-"+user, baseUrl);
        // return "redirect:" + vnpayUrl;
        return  vnpayUrl;
    }

    @Operation(
            summary = "Payment Bill Result",
            description = "REST API to Payment Bill Result inside Estateagency"
    )
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

        String angularSuccessUrl = "http://localhost:4200/history";
        if (paymentStatus == 1) {
            log.info("ovanke");

            billService.updatePayment(id, name);
            
          return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, angularSuccessUrl).build();
        }
        return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, angularSuccessUrl).body(null);
    }

    @Operation(
            summary = "Get Bill by Id REST API",
            description = "REST API to Get Bill By Id inside Estateagency"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/detail/{id}")
    public ResponseEntity<Bill> getMethodName(@PathVariable("id") long id) {
        return ResponseEntity.ok(billService.detail(id));
    }

    @Operation(
            summary = "Report Bill REST API",
            description = "REST API to Report Bill inside Estateagency"
    )
    @GetMapping("/report/{id}")
    public ResponseEntity<List<Object>> report(@PathVariable("id") long id) {
        return ResponseEntity.ok(billService.getReport(id));
    }

    @Operation(
            summary = "Report Bill by contract and maintain REST API",
            description = "REST API to Report Bill by contract and maintain inside Estateagency"
    )
    @GetMapping("/reportcontractmaintain/{id}")
    public ResponseEntity<List<Integer>> reportcontractmaintain(@PathVariable("id") long id) {
        return ResponseEntity.ok(billService.getReportRoomandMaintain(id));
    }

    @Operation(
            summary = "Report Bill by Agent REST API",
            description = "REST API to Report Bill by Agent inside Estateagency"
    )
    @GetMapping("/reportagent/{id}")
    public ResponseEntity<Map<String,Integer>> reportAgent(@PathVariable("id") long id) {
        return ResponseEntity.ok(billService.getReportAgent(id));
    }

    @Operation(
            summary = "Report Bill by Admin REST API",
            description = "REST API to Report Bill by Admin inside Estateagency"
    )
    @GetMapping("/reportadmin")
    public ResponseEntity<Map<String,Integer>> reportAdmin() {
        return ResponseEntity.ok(billService.getReportAdmin());
    }

}
