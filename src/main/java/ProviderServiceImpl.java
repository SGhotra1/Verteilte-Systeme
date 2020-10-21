import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ProviderServiceImpl implements com.ghotraspadavecchia.thrift.impl.ProviderService.Iface {

  @Override
  public void saveData(String value, String fileName) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
      writer.append(value + "\n");
      writer.close();
    }
    catch (IOException e){
      e.printStackTrace();
    }
  }
}
