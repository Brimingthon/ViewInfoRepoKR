package org.project.handler;

import org.project.model.Repo;
import org.project.repository.RepoRepository;
import org.project.service.RepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SceduleTask {

	@Autowired
	private RepoService repoService;
	private RepoRepository repoRepository;

	@Scheduled (fixedRate = 3600000) // кожну годину
	public void updateRepoInfo() {
		List<Repo> repos = repoRepository.findAll();
		for (Repo repo : repos) {
			repo = repoService.fetchRepoInfo(repo.getOwner(), repo.getName());
			repoService.saveRepo(repo);
		}
	}
}
