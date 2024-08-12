package bg.academy.payments.controller;

import bg.academy.payments.model.Invoice;
import bg.academy.payments.service.InvoiceService;
import bg.academy.payments.service.OverdueInvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@RestController
@RequestMapping("/api/v1/invoice")
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final OverdueInvoiceService overdueInvoiceService;
    public InvoiceController(InvoiceService invoiceService, OverdueInvoiceService overdueInvoiceService) {
        this.invoiceService = invoiceService;
        this.overdueInvoiceService = overdueInvoiceService;
    }

    @GetMapping
    public List<Invoice> getAllInvoice(){
        return invoiceService.getAllInvoice();
    }

    @PostMapping
    public ResponseEntity<String> createInvoice(@Valid @RequestBody Invoice invoice){
        invoiceService.createInvoice(invoice);
        return new ResponseEntity<>("Invoice with ID: "+invoice.getIdInvoice()+" created successfully!", HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> updateInvoice(@RequestBody Invoice invoice){
        invoiceService.updateInvoice(invoice);
        return new ResponseEntity<>("Invoice with ID: " +invoice.getIdInvoice()+" has been modified successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInvoice(@PathVariable int id){
        invoiceService.deleteInvoice(id);
        return new ResponseEntity<>("Invoice with ID: " +id+" has been deleted successfully!", HttpStatus.OK);
    }

    @GetMapping("/overduePayment/{id}")
    public ResponseEntity<List<String>> getPaymentsOverdueInvoicesByDebtor(@PathVariable int id){
        List<String> overduePaymentsByInvoiceDetails = overdueInvoiceService.paymentsToInvoiceInfo(id);
        return new ResponseEntity<>(overduePaymentsByInvoiceDetails,HttpStatus.OK);
    }

    @GetMapping("/overdue/{id}")
    public ResponseEntity<List<String>> getDetailsInvoicesByDebtor(@PathVariable int id){
        List<String> overdueInvoiceDetails = overdueInvoiceService.overdueInvoiceDetails(id);
        return new ResponseEntity<>(overdueInvoiceDetails,HttpStatus.OK);
    }

    @GetMapping("/overdue/by-number/{id}/{number}")
    public ResponseEntity<List<String>> getDetailsInvoicesByNumber(@PathVariable int id, @PathVariable int number){
        List<String> overdueInvoiceDetails = overdueInvoiceService.overdueInvoiceDetailsByNumber(id,number);
        return new ResponseEntity<>(overdueInvoiceDetails,HttpStatus.OK);
    }

    @GetMapping("/overdue/by-expiryDate/{id}/{expiryDate}")
    public ResponseEntity<List<String>> getDetailsInvoicesByExpiryDate(@PathVariable int id, @PathVariable String expiryDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(expiryDate);
        List<String> overdueInvoiceDetails = overdueInvoiceService.overdueInvoiceDetailsByExpiryDate(id, date);
        return new ResponseEntity<>(overdueInvoiceDetails, HttpStatus.OK);
    }
}
