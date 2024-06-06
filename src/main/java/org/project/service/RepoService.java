package org.project.service;

import org.project.model.Repo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RepoService {
	Repo fetchRepoInfo(String owner, String name);

	Page<Repo> getAllReposByStudentId(long studentId, PageRequest pageRequest);

	void saveRepo(Repo repo);

	String getGeneralInfo(long repoId);
	String getInfoAboutCommit(long repoId);

}
