USE FacturacionRestaurante;
insert into oficios (tipo, salario) values 
('Administrador', 99999.99),
('Cajero', 65000.50),
('Mesero', 40000.00);
insert into trabajadores (cedula, nombre, apellido, nacimiento, foto, contrasena,activo, id_oficio) values 
('1001', 'Ana', 'Rojas', '1990-01-01', 'c4725ed6-a2a8-4112-9572-639515e759a0_foto1.png', '1234',1, 3),
('1002', 'Luis', 'Gomez', '1988-05-12', '3a2ab7b3-0e76-4b0b-8036-815ad0fc87b8_foto2.png', '1234',1, 3),
('1003', 'Sara', 'Lopez', '1995-03-22', '4183b335-f95b-4724-ab04-bf488f3e3448_foto3.png', '1234',1, 2),
('1004', 'Pedro', 'Perez', '2000-08-10', 'f74c6d39-39a0-4683-98ae-78c5c058f144_foto4.png', '1234',1, 2),
('1005', 'Edixon', 'Castillo', '1992-11-15', 'b00a88c9-af7a-4d1a-8096-1d852a475dde_foto5.png', '1904',1, 1);
insert into mesas (numero, capacidad, disponible) values 
(1, 4, true),
(2, 2, true),
(3, 6, false),
(4, 4, true),
(5, 8, false);
UPDATE trabajadores SET adminTemporalInicio = null,adminTemporalFin = null,activo = 0 WHERE cedula = '1001';
insert into clientes (correo, cedula, telefono) values 
('ana@correo.com', '2001', '3100000001'),
('luis@correo.com', '2002', '3100000002'),
('sara@correo.com', '2003', '3100000003'),
('pedro@correo.com', '2004', '3100000004'),
('elena@correo.com', '2005', '3100000005');
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
insert into caja (fecha_apertura, hora_apertura, monto_apertura, fecha_cierre, hora_cierre, monto_cierre, cedula_trabajador) values 
('2025-07-10', '08:00:00', 100000, '2025-07-10', '22:00:00', 500000, '1001'),
('2025-07-11', '08:00:00', 120000, '2025-07-11', '22:00:00', 450000, '1002'),
('2025-07-12', '08:00:00', 110000, '2025-07-12', '22:00:00', 400000, '1003'),
('2025-07-13', '08:00:00', 105000, '2025-07-13', '22:00:00', 470000, '1004'),
('2025-07-14', '08:00:00', 90000,  '2025-07-14', '22:00:00', 430000, '1005');
insert into pedidos (numero_mesa, fecha, hora, valor_total, id_caja, numero_clientes, id_reserva, correo_cliente, metodo_pago,facturado) values 
(1, '2025-07-10', '18:00:00', 80000, 1, 2, 1, 'ana@correo.com', 'Efectivo',1),
(2, '2025-07-11', '19:00:00', 75000, 2, 3, 2, 'luis@correo.com', 'Tarjeta',1),
(3, '2025-07-12', '20:00:00', 90000, 3, 4, 3, 'sara@correo.com', 'Nequi',1),
(4, '2025-07-13', '21:00:00', 85000, 4, 2, 4, 'pedro@correo.com', 'Efectivo',1),
(5, '2025-07-14', '22:00:00', 92000, 5, 5, 5, 'elena@correo.com', 'Tarjeta',1);
insert into detalle_pedido (id_pedido, id_comida, cantidad_comida, id_bebida, cantidad_bebida, id_coctel, cantidad_coctel) values 
(1, 1, 1, 1, 1, 1, 1),
(2, 2, 2, 2, 1, 2, 1),
(3, 3, 1, 3, 2, 3, 2),
(4, 4, 1, 4, 1, 4, 1),
(5, 5, 2, 5, 2, 5, 1);