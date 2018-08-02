package application.services;

import java.util.List;

public interface DataService {
	
	public void setTarget(Object target) throws Exception;
	
	public Object getTarget();
	
	public List<List<Object>> getData() throws Exception;
	
	public boolean isValid();

}
