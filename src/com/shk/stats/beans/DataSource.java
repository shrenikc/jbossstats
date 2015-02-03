package com.shk.stats.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "DATASOURCE_SEQ", sequenceName = "datasource_sequence", allocationSize = 1)
public class DataSource extends Resources {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DATASOURCE_SEQ")
	int datasourceid;

	int activeCount;
	int availableCount;
	int maxUsedCount;
	
	String name;
	
	@ManyToOne
	@JoinColumn(name="applicationid")
	Application application;

	public DataSource(String text) {
		parse(text);
	}

	public void parse(String text) {
		int index = text.indexOf("\"ActiveCount\"");

		activeCount = Integer.parseInt(text.substring(index + 18,
				text.indexOf("\"", index + 18)));

		index = text.indexOf("\"AvailableCount\"");
		availableCount = Integer.parseInt(text.substring(index + 21,
				text.indexOf("\"", index + 21)));

		index = text.indexOf("\"MaxUsedCount\"");
		maxUsedCount = Integer.parseInt(text.substring(index + 19,
				text.indexOf("\"", index + 19)));

	}

	@Override
	public String toString() {
		return activeCount + "," + availableCount + "," + maxUsedCount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

}
