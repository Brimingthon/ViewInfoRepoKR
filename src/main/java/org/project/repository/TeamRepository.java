package org.project.repository;


import org.project.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

	@Query ("select t from Team t where t.telegramUserId = ?1 order by t.name")
	Page<Team> findByTelegramUserId(long telegramUserId, Pageable pageable);

}
