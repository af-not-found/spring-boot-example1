package net.afnf.sboot1;

import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class SpringBootApplication {

    private static Log logger = LogFactory.getLog(SpringBootApplication.class);

    public static void main(String[] args) throws Exception {
        shutdownAndRun(SpringBootApplication.class, args);
    }

    protected static ConfigurableApplicationContext shutdownAndRun(Class<?> clazz, String[] args) {

        try {
            return SpringApplication.run(clazz, args);
        }
        catch (Exception e) {

            // java.net.BindException: Address already in use: bind
            if ("Tomcat connector in failed state".equals(e.getMessage())) {

                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) new URL("http://localhost:8081/shutdown").openConnection();
                    connection.setRequestMethod("POST");
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        logger.info("Shutdown OK");

                        // launch again
                        return SpringApplication.run(clazz, args);
                    }
                    else {
                        throw new IllegalStateException("Invalid response code : " + responseCode);
                    }
                }
                catch (Exception e2) {
                    throw new IllegalStateException("Shutdown failed", e2);
                }
                finally {
                    connection.disconnect();
                }
            }
            else {
                throw e;
            }
        }
    }
}
