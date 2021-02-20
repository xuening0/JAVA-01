package service.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Repository
public class StudentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert_jdbc() throws Exception {
        Connection con = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
            con.setAutoCommit(false);
            statement = con.createStatement();
            String sql = "select * from t_app";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                log.info("resultSet id:{}, status:{}",resultSet.getLong(1), resultSet.getString(2));
            }

            String sql2 = "update t_app set status = 'aa' where id =?";
            PreparedStatement preparedStatement = con.prepareStatement(sql2);
            preparedStatement.setLong(1,1L);
            int i = preparedStatement.executeUpdate();
            ResultSet resultSet1 = con.prepareStatement(sql).executeQuery();
            while (resultSet1.next()){
                log.info("executeUpdate 影响数量: {}, resultSet1 id:{}, status:{}",i, resultSet1.getLong(1), resultSet1.getString(2));
            }
            int b = 1/0;
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
                String sql = "select * from t_app";
                resultSet = statement.executeQuery(sql);
                while (resultSet.next()){
                    log.info("rollback resultSet id:{}, status:{}",resultSet.getLong(1), resultSet.getString(2));
                }
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (con != null) {
                con.close();
            }
        }

    }
    public void insertData() {
        Arrays.asList("b", "c").forEach(status -> {
            jdbcTemplate.update("INSERT INTO t_app (status) VALUES (?)", status);
        });

    }

    public void listData() {
        log.info("Count: {}", jdbcTemplate.queryForObject("SELECT COUNT(*) FROM t_app", Long.class));

        List<String> list = jdbcTemplate.queryForList("SELECT status FROM t_app", String.class);
        list.forEach(s -> log.info("status: {}", s));

        List<Student2> fooList = jdbcTemplate.query("SELECT * FROM t_app", new RowMapper<Student2>() {
            @Override
            public Student2 mapRow(ResultSet rs, int rowNum) throws SQLException {
                return Student2.builder()
                        .id(rs.getLong(1))
                        .status(rs.getString(2))
                        .build();
            }
        });
        fooList.forEach(f -> log.info("t_app: {}", f));
    }
}
