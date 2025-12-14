package com.sweetshop;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MongoUriTest {

    @Test
    public void printMongoUriAndCheckSrv() throws Exception {
        Properties props = new Properties();
        try (InputStream in = new FileInputStream("src/main/resources/application.properties")) {
            props.load(in);
        }

        String uri = props.getProperty("spring.data.mongodb.uri");
        System.out.println("spring.data.mongodb.uri=" + uri);
        assertNotNull(uri, "spring.data.mongodb.uri should be set in application.properties");

        if (uri != null && uri.startsWith("mongodb+srv://")) {
            // extract host part after '@' and before first '/'
            int at = uri.indexOf('@');
            if (at > -1) {
                String hostPart = uri.substring(at + 1);
                int slash = hostPart.indexOf('/');
                if (slash > 0)
                    hostPart = hostPart.substring(0, slash);
                System.out.println("Extracted SRV host: " + hostPart);

                // run nslookup -type=SRV _mongodb._tcp.{hostPart}
                String srv = "_mongodb._tcp." + hostPart;
                try {
                    ProcessBuilder pb = new ProcessBuilder("nslookup", "-type=SRV", srv);
                    pb.redirectErrorStream(true);
                    Process p = pb.start();
                    try (BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                        String line;
                        System.out.println("nslookup output for " + srv + ":");
                        while ((line = r.readLine()) != null) {
                            System.out.println(line);
                        }
                    }
                    p.waitFor();
                } catch (IOException e) {
                    System.out.println("nslookup not available or failed: " + e.getMessage());
                }
            }
        }
    }
}
