drop table if exists user_role;
drop table if exists users;
drop table if exists user_requests;
drop table if exists managements;
drop table if exists degrees;
drop table if exists db_types;
drop table if exists states;


# Таблица Пользователи базы данных
create table user_requests(
    id bigint not null auto_increment,  # идентификатор
    iin varchar(12) not null,    # иин
    first_name varchar(50),             # фамилия
    middle_name varchar(50),            # имя
    last_name varchar(50),              # отчество
    management_id bigint,               # подразделение
    department varchar(200),            # служба
    degree_id bigint,                   # звание
    job varchar(255),                   # должность
    phone varchar(12) not null,  # сотовый телефон
    db_type_id bigint,                  # тип базы данных
    init_match boolean,                 # доступ к модулю "инициативные совпадения"
    emergency_input boolean,            # доступ к модулю "экстренный ввод"
    interrupted_cases boolean,          # доступ к модулю "прерванные дела"
    reset_password boolean,             # обнулить пароль
    delete_user boolean,                # удалить пользователя
    login varchar(50),                  # логин
    password varchar(50),               # пароль
    state_id bigint,                    # состояние
    create_date datetime,               # дата создания
    change_date datetime,               # дата изменения
    create_user varchar(50),            # кем создан
    change_user varchar(50),            # кем изменен
    primary key (id)
)
    engine = InnoDB
    default character set = utf8;

# create trigger ins_user_requests
#     before insert
#     on user_requests
#     for each row
# begin
#     set new.create_date = now();
# end;
#
# create trigger upd_user_requests
#     before update
#     on user_requests
#     for each row
# begin
#     set new.change_date = now();
# end;

# Таблица управления/отделы полиции
create table managements(
    id bigint not null auto_increment,
    name varchar(255),
    primary key (id)
)
    engine = InnoDB
    default character set = utf8;

# Таблица звания
create table degrees(
    id bigint not null auto_increment,
    name varchar(255),
    primary key (id)
)
    engine = InnoDB
    default character set = utf8;

# Таблица типы базы данных
create table db_types(
    id bigint not null auto_increment,
    name varchar(255),
    primary key (id)
)
    engine = InnoDB
    default character set = utf8;

# Таблица состояния (заяки)
create table states(
    id bigint not null auto_increment,
    name varchar(255),
    primary key (id)
)
    engine = InnoDB
    default character set = utf8;

# Пользователи системы для теста

create table users(
    id bigint not null auto_increment,
    username varchar(255) not null unique,
    password varchar(255),
    active boolean,
    first_name varchar(50),
    middle_name varchar(50),
    last_name varchar(50),
    management_id bigint,
    primary key (id)
)
    engine = InnoDB
    default character set = utf8;

create table user_role(
    user_id bigint,
    roles varchar(255),
    primary key (user_id, roles)
)
    engine = InnoDB
    default character set = utf8;


