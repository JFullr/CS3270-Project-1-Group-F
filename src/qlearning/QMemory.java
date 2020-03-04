package qlearning;

import java.util.HashMap;

public class QMemory {
	
	HashMap<QValue, Double> valueMap;

	public QMemory() {
		this.valueMap = new HashMap<QValue, Double>();
	}

	public void setWeight(QValue key, double weight) {
		this.valueMap.put(key, 0.0);
	}

	public double getWeight(QValue key) {
		if(!this.valueMap.containsKey(key)) {
			this.valueMap.put(key, 0.0);
		}
		return this.valueMap.get(key);
	}


}
