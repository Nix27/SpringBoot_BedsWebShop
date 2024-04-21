package hr.bestwebshop.bedwebshop.service.implementation;

import hr.bestwebshop.bedwebshop.model.PaymentType;
import hr.bestwebshop.bedwebshop.repository.PaymentTypeRepository;
import hr.bestwebshop.bedwebshop.service.abstraction.PaymentTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentTypeServiceImpl implements PaymentTypeService {

    private PaymentTypeRepository paymentTypeRepository;

    @Override
    public List<PaymentType> getAllPaymentTypes() {
        return paymentTypeRepository.findAll();
    }
}
