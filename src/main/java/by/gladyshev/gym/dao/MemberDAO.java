package by.gladyshev.gym.dao;

import by.gladyshev.gym.entity.Member;
import by.gladyshev.gym.entity.impl.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MemberDAO extends DAO{

    public MemberDAO(@Autowired JdbcTemplate jdbcTemplate, @Autowired MemberMapper rm) {
        super(jdbcTemplate);
        //if table name changes, also change it in MembershipDAO.create
        this.table = "members";
        this.rm = rm;
    }
    @Override
    public void create(Entity entity)
    {
        Member member = (Member) entity;
        int id = getID();
        jdbcTemplate.update("INSERT INTO "+table+" values(?,?,?,?)",++id, member.getName(),
                member.getPassword(), member.getRole().name());
    }

    public void update(Entity entity)
    {
        Member member = (Member) entity;
        jdbcTemplate.update("UPDATE "+ table+" SET "+ " name =?, password=?, role=? where id =?",
                member.getName(), member.getPassword(), member.getRole().name(), member.getId());
    }
    public void delete(int id)
    {
        jdbcTemplate.update("DELETE FROM "+table+" WHERE id = ?", id);
    }

    public Entity findByName(String name)
    {
        return (Entity) jdbcTemplate.query("SELECT * FROM " + table + " WHERE name =?", new Object[]{name}, rm)
                .stream().findAny().orElse(null);
    }

}
