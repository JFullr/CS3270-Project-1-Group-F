package qlearning;

import java.util.HashMap;
import java.util.Set;

public class QMemory {
	
	HashMap<QValue, Double> valueMap;

	public QMemory() {
		this.valueMap = new HashMap<QValue, Double>();
	}

	public void setWeight(QValue key, double weight) {
		this.valueMap.put(key, weight);
	}

	public double getWeight(QValue key) {
		if(!this.valueMap.containsKey(key)) {
			this.valueMap.put(key, 0.0);
		}
		return this.valueMap.get(key);
	}

	public Set<QValue> getKeys() {
		return this.valueMap.keySet();
	}
	
}
