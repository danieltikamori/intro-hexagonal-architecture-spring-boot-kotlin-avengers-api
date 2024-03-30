create table avenger (
                         id bigserial not null,
                         nick varchar(128),
                         person varchar(128),
                         description varchar(255),
                         history text,
                         primary key (id)
);

alter table avenger add constraint UK_5r88eemotwgru6k0ilqb2ledh unique (nick);