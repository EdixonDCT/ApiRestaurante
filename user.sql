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
    id_oficio int not null,
    primary key (cedula),
    foreign key (id_oficio) references oficios(codigo)
);

create table mesas (
    numero int not null,
    capacidad int not null,
    disponible boolean default false,
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
    nota text,
    correo_cliente varchar(100) not null,
    metodo_pago varchar(50),
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
    id_bebida int,
    cantidad_bebida int,
    id_coctel int,
    cantidad_coctel int,
    primary key (id),
    foreign key (id_pedido) references pedidos(id),
    foreign key (id_comida) references comidas(id),
    foreign key (id_bebida) references bebidas(id),
    foreign key (id_coctel) references cocteles(id)
);
-- Insert para oficios
insert into oficios (tipo, salario) values 
('Administrador', 99999.99),
('Cajero', 65000.50),
('Mesero', 40000.00);
-- Insert para trabajadores
insert into trabajadores (cedula, nombre, apellido, nacimiento, foto, contrasena, id_oficio) values 
('1001', 'Ana', 'Rojas', '1990-01-01', '3e90db74-9cf7-4b49-926e-f6cda1f76438_foto1.png', '1234', 3),
('1002', 'Luis', 'Gomez', '1988-05-12', '16f4d046-bf96-48d5-b1f4-59bc7aa2d02d_foto2.png', '1234', 3),
('1003', 'Sara', 'Lopez', '1995-03-22', 'c0f973a7-326a-4800-9ad1-63c98a4ebead_foto3.png', '1234', 2),
('1004', 'Pedro', 'Perez', '2000-08-10', '73553a2e-2533-4488-bf19-08a583187e0a_foto4.png', '1234', 2),
('1005', 'Edixon', 'Castillo', '1992-11-15', '58cbed78-b6c9-4d6f-99cb-9183fc115037_foto5.png', '1904', 1);

-- Insert para mesas
insert into mesas (numero, capacidad, disponible) values 
(1, 4, true),
(2, 2, true),
(3, 6, false),
(4, 4, true),
(5, 8, false);

-- Insert para clientes
insert into clientes (correo, cedula, telefono) values 
('ana@correo.com', '2001', '3100000001'),
('luis@correo.com', '2002', '3100000002'),
('sara@correo.com', '2003', '3100000003'),
('pedro@correo.com', '2004', '3100000004'),
('elena@correo.com', '2005', '3100000005');

-- Insert para comidas
insert into comidas (nombre, precio, tipo, imagen, disponible) values 
('Pizza', 25000, 'Italiana', 'pizza.jpg', true),
('Hamburguesa', 18000, 'Americana', 'hamburguesa.jpg', true),
('Taco', 12000, 'Mexicana', 'taco.jpg', true),
('Sushi', 30000, 'Japonesa', 'sushi.jpg', false),
('Ensalada', 15000, 'Vegana', 'ensalada.jpg', true);

-- Insert para bebidas
insert into bebidas (nombre, precio, unidad, tipo, imagen, disponible) values 
('Coca-Cola', 4000, 'Botella', 'Refresco', 'coca.jpg', true),
('Agua', 3000, 'Botella', 'Natural', 'agua.jpg', true),
('Jugo de Mango', 5000, 'Vaso', 'Fruta', 'mango.jpg', true),
('Café', 3500, 'Taza', 'Caliente', 'cafe.jpg', false),
('Té', 3000, 'Taza', 'Caliente', 'te.jpg', true);

-- Insert para cocteles
insert into cocteles (nombre, precio, imagen, disponible) values 
('Mojito', 12000, 'mojito.jpg', true),
('Margarita', 13000, 'margarita.jpg', true),
('Piña Colada', 14000, 'colada.jpg', false),
('Caipiriña', 11000, 'caipirina.jpg', true),
('Bloody Mary', 15000, 'bloody.jpg', true);

-- Insert para ingredientes
insert into ingredientes (nombre) values 
('Harina'),
('Queso'),
('Pollo'),
('Tomate'),
('Lechuga');

-- Insert para ingredientes_comida
insert into ingredientes_comida (id_ingrediente, id_comida) values 
(1, 1),
(2, 1),
(3, 2),
(4, 3),
(5, 5);

-- Insert para ingredientes_coctel
insert into ingredientes_coctel (id_ingrediente, id_coctel) values 
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

-- Insert para reservas
insert into reservas (cantidad_tentativa,precio, fecha, fecha_tentativa, hora_tentativa) values 
(1,50000, '2025-07-10', '2025-07-11', '18:00:00'),
(2,60000, '2025-07-10', '2025-07-12', '19:00:00'),
(3,55000, '2025-07-10', '2025-07-13', '20:00:00'),
(4,70000, '2025-07-10', '2025-07-14', '21:00:00'),
(5,45000, '2025-07-10', '2025-07-15', '17:00:00');

-- Insert para caja
insert into caja (fecha_apertura, hora_apertura, monto_apertura, fecha_cierre, hora_cierre, monto_cierre, cedula_trabajador) values 
('2025-07-10', '08:00:00', 100000, '2025-07-10', '22:00:00', 500000, '1001'),
('2025-07-11', '08:00:00', 120000, '2025-07-11', '22:00:00', 450000, '1002'),
('2025-07-12', '08:00:00', 110000, '2025-07-12', '22:00:00', 400000, '1003'),
('2025-07-13', '08:00:00', 105000, '2025-07-13', '22:00:00', 470000, '1004'),
('2025-07-14', '08:00:00', 90000,  '2025-07-14', '22:00:00', 430000, '1005');

-- Insert para pedidos
insert into pedidos (numero_mesa, fecha, hora, valor_total, id_caja, numero_clientes, id_reserva, nota, correo_cliente, metodo_pago) values 
(1, '2025-07-10', '18:00:00', 80000, 1, 2, 1, 'Sin cebolla', 'ana@correo.com', 'Efectivo'),
(2, '2025-07-11', '19:00:00', 75000, 2, 3, 2, 'Con extra queso', 'luis@correo.com', 'Tarjeta'),
(3, '2025-07-12', '20:00:00', 90000, 3, 4, 3, '', 'sara@correo.com', 'Nequi'),
(4, '2025-07-13', '21:00:00', 85000, 4, 2, 4, 'Mesa exterior', 'pedro@correo.com', 'Efectivo'),
(5, '2025-07-14', '22:00:00', 92000, 5, 5, 5, '', 'elena@correo.com', 'Tarjeta');

-- Insert para detalle_pedido
insert into detalle_pedido (id_pedido, id_comida, cantidad_comida, id_bebida, cantidad_bebida, id_coctel, cantidad_coctel) values 
(1, 1, 1, 1, 1, 1, 1),
(2, 2, 2, 2, 1, 2, 1),
(3, 3, 1, 3, 2, 3, 2),
(4, 4, 1, 4, 1, 4, 1),
(5, 5, 2, 5, 2, 5, 1);

drop table if exists detalle_pedido;
drop table if exists pedidos;
drop table if exists caja;
drop table if exists reservas;
drop table if exists ingredientes_coctel;
drop table if exists ingredientes_comida;
drop table if exists ingredientes;
drop table if exists cocteles;
drop table if exists bebidas;
drop table if exists comidas;
drop table if exists clientes;
drop table if exists mesas;
drop table if exists trabajadores;
drop table if exists oficios;