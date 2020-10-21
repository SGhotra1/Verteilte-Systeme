import java.lang.reflect.Array;
import java.util.ArrayList;

public class SensorData {

  private int id;
  private String type;
  private String value;
  private static SensorData single_instance = null;

  private ArrayList<Double> humidity1Value;
  private ArrayList<Double> humidity2Value;
  private ArrayList<Double> brightness1Value;
  private ArrayList<Double> temperature1Value;

  private ArrayList<String> humidity1;
  private ArrayList<String> humidity2;
  private ArrayList<String> brightness1;
  private ArrayList<String> temperature1;
  private ArrayList<String> allSensors;

  public SensorData() {
    humidity1Value = new ArrayList<Double>();
    humidity2Value = new ArrayList<Double>();
    brightness1Value = new ArrayList<Double>();
    temperature1Value = new ArrayList<Double>();
    humidity1 = new ArrayList<String>();
    humidity2 = new ArrayList<String>();
    brightness1 = new ArrayList<String>();
    temperature1 = new ArrayList<String>();
    allSensors = new ArrayList<String>();
  }

  public static SensorData getInstance() {
    if (single_instance == null) {
      single_instance = new SensorData();
    }

    return single_instance;
  }

  public void addMeasurement(String msg) {

    String value = msg.substring(msg.indexOf(":") + 2);
    value = value.substring(0, value.indexOf(" ")).replace(",", ".");

    if (msg.contains("Helligkeitssensor")) {
      brightness1Value.add(Double.parseDouble(value));
    } else if (msg.contains("Luftfeuchtigkeitssensor 1")) {
      humidity1Value.add(Double.parseDouble(value));
    } else if (msg.contains("Luftfeuchtigkeitssensor 2")) {
      humidity2Value.add(Double.parseDouble(value));
    } else if (msg.contains("Temperatursensor")) {
      temperature1Value.add(Double.parseDouble(value));
    }
  }

  public void addMessage(String msg) {
    allSensors.add(msg);
    if (msg.contains("Helligkeitssensor")) {
      brightness1.add(msg);
    } else if (msg.contains("Luftfeuchtigkeitssensor 1")) {
      humidity1.add(msg);
    } else if (msg.contains("Luftfeuchtigkeitssensor 2")) {
      humidity2.add(msg);
    } else if (msg.contains("Temperatursensor")) {
      temperature1.add(msg);
    }
  }

  public ArrayList<Double> getHumidity1Value() {
    return humidity1Value;
  }

  public ArrayList<Double> getHumidity2Value() {
    return humidity2Value;
  }

  public ArrayList<Double> getBrightness1Value() {
    return brightness1Value;
  }

  public ArrayList<Double> getTemperature1Value() {
    return temperature1Value;
  }


  public ArrayList<String> getHumidity1() {
    return humidity1;
  }

  public ArrayList<String> getHumidity2() {
    return humidity2;
  }

  public ArrayList<String> getBrightness1() {
    return brightness1;
  }

  public ArrayList<String> getTemperature1() {
    return temperature1;
  }

  public ArrayList<String> getAllSensors() { return allSensors; }
}
