create table animal (
			id_animal int primary key GENERATED ALWAYS AS IDENTITY ,
			nome varchar(255) not null,
			idade date,
			coleira_id int unique,
			coleira_on BOOLEAN default false,
			limite_tentativas int default 3,
			contador_tentativa int default 0,
			porte int default 2,
			maturidade int,
			email_tutor varchar(300)
);

create table situacao (
			id_situacao int primary key GENERATED always as identity,
			id_animal int not null,
			batimentos int,
			presao_arterial_sistolica int,
			presao_arterial_diastolica int,
			temperatura int,
			posicao_geografica GEOGRAPHY(Point),
			situacao_ja_analisada boolean DEFAULT FALSE,
			FOREIGN KEY (id_animal) REFERENCES animal(id_animal)
	);

create table anomalia (
			id_anomalia int primary key GENERATED ALWAYS AS IDENTITY,
			id_animal int,
			id_situacao int,
			porcetagem_acuracia int,
			descricao_anomalia varchar(255),
			sistema boolean,
			foreign key (id_animal) references animal(id_animal),
			foreign key (id_situacao) references situacao(id_situacao)

);

-- drop table animal cascade;
-- drop table anomalia cascade;
-- drop table situacao cascade;