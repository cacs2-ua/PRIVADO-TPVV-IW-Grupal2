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



INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (9, true, 'ZEDMcEB7NaEI6LRGMhUFgNqukf0bbtnj', 'W18395678', 'Calle de la roma 32', '2025-01-03 00:00:00.000000', 'ES61 1284 3656 42 0456323532', 'Manzana', 'España', 'Albacete', 'www.manzana.com', 1);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (1, false, 'mi-api-key-12345', 'CIF123456', 'Calle Falsa 123', '2024-06-20 00:00:00.000000', 'ES9121000418450200051332', 'Comercio Ejemplo', 'España', 'Madrid', 'https://comercio-ejemplo.com/back', 1);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (3, true, '6ohXIrQIZDCAtGQZ5cp5h912FHNYZySz', 'A12345676', 'Av. de la Innovación, 45, Madrid, 28000', '2024-12-31 00:00:00.000000', 'ES1234567890123456789012', 'Soluciones Tech S.L.', 'España', 'Madrid', 'www.soltech.es', 1);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (8, true, 'wnUR1SUss0Ewh7rwWut1465rPcigKWRr', 'CH123456789', '1, Rue du Rhône, 1211 Geneva, Switzerland', '2025-01-01 00:00:00.000000', 'CH9300762011623852957000', 'Rolex SA', 'Suiza', 'Ginebra', 'www.rolex.com', 9);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (999, false, '4444444444', 'CIF123459', 'Calle Falsa 124', '2024-04-10 00:00:00.000000', 'ES9121000418450200051333', 'Comercio Ejemplo 3', 'España', 'Madrid', 'https://comercio-ejemplo.com/back', 1);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (998, false, 'mi-api-key-12346', 'CIF123457', 'Calle Falsa 124', '2024-01-16 00:00:00.000000', 'ES9121000418450200051333', 'Comercio Ejemplo 2', 'España', 'Madrid', 'http://localhost:8246/tienda/receivePedido', 1);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (6, true, 'xJSOSRryxswisEk92dcxenPKA48b5x4s', 'GB123456789', '123 Innovation Road, London, EC1A 1AA', '2024-12-31 00:00:00.000000', 'GB29XABC1016123456789012', 'Innovative Solutions Ltd.', 'Reino Unido', 'Londres', 'www.innovativesolutions.co.uk', 6);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (7, false, 'W5jCkRTK1S1QnZ6EJPYyHQmuaydtlvfL', 'D87654321', 'Carrera 7 No 23-45, Bogotá, 110010', '2024-12-31 00:00:00.000000', 'CO9876543210987654321098', 'Creatividad 360 S.L.', 'Portugal', 'Bogotá', 'www.creatividad360.com', 5);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (5, true, 'y61YgpmmlntUheVgFGMPmbu1khFj8KwY', 'FR12345678901', '45 Rue de l''Innovation, 75001 Paris', '2024-12-31 00:00:00.000000', 'FR7612345678901234567890123', ' Solutions Innovantes S.A.S.', 'Francia', 'Île-de-France', 'www.solutionsinnovantes.fr', 2);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (4, true, '94oUchPLl6X3PrHVcizFqVlKJgDMN1FB', 'B98765432', 'Calle Reforma 1234, Piso 6, 01000', '2024-12-31 00:00:00.000000', 'SZ9876543210123456789012', 'Consultoría Global S.A.', 'Suiza', 'Ticcino', 'www.con-glabal.com', 9);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (10, true, 'NNWPnntXEfYl1B3Yw0p5aDksWxS4XxBl', 'FR79330046467', '47 Rue de Saint Jean, 75003 Lyon', '2025-01-11 13:12:52.381806', 'FR1420041010050500013M02606', 'Renault', 'Francia', 'Lyon', 'www.renault.es', 2);
INSERT INTO public.comercios (id, activo, api_key, cif, direccion, fecha_alta, iban, nombre, pais, provincia, url_back, pais_id) VALUES (2, true, '5sgzky466E8ZKyMzdmjNdLLx7vHAfDXA', '4435323A', 'Calle de la verga 22', '2024-12-30 00:00:00.000000', '33334we', 'CocaCola', 'Alemania', 'Brixton', 'comerciopaco.com', 3);



