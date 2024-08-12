package bg.academy.payments.repository;

import bg.academy.payments.enums.Currency;
import bg.academy.payments.model.Debtor;
import bg.academy.payments.model.Invoice;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class InvoiceRepository {
    private final JdbcTemplate jdbcTemplate;

    public InvoiceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class InvoiceRowMapper implements RowMapper<Invoice> {
        @Override
        public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {
            Invoice invoice = new Invoice();
            invoice.setIdInvoice(rs.getInt("idInvoice"));
            invoice.setNumber(rs.getInt("number"));
            invoice.setAmount(rs.getDouble("amount"));
            invoice.setCurrency(Currency.valueOf(rs.getString("currency")));
            Debtor debtor = new Debtor();
            debtor.setId(rs.getInt("id"));
            debtor.setName(rs.getString("name"));
            invoice.setDebtor(debtor);
            invoice.setExpiryDate(rs.getDate("expiryDate"));
            return invoice;
        }
    }

    public List<Invoice> findAll() {
        String sql = "SELECT i.idInvoice," +
                "    i.number," +
                "    i.amount, " +
                "    i.currency, " +
                "    i.debtorId, " +
                "    i.expiryDate, "+
                "    d.id, "+
                "    d.name "+
                "FROM invoice i "+
                "JOIN debtor d ON i.debtorId = d.id";
        return jdbcTemplate.query(sql, new InvoiceRepository.InvoiceRowMapper());
    }

    public Invoice findById(int id) {
        String sql = "SELECT i.idInvoice," +
                "    i.number," +
                "    i.amount, " +
                "    i.currency, " +
                "    i.debtorId, " +
                "    i.expiryDate, "+
                "    d.id, "+
                "    d.name "+
                "FROM invoice i "+
                "JOIN debtor d ON i.debtorId = d.id "+
                "WHERE i.idInvoice = ?";
        return jdbcTemplate.queryForObject(sql, new InvoiceRepository.InvoiceRowMapper(), id);
    }

    public List<Invoice> findOverdueByDebtor(int id){
        String sql = "SELECT i.idInvoice," +
                "    i.number," +
                "    i.amount, " +
                "    i.currency, " +
                "    i.debtorId, " +
                "    i.expiryDate, "+
                "    d.id, "+
                "    d.name "+
                "FROM invoice i "+
                "JOIN debtor d ON i.debtorId = d.id "+
                "WHERE debtorId = ? AND expiryDate < NOW()";
        return jdbcTemplate.query(sql, new InvoiceRepository.InvoiceRowMapper(), id);
    }

    public boolean isInvoicePaid(int id){
        String sql = "SELECT count(*) FROM payments WHERE invoiceId = ?";
        Integer result = jdbcTemplate.queryForObject(sql,Integer.class, id);
        if(result != null && result > 0){
            return true;
        }
        else {
            return false;
        }
    }
    public int insert(Invoice invoice) {
        String sql = "INSERT INTO invoice (number, amount, currency, debtorId, expiryDate) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, invoice.getNumber(), invoice.getAmount(), invoice.getCurrency().name(), invoice.getDebtor().getId(), invoice.getExpiryDate());
    }

    public int update(Invoice invoice) {
        String sql = "UPDATE invoice SET number = ?, amount = ?, currency = ?, debtorId = ?, expiryDate = ? WHERE idInvoice = ?";
        return jdbcTemplate.update(sql, invoice.getNumber(), invoice.getAmount(), invoice.getCurrency().name(), invoice.getDebtor().getId(), invoice.getExpiryDate(), invoice.getIdInvoice());
    }

    public int deleteById(int id) {
        String sql = "DELETE FROM invoice WHERE idInvoice = ?";
        return jdbcTemplate.update(sql, id);
    }
}
