package by.gladyshev.gym.service;

import by.gladyshev.gym.entity.PriceList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

@Service
public class GetPriceService {

    @Value("${priceListPath}")
    private String path;// = "D:\\Работа\\gym\\src\\main\\resources\\priceList.txt";

    public String getPath() {
        return path;
    }

    public PriceList getPrice() {
        PriceList res = new PriceList();
        int[] prices = new int[4];
        int[] content = extractPrice();
        for (int i = 0; i < 4; i++) {
            prices[i] = content[i];
        }
        res.setMonth(prices.clone());
        for (int i = 0; i < 4; i++) {
            prices[i]=content[4+i];
        }
        res.setN3month(prices.clone());
        for (int i = 0; i < 4; i++) {
            prices[i]=content[8+i];
        }
        res.setYear(prices.clone());
        return res;
    }
    private int[] extractPrice() {
        StringBuilder sb = new StringBuilder();
        Resource resource = new ClassPathResource(path);
        File file = null;
        try {
            InputStream input = resource.getInputStream();
            file = resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileReader reader = new FileReader(file))
        {
            int c;
            while((c=reader.read())!=-1){

                sb.append((char)c);
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        String prstr = sb.toString();
        sb = new StringBuilder();
        int counter = 0;
        int[] prices = new int[12];
        for (int i = 0; i < prstr.length(); i++) {
            if(prstr.charAt(i)!=',')
            {
                sb.append(prstr.charAt(i));
            }
            else {
                prices[counter] = Integer.parseInt(sb.toString());
                sb = new StringBuilder();
                counter++;
            }
        }
        prices[11]=Integer.parseInt(sb.toString());
        return prices;
    }

}
