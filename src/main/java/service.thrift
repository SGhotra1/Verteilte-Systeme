namespace java com.ghotraspadavecchia.thrift.impl


service ProviderService {
  void saveData(1:string value, 2:string fileName)
}