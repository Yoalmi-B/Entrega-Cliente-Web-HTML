
package com.ejemplo.crud.controller;

import com.ejemplo.crud.dto.PersonaDto;
import com.ejemplo.crud.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicio")
public class PersonaController {

    private final PersonaService personaService;

    @Autowired
    public PersonaController(PersonaService personaService) {
        this.personaService = personaService;
    }

    @GetMapping("hola")
    public String saludo() {
        return "Este es un saludo desde el proyecto Prueba CRUD";
    }

    @GetMapping("personas")
    public ResponseEntity<?> getPersona(
            @RequestParam("id") String documento) {

        PersonaDto persona =
                personaService.obtenerPersonaPorDocumento(documento);

        if (persona == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada.");
        }

        return ResponseEntity.ok(persona);
    }

    @GetMapping("profesion")
    public ResponseEntity<?> getPersonaIdProfesion(
            @RequestParam("id") String documento,
            @RequestParam String profesion) {

        PersonaDto persona =
                personaService.consultarPersonaIdProfesion(
                        documento, profesion);

        if (persona == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada.");
        }

        return ResponseEntity.ok(persona);
    }

    @GetMapping("personas/{id}")
    public ResponseEntity<?> getPersonaId(
            @PathVariable("id") String documento) {

        PersonaDto persona =
                personaService.obtenerPersonaPorDocumento(documento);

        if (persona == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada.");
        }

        return ResponseEntity.ok(persona);
    }

    @GetMapping("personas/{id}/{profesion}")
    public ResponseEntity<?> getPersonaIdProfesion2(
            @PathVariable String id,
            @PathVariable String profesion) {

        PersonaDto persona =
                personaService.consultarPersonaIdProfesion(
                        id, profesion);

        if (persona == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada.");
        }

        return ResponseEntity.ok(persona);
    }

    @GetMapping("personas-list")
    public ResponseEntity<List<PersonaDto>> getPersonas() {
        return ResponseEntity.ok(
                personaService.obtenerListaPersonas());
    }

    @PostMapping("guardar")
    public ResponseEntity<?> guardarPersona(
            @RequestBody PersonaDto persona) {

        PersonaDto resultado =
                personaService.registrarPersona(persona);

        if (resultado == null) {
            return ResponseEntity.badRequest()
                    .body("No se pudo registrar. Documento duplicado o inválido.");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resultado);
    }

    @PostMapping("login")
    public ResponseEntity<?> validarUsuario(
            @RequestBody PersonaDto persona) {

        PersonaDto resultado =
                personaService.consultarLogin(persona);

        if (resultado == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas.");
        }

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("actualizar")
    public ResponseEntity<?> actualizarPersona(
            @RequestBody PersonaDto persona) {

        PersonaDto resultado =
                personaService.actualizarPersona(persona);

        if (resultado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Persona no encontrada.");
        }

        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarPersona(
            @PathVariable String id) {

        boolean eliminado =
                personaService.eliminarPersona(id);

        if (eliminado) {
            return ResponseEntity.ok(
                    "Persona eliminada exitosamente.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Persona no encontrada.");
    }
}