package bg.academy.payments.service;

import bg.academy.payments.model.Payments;
import bg.academy.payments.repository.PaymentsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentsService {
    private final PaymentsRepository paymentsRepository;

    public PaymentsService(PaymentsRepository paymentsRepository) {
        this.paymentsRepository = paymentsRepository;
    }

    public List<Payments> getAllPayments(){
        return paymentsRepository.findAll();
    }

    public void createPayment(Payments payments){
        paymentsRepository.insert(payments);
    }

    public void updatePayment(Payments payments){
        paymentsRepository.update(payments);
    }

    public void deletePayment(int id){
        paymentsRepository.deleteById(id);
    }
}
