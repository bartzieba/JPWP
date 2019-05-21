package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Sms {
    String kod;

    Sms(String kod){
        this.kod=kod;
    }

    public String Sms_wyslij() {
        try {
            // Construct data
            String apiKey = "apikey=" + "pVNXkubkIjM-e1clyPpYFloce4ewBvChB3lvQtTkeL";
            String message = "&message=" + kod;
            String sender = "&sender=" + "BankJPWP";
            String numbers = "&numbers=" + "048698111596";

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();
            System.out.println(kod);
            return stringBuffer.toString();
        } catch (Exception e) {
            System.out.println("Error SMS "+e);
            return "Error "+e;
        }
    }
}