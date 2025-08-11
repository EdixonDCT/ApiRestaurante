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
select * from pedidos;
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
-- Insert para oficios
insert into oficios (tipo, salario) values 
('Administrador', 99999.99),
('Cajero', 65000.50),
('Mesero', 40000.00);
-- Insert para trabajadores
insert into trabajadores (cedula, nombre, apellido, nacimiento, foto, contrasena,activo, id_oficio) values 
('1001', 'Ana', 'Rojas', '1990-01-01', 'c4725ed6-a2a8-4112-9572-639515e759a0_foto1.png', '1234',1, 3),
('1002', 'Luis', 'Gomez', '1988-05-12', '3a2ab7b3-0e76-4b0b-8036-815ad0fc87b8_foto2.png', '1234',1, 3),
('1003', 'Sara', 'Lopez', '1995-03-22', '4183b335-f95b-4724-ab04-bf488f3e3448_foto3.png', '1234',1, 2),
('1004', 'Pedro', 'Perez', '2000-08-10', 'f74c6d39-39a0-4683-98ae-78c5c058f144_foto4.png', '1234',1, 2),
('1005', 'Edixon', 'Castillo', '1992-11-15', 'b00a88c9-af7a-4d1a-8096-1d852a475dde_foto5.png', '1904',1, 1);

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
('Pizza Mexicana', 25000, 'pizza', 'b7ddc57d-e117-4cd1-929e-ea635ac93ccc_pizza.png', true),
('Hamburguesa', 18000, 'hamburguesa', 'f1c771d8-3c42-45ba-9076-8d08fdeea12e_burger.png', true),
('Perro Royal', 12000, 'perro', '779c1a23-e01e-475a-92eb-8c4eaf399c4a_perrro.png', true),
('Salchipapa', 30000, 'papas', 'd60aeee3-4fec-43c2-9dc1-87fe3cde5668_papas.png', false),
('Ensalada', 15000, 'ensalada', 'f7a25c73-deb3-439e-abd7-3eb377ecc6b5_salad.png', true);

-- Insert para bebidas
insert into bebidas (nombre, precio, unidad, tipo, imagen, disponible) values 
('Coca-Cola', 4000, 'Botella', 'Refresco', '7ac2e2eb-7693-424c-99c0-c4b34e4f9087_coca.png', true),
('Agua', 3000, 'Botella', 'Natural', '130fe7e2-8dda-4877-a920-4b3b44f0fe64_agua.png', true),
('Jugo de Mango', 5000, 'Vaso', 'Fruta', '4e1644d5-af8e-4c9e-84f2-ba4957d030ea_mango.png', true),
('Café', 3500, 'Taza', 'Caliente', '1b83f6ea-ed92-421b-b236-874e3f4a38aa_cafe.png', false),
('Té', 3000, 'Taza', 'Caliente', 'a78e40a3-7a18-4449-99fa-8c9523a85c0f_te.png', true);

-- Insert para cocteles
insert into cocteles (nombre, precio, imagen, disponible) values 
('Mojito', 12000, 'cab4013b-9e63-4025-bf85-fe98e7a89b7a_Mojito.png', true),
('Margarita', 13000, '105b4caf-38df-4f5f-a304-8ef59a19d9b9_marga.png', true),
('Piña Colada', 14000, '994255fd-622f-4044-9b02-ba8869ad4337_colada.png', false),
('Caipiriña', 11000, '02e587a2-b0de-47cb-812b-c251dff92eea_cai.png', true),
('Bloody Mary', 15000, '01cb075a-8563-4df7-9d5c-355cab9698dd_blodymary.png', true);

-- Insert para ingredientes
insert into ingredientes (nombre) values 
('Harina'),
('Queso'),
('Pollo'),
('Tomate'),
('Lechuga'),
('Salsa de Tomate'),    -- 6
('Carne Molida'),       -- 7
('Jalapeños'),          -- 8
('Pan de Hamburguesa'), -- 9
('Carne de Res'),       -- 10
('Queso Cheddar'),      -- 11
('Pan para Perro'),     -- 12
('Salchicha'),          -- 13
('Queso Rallado'),      -- 14
('Papas Fritas'),       -- 15
('Salsa Rosada'),       -- 16
('Sal'),                -- 17
('Pepino'),             -- 18
('Zanahoria'),          -- 19
('Vinagreta'),          -- 20
('Ron'),                -- 21
('Hierbabuena'),        -- 22
('Azúcar'),             -- 23
('Limón'),              -- 24
('Soda'),               -- 25
('Tequila'),            -- 26
('Triple Sec'),         -- 27
('Hielo'),              -- 28
('Crema de Coco'),      -- 29
('Jugo de Piña'),       -- 30
('Cachaça'),            -- 31
('Vodka'),              -- 32
('Jugo de Tomate'),     -- 33
('Salsa Worcestershire'),-- 34
('Pimienta');           -- 35

-- Insert para ingredientes_comida
insert into ingredientes_comida (id_ingrediente, id_comida) values
-- Pizza Mexicana (id_comida = 1)
(1, 1),  -- Harina
(2, 1),  -- Queso
(6, 1),  -- Salsa de Tomate
(7, 1),  -- Carne Molida
(8, 1),  -- Jalapeños

-- Hamburguesa (id_comida = 2)
(9, 2),   -- Pan de Hamburguesa
(10, 2),  -- Carne de Res
(11, 2),  -- Queso Cheddar
(5, 2),   -- Lechuga
(4, 2),   -- Tomate

-- Perro Royal (id_comida = 3)
(12, 3),  -- Pan para Perro
(13, 3),  -- Salchicha
(14, 3),  -- Queso Rallado
(6, 3),   -- Salsa de Tomate
(15, 3),  -- Papas Fritas

-- Salchipapa (id_comida = 4)
(15, 4),  -- Papas Fritas
(13, 4),  -- Salchicha
(14, 4),  -- Queso Rallado
(16, 4),  -- Salsa Rosada
(17, 4),  -- Sal

-- Ensalada (id_comida = 5)
(5, 5),   -- Lechuga
(4, 5),   -- Tomate
(18, 5),  -- Pepino
(19, 5),  -- Zanahoria
(20, 5);  -- Vinagreta


-- Insert para ingredientes_coctel
insert into ingredientes_coctel (id_ingrediente, id_coctel) values
-- Mojito (id_coctel = 1)
(21, 1), -- Ron
(22, 1), -- Hierbabuena
(23, 1), -- Azúcar
(24, 1), -- Limón
(25, 1), -- Soda

-- Margarita (id_coctel = 2)
(26, 2), -- Tequila
(27, 2), -- Triple Sec
(24, 2), -- Limón
(17, 2), -- Sal
(28, 2), -- Hielo

-- Piña Colada (id_coctel = 3)
(21, 3), -- Ron
(29, 3), -- Crema de Coco
(30, 3), -- Jugo de Piña
(28, 3), -- Hielo
(23, 3), -- Azúcar

-- Caipiriña (id_coctel = 4)
(31, 4), -- Cachaça
(24, 4), -- Limón
(23, 4), -- Azúcar
(28, 4), -- Hielo
(22, 4), -- Hierbabuena

-- Bloody Mary (id_coctel = 5)
(32, 5), -- Vodka
(33, 5), -- Jugo de Tomate
(34, 5), -- Salsa Worcestershire
(24, 5), -- Limón
(35, 5); -- Pimienta


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