create table if not exists telegram_user (
	id BIGSERIAL PRIMARY KEY,
	telegram_id BIGINT UNIQUE NOT NULL
);

create table if not exists phase (
	id BIGSERIAL PRIMARY KEY,
	handler_name varchar(250) UNIQUE NOT NULL
);

create table if not exists user_phase (
	user_id BIGINT UNIQUE NOT NULL,
	phase_id BIGINT NOT NULL,
	FOREIGN KEY (phase_id) REFERENCES phase (id)
);

create table if not exists user_message (
    id BIGSERIAL PRIMARY KEY,
	message_id BIGINT UNIQUE NOT NULL,
	user_id BIGINT NOT NULL,
	formatting_type varchar(50) NOT NULL,
	created_at TIMESTAMP NOT NULL
);

create table if not exists team (
	id BIGSERIAL PRIMARY KEY,
	name varchar(50) UNIQUE NOT NULL,
	user_id BIGINT UNIQUE NOT NULL
);

create table if not exists student (
	id BIGSERIAL PRIMARY KEY,
	name varchar(50) NOT NULL,
	surname varchar(50) NOT NULL,
	team_id BIGINT NOT NULL,
	FOREIGN KEY (team_id) REFERENCES team (id)
);

CREATE TABLE IF NOT EXISTS repo (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    owner VARCHAR(50) UNIQUE NOT NULL,
    student_id BIGINT NOT NULL,
    stars INT,
    forks INT,
    open_issues INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    url TEXT,
    FOREIGN KEY (student_id) REFERENCES student (id)
);

