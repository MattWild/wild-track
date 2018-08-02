package application.services;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.KeyValueData;
import org.tempuri.ControlRequestServiceLocator;

import application.objects.entities.Component;

public class ComponentRequestService extends ControlRequestServiceLocator implements DataService {

	private static final long serialVersionUID = 1L;
	
	private Component target = null;
	private String locationString = null;
	
	@Override
	public void setTarget(Object object) {
		target = (Component) object;
		
		generateLocationString();
		if (locationString != null)
			setAddress(locationString);
	}
	
	@Override
	public Object getTarget() {
		return target;
	}
	
	@Override
	public List<List<Object>> getData() throws RemoteException, ServiceException {
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<Object> record = new ArrayList<Object>();
		
		record.add(target.getId());
		target.setVersion(getVersion());
		record.add(target.getVersion());
		record.add(target.getUser()); 
		record.add(target.getPass());
		
		list.add(record);
		return list;
	}
	
	@Override
	public boolean isValid() {
		return locationString != null;
	}
	
	private String getVersion() throws RemoteException, ServiceException {
		if(locationString != null) 
			for (KeyValueData data : getBasicHttpBinding_IControlRequests().getVersion())
				if (data.getKeyName().compareTo("version") == 0) return data.getValue();
		return null;
	}

	private void generateLocationString() {
		StringBuilder sb = new StringBuilder();
		sb.append("http://");
		
		if (target.getParent().getFqdn() != null && target.getParent().getFqdn().length() > 0) {
			sb.append(target.getParent().getIp());
		} else {
			locationString = null;
			return;
		}
		
		sb.append("/");
		
		switch(target.getType()) {
		case ABNT:
			sb.append("GsAbntServices/App/ControlRequestService.svc");
			break;
		case ANSI:
			sb.append("ANSIInboundMessageHandler/ControlRequestService.svc");
			break;
		case CAPABILTYSERVICES:
			sb.append("CapabilityService/ControlRequestService.svc");
			break;
		case CM:
			sb.append("GsCmCommandListener/ControlRequestService.svc");
			break;
		case M2M:
			sb.append("M2MOutboundMessageReceiver/ControlRequestService.svc");
			break;
		case SBS:
			sb.append("SBSOutboundMessageReceiver/ControlRequestService.svc");
			break;
		default:
			locationString = null;
			return;
		}
		
		locationString = sb.toString();
	}
	
	

}
