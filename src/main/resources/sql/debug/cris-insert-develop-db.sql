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



INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (2, true, 'mi-api-key-12346', 'CIF123457', 'Calle Falsa 124', null, 'ES9121000418450200051333', 'Tienda de Juegos de Mesa Chessyx', 'España', 'Madrid', 'http://localhost:8246/tienda/receivePedido', 1);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (1, true, 'mi-api-key-12345', 'CIF123456', 'Calle Falsa 123', null, 'ES9121000418450200051332', 'Gimnasio ASN San Vicente', 'España', 'Alicante', 'https://comercio-ejemplo.com/back', 1);


INSERT INTO public.personas_contacto (id, email, nombre_contacto, telefono, comercio_id) VALUES (1, 'contacto1@gmail.com', 'contacto1', '123456789', 1);
INSERT INTO public.personas_contacto (id, email, nombre_contacto, telefono, comercio_id) VALUES (2, 'contacto2@gmail.com', 'contacto2', '123456789', 2);





INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (1, 123, '2024-10-01 00:00:00.000000', 'Sara Fernández Ruiz', '0000123412344827');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (2, 145, '2007-05-01 00:00:00.000000', 'Marcos González Díaz', '0001123412341938');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (3, 143, '2011-10-01 00:00:00.000000', 'Camila Ortiz Silva', '1000123412347564');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (4, 321, '2015-10-01 00:00:00.000000', 'Martín Herrera Medina', '1001123412342051');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (5, 123, '2006-06-01 00:00:00.000000', 'Pablo Morales Sánchez', '4678467846784678');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (6, 111, '2022-09-01 00:00:00.000000', 'Camila Ortiz Silva', '2000123412348492');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (7, 123, '2024-10-01 00:00:00.000000', 'Gabriel Castillo Muñoz', '0002123412346174');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (8, 521, '2006-05-01 00:00:00.000000', 'Isabella León Calderón', '0003123412343849');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (9, 854, '2007-05-01 00:00:00.000000', 'Álvaro Cruz Jiménez', '1002123412345293');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (10, 178, '2021-04-01 00:00:00.000000', 'Mariana Rivera Álvarez', '1003123412347361');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (11, 111, '2024-10-01 00:00:00.000000', 'Elena Vega Serrano', '2001123412344012');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (12, 111, '2007-05-01 00:00:00.000000', 'Nicolás Domínguez Rivas', '2002123412349832');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (13, 111, '2024-10-01 00:00:00.000000', 'Julieta Romero Flores', '0000223412342574');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (14, 631, '2009-08-01 00:00:00.000000', 'Julieta Romero Flores', '0001223412346143');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (15, 167, '2011-10-01 00:00:00.000000', 'Sebastián Mendoza Vargas', '1000223412348907');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (17, 111, '2024-10-01 00:00:00.000000', 'Martín Herrera Medina', '1001223412344721');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (18, 743, '2023-09-01 00:00:00.000000', 'Carolina Muñoz Fuentes', '2003123412343058');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (19, 321, '2009-08-01 00:00:00.000000', 'Álvaro Cruz Jiménez', '2000223412347294');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (20, 432, '2007-05-01 00:00:00.000000', 'Carolina Muñoz Fuentes', '0002223412341843');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (21, 412, '2032-07-01 00:00:00.000000', 'Nicolás Domínguez Rivas', '0003223412345602');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (22, 971, '2029-11-01 00:00:00.000000', 'Mariana Rivera Álvarez', '1002223412349476');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (23, 616, '2026-06-01 00:00:00.000000', 'Gabriel Castillo Muñoz', '1003223412343157');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (24, 678, '2031-10-01 00:00:00.000000', 'Isabella León Calderón', '2001223412348924');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (25, 812, '2027-05-01 00:00:00.000000', 'Pablo Morales Sánchez', '2002223412344762');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (26, 901, '2029-08-01 00:00:00.000000', 'Andrea López García', '2003223412341039');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (27, 761, '2025-02-01 00:00:00.000000', 'Elena Vega Serrano', '6765676567657428');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (28, 123, '2024-10-01 00:00:00.000000', 'Carlos Pérez Navarro', '0000123412345983');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (29, 123, '2024-10-01 00:00:00.000000', 'Tomás Rivas Ortega', '3232323232323232');







INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (1, 'ACEPT1000', 'PAGO ACEPTADO: PAGO PROCESADO CORRECTAMENTE');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (2, 'ACEPT0001', 'PAGO ACEPTADO: IDENTIDAD DEL TITULAR VERIFICADA');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (3, 'ACEPT0002', 'PAGO ACEPTADO: REVISIÓN ANTIFRAUDE SUPERADA CON ÉXITO');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (4, 'ACEPT0003', 'PAGO ACEPTADO: CONFIRMACIÓN INSTANTÁNEA POR PASARELA DE PAGO');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (5, 'ACEPT0004', 'PAGO ACEPTADO: MONEDA SOPORTADA POR EL PROCESADOR DE PAGOS');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (6, 'PEND0001', 'PAGO PENDIENTE: VERIFICACIÓN MANUAL REQUERIDA');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (7, 'PEND0002', 'PAGO PENDIENTE: TRANSFERENCIA EN ESPERA DE COMPENSACIÓN');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (8, 'PEND0003', 'PAGO PENDIENTE: CONVERSIÓN DE MONEDA EN PROCESO');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (9, 'PEND0004', 'PAGO PENDIENTE: PROCESO DE CONCILIACIÓN BANCARIA EN CURSO');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (10, 'RECH0001', 'PAGO RECHAZADO: SALDO INSUFICIENTE');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (11, 'RECH0002', 'PAGO RECHAZADO: TARJETA BLOQUEADA');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (12, 'RECH0003', 'PAGO RECHAZADO: TARJETA VENCIDA');
INSERT INTO public.estados_pago (id, nombre, razon_estado) VALUES (13, 'RECH0004', 'PAGO RECHAZADO: FALLO EN LA CONEXIÓN CON EL BANCO');






INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (1, '2003-04-02 08:08:08.080000', 888.88, 'ORD-2025-XYZ12345', 2, 10, 1);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (2, '2006-06-07 12:32:11.045000', 726.58, 'ORD-9876543210', 2, 11, 2);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (3, '2007-05-11 04:24:45.097000', 3.14, 'ORD-A1B2C3D4E5', 2, 6, 3);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (4, '2008-06-06 08:24:13.069210', 499.22, 'ORD-00056789-ABC', 2, 7, 4);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (5, '2008-01-06 19:20:21.008700', 867.91, 'ORD-25JAN2025-09876', 2, 1, 5);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (6, '2008-03-11 21:54:57.043000', 120.48, 'ORD-1234XYZ-20250104', 2, 2, 6);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (7, '2008-02-04 20:31:34.768000', 584.32, 'ORD-20250104-56789', 2, 12, 7);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (8, '2009-02-03 09:24:37.003210', 743.89, 'ORD-000001-2025', 2, 13, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (9, '2010-06-07 10:21:23.002450', 215.67, 'ORD-20250104PAY', 2, 8, 9);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (10, '2011-09-11 11:34:34.347000', 990.01, 'ORD-ABCDEF123456', 2, 9, 10);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (11, '2012-06-04 08:47:13.460000', 37.45, 'ORD-87654321-20250104', 2, 3, 11);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (12, '2013-09-09 13:45:00.000000', 658.73, 'ORD-2025JAN001', 2, 4, 12);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (13, '2014-02-11 11:23:34.220000', 94.12, 'ORD-XYZ987654321', 2, 10, 13);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (14, '2015-03-10 12:11:34.045000', 763.04, 'ORD-0123456-XXYYZZ', 2, 11, 14);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (15, '2016-06-11 16:54:11.007600', 481.29, 'ORD-20250104-UUID123', 1, 6, 15);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (17, '2017-09-09 09:31:14.210000', 329.78, 'ORD-9999-ABCDE', 1, 7, 17);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (18, '2018-03-11 10:57:32.021000', 874.56, 'ORD-2025-0000001', 1, 5, 18);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (19, '2019-02-12 13:45:00.054000', 12.34, 'ORD-0987-TKT2025', 1, 2, 19);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (20, '2020-04-02 13:45:00.045600', 63.98, 'ORD-2025-JAN-00001', 1, 12, 20);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (21, '2021-03-06 14:52:11.022200', 315.23, 'ORD-12345678-2025', 1, 13, 21);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (22, '2022-06-11 21:12:43.000210', 876.41, 'ORD-A12345-B6789', 1, 8, 22);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (23, '2023-09-09 07:32:24.003400', 502.66, 'ORD-20250104-XYZ', 1, 9, 23);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (24, '2024-06-07 09:07:05.000020', 128.09, 'ORD-000012345678', 1, 3, 24);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (25, '2024-07-11 17:13:47.453000', 745.34, 'ORD-001-56789XYZ', 1, 4, 25);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (26, '2024-08-27 20:37:21.724000', 256.78, 'ORD-01042025-1234', 1, 5, 26);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (27, '2024-09-14 10:32:34.760000', 999.45, 'ORD-ABCDE-98765', 1, 1, 27);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (28, '2024-09-23 21:57:00.045000', 0.12, 'ORD-2025-00123', 1, 10, 28);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (29, '2024-10-11 12:47:31.036200', 423.87, 'ORD-20250104-ABC123', 1, 1, 29);






INSERT INTO public.tipos_usuario (id, nombre) VALUES (1, 'administrador');
INSERT INTO public.tipos_usuario (id, nombre) VALUES (2, 'tecnico');
INSERT INTO public.tipos_usuario (id, nombre) VALUES (3, 'comercio');




INSERT INTO public.usuarios (id, activo, contrasenya, email, fecha_alta, nombre, comercio_id, tipo_id) VALUES (1, true, '$2a$10$uEzYq5xTUFwUgBezRaJNvOr7n88Xt7dV.Ne.qg2Pb1K8WmgBNSgP2', 'admin-default@gmail.com', null, 'admin-default', 1, 1);
INSERT INTO public.usuarios (id, activo, contrasenya, email, fecha_alta, nombre, comercio_id, tipo_id) VALUES (2, true, '$2a$10$r/UwgDJHaNd1iJoKHwh9we3q3YxXQlcHDqSJVzIR00sRtwrlRytfy', 'tecnico-default@gmail.com', null, 'tecnico-default', 1, 2);
INSERT INTO public.usuarios (id, activo, contrasenya, email, fecha_alta, nombre, comercio_id, tipo_id) VALUES (3, true, '$2a$10$SeXSpZ0tRIRkWUf7gBeN1u7ykt7x3n0ndNq5Mc4OLlwkQAuOb3SRa', 'comercio-default@gmail.com', null, 'comercio-default', 2, 3);

INSERT INTO public.estados_incidencia (id, nombre) VALUES (1, 'NUEVA');
INSERT INTO public.estados_incidencia (id, nombre) VALUES (2, 'ASIGN');
INSERT INTO public.estados_incidencia (id, nombre) VALUES (3, 'RESUELTA');
