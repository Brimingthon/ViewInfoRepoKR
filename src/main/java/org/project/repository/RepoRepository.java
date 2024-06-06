package org.project.repository;

import org.project.model.Repo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoRepository extends JpaRepository<Repo, Long> {

	@Query ("select r from Repo r where r.studentId.id = ?1 order by r.updatedAt")
	Page<Repo> findByStudentId(long id, Pageable pageable);

	@Query ("select r from Repo r where r.name = ?1 and r.owner = ?2")
	Repo findByNameAndOwner(String name, String owner);


}
