package by.gladyshev.gym.service;

import by.gladyshev.gym.entity.Membership;
import by.gladyshev.gym.entity.MembershipPeriod;
import by.gladyshev.gym.entity.MembershipVisit;
import by.gladyshev.gym.form.MembershipForm;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MembershipCreateService {
    public Membership create(int id, MembershipForm form)
    {
        Membership result = new Membership();
        result.setOwner(id);
        result.setType(form.getType());
        result.setFrom(parseDate(form.getDateFrom()));
        result.setTo(parseDateTo(form.getPeriod()));
        result.setVisitNumber(parseVisit(form.getVisits()));
        return result;
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
    private Date parseDateTo(MembershipPeriod period)
    {
        long day = 86400000L;
        Date date = new Date();
        switch (period){
            case MONTH:
            {
                return new Date(date.getTime()+30*day);
            }
            case THREE_MONTH:
            {
                return new Date(date.getTime()+90*day);
            }
            case YEAR:
            {
                return new Date(date.getTime()+365*day);
            }
            default: {
                return date;
            }
        }
    }
    private int parseVisit(MembershipVisit visit)
    {
        switch (visit)
        {
            case FOUR:
            {
                return 4;
            }
            case EIGHT:
            {
                return 8;
            }
            case TWELVE:
            {
                return 12;
            }
            default:
            {
                return 0;
            }
        }
    }
}
