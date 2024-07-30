package com.ecommerce.ecommerce.Model;

import jakarta.persistence.Column;
import java.time.*;

public class PaymentInformation {
    @Column(name = "cardholder_name")
    private String cardholderName;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    
    @Column(name = "cvv")
    private String cvv;

}
