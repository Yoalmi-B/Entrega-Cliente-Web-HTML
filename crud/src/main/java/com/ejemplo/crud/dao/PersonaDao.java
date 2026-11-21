
package com.ejemplo.crud.dao;

import com.ejemplo.crud.dto.PersonaDto;
import com.ejemplo.crud.util.PersonasUtilidades;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonaDao {

    public PersonaDao() {
        PersonasUtilidades.iniciarLista();
    }

    public PersonaDto consultarPersonaInvidual(String documento) {

        for (PersonaDto p : PersonasUtilidades.listaPersonas) {

            if (p.getDocumento().equals(documento)) {

                return new PersonaDto(
                        p.getDocumento(),
                        p.getNombre(),
                        p.getTelefono(),
                        p.getEdad(),
                        p.getProfesion(),
                        p.getPassword(),
                        p.getTipo()
                );
            }
        }


        return null;
    }

    public PersonaDto consultarPersonaIdProfesion(
            String documento, String profesion) {

        for (PersonaDto p : PersonasUtilidades.listaPersonas) {

            if (p.getDocumento().equals(documento)
                    && p.getProfesion().equalsIgnoreCase(profesion)) {

                return new PersonaDto(
                        p.getDocumento(),
                        p.getNombre(),
                        p.getTelefono(),
                        p.getEdad(),
                        p.getProfesion(),
                        p.getPassword(),
                        p.getTipo()
                );
            }
        }

        return null;
    }

    public List<PersonaDto> obtenerListapPersonas() {
        return PersonasUtilidades.listaPersonas;
    }

    public PersonaDto registrarPersona(PersonaDto persona) {

        for (PersonaDto p : PersonasUtilidades.listaPersonas) {

            if (p.getDocumento().equals(persona.getDocumento())) {
                return null;
            }
        }

        PersonasUtilidades.listaPersonas.add(persona);

        return persona;
    }

    public PersonaDto consultarLogin(String documento, String pass) {

        for (PersonaDto p : PersonasUtilidades.listaPersonas) {

            if (p.getDocumento().equals(documento)
                    && p.getPassword().equals(pass)) {

                return new PersonaDto(
                        p.getDocumento(),
                        p.getNombre(),
                        p.getTelefono(),
                        p.getEdad(),
                        p.getProfesion(),
                        p.getPassword(),
                        p.getTipo()
                );
            }
        }

        return null;
    }

    public PersonaDto actualizarPersona(PersonaDto persona) {

        for (PersonaDto p : PersonasUtilidades.listaPersonas) {

            if (p.getDocumento().equals(persona.getDocumento())) {

                p.setNombre(persona.getNombre());
                p.setTelefono(persona.getTelefono());
                p.setEdad(persona.getEdad());
                p.setProfesion(persona.getProfesion());
                p.setPassword(persona.getPassword());
                p.setTipo(persona.getTipo());

                return p;
            }
        }

        return null;
    }

    public boolean eliminarPersona(PersonaDto persona) {

        return PersonasUtilidades.listaPersonas.removeIf(
                p -> p.getDocumento().equals(persona.getDocumento())
        );
    }
}
