package com.shk.stats.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "THREAD_SEQ", sequenceName = "thread_sequence", allocationSize = 1)
public class Thread extends Resources {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "THREAD_SEQ")
	int threadid;

	@Column(name="live")
	int threadCount;
	@Column(name="daemon")
	int daemonThreadCount;
	String name;

	@ManyToOne
	@JoinColumn(name = "applicationid")
	Application application;

	public Thread(String text) {
		parse(text);
	}

	public Thread() {
	}

	public void parse(String text) {
		int index = text.indexOf("\"thread-count\"");

		threadCount = Integer.parseInt(text.substring(index + 18,
				text.indexOf(",", index + 18)));

		index = text.indexOf("\"daemon-thread-count\"");
		daemonThreadCount = Integer.parseInt(text.substring(index + 25,
				text.indexOf(",", index + 25)));

	}

	@Override
	public String toString() {
		return threadCount + "," + daemonThreadCount;
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
