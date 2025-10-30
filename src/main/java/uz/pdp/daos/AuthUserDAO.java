package uz.pdp.daos;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import uz.pdp.domains.AuthUser;
import java.util.Optional;


@Component
@RequiredArgsConstructor

public class AuthUserDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Long save(@NonNull AuthUser authUser) {
        String sql = "insert into authuser(uname, pwd, role) " +
                "values(:uname, :pwd, :role)";

        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("uname", authUser.getUname())
                .addValue("pwd", authUser.getPwd())
                .addValue("role", authUser.getRole());

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, map, keyHolder, new String[]{"id"});
        return (Long) keyHolder.getKeys().get("id");
    }

    public Optional<AuthUser> findByUsername(@NonNull String username) {
        String sql = "select * from authuser  where uname = :uname";
        MapSqlParameterSource paramSource = new MapSqlParameterSource().addValue("uname", username); // ✅ mos nom
        BeanPropertyRowMapper<AuthUser> rowMapper = BeanPropertyRowMapper.newInstance(AuthUser.class);
        try {
            AuthUser authUser = namedParameterJdbcTemplate.queryForObject(sql, paramSource, rowMapper);
            return Optional.of(authUser);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
