
package com.ejemplo.crud.service;

import com.ejemplo.crud.dao.PersonaDao;
import com.ejemplo.crud.dto.PersonaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaService {

    private final PersonaDao personaDao;

    @Autowired
    public PersonaService(PersonaDao personaDao) {
        this.personaDao = personaDao;
    }

    public PersonaDto obtenerPersonaPorDocumento(String documento) {
        return personaDao.consultarPersonaInvidual(documento);
    }

    public PersonaDto consultarPersonaIdProfesion(
            String documento, String profesion) {

        return personaDao.consultarPersonaIdProfesion(
                documento, profesion);
    }

    public List<PersonaDto> obtenerListaPersonas() {
        return personaDao.obtenerListapPersonas();
    }

    public PersonaDto registrarPersona(PersonaDto persona) {

        if (persona == null
                || persona.getDocumento() == null
                || persona.getDocumento().trim().isEmpty()) {
            return null;
        }

        return personaDao.registrarPersona(persona);
    }

    public PersonaDto consultarLogin(PersonaDto persona) {

        if (persona == null
                || persona.getDocumento() == null
                || persona.getPassword() == null) {
            return null;
        }

        return personaDao.consultarLogin(
                persona.getDocumento(),
                persona.getPassword());
    }

    public PersonaDto actualizarPersona(PersonaDto persona) {

        if (persona == null
                || persona.getDocumento() == null
                || persona.getDocumento().trim().isEmpty()) {
            return null;
        }

        return personaDao.actualizarPersona(persona);
    }

    public boolean eliminarPersona(String documento) {

        if (documento == null || documento.trim().isEmpty()) {
            return false;
        }

        PersonaDto persona =
                personaDao.consultarPersonaInvidual(documento);

        if (persona == null) {
            return false;
        }

        return personaDao.eliminarPersona(persona);
    }
}
