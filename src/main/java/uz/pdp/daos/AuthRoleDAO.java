package uz.pdp.daos;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import uz.pdp.domains.AuthRole;
import uz.pdp.domains.AuthUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor

public class AuthRoleDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    public List<AuthRole> findAllByUserID(@NonNull Long  userID) {
        String sql = "select ar.* from authuser_authrole auar inner join authrole ar on ar.id = auar.role_id where auar.user_id = :userID";
        MapSqlParameterSource paramSource = new MapSqlParameterSource().addValue("userID", userID); // ✅ mos nom
        BeanPropertyRowMapper<AuthUser> rowMapper = BeanPropertyRowMapper.newInstance(AuthUser.class);
        try {
            return namedParameterJdbcTemplate.query(sql, (rs, rowNum) -> AuthRole.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .code(rs.getString("code"))
                    .build());

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}
