package org.project.service;

import org.project.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface StudentService {
	Page<Student> getAllStudentsByTeamId(long teamId, PageRequest pageRequest);

	Student saveStudent(Student student);

}
