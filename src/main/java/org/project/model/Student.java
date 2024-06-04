package org.project.model;

import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.FetchType.LAZY;


@Entity
@Table(name = "student")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.springframework.data.relational.core.mapping.Table(value = "student")
public class Student implements Formatter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "name")
	private String name;
	@Column(name = "surname")
	private String surname;
	@ManyToOne(cascade = MERGE)
	@JoinColumn(name = "team_id")
	private Team team;
	@OneToMany(fetch = LAZY)
	private Set<Repo> repos = new HashSet<>();

	@Override
	public Object[] getFormattedData() {
		return new Object[0];
	}

	@Override
	public List<Object> getFormattedDataAsList() {
		return null;
	}

}
