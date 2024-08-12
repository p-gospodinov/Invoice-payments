package bg.academy.payments.repository;

import bg.academy.payments.enums.Currency;
import bg.academy.payments.model.Debtor;
import bg.academy.payments.model.Invoice;
import bg.academy.payments.model.Payments;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PaymentsRepository {
    private final JdbcTemplate jdbcTemplate;

    public PaymentsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class PaymentsRowMapper implements RowMapper<Payments> {
        @Override
        public Payments mapRow(ResultSet rs, int rowNum) throws SQLException {
            Payments payment = new Payments();
            payment.setIdPayments(rs.getInt("idPayments"));
            payment.setAmount(rs.getDouble("amount"));
            payment.setCurrency(Currency.valueOf(rs.getString("currency")));
            payment.setDate(rs.getDate("date"));

            Invoice invoice = new Invoice();
            invoice.setIdInvoice(rs.getInt("idInvoice"));
            invoice.setNumber(rs.getInt("number"));
            invoice.setAmount(rs.getDouble("amount"));
            invoice.setCurrency(Currency.valueOf(rs.getString("currency")));
            invoice.setExpiryDate(rs.getDate("expiryDate"));

            Debtor debtor = new Debtor();
            debtor.setId(rs.getInt("id"));
            debtor.setName(rs.getString("name"));

            invoice.setDebtor(debtor);
            payment.setInvoice(invoice);
            return payment;
        }
    }

    public List<Payments> findAll() {
        String sql = "SELECT p.idPayments, "+
                "    p.amount, " +
                "    p.currency, " +
                "    p.date, " +
                "    i.idInvoice, " +
                "    i.number," +
                "    i.amount, " +
                "    i.currency, " +
                "    i.debtorId, " +
                "    i.expiryDate, "+
                "    d.id, "+
                "    d.name "+
                "FROM payments p "+
                "JOIN invoice i ON p.invoiceId = i.idInvoice "+
                "JOIN debtor d ON i.debtorId = d.id";
        return jdbcTemplate.query(sql, new PaymentsRowMapper());
    }

    public Payments findById(int id) {
        String sql = "SELECT p.idPayments, "+
                "    p.amount, " +
                "    p.currency, " +
                "    p.date, " +
                "    i.idInvoice, " +
                "    i.number," +
                "    i.amount, " +
                "    i.currency, " +
                "    i.debtorId, " +
                "    i.expiryDate, "+
                "    d.id, "+
                "    d.name "+
                "FROM payments p "+
                "JOIN invoice i ON p.invoiceId = i.idInvoice "+
                "JOIN debtor d ON i.debtorId = d.id "+
                "WHERE p.idPayments = ?";
        return jdbcTemplate.queryForObject(sql, new PaymentsRowMapper(), id);
    }

    public List<Payments> findAllByInvoiceId(int invoiceId){
        String sql = "SELECT p.idPayments, "+
                "    p.amount, " +
                "    p.currency, " +
                "    p.date, " +
                "    i.idInvoice, " +
                "    i.number," +
                "    i.amount, " +
                "    i.currency, " +
                "    i.debtorId, " +
                "    i.expiryDate, "+
                "    d.id, "+
                "    d.name "+
                "FROM payments p "+
                "JOIN invoice i ON p.invoiceId = i.idInvoice "+
                "JOIN debtor d ON i.debtorId = d.id "+
                "WHERE invoiceId = ?";
        return jdbcTemplate.query(sql, new PaymentsRowMapper(), invoiceId);
    }
    public int insert(Payments payment) {
        String sql = "INSERT INTO payments (amount, currency, date, invoiceId) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, payment.getAmount(), payment.getCurrency().name(), payment.getDate(), payment.getInvoice().getIdInvoice());
    }

    public int update(Payments payment) {
        String sql = "UPDATE payments SET amount = ?, currency = ?, date = ?, invoiceId = ? WHERE idPayments = ?";
        return jdbcTemplate.update(sql, payment.getAmount(), payment.getCurrency().name(), payment.getDate(), payment.getInvoice().getIdInvoice(), payment.getIdPayments());
    }

    public int deleteById(int id) {
        String sql = "DELETE FROM payments WHERE idPayments = ?";
        return jdbcTemplate.update(sql, id);
    }
}
