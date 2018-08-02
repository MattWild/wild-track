package application.objects.entities;

public class CollectorComponent extends Component {
	public CollectorComponent(int id, Server server) {
		super(ComponentType.AMS, id, server);
	}
	
	public CollectorComponent(Server server) {
		super(ComponentType.AMS, server);
	}

	private Collector collector;
	
	public Collector getCollector() {
		return collector;
	}

	public void setCollector(Collector collector) {
		this.collector = collector;
	}

}
