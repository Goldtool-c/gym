package by.gladyshev.gym.service;

import by.gladyshev.gym.entity.PriceList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class SetPriceService {
    @Value("${priceListPath}")
    private String path;
    public void setPrice(PriceList form)
    {
        Resource resource = new ClassPathResource(path);
        File file = null;
        try {
            file = resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(FileWriter writer = new FileWriter(file, false))
        {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 4; i++) {
                sb.append(form.getMonth()[i]);
                sb.append(',');
            }
            for (int i = 0; i < 4; i++) {
                sb.append(form.getN3month()[i]);
                sb.append(',');
            }
            for (int i = 0; i < 4; i++) {
                sb.append(form.getYear()[i]);
                if(i!=3) {
                    sb.append(',');
                }
            }
            writer.write(sb.toString());
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
}
