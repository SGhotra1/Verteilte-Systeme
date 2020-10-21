import java.util.Random;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class TemperatureSensor extends Sensor {

  public TemperatureSensor(double v, int n) {
    this.value = v;
    this.number = n;
    this.start();
  }

  public void run() {
    Random random = new Random();
    int i = 0;
    MqttClient client;
    MqttConnectOptions mqttConnectOpts = new MqttConnectOptions();
    mqttConnectOpts.setCleanSession(true);
    while (true) {
      try {
        client = new MqttClient("tcp://" + host + ":" + port, MqttClient.generateClientId(), null);
        client.connect(mqttConnectOpts);
        byte data[] = new byte[1024];
        data = toString().getBytes();
        MqttMessage message = new MqttMessage(data);
        message.setQos(0); //Fire and forget
        client.publish("server/temperatur/" + number, message);
        if (i % 2 == 0) {
          this.value -= random.nextDouble();
        } else {
          this.value += random.nextDouble();
        }
        try {
          Thread.sleep(1500);
        } catch (Exception e) {
          e.printStackTrace();
        }
        i++;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public String toString() {
    return "Temperatursensor " + this.number + ": " + String.format("%.2f", this.value)
        + " Grad Celsius";
  }
}
