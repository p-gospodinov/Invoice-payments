package bg.academy.payments.repository;

import bg.academy.payments.model.Debtor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DebtorRepository {
    private final JdbcTemplate jdbcTemplate;

    public DebtorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class DebtorRowMapper implements RowMapper<Debtor> {
        @Override
        public Debtor mapRow(ResultSet rs, int rowNum) throws SQLException {
            Debtor debtor = new Debtor();
            debtor.setId(rs.getInt("id"));
            debtor.setName(rs.getString("name"));
            return debtor;
        }
    }
    public List<Debtor> findAll() {
        String sql = "SELECT * FROM debtor";
        return jdbcTemplate.query(sql, new DebtorRepository.DebtorRowMapper());
    }

    public Debtor findById(int id) {
        String sql = "SELECT * FROM debtor WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new DebtorRepository.DebtorRowMapper(), id);
    }

    public int insert(Debtor debtor) {
        String sql = "INSERT INTO debtor (name) VALUES (?)";
        return jdbcTemplate.update(sql, debtor.getName());
    }

    public int update(Debtor debtor) {
        String sql = "UPDATE debtor SET name = ? WHERE id = ?";
        return jdbcTemplate.update(sql, debtor.getName(), debtor.getId());
    }

    public int deleteById(int id) {
        String sql = "DELETE FROM debtor WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
