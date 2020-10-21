import com.ghotraspadavecchia.thrift.impl.ProviderService.Client;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ProviderServer extends Thread {
  private static int index;
  public ProviderServer(){
    index = 0;
    this.start();
  }

  public void run(){

      try {
        TTransport transport;

        transport = new TSocket("localhost", 9090);
        transport.open();

        TProtocol protocol = new TBinaryProtocol(transport);
        com.ghotraspadavecchia.thrift.impl.ProviderService.Client client = new Client(protocol);
        while(index < 5000) {
          perform(client);
          Thread.sleep(250);
        }
        transport.close();
      } catch (TException x) {
        x.printStackTrace();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

  }

  private static void perform(com.ghotraspadavecchia.thrift.impl.ProviderService.Client client) throws TException{

    if(SensorData.getInstance().getAllSensors().size() > 0 && SensorData.getInstance().getAllSensors().size() > index) {
      client.saveData(SensorData.getInstance().getAllSensors().get(index), "src/main/SmarthomeData.txt");
      index++;
    }
  }

}
