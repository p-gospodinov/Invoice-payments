package bg.academy.payments.model;

import bg.academy.payments.enums.Currency;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Invoice {
    private int idInvoice;
    private int number;
    private double amount;
    private Currency currency;
    private  Debtor debtor;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date expiryDate;

    public int getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(int idInvoice) {
        this.idInvoice = idInvoice;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Debtor getDebtor() {
        return debtor;
    }

    public void setDebtor(Debtor debtor) {
        this.debtor = debtor;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "Invoice{\n" +
                "idInvoice=" + idInvoice + "\n" +
                ", number=" + number + "\n" +
                ", amount=" + amount + "\n" +
                ", currency=" + currency + "\n" +
                ", debtorId=" + debtor.getId() + "\n" +
                ", debtorName=" + debtor.getName() + "\n" +
                ", expiryDate=" + expiryDate + "\n" +
                "}";
    }
}
