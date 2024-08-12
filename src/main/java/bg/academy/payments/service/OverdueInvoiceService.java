package bg.academy.payments.service;

import bg.academy.payments.model.Invoice;
import bg.academy.payments.model.Payments;
import bg.academy.payments.repository.InvoiceRepository;
import bg.academy.payments.repository.PaymentsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class OverdueInvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final PaymentsRepository paymentsRepository;

    public OverdueInvoiceService(InvoiceRepository invoiceRepository, PaymentsRepository paymentsRepository) {
        this.invoiceRepository = invoiceRepository;
        this.paymentsRepository = paymentsRepository;
    }

    public List<Double> sumAllPayments(List<List<Payments>> allPayments){
        List<Double> sumOfPayments = new ArrayList<>();
        for (List<Payments> invoice : allPayments){
            for (Payments payment : invoice){
                sumOfPayments.add(payment.getAmount());
            }
        }
        return sumOfPayments;
    }

    public List<String> overdueInvoiceDetails(int debtorId){
        List<Invoice> overdueInvoices = invoiceRepository.findOverdueByDebtor(debtorId);
        List<String> result = new ArrayList<>();
        result.add("These are all the details for overdue invoices of debtor with id: "+debtorId+"!");
        for(Invoice i : overdueInvoices){
            result.add(i.toString());
        }
        return result;
    }
    public List<String> overdueInvoiceDetailsByNumber(int debtorId, int invoiceNumber){
        List<Invoice> overdueInvoices = invoiceRepository.findOverdueByDebtor(debtorId);
        List<String> result = new ArrayList<>();
        result.add("These are all the details for overdue invoices with number: "+invoiceNumber+
                " of debtor with id: "+debtorId+"!");
        for(Invoice i : overdueInvoices){
            if(i.getNumber() == invoiceNumber) {
                result.add(i.toString());
            }
        }
        if(result.isEmpty()){
            result.clear();
            result.add("There is no overdue invoice with this number!");
        }
        return result;
    }

    public List<String> overdueInvoiceDetailsByExpiryDate(int debtorId, Date expiryDate) {
        List<Invoice> overdueInvoices = invoiceRepository.findOverdueByDebtor(debtorId);
        List<String> result = new ArrayList<>();
        result.add("These are all the details for overdue invoices with expiry date: "+expiryDate+
                " of debtor with id: " + debtorId + "!");
        for (Invoice i : overdueInvoices) {
            if (i.getExpiryDate().equals(expiryDate)) {
                result.add(i.toString());
            }
        }
        return result;
    }

    public List<String> paymentsToInvoiceInfo(int debtorId){
        List<Invoice> overdueInvoices = invoiceRepository.findOverdueByDebtor(debtorId);
        List<List<Payments>> paymentsByInvoice = new ArrayList<>();
        List<String> result = new ArrayList<>();
        for(Invoice i : overdueInvoices){
            paymentsByInvoice.add(paymentsRepository.findAllByInvoiceId(i.getIdInvoice()));
        }
        List<Double> sumOfPaymentsByInvoice = sumAllPayments(paymentsByInvoice);
        Iterator overdueInvoicesIterator = overdueInvoices.iterator();
        Iterator sumOfPaymentsByInvoiceIterator = sumOfPaymentsByInvoice.iterator();
        while (overdueInvoicesIterator.hasNext() && sumOfPaymentsByInvoiceIterator.hasNext()){
            Invoice invoice = (Invoice) overdueInvoicesIterator.next();
            double sumByInvoice = (double) sumOfPaymentsByInvoiceIterator.next();
            String paymentDetails = ("Invoice with id: "+invoice.getIdInvoice()+" has "+sumByInvoice+" "+invoice.getCurrency()+
                    " paid debts and the remaining amount is: " +(invoice.getAmount()-sumByInvoice)+ "!");
            result.add(paymentDetails);
        }
        return result;
    }
}
