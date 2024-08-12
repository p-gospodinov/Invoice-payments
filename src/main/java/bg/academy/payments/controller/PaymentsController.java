package bg.academy.payments.controller;

import bg.academy.payments.model.Payments;
import bg.academy.payments.service.PaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentsController {
    private final PaymentsService paymentsService;
    @Autowired
    public PaymentsController(PaymentsService paymentsService) {
        this.paymentsService = paymentsService;
    }

    @GetMapping
    public List<Payments> getAllPayments(){
        return paymentsService.getAllPayments();
    }

    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody Payments payment){
        paymentsService.createPayment(payment);
        return  new ResponseEntity<>("Payment with ID: "+payment.getIdPayments()+" on invoice number: "
                +payment.getInvoice().getNumber()+" made successfully!", HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> updatePayment(@RequestBody Payments payment){
        paymentsService.updatePayment(payment);
        return new ResponseEntity<>("Payment with ID: "+payment.getIdPayments()+" on invoice number: "
                +payment.getInvoice().getNumber()+" updated successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePayment(@PathVariable int id){
        paymentsService.deletePayment(id);
        return new ResponseEntity<>("Payment with ID: "+id+" deleted successfully!", HttpStatus.OK);
    }

}
