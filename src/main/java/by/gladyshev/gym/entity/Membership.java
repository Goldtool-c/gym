package by.gladyshev.gym.entity;

import by.gladyshev.gym.entity.impl.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Membership implements Entity {
    private int id;
    private MembershipType type;
    private Date from;
    private Date to;
    private int visitNumber;

    public String getToDateFormat()
    {
        String pattern = "dd.MM.yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(to);
    }

    public String getFromDateFormat()
    {
        String pattern = "dd.MM.yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(from);
    }

    public boolean isRelevant()
    {
        Date date = new Date();
        return visitNumber > 0 && date.before(to);
    }
    public void visit()
    {
        if(type == MembershipType.LIMITED)
        {
            visitNumber--;
        }
        //todo log
    }
}
