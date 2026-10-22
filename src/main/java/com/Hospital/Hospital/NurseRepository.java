package com.Hospital.Hospital;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called nurseRepository
// CRUD refers Create, Read, Update, Delete

public interface NurseRepository extends CrudRepository<Nurse, Integer> {

	Optional<Nurse> findFirstByFullNameIgnoreCaseOrUserNameIgnoreCase(String fullName, String userName);

}