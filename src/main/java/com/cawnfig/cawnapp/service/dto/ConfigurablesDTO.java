package com.cawnfig.cawnapp.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Configurables entity.
 */
public class ConfigurablesDTO implements Serializable {

    private Long id;

    @NotNull
    private String stage;

    @NotNull
    private String organization;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConfigurablesDTO configurablesDTO = (ConfigurablesDTO) o;
        if(configurablesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), configurablesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConfigurablesDTO{" +
            "id=" + getId() +
            ", stage='" + getStage() + "'" +
            ", organization='" + getOrganization() + "'" +
            "}";
    }
}
