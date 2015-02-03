package com.shk.stats.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Memory extends Resources {

	@Id
	@SequenceGenerator(name = "MEMORY_SEQ", sequenceName = "memory_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMORY_SEQ")
	int memoryid;
	long heapInit;
	long heapUsed;
	long heapCommitted;
	long heapMax;
	long nonHeapInit;
	long nonHeapUsed;
	long nonHeapCommitted;
	long nonHeapMax;
	String name;

	@ManyToOne
	@JoinColumn(name = "applicationid")
	Application application;

	public Memory(String text) {
		parse(text);
	}

	public Memory() {
	}

	public void parse(String text) {
		int index = text.indexOf("\"init\"");

		heapInit = Long.parseLong(text.substring(index + 10,
				text.indexOf("L", index + 10)))
				/ (1024 * 1024);

		index = text.indexOf("\"used\"");
		heapUsed = Long.parseLong(text.substring(index + 10,
				text.indexOf("L", index + 10)))
				/ (1024 * 1024);

		index = text.indexOf("\"committed\"");
		heapCommitted = Long.parseLong(text.substring(index + 15,
				text.indexOf("L", index + 15)))
				/ (1024 * 1024);

		index = text.indexOf("\"max\"");
		heapMax = Long.parseLong(text.substring(index + 9,
				text.indexOf("L", index + 9)))
				/ (1024 * 1024);

		// Get non-heap configs
		text = text.substring(index + 9);
		index = text.indexOf("\"init\"");

		nonHeapInit = Long.parseLong(text.substring(index + 10,
				text.indexOf("L", index + 10)))
				/ (1024 * 1024);

		index = text.indexOf("\"used\"");
		nonHeapUsed = Long.parseLong(text.substring(index + 10,
				text.indexOf("L", index + 10)))
				/ (1024 * 1024);

		index = text.indexOf("\"committed\"");
		nonHeapCommitted = Long.parseLong(text.substring(index + 15,
				text.indexOf("L", index + 15)))
				/ (1024 * 1024);

		index = text.indexOf("\"max\"");
		nonHeapMax = Long.parseLong(text.substring(index + 9,
				text.indexOf("L", index + 9)))
				/ (1024 * 1024);

	}

	@Override
	public String toString() {
		return heapInit + "," + heapUsed + "," + heapCommitted + "," + heapMax
				+ "," + nonHeapInit + "," + nonHeapUsed + ","
				+ nonHeapCommitted + "," + nonHeapMax;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
