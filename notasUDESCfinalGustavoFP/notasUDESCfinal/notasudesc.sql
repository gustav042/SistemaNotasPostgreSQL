create sequence id_avaliacao;
create sequence id_disciplina;
create sequence id_semestre;
create sequence id_aluno;

create table aluno(
    id int,
    nome varchar(50),
    cpf varchar(50),
    senha varchar(50),
    curso varchar(50),
    primary key (id)
);

create table semestre(
    id int,
    num_semestre varchar(50),
    id_aluno int,
    primary key(id),
    foreign key (id_aluno) references aluno
);

create table disciplina(
    id int,
    nome varchar(40),
    primary key(id),
    id_semestre int,
    foreign key(id_semestre) references semestre
);


create table avaliacao(
    id int,
    nota Double precision,
    peso Double precision,
    nome varchar(50),
    dia varchar(15),
    id_disciplina int,
    primary key(id),
    foreign key(id_disciplina) references disciplina
);
