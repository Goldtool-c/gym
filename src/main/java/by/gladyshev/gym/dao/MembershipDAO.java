package by.gladyshev.gym.dao;

import by.gladyshev.gym.entity.Membership;
import by.gladyshev.gym.entity.impl.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MembershipDAO extends DAO{

    public MembershipDAO(@Autowired JdbcTemplate jdbcTemplate, @Autowired MembershipMapper rm) {
        super(jdbcTemplate);
        this.table = "memberships";
        this.rm = rm;
    }

    public void update(Entity entity)
    {
        Membership membership = (Membership) entity;
        jdbcTemplate.update("UPDATE "+ table+" SET type=?, fromdate=?" +
                ", todate=?, visits=?, last_visit=? where id =?",
                membership.getType().name(),
                membership.getFromDateFormat(), membership.getToDateFormat(), membership.getVisitNumber(),
                membership.getLastVisit(), membership.getId());
    }
    public void create(Entity entity)
    {
        Membership membership = (Membership) entity;
        int id = getID();
        jdbcTemplate.update("INSERT INTO "+table+" VALUES (?,?,?,?,?,?)",
                ++id, membership.getType().name(), membership.getFromDateFormat(),
                membership.getToDateFormat(), membership.getVisitNumber(), membership.getOwner());
        String mss = jdbcTemplate.queryForObject("SELECT memberships from members where id = "+membership.getOwner(), String.class);
        if(mss!=null)
        {
            mss = mss+","+id;
        }
        else {
            mss = ""+id;
        }
        jdbcTemplate.update("UPDATE members SET memberships = ? where id =?",mss, membership.getOwner());
    }
}
