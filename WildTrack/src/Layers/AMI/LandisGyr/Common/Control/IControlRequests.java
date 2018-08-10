/**
 * IControlRequests.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package Layers.AMI.LandisGyr.Common.Control;

public interface IControlRequests extends java.rmi.Remote {
    public java.lang.Boolean cancelCommand(java.lang.String commandID, java.util.Calendar commandSentDateTime) throws java.rmi.RemoteException;
    public org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.KeyValueData[] getVersion() throws java.rmi.RemoteException;
    public Layers.AMI.LandisGyr.Common.Control.DisposeDevicesResponseTO disposeDevices(Layers.AMI.LandisGyr.Common.Control.DisposeDevicesRequest disposeDevicesReq) throws java.rmi.RemoteException;
    public java.lang.Boolean checkCommDelaysOfGNDCollectors() throws java.rmi.RemoteException;
    public Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefixRequest manageNetworkPrefixes(Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefix manageNetworkPrefixReq) throws java.rmi.RemoteException;
    public Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefixRequest[] getNetworkPrefixes() throws java.rmi.RemoteException;
    public java.lang.Boolean checkCommDelaysForEndpoints() throws java.rmi.RemoteException;
    public java.lang.Boolean refreshCommStateforGNDCollectors(Layers.AMI.LandisGyr.Common.Control.CollectorCommunication message) throws java.rmi.RemoteException;
    public void refreshComStateForNetworkDevices(Layers.AMI.LandisGyr.Common.Control.NetworkDevicesCommunication networkDevicesComStatusReq) throws java.rmi.RemoteException;
}
