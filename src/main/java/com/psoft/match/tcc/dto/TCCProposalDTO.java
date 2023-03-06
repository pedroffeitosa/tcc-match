package com.psoft.match.tcc.dto;

import java.util.Collection;

import com.psoft.match.tcc.model.tcc.TCCStatus;


public class TCCProposalDTO {

	private String tittle;
	
	private String description;
	
	private TCCStatus tccStatus;
	
	private Collection<Long> idsAreas;
	
	public String getTittle() {
		return this.tittle;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public TCCStatus getTccStatus() {
		return this.tccStatus;
	}
	
	public Collection<Long> getIdsAreas() {
		return this.idsAreas;
	}
}
