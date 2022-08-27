package by.gladyshev.gym.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class SetTextService {
    @Value("${mainTextPath}")
    private String path;
    public void setText(String text){

        Resource resource = new ClassPathResource(path);
        File file = null;
        try {
            file = resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(FileWriter writer = new FileWriter(file, false))
        {
            writer.write(text);
            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        System.out.println(text);
    }
}
