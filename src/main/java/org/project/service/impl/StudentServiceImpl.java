package org.project.service.impl;

import lombok.AllArgsConstructor;
import org.project.model.Student;
import org.project.repository.StudentRepository;
import org.project.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
	private final StudentRepository studentRepository;
	@Override
	public Page<Student> getAllStudentsByTeamId(long teamId, PageRequest pageRequest) {
		return studentRepository.findStudentsByTeamId(teamId, pageRequest);
	}

	@Override
	public Student saveStudent(Student student) {
		return studentRepository.save(student);
	}

}
