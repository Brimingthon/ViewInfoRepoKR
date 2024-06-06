package org.project.service.impl;

import lombok.AllArgsConstructor;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.project.model.Repo;
import org.project.repository.RepoRepository;
import org.project.service.RepoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class RepoServiceImpl implements RepoService {
	private final RepoRepository repoRepository;
	@Override
	public Repo fetchRepoInfo(String owner, String name) {
		Repo repo = null;
		try {
			repo = repoRepository.findByNameAndOwner(name, owner);
			GitHub github = GitHub.connectAnonymously();

			GHRepository repository = github.getRepository(owner + "/"+ name);

			repo.setStars(repository.getStargazersCount());
			repo.setForks(repository.getForks());
			repo.setOpenIssues(repository.getOpenIssueCount());
			return null;
		}catch (IOException e){
			e.printStackTrace();
		}

		return repo;
	}

	@Override
	public Page<Repo> getAllReposByStudentId(long studentId, PageRequest pageRequest) {
		return repoRepository.findByStudentId(studentId, pageRequest);
	}

	@Override
	public void saveRepo(Repo repo) {
		repoRepository.save(repo);
	}

	@Override
	public String getGeneralInfo(long repoId) {
		Repo repo = repoRepository.getById(repoId);
		String genInfo = null;
		try {
			GitHub github = GitHub.connectAnonymously();

			GHRepository repository = github.getRepository(repo.getOwner() + "/" + repo.getName());

			genInfo = String.format(
					"<b>Репозиторій:</b> <i>%s</i>\n" +
							"<b>Опис:</b> <i>%s</i>\n" +
							"<b>Гілка за замовчуванням:</b> <i>%s</i>\n" +
							"<b>Відкриті питання:</b> <i>%d</i>",
					repository.getFullName(),
					repository.getDescription(),
					repository.getDefaultBranch(),
					repository.getOpenIssueCount()
			);
			return genInfo;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return genInfo;
	}


	@Override
	public String getInfoAboutCommit(long repoId) {
		Repo repo = repoRepository.getById(repoId);
		String info = null;
		try {
			GitHub github = GitHub.connectAnonymously();

			GHRepository repository = github.getRepository(repo.getOwner() + "/" + repo.getName());

			GHCommit latestCommit = repository.listCommits().toList().get(0);
			String sha = latestCommit.getSHA1();

			GHCommit commitDetails = repository.getCommit(sha);

			StringBuilder changes = new StringBuilder();
			List<GHCommit.File> files = commitDetails.getFiles();
			if (files != null && !files.isEmpty()) {
				for (GHCommit.File file : files) {
					changes.append(String.format("<b>Файл:</b> <i>%s</i>\n<b>Зміни:</b>\n<i>%s</i>\n", file.getFileName(), file.getPatch()));
				}
			} else {
				changes.append("<i>Змін у файлах не виявлено.</i>\n");
			}

			info = String.format(
					"<b>Репозиторій:</b> <i>%s</i>\n" +
							"<b>SHA коміту:</b> <i>%s</i>\n" +
							"<b>Повідомлення коміту:</b> <i>%s</i>\n" +
							"<b>Зміни:</b>\n%s",
					repository.getFullName(),
					commitDetails.getSHA1(),
					commitDetails.getCommitShortInfo().getMessage(),
					changes.toString()
			);

			return info;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}

}
