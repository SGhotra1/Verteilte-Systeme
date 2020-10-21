import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import java.util.Arrays;
import java.util.regex.Pattern;


public class CentralServer extends Thread {

  private static int port = 80;
  private static String host = "localhost";
  private DatagramSocket serverSocket;


  public CentralServer() {
    this.start();
  }

  public void run() {
    HttpServer server;

    try {
      server = HttpServer.create(new InetSocketAddress(port), 0);
     // DatagramSocket serverSocket = new DatagramSocket(port);
      CentralServer s = new CentralServer();
      myHandler mh = s.new myHandler();
      server.createContext("/server", mh);

      server.setExecutor(null);
      server.start();
      System.out.println("Server started");

    } catch (Exception e) {
      System.out.println(e);
    }
  }
/*  public static void main(String[] args) throws Exception{
    //ServerSocket listenSocket = new ServerSocket(port);

  }*/

  class myHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange t) throws IOException {
      t.getResponseHeaders().add("Content-type", "text/html");
      t.getResponseHeaders().add("Refresh", "3");
      //t.getRequestBody();
      String uri = t.getRequestURI().toString();
      String response = "";
      if(Pattern.matches("/server/history/((luftfeuchtigkeit/1)|(helligkeit)|(temperatur)|(luftfeuchtigkeit/2))", uri)){
        if(uri.contains("helligkeit")){
           for(int i = 0; i < SensorData.getInstance().getBrightness1().size(); i++){
             response = response + SensorData.getInstance().getBrightness1().get(i) + "<br>";
           }
        }
        else if(uri.contains("luftfeuchtigkeit")){
          if(uri.contains("1")){
            for(int i = 0; i < SensorData.getInstance().getHumidity1().size(); i++){
              response = response + SensorData.getInstance().getHumidity1().get(i) + "<br>";
            }
          }
          else if(uri.contains("2")){
            for(int i = 0; i < SensorData.getInstance().getHumidity2().size(); i++){
              response = response + SensorData.getInstance().getHumidity2().get(i) + "<br>";
            }
          }
        }
        else if(uri.contains("temperatur")){
          for(int i = 0; i < SensorData.getInstance().getTemperature1().size(); i++){
            response = response + SensorData.getInstance().getTemperature1().get(i) + "<br>";
          }
        }
      }
      else if(Pattern.matches("/server/helligkeit", uri)){
        response = SensorData.getInstance().getBrightness1().get(SensorData.getInstance().getBrightness1().size() - 1);
      }
      else if(Pattern.matches("/server/luftfeuchtigkeit/((1)|(2))", uri)){
        if(uri.contains("1")){
          response = SensorData.getInstance().getHumidity1().get(SensorData.getInstance().getHumidity1().size() - 1);
        }
        else if(uri.contains("2")){
          response = SensorData.getInstance().getHumidity2().get(SensorData.getInstance().getHumidity2().size() - 1);
        }
      }
      else if(Pattern.matches("/server/temperatur", uri)){
        response = SensorData.getInstance().getTemperature1().get(SensorData.getInstance().getTemperature1().size() - 1);
      }
      else {
         response =
            SensorData.getInstance().getBrightness1()
                .get(SensorData.getInstance().getBrightness1().size() - 1) + "<br>"
                + SensorData.getInstance().getHumidity1()
                .get(SensorData.getInstance().getHumidity1().size() - 1) + "<br>"
                + SensorData.getInstance().getHumidity2()
                .get(SensorData.getInstance().getHumidity2().size() - 1) + "<br>"
                + SensorData.getInstance().getTemperature1()
                .get(SensorData.getInstance().getTemperature1().size() - 1) + "<br>";
      }
      //String response = Arrays.toString(SensorData.getInstance().getBrightness1().toArray());
      t.sendResponseHeaders(200, response.length());
      OutputStream os = t.getResponseBody();
      os.write(response.getBytes());
      os.close();
    }
  }
}
