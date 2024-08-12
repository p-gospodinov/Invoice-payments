package bg.academy.payments.service;

import bg.academy.payments.model.Debtor;
import bg.academy.payments.repository.DebtorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DebtorService {
    private final DebtorRepository debtorRepository;

    public DebtorService(DebtorRepository debtorRepository) {
        this.debtorRepository = debtorRepository;
    }

    public List<Debtor> getAllDebtors(){
        return debtorRepository.findAll();
    }

    public void createDebtor(Debtor debtor) {
        debtorRepository.insert(debtor);
    }

    public void updateDebtor(Debtor debtor) {
        debtorRepository.update(debtor);
    }
}
