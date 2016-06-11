package com.egen.egen_be_challenge.utilities;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

@Rule(name = "Upper Bound Rule", description = "This rule will save alert if the person weight shoots by 10% of base weight.")
public class EasyRule2 {
	
	private int baseValue;
	private Sensor sensor;

	public EasyRule2() {
	}

	public EasyRule2(int baseValue, Sensor sensor) {
		this.baseValue = baseValue;
		this.sensor = sensor;
	}

	@Condition
	public boolean when() {
		// check if the person weight shoots by 10%
		return sensor.getValue() > (1.1 * baseValue);
	}

	@Action
	public void then() throws Exception {
		new MorphiaMongo().createAlert(sensor.getTimeStamp() , "The weight of the person shoots 10% over his base weight");
	}
}
