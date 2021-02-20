package service.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BatchStudentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void batchInsert() {
        jdbcTemplate.batchUpdate("INSERT INTO t_app (status) VALUES (?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, "b-" + i);
                    }

                    @Override
                    public int getBatchSize() {
                        return 2;
                    }
                });

        List<Student2> list = new ArrayList<>();
        list.add(Student2.builder().id(100L).status("b-100").build());
        list.add(Student2.builder().id(101L).status("b-101").build());
        namedParameterJdbcTemplate.batchUpdate("INSERT INTO t_app (ID, status) VALUES (:id, :status)",
                        SqlParameterSourceUtils.createBatch(list));
    }
}
