package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "event_type_definition")
public class EventTypeDefinition implements Serializable {
	private static final long serialVersionUID = 6067921645190702550L;

	@Id
	@SequenceGenerator(name = "eventTypeDefinitionIdSeq", sequenceName = "event_type_definition_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eventTypeDefinitionIdSeq")
	@Column(name = "id")
	private Integer id;

	@Basic
	@Column(name = "name", length = 45, nullable = false)
	private String sequenceName;

	@Transient
	private String seqOrder;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSequenceName() {
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public String getSeqOrder() {
		return seqOrder;
	}

	public void setSeqOrder(String seqOreder) {
		this.seqOrder = seqOreder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((sequenceName == null) ? 0 : sequenceName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventTypeDefinition recSeq = (EventTypeDefinition) obj;
		if (id == null) {
			if (recSeq.id != null)
				return false;
		} else if (!id.equals(recSeq.id))
			return false;
		if (sequenceName == null) {
			if (recSeq.sequenceName != null)
				return false;
		} else if (!sequenceName.equals(recSeq.sequenceName))
			return false;

		return true;
	}

}
