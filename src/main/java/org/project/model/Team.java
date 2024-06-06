package org.project.model;

import lombok.*;
import org.project.util.enums.Status;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "team")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@org.springframework.data.relational.core.mapping.Table(value = "team")
public class Team implements Formatter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "name")
	private String name;
	@Column(nullable = false, name = "user_id")
	private long telegramUserId;
	@OneToMany(fetch = LAZY)
	private Set<Student> students = new HashSet<>();
	@Column(name = "status", nullable = false)
	@Enumerated(value = STRING)
	private Status status;

	@Override
	public Object[] getFormattedData() {
		return new Object[0];
	}

	@Override
	public List<Object> getFormattedDataAsList() {
		return null;
	}

}
