import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import logic.Server;

import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.ServerSocket;
import java.security.GeneralSecurityException;
import java.util.Date;

public class Main {

//    public static void main(String[] args) throws IOException {
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//
//        UpdatePasswordAction updatePasswordAction = new UpdatePasswordAction("vered", "2334");
//
//        String result = objectMapper.writeValueAsString(updatePasswordAction);
//
//        System.out.println(result);
//
//        UpdatePasswordAction updatePasswordAction1 = objectMapper.readValue(result, UpdatePasswordAction.class);
//
//        System.out.println(updatePasswordAction1.toString());
//
//    }

    public static void main(String[] args) throws IOException {

        Server server = new Server();

        try (ServerSocket serverSocket = server.getSSLServerSocket()) {

            System.out.println(new Date() + " --> Server waits for clients...");
            while (true) {
                final SSLSocket socket = (SSLSocket) serverSocket.accept(); // blocking
                server.createConnection(socket);

            } // while
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }// main

}
