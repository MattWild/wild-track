package application.objects.entities;

public class CollectorComponent extends Component {
	public CollectorComponent(int id) {
		super(ComponentType.COLLECTOR, id);
	}

	private Collector collector;
	
	public Collector getCollector() {
		return collector;
	}

	public void setCollector(Collector collector) {
		this.collector = collector;
	}

}
