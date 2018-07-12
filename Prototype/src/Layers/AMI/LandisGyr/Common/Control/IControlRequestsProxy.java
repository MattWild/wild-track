package Layers.AMI.LandisGyr.Common.Control;

public class IControlRequestsProxy implements Layers.AMI.LandisGyr.Common.Control.IControlRequests {
  private String _endpoint = null;
  private Layers.AMI.LandisGyr.Common.Control.IControlRequests iControlRequests = null;
  
  public IControlRequestsProxy() {
    _initIControlRequestsProxy();
  }
  
  public IControlRequestsProxy(String endpoint) {
    _endpoint = endpoint;
    _initIControlRequestsProxy();
  }
  
  private void _initIControlRequestsProxy() {
    try {
      iControlRequests = (new org.tempuri.ControlRequestServiceLocator()).getBasicHttpBinding_IControlRequests();
      if (iControlRequests != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iControlRequests)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iControlRequests)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iControlRequests != null)
      ((javax.xml.rpc.Stub)iControlRequests)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public Layers.AMI.LandisGyr.Common.Control.IControlRequests getIControlRequests() {
    if (iControlRequests == null)
      _initIControlRequestsProxy();
    return iControlRequests;
  }
  
  public java.lang.Boolean cancelCommand(java.lang.String commandID, java.util.Calendar commandSentDateTime) throws java.rmi.RemoteException{
    if (iControlRequests == null)
      _initIControlRequestsProxy();
    return iControlRequests.cancelCommand(commandID, commandSentDateTime);
  }
  
  public org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.KeyValueData[] getVersion() throws java.rmi.RemoteException{
    if (iControlRequests == null)
      _initIControlRequestsProxy();
    return iControlRequests.getVersion();
  }
  
  public Layers.AMI.LandisGyr.Common.Control.DisposeDevicesResponseTO disposeDevices(Layers.AMI.LandisGyr.Common.Control.DisposeDevicesRequest disposeDevicesReq) throws java.rmi.RemoteException{
    if (iControlRequests == null)
      _initIControlRequestsProxy();
    return iControlRequests.disposeDevices(disposeDevicesReq);
  }
  
  public java.lang.Boolean checkCommDelaysOfGNDCollectors() throws java.rmi.RemoteException{
    if (iControlRequests == null)
      _initIControlRequestsProxy();
    return iControlRequests.checkCommDelaysOfGNDCollectors();
  }
  
  public Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefixRequest manageNetworkPrefixes(Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefix manageNetworkPrefixReq) throws java.rmi.RemoteException{
    if (iControlRequests == null)
      _initIControlRequestsProxy();
    return iControlRequests.manageNetworkPrefixes(manageNetworkPrefixReq);
  }
  
  public Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefixRequest[] getNetworkPrefixes() throws java.rmi.RemoteException{
    if (iControlRequests == null)
      _initIControlRequestsProxy();
    return iControlRequests.getNetworkPrefixes();
  }
  
  public java.lang.Boolean checkCommDelaysForEndpoints() throws java.rmi.RemoteException{
    if (iControlRequests == null)
      _initIControlRequestsProxy();
    return iControlRequests.checkCommDelaysForEndpoints();
  }
  
  public java.lang.Boolean refreshCommStateforGNDCollectors(Layers.AMI.LandisGyr.Common.Control.CollectorCommunication message) throws java.rmi.RemoteException{
    if (iControlRequests == null)
      _initIControlRequestsProxy();
    return iControlRequests.refreshCommStateforGNDCollectors(message);
  }
  
  public void refreshComStateForNetworkDevices(Layers.AMI.LandisGyr.Common.Control.NetworkDevicesCommunication networkDevicesComStatusReq) throws java.rmi.RemoteException{
    if (iControlRequests == null)
      _initIControlRequestsProxy();
    iControlRequests.refreshComStateForNetworkDevices(networkDevicesComStatusReq);
  }
  
  
}