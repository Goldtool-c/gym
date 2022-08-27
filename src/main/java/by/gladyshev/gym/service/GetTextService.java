package by.gladyshev.gym.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

@Service
public class GetTextService {
    @Value("${mainTextPath}")
    private String path;
    public String getText()
    {
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
        return sb.toString();
    }
}