INSERT INTO public.personas_contacto (id, email, nombre_contacto, telefono, comercio_id) VALUES (8, 'Jorge@manzana.com', 'Jorge Paredes', '665444321', 9);
INSERT INTO public.personas_contacto (id, email, nombre_contacto, telefono, comercio_id) VALUES (9, 'KJ@renault.com', 'Jean Pierre', '332454765', 10);
INSERT INTO public.personas_contacto (id, email, nombre_contacto, telefono, comercio_id) VALUES (10, 'fernando@cocacola.com', 'Fernando', '654555309', 2);
INSERT INTO public.personas_contacto (id, email, nombre_contacto, telefono, comercio_id) VALUES (2, 'javier.gonzalez@solucionestech.com', 'Javier González', '912345678', 3);
INSERT INTO public.personas_contacto (id, email, nombre_contacto, telefono, comercio_id) VALUES (3, 'mariana.perez@consultoriaglobal.com', 'Mariana Pérez', '555987654', 4);
INSERT INTO public.personas_contacto (id, email, nombre_contacto, telefono, comercio_id) VALUES (4, 'claire.dupont@solutionsinnovantes.fr', 'Claire Dupont', '123456789', 5);
INSERT INTO public.personas_contacto (id, email, nombre_contacto, telefono, comercio_id) VALUES (5, 'james.williams@innovativesolutions.co.uk', 'James Williams', '2012345678', 6);
INSERT INTO public.personas_contacto (id, email, nombre_contacto, telefono, comercio_id) VALUES (6, 'felipe.vargas@creatividad360.com', 'Felipe Vargas', '300654321', 7);
INSERT INTO public.personas_contacto (id, email, nombre_contacto, telefono, comercio_id) VALUES (7, 'anne-marie.dubois@rolex.com', 'Anne-Marie Dubois', '223022222', 8);
INSERT INTO public.personas_contacto (id, email, nombre_contacto, telefono, comercio_id) VALUES (1, 'weewelt@gmail.com', 'Pierre', '123456789', 1);




INSERT INTO public.tipos_usuario (id, nombre) VALUES (1, 'administrador');
INSERT INTO public.tipos_usuario (id, nombre) VALUES (2, 'tecnico');
INSERT INTO public.tipos_usuario (id, nombre) VALUES (3, 'comercio');




INSERT INTO public.usuarios (id, activo, contrasenya, email, nombre, comercio_id, tipo_id, fecha_alta) VALUES (4, true, '$2a$10$cxFF5JeL5pPPd0eCJdMdPOptm6v663cN96a.9hybU61MTGTFJ2qCK', 'ee@ee', 'ee', 2, 1, '2025-01-03 20:54:51.250245');
INSERT INTO public.usuarios (id, activo, contrasenya, email, nombre, comercio_id, tipo_id, fecha_alta) VALUES (5, false, '$2a$10$StFlpJJumiMMzld1radOW.032pf/3y.nh9LCpLOMbaHdhaeKad.z.', 'edu@cocacola.com', 'Eduardo', 2, 2, '2025-01-10 12:12:46.312941');
INSERT INTO public.usuarios (id, activo, contrasenya, email, nombre, comercio_id, tipo_id, fecha_alta) VALUES (6, true, '$2a$10$oJWLdn0sLfsH.ZK/UypF0uF7uFVaf0VJpxY6IPDNp/vzMAVTQw6BW', 'pacxo@dadsf.cio', 'PacoWwwq', 9, 3, '2025-01-10 12:16:02.202836');
INSERT INTO public.usuarios (id, activo, contrasenya, email, nombre, comercio_id, tipo_id, fecha_alta) VALUES (997, false, '$2a$10$uEzYq5xTUFwUgBezRaJNvOr7n88Xt7dV.Ne.qg2Pb1K8WmgBNSgP2', 'admin-default@gmail.com', 'admin-default', 1, 1, '2023-05-06 23:16:23.000000');
INSERT INTO public.usuarios (id, activo, contrasenya, email, nombre, comercio_id, tipo_id, fecha_alta) VALUES (998, false, '$2a$10$r/UwgDJHaNd1iJoKHwh9we3q3YxXQlcHDqSJVzIR00sRtwrlRytfy', 'tecnico-default@gmail.com', 'tecnico-default', 1, 2, '2025-01-03 19:18:13.000000');
INSERT INTO public.usuarios (id, activo, contrasenya, email, nombre, comercio_id, tipo_id, fecha_alta) VALUES (999, false, '$2a$10$SeXSpZ0tRIRkWUf7gBeN1u7ykt7x3n0ndNq5Mc4OLlwkQAuOb3SRa', 'comercio-default@gmail.com', 'comercio-default', 1, 3, '2025-01-03 14:18:17.000000');



