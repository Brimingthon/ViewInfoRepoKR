package org.project.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;
import static javax.persistence.CascadeType.MERGE;


@Entity
@Table(name = "repo")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.springframework.data.relational.core.mapping.Table(value = "repo")
public class Repo implements Formatter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "name")
	private String name;
	@Column(name = "owner")
	private String owner;
	@ManyToOne(cascade = MERGE)
	@JoinColumn(name = "student_id")
	private Student studentId;
	@Column(name = "stars")
	private int stars;
	@Column(name = "forks")
	private int forks;
	@Column(name = "open_issues")
	private int openIssues;
	@Column(name = "created_at")
	private LocalTime createdAt;
	@Column(name = "updated_at")
	private LocalTime updatedAt;
	@Column(name = "url")
	private String url;

	@Override
	public Object[] getFormattedData() {
		return new Object[0];
	}

	@Override
	public List<Object> getFormattedDataAsList() {
		return null;
	}

}
