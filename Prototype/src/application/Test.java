package application;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.tempuri.ControlRequestServiceLocator;
import org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.KeyValueData;

public class Test {
	String ABNT = "GsAbntServices/App/ControlRequestService.svc";
	String M2M = "M2MOutboundMessageReceiver/ControlRequestService.svc";
	String SBS = "SBSOutboundMessageReceiver/ControlRequestService.svc";
	String CM = "GsCmCommandListener/ControlRequestService.svc";
	String CapabilityService = "CapabilityService/ControlRequestService.svc";
	String ANSI = "ANSIInboundMessageHandler/ControlRequestService.svc";
	
	
	public static void main(String[] args) {
		ControlRequestServiceLocator locator = new ControlRequestServiceLocator();
		
		String[] strings = new String[] {"http://SustCell01.LGD.NET/M2MOutboundMessageReceiver/ControlRequestService.svc", "http://BRZLSustaining2.am.bm.net/SBSOutboundMessageReceiver/ControlRequestService.svc","http://BRZLSustaining1.am.bm.net/GsAbntServices/App/ControlRequestService.svc","http://BRZLSustaining1.am.bm.net/GsCmCommandListener/ControlRequestService.svc", "http://USATLRD510.am.bm.net/CapabilityService/ControlRequestService.svc", "http://USATLRD510.am.bm.net/ANSIInboundMessageHandler/ControlRequestService.svc"};
		for (String string : strings) {
			try {
				locator.setAddress(string);
				
				for (KeyValueData data :locator.getBasicHttpBinding_IControlRequests().getVersion())
				if (data.getKeyName().compareTo("version") == 0) System.out.println(data.getValue());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
