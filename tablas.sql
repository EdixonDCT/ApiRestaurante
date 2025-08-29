CREATE DATABASE FacturacionRestaurante;

USE FacturacionRestaurante;

create table oficios (
    codigo int auto_increment,
    tipo varchar(50) not null unique,
    salario decimal(10,2) not null,
    primary key (codigo)
);
create table trabajadores (
    cedula varchar(20) not null,
    nombre varchar(50) not null,
    apellido varchar(50) not null,
    nacimiento date not null,
    foto varchar(255),
    contrasena varchar(100) not null,
	activo boolean,
    id_oficio int not null,
    adminTemporalInicio date,
    adminTemporalFin date,
    primary key (cedula),
    foreign key (id_oficio) references oficios(codigo)
);
create table mesas (
    numero int not null,
    capacidad int not null,
    disponible boolean default true,
    primary key (numero)
);
create table clientes (
	correo varchar(100) not null,
    cedula varchar(20),
    telefono varchar(20),
    primary key (correo)
);
create table comidas (
    id int auto_increment,
    nombre varchar(100) unique not null,
    precio decimal(10,2) not null,
    tipo varchar(50) not null,
    imagen varchar(255),
    disponible boolean default false,
    primary key (id)
);
create table bebidas (
    id int auto_increment,
    nombre varchar(100) not null,
    precio decimal(10,2) not null,
    unidad varchar(100) not null,
    tipo varchar(50) not null, 
    imagen varchar(255),
    disponible boolean default false,
    primary key (id)
);
create 	table cocteles (
    id int auto_increment,
    nombre varchar(100) unique not null,
    precio decimal(10,2) not null,
	imagen varchar(255),
    disponible boolean default false,
    primary key (id)
);
create table ingredientes (
    id int auto_increment,
    nombre varchar(100) unique not null,
    primary key (id)
);
create table ingredientes_comida (
    id int auto_increment,
    id_ingrediente int not null,
    id_comida int not null,
    primary key (id),
    foreign key (id_ingrediente) references ingredientes(id),
    foreign key (id_comida) references comidas(id)
);
create table ingredientes_coctel (
    id int auto_increment,
    id_ingrediente int not null,
    id_coctel int not null,
    primary key (id),
    foreign key (id_ingrediente) references ingredientes(id),
    foreign key (id_coctel) references cocteles(id)
);
create table reservas (
    id int auto_increment,
    cantidad_tentativa int not null,
    precio decimal(10,2) not null,
    fecha date not null default(current_date()),
    fecha_tentativa date not null,
    hora_tentativa time not null,
    primary key (id)
);
create table caja (
    id int auto_increment,
    fecha_apertura date not null default(current_date()),
    hora_apertura time not null default(current_time()),
    monto_apertura decimal(10,2) not null,
    fecha_cierre date,
    hora_cierre time,
    monto_cierre decimal(10,2),
    cedula_trabajador varchar(20) not null,
    primary key (id),
    foreign key (cedula_trabajador) references trabajadores(cedula)
);
create table pedidos (
    id int auto_increment,
    numero_mesa int not null,
    fecha date,
    hora time, 
    valor_total decimal(10,2) default(0),
    id_caja int,
    numero_clientes int,
    id_reserva int,
    correo_cliente varchar(100) not null,
    metodo_pago varchar(50),
    facturado boolean default(false),
    eliminado boolean default(false),
    primary key (id),
    foreign key (numero_mesa) references mesas(numero),
    foreign key (id_caja) references caja(id),
    foreign key (id_reserva) references reservas(id),
    foreign key (correo_cliente) references clientes(correo)
);
create table detalle_pedido (
    id int auto_increment,
    id_pedido int not null,
    id_comida int,
    cantidad_comida int,
    nota_comida varchar(255),
    id_bebida int,
    cantidad_bebida int,
	nota_bebida varchar(255),
    id_coctel int,
    cantidad_coctel int,
	nota_coctel varchar(255),
    primary key (id),
    foreign key (id_pedido) references pedidos(id),
    foreign key (id_comida) references comidas(id),
    foreign key (id_bebida) references bebidas(id),
    foreign key (id_coctel) references cocteles(id)
);