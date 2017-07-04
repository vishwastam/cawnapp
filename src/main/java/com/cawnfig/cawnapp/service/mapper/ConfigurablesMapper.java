package com.cawnfig.cawnapp.service.mapper;

import com.cawnfig.cawnapp.domain.*;
import com.cawnfig.cawnapp.service.dto.ConfigurablesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Configurables and its DTO ConfigurablesDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConfigurablesMapper extends EntityMapper <ConfigurablesDTO, Configurables> {
    
    
    default Configurables fromId(Long id) {
        if (id == null) {
            return null;
        }
        Configurables configurables = new Configurables();
        configurables.setId(id);
        return configurables;
    }
}
