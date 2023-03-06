package com.psoft.match.tcc.model.tcc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.psoft.match.tcc.util.exception.tcc.InvalidTCCStatusException;

public enum TCCStatus {
    PENDING_APPROVAL("PENDING_APPROVAL"),
    APPROVED("APPROVED"),
    ON_GOING("ON_GOING"),
    FINISHED("FINISHED");
	
	private String description;
	
	TCCStatus(String tipo){
        this.description = tipo;
    }

    @JsonValue
    public String getDescription(){
        return this.description;
    }
    
    @JsonCreator
    public static TCCStatus fromText(String description) {
        for (TCCStatus tccStatus : TCCStatus.values()) {
            if (tccStatus.getDescription().equals(description))
                return tccStatus;
        }
        throw new InvalidTCCStatusException(description);
    }
}
