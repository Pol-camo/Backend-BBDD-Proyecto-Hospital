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
 
	// Simulates looking up a nurse by full name or username
	@GetMapping("/name/{name}")
	public ResponseEntity<Nurse> findByName(@PathVariable("name") String name) {
 
		// Get (or simulate) the list of registered nurses
		List<Nurse> nurses = getMockNurses();
 
		// Find the nurse whose full name or username matches 'name'
		for (Nurse nurse : nurses) {
			if (nurse.getFullName().equalsIgnoreCase(name) ||
				nurse.getUserName().equalsIgnoreCase(name)) {
				return ResponseEntity.ok(nurse);
			}
		}
 
		return ResponseEntity.notFound().build();
	}
 
	// Static test data (simulating temporary storage / JSON)
	private List<Nurse> getMockNurses() {
		List<Nurse> list = new ArrayList<>();
 
		Nurse n1 = new Nurse();
		n1.setId(1);
		n1.setFullName("Laura Gomez");
		n1.setUserName("laura.g");
		n1.setPassword("1234");
 
		Nurse n2 = new Nurse();
		n2.setId(2);
		n2.setFullName("Carlos Perez");
		n2.setUserName("carlos.p");
		n2.setPassword("5678");
 
		list.add(n1);
		list.add(n2);
 
		return list;
	}
 
	@PostMapping("/login")
	public ResponseEntity<Boolean> login(@RequestBody LoginRequest request) {
 
		List<Nurse> nurses = getMockNurses();
 
		for (Nurse nurse : nurses) {
 
			if (nurse.getUserName().equals(request.getUsername())
					&& nurse.getPassword().equals(request.getPassword())) {
 
				return ResponseEntity.ok(true);
			}
		}
 
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
	}
}
