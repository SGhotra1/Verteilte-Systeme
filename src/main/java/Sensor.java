import java.util.Random;
import org.eclipse.paho.client.mqttv3.*;
public class Sensor extends Thread {

  protected double value;
  protected int number;
  protected static String host = "localhost";
  protected static int port = 1883;
  public Sensor(){

  }
  public Sensor(int v, int n){
    this.value = v;
    this.number = n;
  }

  public static void main(String[] args) {

  } // end of main
}
