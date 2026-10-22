package com.Hospital.Hospital;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/nurse")
public class NurseController {
	
	
	@GetMapping("/index")
	public List<Nurse> getAll() {
	    return getMockNurses();
	}
	
    // Método que simula la búsqueda de un enfermero por su nombre completo o de usuario
    @GetMapping("/name/{name}")
    public ResponseEntity<Nurse> findByName(@PathVariable("name") String name) {
        
        // Obtenemos o simulamos la lista de enfermeros registrados
        List<Nurse> nurses = getMockNurses();

        // Buscamos el enfermero cuyo nombre completo o nombre de usuario coincida con 'name'
        for (Nurse nurse : nurses) {
            if (nurse.getNombreCompleto().equalsIgnoreCase(name) || 
                nurse.getNombreUsuario().equalsIgnoreCase(name)) {
                return ResponseEntity.ok(nurse);
            }
        }

        return ResponseEntity.notFound().build();
    }
    
    private List<Nurse> getMockNurses() {
        List<Nurse> list = new ArrayList<>();

        Nurse n1 = new Nurse();
        n1.setId(1);
        n1.setNombreCompleto("Laura Gomez");
        n1.setNombreUsuario("laura.g");
        n1.setContrasena("1234");

        Nurse n2 = new Nurse();
        n2.setId(2);
        n2.setNombreCompleto("Carlos Perez");
        n2.setNombreUsuario("carlos.p");
        n2.setContrasena("5678");

        list.add(n1);
        list.add(n2);

        return list;
    }
    
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody LoginRequest request) {

        List<Nurse> nurses = getMockNurses();

        for (Nurse nurse : nurses) {

            if (nurse.getNombreUsuario().equals(request.getNombreUsuario())
                    && nurse.getContrasena().equals(request.getContrasena())) {

                return ResponseEntity.ok(true);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }

   
    // Datos estáticos de prueba (simulando almacenamiento temporal/JSON)

}
