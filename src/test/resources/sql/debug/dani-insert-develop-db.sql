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



INSERT INTO public.tipos_usuario (id, nombre) VALUES (1, 'administrador');
INSERT INTO public.tipos_usuario (id, nombre) VALUES (2, 'tecnico');
INSERT INTO public.tipos_usuario (id, nombre) VALUES (3, 'comercio');




INSERT INTO public.usuarios (id, activo, contrasenya, email, nombre, comercio_id, tipo_id) VALUES (2, true, '$2a$10$r/UwgDJHaNd1iJoKHwh9we3q3YxXQlcHDqSJVzIR00sRtwrlRytfy', 'tecnico-default@gmail.com', 'tecnico-default', 1, 2);
INSERT INTO public.usuarios (id, activo, contrasenya, email, nombre, comercio_id, tipo_id) VALUES (3, true, '$2a$10$SeXSpZ0tRIRkWUf7gBeN1u7ykt7x3n0ndNq5Mc4OLlwkQAuOb3SRa', 'comercio-default@gmail.com', 'comercio-default', 1, 3);
INSERT INTO public.usuarios (id, activo, contrasenya, email, nombre, comercio_id, tipo_id) VALUES (1, true, '$2a$10$uEzYq5xTUFwUgBezRaJNvOr7n88Xt7dV.Ne.qg2Pb1K8WmgBNSgP2', 'admin-default@gmail.com', 'admin-default', 1, 1);


