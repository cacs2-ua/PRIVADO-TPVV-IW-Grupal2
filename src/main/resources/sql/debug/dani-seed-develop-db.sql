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





INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (1, 123, '2024-10-01 00:00:00.000000', 'Persona Rechazada 1', '0000123412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (2, 145, '2007-05-01 00:00:00.000000', 'Persona Rechazada 2', '0001123412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (3, 143, '2011-10-01 00:00:00.000000', 'Persona Pendiente 1', '1000123412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (4, 321, '2015-10-01 00:00:00.000000', 'Persona Pendiente 2', '1001123412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (5, 123, '2006-06-01 00:00:00.000000', 'Persona Aceptada General', '4678467846784678');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (6, 111, '2022-09-01 00:00:00.000000', 'Persona Aceptada 1', '2000123412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (7, 123, '2024-10-01 00:00:00.000000', 'Persona Rechazada 3', '0002123412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (8, 521, '2006-05-01 00:00:00.000000', 'Persona Rechazada 4', '0003123412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (9, 854, '2007-05-01 00:00:00.000000', 'Persona Pendiente 3', '1002123412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (10, 178, '2021-04-01 00:00:00.000000', 'Persona Pendiente 4', '1003123412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (11, 111, '2024-10-01 00:00:00.000000', 'Persona Aceptada 2', '2001123412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (12, 111, '2007-05-01 00:00:00.000000', 'Persona Aceptada 3', '2002123412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (13, 111, '2024-10-01 00:00:00.000000', 'Persona Rechazada 5', '0000223412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (14, 631, '2009-08-01 00:00:00.000000', 'Persona Rechazada 6', '0001223412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (15, 167, '2011-10-01 00:00:00.000000', 'Persona Pendiente 5', '1000223412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (17, 111, '2024-10-01 00:00:00.000000', 'Persona Pendiente 6', '1001223412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (18, 743, '2023-09-01 00:00:00.000000', 'Persona Pendiente 4', '2003123412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (19, 321, '2009-08-01 00:00:00.000000', 'Persona Aceptada 5', '2000223412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (20, 432, '2007-05-01 00:00:00.000000', 'Persona Rechazada 7', '0002223412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (21, 412, '2032-07-01 00:00:00.000000', 'Persona Rechazada 8', '0003223412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (22, 971, '2029-11-01 00:00:00.000000', 'Persona Pendiente 7', '1002223412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (23, 616, '2026-06-01 00:00:00.000000', 'Persona Pendiente 8', '1003223412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (24, 678, '2031-10-01 00:00:00.000000', 'Persona Aceptada 6', '2001223412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (25, 812, '2027-05-01 00:00:00.000000', 'Persona Aceptada 7', '2002223412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (26, 901, '2029-08-01 00:00:00.000000', 'Persona Aceptada 8', '2003223412341234');
INSERT INTO public.tarjetas_pago (id, cvc, fecha_caducidad, nombre, numero_tarjeta) VALUES (27, 761, '2025-02-01 00:00:00.000000', 'Persona Aceptada General 2', '6765676567656765');




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







INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (1, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 10, 1);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (2, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 11, 2);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (3, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 6, 3);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (4, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 7, 4);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (5, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 1, 5);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (6, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 2, 6);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (7, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 12, 7);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (8, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 13, 8);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (9, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 8, 9);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (10, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 9, 10);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (11, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 3, 11);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (12, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 4, 12);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (13, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 10, 13);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (14, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 11, 14);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (15, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 6, 15);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (17, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 7, 17);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (18, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 5, 18);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (19, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 2, 19);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (20, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 12, 20);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (21, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 13, 21);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (22, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 8, 22);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (23, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 9, 23);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (24, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 3, 24);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (25, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 4, 25);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (26, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 5, 26);
INSERT INTO public.pagos (id, fecha, importe, ticket_ext, comercio_id, estado_id, tarjeta_pago_id) VALUES (27, '2029-09-09 13:45:00.000000', 888.888, 'TICKET-888', 2, 1, 27);




INSERT INTO public.tipos_usuario (id, nombre) VALUES (1, 'administrador');
INSERT INTO public.tipos_usuario (id, nombre) VALUES (2, 'tecnico');
INSERT INTO public.tipos_usuario (id, nombre) VALUES (3, 'comercio');





INSERT INTO public.usuarios (id, activo, contrasenya, email, nombre, comercio_id, tipo_id) VALUES (1, true, '$2a$10$uEzYq5xTUFwUgBezRaJNvOr7n88Xt7dV.Ne.qg2Pb1K8WmgBNSgP2', 'admin-default@gmail.com', 'admin-default', 1, 1);
INSERT INTO public.usuarios (id, activo, contrasenya, email, nombre, comercio_id, tipo_id) VALUES (2, true, '$2a$10$r/UwgDJHaNd1iJoKHwh9we3q3YxXQlcHDqSJVzIR00sRtwrlRytfy', 'tecnico-default@gmail.com', 'tecnico-default', 1, 2);
INSERT INTO public.usuarios (id, activo, contrasenya, email, nombre, comercio_id, tipo_id) VALUES (3, true, '$2a$10$SeXSpZ0tRIRkWUf7gBeN1u7ykt7x3n0ndNq5Mc4OLlwkQAuOb3SRa', 'comercio-default@gmail.com', 'comercio-default', 1, 3);


