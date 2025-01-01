DELETE FROM parametros;
DELETE FROM mensajes;
DELETE FROM valoraciones_tecnico;
DELETE FROM usuarios;
DELETE FROM incidencias;
DELETE FROM estados_incidencia;
DELETE FROM tipos_usuario;
DELETE FROM pagos;
DELETE FROM estados_pago;
DELETE FROM tarjetas_pago;
DELETE FROM personas_contacto;
DELETE FROM comercios;
DELETE FROM paises;



INSERT INTO public.paises (id, nombre) VALUES (1, 'España');
INSERT INTO public.paises (id, nombre) VALUES (2, 'Francia');
INSERT INTO public.paises (id, nombre) VALUES (3, 'Alemania');
INSERT INTO public.paises (id, nombre) VALUES (4, 'Italia');
INSERT INTO public.paises (id, nombre) VALUES (5, 'Portugal');
INSERT INTO public.paises (id, nombre) VALUES (6, 'Reino Unido');
INSERT INTO public.paises (id, nombre) VALUES (7, 'Países Bajos');
INSERT INTO public.paises (id, nombre) VALUES (8, 'Bélgica');
INSERT INTO public.paises (id, nombre) VALUES (9, 'Suiza');
INSERT INTO public.paises (id, nombre) VALUES (10, 'Suecia');


INSERT INTO public.comercios (id, activo, api_key, cif, direccion, iban, nombre, pais, provincia, url_back, pais_id) VALUES (1, true, 'mi-api-key-12345', 'CIF123456', 'Calle Falsa 123', 'ES9121000418450200051332', 'Comercio Ejemplo', 'España', 'Madrid', 'https://comercio-ejemplo.com/back', 1);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, iban, nombre, pais, provincia, url_back, pais_id) VALUES (2, true, 'mi-api-key-12346', 'CIF123457', 'Calle Falsa 124', 'ES9121000418450200051333', 'Comercio Ejemplo 2', 'España', 'Madrid', 'http://localhost:8246/tienda/receivePedido', 1);





INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (4, 432, '2007-05-01 00:00:00.000000', 'Ana Bogoles', '3333333333333333');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (5, 276, '2021-10-01 00:00:00.000000', 'Hola Buenas', '9999999999999999');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (7, 123, '2024-10-01 00:00:00.000000', 'Kirby Karbo', '2222222222222222');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (9, 222, '2002-02-01 00:00:00.000000', 'Prueba Post dentro de Post', '1212121212121212');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (10, 222, '2024-10-01 00:00:00.000000', 'POST DENTRO DE POST', '3434343434343434');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (11, 243, '2024-10-01 00:00:00.000000', 'hell yeah', '5454545454545454');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (12, 432, '2024-10-01 00:00:00.000000', 'buenas perro', '9191919191919191');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (6, 111, '2024-10-01 00:00:00.000000', 'Guardar Pedido', '1111111111111112');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (13, 456, '2009-08-01 00:00:00.000000', 'Pedido Guardado en BD', '8484848484848484');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (14, 789, '2024-10-01 00:00:00.000000', 'Mmaut', '11111111111112222');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (16, 123, '2024-12-31 17:40:03.748000', 'admin1', '1010101010101010');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (17, 123, '2024-10-01 00:00:00.000000', 'admin1', '1111111111111666');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (20, 824, '2012-11-01 00:00:00.000000', 'Tails erizo', '7676767676767676');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (19, 879, '2011-08-01 00:00:00.000000', 'Pene Erizo', '7070707070707070');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (21, 437, '2003-02-01 00:00:00.000000', 'Boniato Chulo', '2465246524652465');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (22, 547, '2024-10-01 00:00:00.000000', 'Blackie is Black', '0202020202020202');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (15, 123, '2024-10-01 00:00:00.000000', 'Pagador ejemplo', '0000000000000000');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (8, 123, '2024-10-01 00:00:00.000000', 'admin1', '1111111111111111');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (3, 345, '2024-10-01 00:00:00.000000', 'admin fresco', '6666666666666666');





INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (8, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (9, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (10, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (11, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (12, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (13, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (14, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (15, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (16, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (17, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (18, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (19, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (20, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (21, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (22, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (23, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (24, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (25, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (26, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (27, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (28, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (29, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (30, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (31, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (32, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (33, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (34, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (35, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (36, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (37, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (38, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (39, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (40, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (41, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (42, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (43, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (48, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (49, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (50, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (51, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (52, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (53, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (54, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (55, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (56, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (57, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (58, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (59, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (60, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (61, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (62, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (63, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (64, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (65, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (66, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (67, 'acept001', 'Pago procesado correctamente.');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (68, 'acept001', 'Pago procesado correctamente.');




INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (2, '2029-09-09', 777.777, 'TICKET-777', 2, 8, 3);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (3, '2029-09-09', 888.888, 'TICKET-888', 2, 9, 4);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (4, '2029-09-09', 888.888, 'TICKET-888', 2, 10, 3);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (5, '2029-09-09', 888.888, 'TICKET-888', 2, 11, 5);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (6, '2029-09-09', 888.888, 'TICKET-888', 2, 12, 6);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (7, '2029-09-09', 888.888, 'TICKET-888', 2, 13, 7);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (8, '2029-09-09', 888.888, 'TICKET-888', 2, 14, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (9, '2029-09-09', 888.888, 'TICKET-888', 2, 15, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (10, '2029-09-09', 888.888, 'TICKET-888', 2, 16, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (11, '2029-09-09', 888.888, 'TICKET-888', 2, 17, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (12, '2029-09-09', 888.888, 'TICKET-888', 2, 18, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (13, '2029-09-09', 888.888, 'TICKET-888', 2, 19, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (14, '2029-09-09', 888.888, 'TICKET-888', 2, 20, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (15, '2029-09-09', 888.888, 'TICKET-888', 2, 21, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (16, '2029-09-09', 888.888, 'TICKET-888', 2, 22, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (17, '2029-09-09', 888.888, 'TICKET-888', 2, 23, 9);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (18, '2029-09-09', 888.888, 'TICKET-888', 2, 24, 10);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (19, '2029-09-09', 888.888, 'TICKET-888', 2, 25, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (20, '2029-09-09', 888.888, 'TICKET-888', 2, 26, 3);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (21, '2029-09-09', 888.888, 'TICKET-888', 2, 27, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (22, '2029-09-09', 888.888, 'TICKET-888', 2, 28, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (23, '2029-09-09', 888.888, 'TICKET-888', 2, 29, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (24, '2029-09-09', 888.888, 'TICKET-888', 2, 30, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (25, '2029-09-09', 888.888, 'TICKET-888', 2, 31, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (26, '2029-09-09', 888.888, 'TICKET-888', 2, 32, 11);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (27, '2029-09-09', 888.888, 'TICKET-888', 2, 33, 12);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (28, '2029-09-09', 888.888, 'TICKET-888', 2, 34, 12);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (29, '2029-09-09', 888.888, 'TICKET-888', 2, 35, 6);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (30, '2029-09-09', 888.888, 'TICKET-888', 2, 36, 13);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (31, '2029-09-09', 888.888, 'TICKET-888', 2, 37, 14);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (32, '2029-09-09', 888.888, 'TICKET-888', 2, 38, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (33, '2029-09-09', 888.888, 'TICKET-888', 2, 39, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (34, '2029-09-09', 888.888, 'TICKET-888', 2, 40, 15);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (35, '2029-09-09', 888.888, 'TICKET-888', 2, 41, 15);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (36, '2029-09-09', 888.888, 'TICKET-888', 2, 42, 16);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (37, '2029-09-09', 888.888, 'TICKET-888', 2, 43, 17);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (38, '2029-09-09', 888.888, 'TICKET-888', 2, 48, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (39, '2029-09-09', 888.888, 'TICKET-888', 2, 49, 19);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (40, '2029-09-09', 888.888, 'TICKET-888', 2, 50, 20);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (41, '2029-09-09', 888.888, 'TICKET-888', 2, 51, 19);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (42, '2029-09-09', 888.888, 'TICKET-888', 2, 52, 21);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (43, '2029-09-09', 888.888, 'TICKET-888', 2, 53, 21);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (44, '2029-09-09', 888.888, 'TICKET-888', 2, 54, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (45, '2029-09-09', 888.888, 'TICKET-888', 2, 55, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (46, '2029-09-09', 888.888, 'TICKET-888', 2, 56, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (47, '2029-09-09', 888.888, 'TICKET-888', 2, 57, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (48, '2029-09-09', 888.888, 'TICKET-888', 2, 58, 22);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (49, '2029-09-09', 888.888, 'TICKET-888', 2, 59, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (50, '2029-09-09', 888.888, 'TICKET-888', 2, 60, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (51, '2029-09-09', 888.888, 'TICKET-888', 2, 61, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (52, '2029-09-09', 888.888, 'TICKET-888', 2, 62, 15);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (53, '2029-09-09', 888.888, 'TICKET-888', 2, 63, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (54, '2029-09-09', 888.888, 'TICKET-888', 2, 64, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (55, '2029-09-09', 888.888, 'TICKET-888', 2, 65, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (56, '2029-09-09', 888.888, 'TICKET-888', 2, 66, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (57, '2029-09-09', 888.888, 'TICKET-888', 2, 67, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (58, '2029-09-09', 888.888, 'TICKET-888', 2, 68, 3);




INSERT INTO public.tipos_usuario (id, nombre) VALUES (1, 'administrador');
INSERT INTO public.tipos_usuario (id, nombre) VALUES (2, 'tecnico');
INSERT INTO public.tipos_usuario (id, nombre) VALUES (3, 'comercio');




INSERT INTO public.usuarios (id, activo, contrasenya, email, nombre, comercio_id, tipo_id) VALUES (1, true, '$2a$10$uEzYq5xTUFwUgBezRaJNvOr7n88Xt7dV.Ne.qg2Pb1K8WmgBNSgP2', 'admin-default@gmail.com', 'admin-default', 1, 1);
INSERT INTO public.usuarios (id, activo, contrasenya, email, nombre, comercio_id, tipo_id) VALUES (2, true, '$2a$10$r/UwgDJHaNd1iJoKHwh9we3q3YxXQlcHDqSJVzIR00sRtwrlRytfy', 'tecnico-default@gmail.com', 'tecnico-default', 1, 2);
INSERT INTO public.usuarios (id, activo, contrasenya, email, nombre, comercio_id, tipo_id) VALUES (3, true, '$2a$10$SeXSpZ0tRIRkWUf7gBeN1u7ykt7x3n0ndNq5Mc4OLlwkQAuOb3SRa', 'comercio-default@gmail.com', 'comercio-default', 1, 3);


