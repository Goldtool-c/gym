package by.gladyshev.gym.service;

import by.gladyshev.gym.dao.MemberDAO;
import by.gladyshev.gym.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaginationService {
    @Value("${paginationStringNumber}")
    private int rowsNumber;
    private final MemberDAO memberDAO;
    public List<Member> getPage(int page, int maxId)
    {
        ArrayList<Member> members = new ArrayList<>();
        int id = (rowsNumber*(page-1))+1;
        for (int i = (rowsNumber*(page-1))+1; i <(rowsNumber*(page-1))+1+rowsNumber&&id<=maxId; i++, id++) {
            Member temp = (Member) memberDAO.show(id);
            if(temp==null)
            {
                i--;
                continue;
            }
            members.add(temp);
        }
        return members;
    }

}
