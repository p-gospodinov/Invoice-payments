package bg.academy.payments.model;

import bg.academy.payments.enums.Currency;

import java.util.Date;

public class Payments {
    private int idPayments;
    private double amount;
    private Currency currency;
    private Date date;
    private Invoice invoice;

    public int getIdPayments() {
        return idPayments;
    }

    public void setIdPayments(int idPayments) {
        this.idPayments = idPayments;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
