package by.gladyshev.gym.dao;

import by.gladyshev.gym.entity.Member;
import by.gladyshev.gym.entity.MemberRole;
import by.gladyshev.gym.entity.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class MemberMapper implements RowMapper<Member> {

    private final MembershipDAO memberDAO;

    @Override
    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String password = rs.getString("password");
        MemberRole role = MemberRole.valueOf(rs.getString("role"));
        String membership = rs.getString("memberships");
        String description = rs.getString("description");
        return new Member(id, name, password, role, parseMemberShip(membership), description);
    }
    private ArrayList<Membership> parseMemberShip(String content)
    {
        ArrayList<Membership> result = new ArrayList<>();
        if(content==null)
        {
            return result;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            if(content.charAt(i)!=',')
            {
                sb.append(content.charAt(i));
            }
            else {
                result.add((Membership) memberDAO.show(Integer.parseInt(sb.toString())));
                sb = new StringBuilder();
            }
        }
        result.add((Membership) memberDAO.show(Integer.parseInt(sb.toString())));
        return result;
    }
}
