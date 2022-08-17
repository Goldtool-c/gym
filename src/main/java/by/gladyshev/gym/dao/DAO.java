package by.gladyshev.gym.dao;

import by.gladyshev.gym.entity.impl.Entity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class DAO {
    protected JdbcTemplate jdbcTemplate;
    protected String table;
    protected RowMapper rm;

    public DAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Entity entity)
    {
        jdbcTemplate.update("INSERT INTO "+table+" values (?)", entity.getId());
    }

    public Entity show(int id)
    {

        return (Entity) jdbcTemplate.query("SELECT * FROM " + table + " WHERE id =?", new Object[]{id}, rm)
                .stream().findAny().orElse(null);
    }
     public List index()
     {
         return jdbcTemplate.query("select * from "+table, rm);
     }
     public Integer getID()
     {
         Integer id = jdbcTemplate.queryForObject("SELECT MAX(id) FROM " + table, Integer.class);
         if(id==null)
         {
             return 0;
         }
         return id;
     }

}
