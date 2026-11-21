
const URL_BASE = "http://localhost:8080/servicio";

// Obtener los datos del formulario
function obtenerDatosFormulario() {

    return {
        documento: document.getElementById("documento").value.trim(),
        nombre: document.getElementById("nombre").value.trim(),
        telefono: document.getElementById("telefono").value.trim(),
        edad: parseInt(document.getElementById("edad").value, 10),
        profesion: document.getElementById("profesion").value.trim(),
        password: document.getElementById("password").value,
        tipo: parseInt(document.getElementById("tipo").value, 10)
    };
}

// Mostrar una persona en el formulario
function mostrarPersona(persona) {

    document.getElementById("documento").value = persona.documento || "";
    document.getElementById("nombre").value = persona.nombre || "";
    document.getElementById("telefono").value = persona.telefono || "";
    document.getElementById("edad").value = persona.edad ?? "";
    document.getElementById("profesion").value = persona.profesion || "";
    document.getElementById("password").value = persona.password || "";
    document.getElementById("tipo").value = persona.tipo ?? "";
}

// Buscar persona
async function buscarPersona() {

    const documento = document.getElementById("documento").value.trim();

    if (!documento) {
        alert("Ingresa un documento.");
        return;
    }

    try {

        const respuesta = await fetch(
            `${URL_BASE}/personas/${encodeURIComponent(documento)}`
        );

        if (!respuesta.ok) {
            throw new Error(await respuesta.text());
        }

        const persona = await respuesta.json();

        mostrarPersona(persona);

        alert("Persona encontrada correctamente.");

    } catch (error) {

        console.error(error);
        alert("Error al buscar persona: " + error.message);
    }
}

// Consultar persona por profesión
async function consultarProfesion() {

    const documento = document.getElementById("documento").value.trim();
    const profesion = document.getElementById("profesion").value.trim();

    if (!documento || !profesion) {
        alert("Ingresa el documento y la profesión.");
        return;
    }

    try {

        const url = `${URL_BASE}/profesion?id=${encodeURIComponent(documento)}&profesion=${encodeURIComponent(profesion)}`;

        const respuesta = await fetch(url);

        if (!respuesta.ok) {
            throw new Error(await respuesta.text());
        }

        const persona = await respuesta.json();

        mostrarPersona(persona);

        alert("Consulta realizada correctamente.");

    } catch (error) {

        console.error(error);
        alert("Error al consultar profesión: " + error.message);
    }
}

// Consultar lista de personas
async function obtenerListaPersonas() {

    try {

        const respuesta = await fetch(`${URL_BASE}/personas-list`);

        if (!respuesta.ok) {
            throw new Error(await respuesta.text());
        }

        const personas = await respuesta.json();

        console.log("Lista de personas:");

        console.table(personas);

        const contenedor = document.getElementById("listaPersonas");

        contenedor.innerHTML = "";

       if (personas.length === 0) {
            const fila = document.createElement("tr");
            const celda = document.createElement("td");

            celda.colSpan = 6;
            celda.textContent = "No hay personas registradas.";
            celda.className = "text-center";

            fila.appendChild(celda);
            contenedor.appendChild(fila);

            return;
        }

        personas.forEach(persona => {

            const fila = document.createElement("tr");

            [
                persona.documento,
                persona.nombre,
                persona.telefono,
                persona.edad,
                persona.profesion,
                persona.tipo
            ].forEach(valor => {

                const celda = document.createElement("td");

                celda.textContent = valor ?? "";

                fila.appendChild(celda);
            });

            contenedor.appendChild(fila);
        });

    } catch (error) {

        console.error(error);
        alert("Error al obtener personas: " + error.message);
    }
}

// Registrar persona
async function registrarPersona() {

    const persona = obtenerDatosFormulario();

    if (
        !persona.documento ||
        !persona.nombre ||
        !persona.telefono ||
        !persona.profesion ||
        !persona.password ||
        !Number.isInteger(persona.edad) ||
        !Number.isInteger(persona.tipo) ||
        persona.edad < 1
    ) {
        alert("Completa correctamente todos los campos.");
        return;
    }

    try {

        const respuesta = await fetch(`${URL_BASE}/guardar`, {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(persona)
        });

        if (!respuesta.ok) {
            throw new Error(await respuesta.text());
        }

        const resultado = await respuesta.json();

        console.log("Persona registrada:", resultado);

        alert("Persona registrada exitosamente.");

        limpiarFormulario();

        obtenerListaPersonas();

    } catch (error) {

        console.error(error);
        alert("Error al registrar: " + error.message);
    }
}

// Actualizar persona
async function actualizarPersona() {

    const persona = obtenerDatosFormulario();

    if (
        !persona.documento ||
        !persona.nombre ||
        !persona.telefono ||
        !persona.profesion ||
        !persona.password ||
        !Number.isInteger(persona.edad) ||
        !Number.isInteger(persona.tipo) ||
        persona.edad < 1
    ) {
        alert("Completa correctamente todos los campos.");
        return;
    }

    try {

        const respuesta = await fetch(`${URL_BASE}/actualizar`, {

            method: "PUT",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(persona)
        });

        if (!respuesta.ok) {
            throw new Error(await respuesta.text());
        }

        const resultado = await respuesta.json();

        console.log("Persona actualizada:", resultado);

        alert("Persona actualizada exitosamente.");

        obtenerListaPersonas();

    } catch (error) {

        console.error(error);
        alert("Error al actualizar: " + error.message);
    }
}

// Eliminar persona
async function eliminarPersona() {

    const documento = document.getElementById("documento").value.trim();

    if (!documento) {
        alert("Ingresa el documento de la persona.");
        return;
    }

    if (!confirm("¿Estás seguro de eliminar esta persona?")) {
        return;
    }

    try {

        const respuesta = await fetch(
            `${URL_BASE}/eliminar/${encodeURIComponent(documento)}`,
            {
                method: "DELETE"
            }
        );

        if (!respuesta.ok) {
            throw new Error(await respuesta.text());
        }

        const mensaje = await respuesta.text();

        alert(mensaje);

        limpiarFormulario();

        obtenerListaPersonas();

    } catch (error) {

        console.error(error);
        alert("Error al eliminar: " + error.message);
    }
}

// Limpiar formulario
function limpiarFormulario() {

    document.getElementById("documento").value = "";
    document.getElementById("nombre").value = "";
    document.getElementById("telefono").value = "";
    document.getElementById("edad").value = "";
    document.getElementById("profesion").value = "";
    document.getElementById("password").value = "";
    document.getElementById("tipo").value = "";
}
