package com.vsoftcorp.kls.business.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "event_type")
public class EventType implements Serializable {
	private static final long serialVersionUID = 6067921645190702550L;

	@Id
	@SequenceGenerator(name = "eventTypeSeq", sequenceName = "event_type_id_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "eventTypeSeq")
	@Column(name = "id")
	private Integer id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "event_type_def_id", referencedColumnName = "id")
	private EventTypeDefinition eventDefId;

	@Basic
	@Column(name = "recovery_order_id")
	private Integer recoveryOrderId;

	@Basic
	@Column(name = "recovery_sequence")
	private Integer recoverySequence;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EventTypeDefinition getEventDefId() {
		return eventDefId;
	}

	public void setEventDefId(EventTypeDefinition eventDefId) {
		this.eventDefId = eventDefId;
	}

	public Integer getRecoveryOrderId() {
		return recoveryOrderId;
	}

	public void setRecoveryOrderId(Integer recoveryOrderId) {
		this.recoveryOrderId = recoveryOrderId;
	}

	public Integer getRecoverySequence() {
		return recoverySequence;
	}

	public void setRecoverySequence(Integer recoverySequence) {
		this.recoverySequence = recoverySequence;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((eventDefId == null) ? 0 : eventDefId.hashCode());

		result = prime * result
				+ ((recoveryOrderId == null) ? 0 : recoveryOrderId.hashCode());
		result = prime
				* result
				+ ((recoverySequence == null) ? 0 : recoverySequence.hashCode());

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
		EventType recSeq = (EventType) obj;
		if (id == null) {
			if (recSeq.id != null)
				return false;
		} else if (!id.equals(recSeq.id))
			return false;
		if (eventDefId == null) {
			if (recSeq.eventDefId != null)
				return false;
		} else if (!eventDefId.equals(recSeq.eventDefId))
			return false;
		if (recoveryOrderId == null) {
			if (recSeq.recoveryOrderId != null)
				return false;
		} else if (!recoveryOrderId.equals(recSeq.recoveryOrderId))
			return false;
		if (recoverySequence == null) {
			if (recSeq.recoverySequence != null)
				return false;
		} else if (!recoverySequence.equals(recSeq.recoverySequence))
			return false;

		return true;
	}

}
