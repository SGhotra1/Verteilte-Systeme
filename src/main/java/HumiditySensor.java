import java.util.Random;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class HumiditySensor extends Sensor {

  public HumiditySensor(double v, int n){
    this.value = v;
    this.number = n;
    this.start();
  }

  public void run(){
    int i = 0;
    Random random = new Random();
    MqttClient client;
    MqttConnectOptions mqttConnectOpts = new MqttConnectOptions();
    mqttConnectOpts.setCleanSession(true);

    while(true){
      try {
        client = new MqttClient("tcp://" + host + ":" + port, MqttClient.generateClientId(), null);
        client.connect(mqttConnectOpts);
        byte data[] = new byte[1024];
        data = toString().getBytes();
        MqttMessage message = new MqttMessage(data);
        message.setQos(0); //Fire and forget
        client.publish("server/luftfeuchtigkeit/" + number, message);
        if (i % 2 == 0) {
          this.value -= random.nextDouble();
          if(this.value < 0){
            this.value = 0;
          }
        } else {
          this.value += random.nextDouble();
          if(this.value > 100){
            this.value = 100;
          }
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
    return "Luftfeuchtigkeitssensor " + this.number + ": " +  String.format("%.2f",this.value) + " %";
  }
}
