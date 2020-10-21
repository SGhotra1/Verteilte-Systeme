import java.util.Random;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class BrightnessSensor extends Sensor {
  private int value;

  public BrightnessSensor(int v, int n){
    this.number = n;
    this.value = v;
    this.start();
  }

  public void run(){
    Random random = new Random();
    int i = 0;
    MqttClient client;
    MqttConnectOptions mqttConnectOpts = new MqttConnectOptions();
    mqttConnectOpts.setCleanSession(true);
    while(true) {
      try {
        client = new MqttClient("tcp://" + host + ":" + port, MqttClient.generateClientId(), null);
        client.connect(mqttConnectOpts);
        byte data[] = new byte[1024];
        data = toString().getBytes();
        MqttMessage message = new MqttMessage(data);
        message.setQos(0); //Fire and forget
        client.publish("server/helligkeit/" + number, message);
        if (i % 2 == 0) {
          this.value -= random.nextInt(1000) + 250;
          if(this.value < 0){
            value = 0;
          }
        } else {
          this.value += random.nextInt(1000) + 250;
        }
        try {
          Thread.sleep(1500);
        } catch (Exception e) {
          e.printStackTrace();
        }
        i++;
      } catch(Exception e){
        e.printStackTrace();
        }
    }

  }

  public String toString(){
    return "Helligkeitssensor " + this.number + ": " + this.value + " Lux";
  }
}
