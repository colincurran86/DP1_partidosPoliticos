package fingerprint;
import java.util.ArrayList;
import java.util.List;

public class MinutiaeTuples {
	private List<Tupla> features;
	
	public MinutiaeTuples(){
		features=new ArrayList<Tupla>();
	}
	public List<Tupla> getFeatures() {
		return features;
	}

	public void setFeatures(List<Tupla> features) {
		this.features = features;
	}
	
	public void add(Tupla t){
		features.add(t);
	}
}
