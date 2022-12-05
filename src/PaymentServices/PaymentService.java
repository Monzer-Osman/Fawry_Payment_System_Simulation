package PaymentServices;

public interface PaymentService {
    Transaction pay(String serviceNumber, int amount) throws Exception;
}