insert into users(id, username, password, active) values (1, 'admin', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', true);

insert into user_role(user_id, roles) values (1, 'ROLE_USER');
insert into user_role(user_id, roles) values (1, 'ROLE_ADMIN');

insert into degrees(id, name) values(1, 'РЯДОВОЙ');
insert into degrees(id, name) values(2, 'МЛАДШИЙ СЕРЖАНТ');
insert into degrees(id, name) values(3, 'СЕРЖАНТ');
insert into degrees(id, name) values(4, 'СТАРШИЙ СЕРЖАНТ');
insert into degrees(id, name) values(5, 'СТАРШИНА');
insert into degrees(id, name) values(6, 'МЛАДЩИЙ ЛЕЙТЕНАНТ');
insert into degrees(id, name) values(7, 'ЛЕЙТЕНАНТ');
insert into degrees(id, name) values(8, 'СТАРШИЙ ЛЕЙТЕНАНТ');
insert into degrees(id, name) values(9, 'КАПИТАН');
insert into degrees(id, name) values(10, 'МАЙОР');
insert into degrees(id, name) values(11, 'ПОДПОЛКОВНИК');
insert into degrees(id, name) values(12, 'ПОЛКОВНИК');

insert into managements(id, name) values(1, 'ДП КАРАГАНДИНСКОЙ ОБЛАСТИ');
insert into managements(id, name) values(2, 'ОКТЯБРЬСКИЙ ОП');
insert into managements(id, name) values(3, 'ЦЕНТРАЛЬНЫЙ ОП');
insert into managements(id, name) values(4, 'МИХАЙЛОВСКИЙ ОП');
insert into managements(id, name) values(5, 'КИРОВСКИЙ ОП');
insert into managements(id, name) values(6, 'ЮГО-ВОСТОЧНЫЙ ОП');
insert into managements(id, name) values(7, 'ЖЕЛЕЗНОДОРОЖНЫЙ ОП');
insert into managements(id, name) values(8, 'НОВО-МАЙКУДУКСКИЙ ОП');
insert into managements(id, name) values(9, 'УП Г.КАРАГАНДЫ');
insert into managements(id, name) values(10, 'ВОСТОЧНЫЙ ОП');
insert into managements(id, name) values(11, 'СТАРОГОРОДСКОЙ ОП');
insert into managements(id, name) values(12, 'УП Г.ТЕМИРТАУ');
insert into managements(id, name) values(13, 'УП Г.ЖЕЗКАЗГАН');
insert into managements(id, name) values(14, 'ОП Г.САРАНЬ');
insert into managements(id, name) values(15, 'ОП Г.ШАХТИНСК');
insert into managements(id, name) values(16, 'ОП Г.ПРИОЗЕРСК');
insert into managements(id, name) values(17, 'ОП Г.КАРАЖАЛ');
insert into managements(id, name) values(18, 'ОП Г.САТПАЕВ');
insert into managements(id, name) values(19, 'ОП Г.БАЛХАШ');
insert into managements(id, name) values(20, 'ОП АБАЙСКОГО РАЙОНА');
insert into managements(id, name) values(21, 'ТОПАРСКИЙ ОП');
insert into managements(id, name) values(22, 'ОП БУХАР-ЖЫРАУСКОГО РАЙОНА');
insert into managements(id, name) values(23, 'ОП ИМ.МУСТАФИНА');
insert into managements(id, name) values(24, 'ОП ОСАКАРОВСКОГО РАЙОНА');
insert into managements(id, name) values(25, 'ОП НУРИНСКОГО РАЙОНА');
insert into managements(id, name) values(26, 'ОП КАРКАРАЛИНСКОГО РАЙОНА');
insert into managements(id, name) values(27, 'ОП АКТОГАЙСКОГО РАЙОНА');
insert into managements(id, name) values(28, 'ОП ШЕТСКОГО РАЙОНА');
insert into managements(id, name) values(29, 'ОП ЖАНААРКИНСКОГО РАЙОНА');
insert into managements(id, name) values(30, 'ОП УЛЫТАУСКОГО РАЙОНА');

insert into states(id, name) values(1, 'НОВАЯ ЗАЯВКА');
insert into states(id, name) values(2, 'ПОЛУЧЕНИЕ ПАРОЛЯ');
insert into states(id, name) values(3, 'ЗАЯВКА ЗАКРЫТА');
insert into states(id, name) values(4, 'НА РЕГИСТРАЦИИ');
insert into states(id, name) values(5, 'ПОЛЬЗОВАТЕЛЬ УДАЛЕН');

insert into db_types(id, name) values (1, 'ИБД МВД');
insert into db_types(id, name) values (2, 'ИБД ОБЛАСТНОЙ');
insert into db_types(id, name) values (3, 'ИНФОСЕРВИС');
insert into db_types(id, name) values (4, 'АБД СТАТИСТИКА');
insert into db_types(id, name) values (5, 'АБД УОН');
insert into db_types(id, name) values (6, 'БЕРКУТ ГО');