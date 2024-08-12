package bg.academy.payments.controller;

import bg.academy.payments.model.Debtor;
import bg.academy.payments.service.DebtorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/debtor")
public class DebtorController {
    private final DebtorService debtorService;
    public DebtorController(DebtorService debtorService) {
        this.debtorService = debtorService;
    }

    @GetMapping
    public List<Debtor> getAllDebtors(){
        return debtorService.getAllDebtors();
    }

    @PostMapping //(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createDebtor(@Valid @RequestBody Debtor debtor){
        debtorService.createDebtor(debtor);
        return new ResponseEntity<>("Debtor with ID: "+debtor.getId()+" created successfully!", HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> updateDebtor(@RequestBody Debtor debtor){
        debtorService.updateDebtor(debtor);
        return new ResponseEntity<>("Debtor with ID: " +debtor.getId()+" has been modified successfully!", HttpStatus.OK);
    }

}
