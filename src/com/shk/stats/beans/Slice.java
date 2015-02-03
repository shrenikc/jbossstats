package com.shk.stats.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;

@Entity(name = "stats")
@SequenceGenerator(name = "STATS_SEQ", sequenceName = "slice_sequence", allocationSize = 1)
public class Slice {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STATS_SEQ")
	@Column(name = "statsid")
	int id;
	String environment;
	String sliceName;
	String machineName;
	Date configTime;
	String batchId;

	@OneToOne(mappedBy = "slice", cascade=CascadeType.ALL)
	Application nsp;
	@OneToOne(mappedBy = "slice", cascade=CascadeType.ALL)
	Application tm1;
	@OneToOne(mappedBy = "slice", cascade=CascadeType.ALL)
	Application tm2;

	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyyMMdd_HHmmss");

	
	private static List<String> nspDatasourceNames = Arrays.asList("PROD-Docstore-10G",
			"PROD-Docstore-11G", "PROD-NSP-Hub-jms", "PROD-NSP-Hub-smx",
			"PROD-NSP-Services", "PROD-NSP-int1-1", "PROD-NSP-int1-2",
			"PROD-NSP-int2-1", "PROD-NSP-int2-2", "PROD-NSP-int3-1",
			"PROD-NSP-int3-2", "PROD-NSP-int4-1", "PROD-NSP-int4-2",
			"PROD-NSP-int5-1", "PROD-NSP-int5-2");

	private static List<String> nspMemoryNames = Arrays.asList("PROD-Docstore-Config",
			"PROD-Docstore-10G", "PROD-Docstore-11G", "PROD-NSP-Config",
			"PROD-NSP-Hub", "PROD-NSP-Services", "PROD-NSP-int1-1",
			"PROD-NSP-int1-2", "PROD-NSP-int2-1", "PROD-NSP-int2-2",
			"PROD-NSP-int3-1", "PROD-NSP-int3-2", "PROD-NSP-int4-1",
			"PROD-NSP-int4-2", "PROD-NSP-int5-1", "PROD-NSP-int5-2");

	private static List<String> nspThreadNames = Arrays.asList("PROD-Docstore-Config",
			"PROD-Docstore-10G", "PROD-Docstore-11G", "PROD-NSP-Config",
			"PROD-NSP-Hub", "PROD-NSP-Services", "PROD-NSP-int1-1",
			"PROD-NSP-int1-2", "PROD-NSP-int2-1", "PROD-NSP-int2-2",
			"PROD-NSP-int3-1", "PROD-NSP-int3-2", "PROD-NSP-int4-1",
			"PROD-NSP-int4-2", "PROD-NSP-int5-1", "PROD-NSP-int5-2");
	

	
	private static List<String> tm1DatasourceNames = Arrays.asList("TM1-mortgage",
			"TM1-nsp", "TM1-quartzonly");

	private static List<String> tm1MemoryNames = Arrays.asList("TM1");

	private static List<String> tm1ThreadNames = Arrays.asList("TM1");


	private static List<String> tm2DatasourceNames = Arrays.asList("TM2-mortgage",
			"TM2-nsp", "TM2-quartzonly");

	private static List<String> tm2MemoryNames = Arrays.asList("TM2");

	private static List<String> tm2ThreadNames = Arrays.asList("TM2");

	
	public String getEnvironment() {
		return environment;
	}

	public Slice(String text) {
		parse(text);
	}

	public String getSliceName() {
		return sliceName;
	}

	public String getMachineName() {
		return machineName;
	}

	public Application getNsp() {
		return nsp;
	}

	public Application getTm1() {
		return tm1;
	}

	public Application getTm2() {
		return tm2;
	}

	@Override
	public String toString() {
		return "Slice [environment=" + environment + ", sliceName=" + sliceName
				+ ", machineName=" + machineName + ", configTime=" + configTime
				+ ", nsp=" + nsp + ", tm1=" + tm1 + ", tm2=" + tm2 + "]";
	}

	public Date getTime() {
		return configTime;
	}

	public Slice() {
		super();
	}

	public void parse(String text) {
		int index = text.indexOf("#", 2);

		String header = text.substring(2, index);

		StringTokenizer tokenizer = new StringTokenizer(header, ",");

		environment = tokenizer.nextToken();
		machineName = tokenizer.nextToken();
		sliceName = tokenizer.nextToken();

		try {
			batchId = tokenizer.nextToken();
			configTime = sdf.parse(batchId);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		text = text.substring(index);

		if (text.startsWith("#NSP")) {
			index = text.indexOf("#TM");
			nsp = new Application(text.substring(4, index), nspDatasourceNames, nspMemoryNames, nspThreadNames);
			nsp.setName("NSP");
			nsp.setSlice(this);
			text = text.substring(index);
		}
		if (text.startsWith("#TM1")) {
			index = text.indexOf("#TM", 2);
			tm1 = new Application(text.substring(4, index), tm1DatasourceNames, tm1MemoryNames, tm1ThreadNames);
			tm1.setName("TM1");
			tm1.setSlice(this);
			text = text.substring(index);
		}
		if (text.startsWith("#TM2")) {
			tm2 = new Application(text.substring(4), tm2DatasourceNames, tm2MemoryNames, tm2ThreadNames);
			tm2.setName("TM2");
			tm2.setSlice(this);
		}

	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	

}
