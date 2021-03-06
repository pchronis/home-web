package eu.daiad.web.model.amphiro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AmphiroSessionDetails extends AmphiroSession {

	private ArrayList<AmphiroMeasurement> measurements;

	public AmphiroSessionDetails() {
		super();

		this.measurements = new ArrayList<AmphiroMeasurement>();
	}

	public void add(AmphiroMeasurement measurement) {
		this.measurements.add(measurement);
	}

	public ArrayList<AmphiroMeasurement> getMeasurements() {
		Collections.sort(this.measurements,
				new Comparator<AmphiroMeasurement>() {

					public int compare(AmphiroMeasurement o1,
							AmphiroMeasurement o2) {
						if (o1.getIndex() <= o2.getIndex()) {
							return -1;
						} else {
							return 1;
						}
					}
				});

		return this.measurements;
	}

	public void setMeasurements(ArrayList<AmphiroMeasurement> measurements) {
		if (measurements == null) {
			new ArrayList<AmphiroMeasurement>();
		} else {
			this.measurements = measurements;
		}
	}
}
