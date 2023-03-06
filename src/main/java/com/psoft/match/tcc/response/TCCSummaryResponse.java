package com.psoft.match.tcc.response;

import com.psoft.match.tcc.model.tcc.TCC;

import java.util.Collection;

public class TCCSummaryResponse {

    private String term;

    private Collection<TCC> onGoingTCCs;

    private Collection<TCC> finishedTCCs;

    public TCCSummaryResponse(String term, Collection<TCC> onGoingTCCs, Collection<TCC> finishedTCCs) {
        this.term = term;
        this.onGoingTCCs = onGoingTCCs;
        this.finishedTCCs = finishedTCCs;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Collection<TCC> getOnGoingTCCs() {
        return onGoingTCCs;
    }

    public void setOnGoingTCCs(Collection<TCC> onGoingTCCs) {
        this.onGoingTCCs = onGoingTCCs;
    }

    public Collection<TCC> getFinishedTCCs() {
        return finishedTCCs;
    }

    public void setFinishedTCCs(Collection<TCC> finishedTCCs) {
        this.finishedTCCs = finishedTCCs;
    }
}
