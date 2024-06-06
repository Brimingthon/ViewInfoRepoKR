package org.project.repository;


import org.project.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	@Query ("select s from Student s where s.team.id = ?1 order by s.surname")
	Page<Student> findStudentsByTeamId(long id, Pageable pageable);

}
