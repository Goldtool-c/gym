package by.gladyshev.gym.dao;

import by.gladyshev.gym.entity.Membership;
import by.gladyshev.gym.entity.MembershipType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class MembershipMapper implements RowMapper<Membership> {
    @Override
    public Membership mapRow(ResultSet rs, int rowNum) throws SQLException {
        Membership membership = new Membership();
        membership.setId(rs.getInt("id"));
        membership.setType(MembershipType.valueOf(rs.getString("type")));
        membership.setFrom(parseDate(rs.getString("fromdate")));
        membership.setTo(parseDate(rs.getString("todate")));
        membership.setVisitNumber(rs.getInt("visits"));
        return membership;
    }
    private Date parseDate(String content)
    {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(content);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
