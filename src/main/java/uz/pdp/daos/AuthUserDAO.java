package uz.pdp.daos;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import uz.pdp.domains.AuthUser;

import java.util.Map;
import java.util.Optional;
import java.util.function.LongToIntFunction;

@Component
@RequiredArgsConstructor

public class AuthUserDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Long save(@NonNull AuthUser authUser) {
        String sql = "insert into authuser(user_name, password,role) values(?,?,?)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("username", authUser.getUsername())
                .addValue("password", authUser.getPassword())
                .addValue("role", authUser.getRole());
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource, keyHolder, new String[]{"id"});
        return (Long) keyHolder.getKeys().get("id");
    }
    public Optional<AuthUser> findByUsername(@NonNull String username) {
        String sql = "select * from authuser t where t.user_name = ?";
        MapSqlParameterSource paramSource = new MapSqlParameterSource().addValue("username", username);
        BeanPropertyRowMapper<AuthUser> rowMapper = BeanPropertyRowMapper.newInstance(AuthUser.class);
        try {
            AuthUser authUser = namedParameterJdbcTemplate.queryForObject(sql, paramSource, rowMapper);
            return Optional.of(authUser);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
