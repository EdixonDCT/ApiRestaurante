USE FacturacionRestaurante;

create table permisos(
	id int auto_increment,
    nombre varchar(50) not null unique,
    descripcion text,
    primary key (id)
);
create table roles(
    id int auto_increment,
    nombre varchar(50) not null unique,
    salario decimal(10,2),
    primary key (id)
);
create table rolesPermisos (
    id int auto_increment,
    id_rol int not null,
    id_permiso int not null,
    primary key (id),
	foreign key (id_rol) references roles(id),
    foreign key (id_permiso) references permisos(id)
);
create table usuarios(
	id int auto_increment,
    cedula varchar(20) not null,
    nombre varchar(50) not null,
    apellido varchar(50) not null,
    nacimiento date,
    foto varchar(255),
    contrasena varchar(20),
	activo boolean default(0),
    eliminado boolean default(0),
    primary key (id)
);
create table rolesUsuarios (
    id int auto_increment,
    id_rol int not null,
    id_usuario int not null,
    primary key (id),
	foreign key (id_rol) references roles(id),
    foreign key (id_usuario) references usuarios(id)
);
create table mesas (
    numero int not null,
    capacidad int not null,
    disponible boolean default true,
    primary key (numero)
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
    id_usuario int not null,
    primary key (id),
    foreign key (id_usuario) references usuarios(id)
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
    id_usuario int not null,
    metodo_pago varchar(50),
    facturado boolean default(false),
    eliminado boolean default(false),
    primary key (id),
    foreign key (numero_mesa) references mesas(numero),
    foreign key (id_caja) references caja(id),
    foreign key (id_reserva) references reservas(id),
    foreign key (id_usuario) references usuarios(id)
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
insert into roles(nombre, salario) values 
('Trabajador',null),
('Cliente',null),
('Administrador', 99999.99),
('Cajero', 65000.50),
('Mesero', 40000.00);

insert into permisos(nombre,descripcion) values
('Mesa.listar','visualizar las mesas.'),
('Mesa.crear','creacion de mesas.'),
('Mesa.editar','modificacion de mesas.'),
('Mesa.eliminar','eliminacion de mesas.'),
('Caja.listar','visualizar las cajas.'),
('Caja.crear','creacion de cajas.'),
('Caja.editar','modificacion de cajas.'),
('Caja.eliminar','eliminacion de cajas.'),
('Pedido.listar','visualizar las pedidos.'),
('Pedido.crear','creacion de pedidos.'),
('Pedido.editar','modificacion de pedidos.'),
('Pedido.eliminar','eliminacion de pedidos.'),
('Pedido.eliminadoSuave','eliminado suave para los pedidos.'),
('Pedido.eliminadosListar','listar pedidos eliminados con eliminado suave.'),
('Reserva.listar','visualizar las reservas.'),
('Reserva.crear','creacion de reservas.'),
('Reserva.editar','modificacion de reservas.'),
('Reserva.eliminar','eliminacion de reservas.'),
('Ingrediente.listar','visualizar las ingredientes.'),
('Ingrediente.crear','creacion de ingredientes.'),
('Ingrediente.editar','modificacion de ingredientes.'),
('Ingrediente.eliminar','eliminacion de ingredientes.'),
('Platillo.listar','visualizar las platillos.'),
('Platillo.crear','creacion de platillos.'),
('Platillo.editar','modificacion de platillos.'),
('Platillo.eliminar','eliminacion de platillos.'),
('Platillo.eliminadoSuave','eliminado suave para los platillos.'),
('Platillo.eliminadosListar','listar pedidos eliminados con eliminado platillos.'),
('Trabajadores.listar','visualizar las ingredientes.'),
('Trabajadores.crear','creacion de ingredientes.'),
('Trabajadores.editar','modificacion de ingredientes.'),
('Trabajadores.eliminar','eliminacion de ingredientes.'),
('Trabajadores.eliminadoSuave','eliminado suave para los trabajadores.'),
('Trabajadores.eliminadosListar','listar pedidos eliminados con eliminado trabajadores.');

insert into rolesPermisos(id_rol, id_permiso) values
(3,1),
(3,2),
(3,3),
(3,4),
(3,5),
(3,6),
(3,7),
(3,8),
(3,9),
(3,10),
(3,11),
(3,12),
(3,13),
(3,14),
(3,15),
(3,16),
(3,17),
(3,18),
(3,19),
(3,20),
(3,21),
(3,22),
(3,23),
(3,24),
(3,25),
(3,26),
(3,27),
(3,28),
(3,29),
(3,30),
(3,31),
(3,32),
(3,33),
(3,34),
(4,1),
(4,3),
(4,5),
(4,6),
(4,9),
(4,10),
(4,11),
(4,12),
(4,15),
(4,16),
(4,17),
(4,18),
(5,1),
(5,9),
(5,10),
(5,11);

insert into usuarios(cedula, nombre, apellido, nacimiento, foto, contrasena,activo) values 
('1001', 'Ana', 'Rojas', '1990-01-01', 'c4725ed6-a2a8-4112-9572-639515e759a0_foto1.png', '1234',1),
('1002', 'Luis', 'Gomez', '1988-05-12', '3a2ab7b3-0e76-4b0b-8036-815ad0fc87b8_foto2.png', '1234',1),
('1003', 'Sara', 'Lopez', '1995-03-22', '4183b335-f95b-4724-ab04-bf488f3e3448_foto3.png', '1234',1),
('1004', 'Pedro', 'Perez', '2000-08-10', 'f74c6d39-39a0-4683-98ae-78c5c058f144_foto4.png', '1234',1),
('1005', 'Edixon', 'Castillo', '1992-11-15', 'b00a88c9-af7a-4d1a-8096-1d852a475dde_foto5.png', '1904',1);
insert into usuarios(cedula,nombre,apellido) values
('1001','Ana','Rojas'),
('1002','Luis','Gomez'),
('1003','Sara','Lopez'),
('1004','Pedro','Perez'),
('1005','Edixon','Castillo');
insert into rolesUsuarios(id_rol,id_usuario) values
(1,1),
(1,2),
(1,3),
(1,4),
(1,5),
(5,1),
(5,2),
(4,3),
(4,4),
(3,5),
(2,6),
(2,7),
(2,8),
(2,9),
(2,10);

insert into mesas (numero, capacidad, disponible) values 
(1, 4, true),
(2, 2, true),
(3, 6, false),
(4, 4, true),
(5, 8, false);

insert into comidas (nombre, precio, tipo, imagen, disponible) values 
('Pizza Mexicana', 25000, 'pizza', 'b7ddc57d-e117-4cd1-929e-ea635ac93ccc_pizza.png', true),
('Hamburguesa', 18000, 'hamburguesa', 'f1c771d8-3c42-45ba-9076-8d08fdeea12e_burger.png', true),
('Perro Royal', 12000, 'perro', '779c1a23-e01e-475a-92eb-8c4eaf399c4a_perrro.png', true),
('Salchipapa', 30000, 'papas', 'd60aeee3-4fec-43c2-9dc1-87fe3cde5668_papas.png', false),
('Ensalada', 15000, 'ensalada', 'f7a25c73-deb3-439e-abd7-3eb377ecc6b5_salad.png', true);

insert into bebidas (nombre, precio, unidad, tipo, imagen, disponible) values 
('Coca-Cola', 4000, 'Botella', 'Refresco', '7ac2e2eb-7693-424c-99c0-c4b34e4f9087_coca.png', true),
('Agua', 3000, 'Botella', 'Natural', '130fe7e2-8dda-4877-a920-4b3b44f0fe64_agua.png', true),
('Jugo de Mango', 5000, 'Vaso', 'Fruta', '4e1644d5-af8e-4c9e-84f2-ba4957d030ea_mango.png', true),
('Café', 3500, 'Taza', 'Caliente', '1b83f6ea-ed92-421b-b236-874e3f4a38aa_cafe.png', false),
('Té', 3000, 'Taza', 'Caliente', 'a78e40a3-7a18-4449-99fa-8c9523a85c0f_te.png', true);

insert into cocteles (nombre, precio, imagen, disponible) values 
('Mojito', 12000, 'cab4013b-9e63-4025-bf85-fe98e7a89b7a_Mojito.png', true),
('Margarita', 13000, '105b4caf-38df-4f5f-a304-8ef59a19d9b9_marga.png', true),
('Piña Colada', 14000, '994255fd-622f-4044-9b02-ba8869ad4337_colada.png', false),
('Caipiriña', 11000, '02e587a2-b0de-47cb-812b-c251dff92eea_cai.png', true),
('Bloody Mary', 15000, '01cb075a-8563-4df7-9d5c-355cab9698dd_blodymary.png', true);

insert into ingredientes (nombre) values 
('Harina'),
('Queso'),
('Pollo'),
('Tomate'),
('Lechuga'),
('Salsa de Tomate'),
('Carne Molida'),
('Jalapeños'),
('Pan de Hamburguesa'),
('Carne de Res'),
('Queso Cheddar'),
('Pan para Perro'),
('Salchicha'),
('Queso Rallado'),
('Papas Fritas'),
('Salsa Rosada'),
('Sal'),
('Pepino'),
('Zanahoria'),
('Vinagreta'),
('Ron'),
('Hierbabuena'),
('Azúcar'),
('Limón'),
('Soda'),
('Tequila'),
('Triple Sec'),
('Hielo'),
('Crema de Coco'),
('Jugo de Piña'),
('Cachaça'),
('Vodka'),
('Jugo de Tomate'),
('Salsa Worcestershire'),
('Pimienta');

insert into ingredientes_comida (id_ingrediente, id_comida) values
(1, 1),
(2, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 2),
(10, 2),
(11, 2),
(5, 2),
(4, 2),
(12, 3),
(13, 3),
(14, 3),
(6, 3),
(15, 3),
(15, 4),
(13, 4),
(14, 4),
(16, 4),
(17, 4),
(5, 5),
(4, 5),
(18, 5),
(19, 5),
(20, 5);

insert into ingredientes_coctel (id_ingrediente, id_coctel) values
(21, 1),
(22, 1),
(23, 1),
(24, 1),
(25, 1),
(26, 2),
(27, 2),
(24, 2),
(17, 2),
(28, 2),
(21, 3),
(29, 3),
(30, 3),
(28, 3),
(23, 3),
(31, 4),
(24, 4),
(23, 4),
(28, 4),
(22, 4),
(32, 5),
(33, 5),
(34, 5),
(24, 5),
(35, 5);

insert into reservas (cantidad_tentativa,precio, fecha, fecha_tentativa, hora_tentativa) values 
(1,50000, '2025-07-10', '2025-07-11', '18:00:00'),
(2,60000, '2025-07-10', '2025-07-12', '19:00:00'),
(3,55000, '2025-07-10', '2025-07-13', '20:00:00'),
(4,70000, '2025-07-10', '2025-07-14', '21:00:00'),
(5,45000, '2025-07-10', '2025-07-15', '17:00:00');

insert into caja (fecha_apertura, hora_apertura, monto_apertura, fecha_cierre, hora_cierre, monto_cierre, id_usuario) values 
('2025-07-10', '08:00:00', 100000, '2025-07-10', '22:00:00', 500000, 3),
('2025-07-11', '08:00:00', 120000, '2025-07-11', '22:00:00', 450000, 3),
('2025-07-12', '08:00:00', 110000, '2025-07-12', '22:00:00', 400000, 4),
('2025-07-13', '08:00:00', 105000, '2025-07-13', '22:00:00', 470000, 4),
('2025-07-14', '08:00:00', 90000,  '2025-07-14', '22:00:00', 430000, 5);

insert into pedidos (numero_mesa, fecha, hora, valor_total, id_caja, numero_clientes, id_reserva, id_usuario, metodo_pago,facturado) values 
(1, '2025-07-10', '18:00:00', 80000, 1, 2, 1, 6, 'Efectivo',1),
(2, '2025-07-11', '19:00:00', 75000, 2, 3, 2, 7, 'Tarjeta',1),
(3, '2025-07-12', '20:00:00', 90000, 3, 4, 3, 8, 'Nequi',1),
(4, '2025-07-13', '21:00:00', 85000, 4, 2, 4, 9, 'Efectivo',1),
(5, '2025-07-14', '22:00:00', 92000, 5, 5, 5, 10, 'Tarjeta',1);

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
drop table if exists mesas;
drop table if exists rolesUsuarios;
drop table if exists rolesPermisos;
drop table if exists usuarios;
drop table if exists roles;
drop table if exists permisos;