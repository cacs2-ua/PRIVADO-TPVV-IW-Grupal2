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



INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (3, true, '6ohXIrQIZDCAtGQZ5cp5h912FHNYZySz', 'A12345676', 'Av. de la Innovación, 45, Madrid, 28000', '2024-12-31', 'ES1234567890123456789012', 'Soluciones Tech S.L.', 'España', 'Madrid', 'www.soltech.es', 1);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (8, true, 'wnUR1SUss0Ewh7rwWut1465rPcigKWRr', 'CH123456789', '1, Rue du Rhône, 1211 Geneva, Switzerland', '2025-01-01', 'CH9300762011623852957000', 'Rolex SA', 'Suiza', 'Ginebra', 'www.rolex.com', 9);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (999, false, '4444444444', 'CIF123459', 'Calle Falsa 124', '2024-04-10', 'ES9121000418450200051333', 'Comercio Ejemplo 3', 'España', 'Madrid', 'https://comercio-ejemplo.com/back', 1);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (998, false, 'mi-api-key-12346', 'CIF123457', 'Calle Falsa 124', '2024-01-16', 'ES9121000418450200051333', 'Comercio Ejemplo 2', 'España', 'Madrid', 'http://localhost:8246/tienda/receivePedido', 1);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (1, false, 'mi-api-key-12345', 'CIF123456', 'Calle Falsa 123', '2024-06-20', 'ES9121000418450200051332', 'Comercio Ejemplo', 'España', 'Madrid', 'https://comercio-ejemplo.com/back', 1);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (6, true, 'xJSOSRryxswisEk92dcxenPKA48b5x4s', 'GB123456789', '123 Innovation Road, London, EC1A 1AA', '2024-12-31', 'GB29XABC1016123456789012', 'Innovative Solutions Ltd.', 'Reino Unido', 'Londres', 'www.innovativesolutions.co.uk', 6);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (7, false, 'W5jCkRTK1S1QnZ6EJPYyHQmuaydtlvfL', 'D87654321', 'Carrera 7 No 23-45, Bogotá, 110010', '2024-12-31', 'CO9876543210987654321098', 'Creatividad 360 S.L.', 'Portugal', 'Bogotá', 'www.creatividad360.com', 5);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (5, true, 'y61YgpmmlntUheVgFGMPmbu1khFj8KwY', 'FR12345678901', '45 Rue de l''Innovation, 75001 Paris', '2024-12-31', 'FR7612345678901234567890123', ' Solutions Innovantes S.A.S.', 'Francia', 'Île-de-France', 'www.solutionsinnovantes.fr', 2);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (4, true, '94oUchPLl6X3PrHVcizFqVlKJgDMN1FB', 'B98765432', 'Calle Reforma 1234, Piso 6, 01000', '2024-12-31', 'SZ9876543210123456789012', 'Consultoría Global S.A.', 'Suiza', 'Ticcino', 'www.con-glabal.com', 9);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (2, true, '5sgzky466E8ZKyMzdmjNdLLx7vHAfDXA', '4435323A', 'Calle de la verga 22', '2024-12-30', '33334we', 'CocaCola', 'Alemania', 'Brixton', 'comerciopaco.com', 3);



INSERT INTO public.personas_contacto (id, email, nombre_contacto, telefono, comercio_id) VALUES (2, 'javier.gonzalez@solucionestech.com', 'Javier González', '912345678', 3);
INSERT INTO public.personas_contacto (id, email, nombre_contacto, telefono, comercio_id) VALUES (3, 'mariana.perez@consultoriaglobal.com', 'Mariana Pérez', '555987654', 4);
INSERT INTO public.personas_contacto (id, email, nombre_contacto, telefono, comercio_id) VALUES (4, 'claire.dupont@solutionsinnovantes.fr', 'Claire Dupont', '123456789', 5);
INSERT INTO public.personas_contacto (id, email, nombre_contacto, telefono, comercio_id) VALUES (5, 'james.williams@innovativesolutions.co.uk', 'James Williams', '2012345678', 6);
INSERT INTO public.personas_contacto (id, email, nombre_contacto, telefono, comercio_id) VALUES (6, 'felipe.vargas@creatividad360.com', 'Felipe Vargas', '300654321', 7);
INSERT INTO public.personas_contacto (id, email, nombre_contacto, telefono, comercio_id) VALUES (7, 'anne-marie.dubois@rolex.com', 'Anne-Marie Dubois', '223022222', 8);
INSERT INTO public.personas_contacto (id, email, nombre_contacto, telefono, comercio_id) VALUES (1, 'weewelt@gmail.com', 'Pierre', '123456789', 1);



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




INSERT INTO public.usuarios (id, activo, contrasenya, email, nombre, comercio_id, tipo_id) VALUES (2, true, '$2a$10$r/UwgDJHaNd1iJoKHwh9we3q3YxXQlcHDqSJVzIR00sRtwrlRytfy', 'tecnico-default@gmail.com', 'tecnico-default', 1, 2);
INSERT INTO public.usuarios (id, activo, contrasenya, email, nombre, comercio_id, tipo_id) VALUES (3, true, '$2a$10$SeXSpZ0tRIRkWUf7gBeN1u7ykt7x3n0ndNq5Mc4OLlwkQAuOb3SRa', 'comercio-default@gmail.com', 'comercio-default', 1, 3);
INSERT INTO public.usuarios (id, activo, contrasenya, email, nombre, comercio_id, tipo_id) VALUES (1, true, '$2a$10$uEzYq5xTUFwUgBezRaJNvOr7n88Xt7dV.Ne.qg2Pb1K8WmgBNSgP2', 'admin-default@gmail.com', 'admin-default', 1, 1);


