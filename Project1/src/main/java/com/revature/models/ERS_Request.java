package com.revature.models;

import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class ERS_Request {
	private int id;
	private BigDecimal amount;
	private Timestamp submitted;
	private Timestamp resolved;
	private String description;
	private BufferedImage receipt;
	private int author;
	private Integer resolver;
	private int status;
	private int type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Timestamp getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Timestamp submitted) {
		this.submitted = submitted;
	}

	public Timestamp getResolved() {
		return resolved;
	}

	public void setResolved(Timestamp resolved) {
		this.resolved = resolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BufferedImage getReceipt() {
		return receipt;
	}

	public void setReceipt(BufferedImage recipt) {
		this.receipt = recipt;
	}

	public int getAuthor() {
		return author;
	}

	public void setAuthor(int author) {
		this.author = author;
	}

	public Integer getResolver() {
		return resolver;
	}

	public void setResolver(Integer resolver) {
		this.resolver = resolver;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ERS_Request [id=" + id + ", amount=" + amount + ", submitted=" + submitted + ", resolved=" + resolved
				+ ", description=" + description + ", author=" + author + ", resolver=" + resolver + ", status="
				+ status + ", type=" + type + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + author;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((resolved == null) ? 0 : resolved.hashCode());
		result = prime * result + ((resolver == null) ? 0 : resolver.hashCode());
		result = prime * result + status;
		result = prime * result + ((submitted == null) ? 0 : submitted.hashCode());
		result = prime * result + type;
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
		ERS_Request other = (ERS_Request) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (author != other.author)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (resolved == null) {
			if (other.resolved != null)
				return false;
		} else if (!resolved.equals(other.resolved))
			return false;
		if (resolver == null) {
			if (other.resolver != null)
				return false;
		} else if (!resolver.equals(other.resolver))
			return false;
		if (status != other.status)
			return false;
		if (submitted == null) {
			if (other.submitted != null)
				return false;
		} else if (!submitted.equals(other.submitted))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public ERS_Request(int id, BigDecimal amount, Timestamp submitted, Timestamp resolved, String description,
			BufferedImage receipt, int author, Integer resolver, int status, int type) {
		super();
		this.id = id;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.receipt = receipt;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}
}
