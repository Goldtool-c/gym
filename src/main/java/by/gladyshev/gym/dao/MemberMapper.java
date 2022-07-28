package by.gladyshev.gym.dao;

import by.gladyshev.gym.entity.Member;
import by.gladyshev.gym.entity.MemberRole;
import by.gladyshev.gym.entity.Membership;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class MemberMapper implements RowMapper<Member> {

    @Override
    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String password = rs.getString("password");
        MemberRole role = MemberRole.valueOf(rs.getString("role"));
        String membership = rs.getString("memberships");
        return new Member(id, name, password, role, parseMemberShip(membership));
    }
    private ArrayList<Integer> parseMemberShip(String content)
    {
        ArrayList<Integer> result = new ArrayList<>();
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
                result.add(Integer.parseInt(sb.toString()));
                sb = new StringBuilder();
            }
        }
        result.add(Integer.parseInt(sb.toString()));
        return result;
    }
}
