package com.disney.play;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

@SpringBootApplication
@RestController
public class Application
{
    @RequestMapping("/")
    public String index(@RequestParam(value="key") String key)
    {
        DatabaseReference d = FirebaseDatabase.getInstance().getReference();
        d.child("azureTest").setValue("helloWorld-key: [" + key + "]");
        return "{\"id\":1,\"content\":\"Hello, World!\"}";
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setServiceAccount(new FileInputStream("serviceAccountCredentials.json"))
                .setDatabaseUrl("https://disneyplayapp.firebaseio.com/")
                .build();
               FirebaseApp.initializeApp(options);
        SpringApplication.run(Application.class, args);
    }
}