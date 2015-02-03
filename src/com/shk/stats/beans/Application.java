package com.shk.stats.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity
@SequenceGenerator(name = "APPLICATION_SEQ", sequenceName = "application_sequence", allocationSize = 1)
public class Application {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPLICATION_SEQ")
	int applicationid;

	@OneToOne
	@JoinColumn(name = "statsid")
	Slice slice;

	public Application() {
		// TODO Auto-generated constructor stub
	}

	@OneToMany(mappedBy="application", cascade=CascadeType.ALL)
	List<DataSource> datasources = new ArrayList<DataSource>();
	@OneToMany(mappedBy="application", cascade=CascadeType.ALL)
	List<Memory> memoryList = new ArrayList<Memory>();
	@OneToMany(mappedBy="application", cascade=CascadeType.ALL)
	List<Thread> threadList = new ArrayList<Thread>();
	String name;

	@Transient
	List<String> datasourceNames;
	@Transient
	List<String> memoryNames;
	@Transient
	List<String> threadNames;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDatasources(List<DataSource> datasources) {
		this.datasources = datasources;
	}

	public void setMemoryList(List<Memory> memoryList) {
		this.memoryList = memoryList;
	}

	public void setThreadList(List<Thread> threadList) {
		this.threadList = threadList;
	}

	public Application(String text, List<String> datasourceNames, List<String> memoryNames, List<String> threadNames) {
		parse(text, datasourceNames, memoryNames, threadNames);
	}

	public void parse(String text, List<String> datasourceNames, List<String> memoryNames, List<String> threadNames) {
		int index = -1;
		int dCounter = 0;
		int mCounter = 0;
		int tCounter = 0;
		
		while ((index = text.indexOf("{")) != -1) {
			int lastIndex = text.indexOf("}}");
			String config = text.substring(index, lastIndex + 2);

			if (config.indexOf("\"ActiveCount\"") != -1) {
				DataSource ds = new DataSource(config);
				ds.setName(datasourceNames.get(dCounter++));
				ds.setApplication(this);
				datasources.add(ds);
			}
			if (config.indexOf("\"heap-memory-usage\"") != -1) {
				Memory memory = new Memory(config);
				memory.setName(memoryNames.get(mCounter++));
				memory.setApplication(this);
				memoryList.add(memory);
			}
			if (config.indexOf("\"thread-count\"") != -1) {
				Thread thread = new Thread(config);
				thread.setName(threadNames.get(tCounter++));
				thread.setApplication(this);
				threadList.add(thread);
			}

			text = text.substring(lastIndex + 2);
		}

	}

	public List<DataSource> getDatasources() {
		return datasources;
	}

	public List<Memory> getMemoryList() {
		return memoryList;
	}

	public List<Thread> getThreadList() {
		return threadList;
	}

	public static int secondIndexOf(String text, String limiter) {
		int index = text.indexOf(limiter);
		return text.indexOf(limiter, index + 1);
	}

	public int getId() {
		return applicationid;
	}

	public void setId(int id) {
		this.applicationid = id;
	}

	public Slice getSlice() {
		return slice;
	}

	public void setSlice(Slice slice) {
		this.slice = slice;
	}

}
