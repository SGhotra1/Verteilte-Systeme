import com.ghotraspadavecchia.thrift.impl.ProviderService.Processor;
import java.util.ArrayList;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.server.TServer.Args;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SmartHomeCentral {

  private static int port = 1883;
  private static String host = "localhost";
  private static TServer server;
  private static ProviderServiceImpl handler;
  private static com.ghotraspadavecchia.thrift.impl.ProviderService.Processor processor;


  public static void main(String[] args) {
    new HumiditySensor(30.5, 1);
    new HumiditySensor(40.7, 2);
    new TemperatureSensor(22, 1);
    new BrightnessSensor(5000, 1);
    new CentralServer();
    new ProviderServer();

    try {
      handler = new ProviderServiceImpl();
      processor = new com.ghotraspadavecchia.thrift.impl.ProviderService.Processor(handler);
      Runnable simple = new Runnable() {
        public void run() {
          simple(processor);
        }
      };
      new Thread(simple).start();

      MqttClient client = new MqttClient("tcp://localhost:" + port, MqttClient.generateClientId(), null);
      MqttConnectOptions options = new MqttConnectOptions();

      client.connect();
      client.setCallback(new MqttCallback() {
        public void connectionLost(Throwable cause) {
        }

        public void messageArrived(String topic,
            MqttMessage message)
            throws Exception {
           System.out.println("IP: " + host + "\t Port: " + port + " " + message.toString());
          SensorData.getInstance().addMeasurement(message.toString());
          SensorData.getInstance().addMessage(message.toString());
        }

        public void deliveryComplete(IMqttDeliveryToken token) {
        }
      });
      client.subscribe("server/#");

    } catch (Exception e) {
      e.printStackTrace();
    }
  } // end of main

  public static void simple(com.ghotraspadavecchia.thrift.impl.ProviderService.Processor processor) {
    try {
      TServerTransport serverTransport = new TServerSocket(9090);
      TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));

      System.out.println("Starting the simple server...");
      server.serve();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
