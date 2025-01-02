-- Ajuste unificado de secuencias:
SELECT setval('public.parametros_id_seq', COALESCE((SELECT MAX(id) FROM public.parametros), 0) + 1, false);
SELECT setval('public.paises_id_seq', COALESCE((SELECT MAX(id) FROM public.paises), 0) + 1, false);
SELECT setval('public.comercios_id_seq', COALESCE((SELECT MAX(id) FROM public.comercios), 0) + 1, false);
SELECT setval('public.personas_contacto_id_seq', COALESCE((SELECT MAX(id) FROM public.personas_contacto), 0) + 1, false);
SELECT setval('public.estados_incidencia_id_seq', COALESCE((SELECT MAX(id) FROM public.estados_incidencia), 0) + 1, false);
SELECT setval('public.tarjetas_pago_id_seq', COALESCE((SELECT MAX(id) FROM public.tarjetas_pago), 0) + 1, false);
SELECT setval('public.estados_pago_id_seq', COALESCE((SELECT MAX(id) FROM public.estados_pago), 0) + 1, false);
SELECT setval('public.pagos_id_seq', COALESCE((SELECT MAX(id) FROM public.pagos), 0) + 1, false);
SELECT setval('public.tipos_usuario_id_seq', COALESCE((SELECT MAX(id) FROM public.tipos_usuario), 0) + 1, false);
SELECT setval('public.incidencias_id_seq', COALESCE((SELECT MAX(id) FROM public.incidencias), 0) + 1, false);
SELECT setval('public.usuarios_id_seq', COALESCE((SELECT MAX(id) FROM public.usuarios), 0) + 1, false);
SELECT setval('public.valoraciones_tecnico_id_seq', COALESCE((SELECT MAX(id) FROM public.valoraciones_tecnico), 0) + 1, false);
SELECT setval('public.mensajes_id_seq', COALESCE((SELECT MAX(id) FROM public.mensajes), 0) + 1, false);
