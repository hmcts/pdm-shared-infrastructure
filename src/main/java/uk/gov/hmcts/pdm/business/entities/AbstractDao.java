package uk.gov.hmcts.pdm.business.entities;

public abstract class AbstractDao {

    public abstract Integer getVersion();

    public abstract void setVersion(Integer version);
}
