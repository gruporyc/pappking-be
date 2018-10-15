package co.ppk.utilities;

import org.springframework.stereotype.Component;

@Component
public class MessageHelper {

    public String[] asArray (String message){
        message = message.replaceAll("\\s+", " ").trim().toUpperCase();
        String[] parts = message.split(" ");
        String var1 = parts[0];
        String var2 = parts[1];

    return parts;

    }


}
