package bg.academy.payments.service;

import bg.academy.payments.model.Invoice;
import bg.academy.payments.repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> getAllInvoice(){
        return invoiceRepository.findAll();
    }

    public void createInvoice(Invoice invoice){
        invoiceRepository.insert(invoice);
    }
    public  void updateInvoice(Invoice invoice){
        invoiceRepository.update(invoice);
    }
    public void deleteInvoice(int id){
       if (!invoiceRepository.isInvoicePaid(id)){
           invoiceRepository.deleteById(id);
       }
       else{
           System.out.println("Invoice with ID: "+id+" cannot be deleted, because it has been fully or partially paid");
       }
    }
}
