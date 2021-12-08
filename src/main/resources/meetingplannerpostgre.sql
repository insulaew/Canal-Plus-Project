/*Users*/
INSERT INTO public.users(utilisateur_id, email, first_name, last_name, password) VALUES (1, 'media.svd@outlook.fr', 'Nicolas', 'Sivignon', 'sudoku13');
INSERT INTO users(utilisateur_id, email, first_name, last_name, password) VALUES (2, 'danisivi26@orange.fr', 'Danièle', 'Cattoen', 'jasmin26');
INSERT INTO users(utilisateur_id, email, first_name, last_name, password) VALUES (3, 'lucas.dubosmichel@alten.com', 'Lucas', 'Dubos', 'michel');

/*Outils libres*/
INSERT INTO public.free_tool(outil_libre_id, type) VALUES (1, 'Pieuvre');
INSERT INTO public.free_tool(outil_libre_id, type) VALUES (2, 'Pieuvre');
INSERT INTO public.free_tool(outil_libre_id, type) VALUES (3, 'Pieuvre');
INSERT INTO public.free_tool(outil_libre_id, type) VALUES (4, 'Pieuvre');
INSERT INTO public.free_tool(outil_libre_id, type) VALUES (5, 'Ecran');
INSERT INTO public.free_tool(outil_libre_id, type) VALUES (6, 'Ecran');
INSERT INTO public.free_tool(outil_libre_id, type) VALUES (7, 'Ecran');
INSERT INTO public.free_tool(outil_libre_id, type) VALUES (8, 'Ecran');
INSERT INTO public.free_tool(outil_libre_id, type) VALUES (9, 'Ecran');
INSERT INTO public.free_tool(outil_libre_id, type) VALUES (10, 'Webcam');
INSERT INTO public.free_tool(outil_libre_id, type) VALUES (11, 'Webcam');
INSERT INTO public.free_tool(outil_libre_id, type) VALUES (12, 'Webcam');
INSERT INTO public.free_tool(outil_libre_id, type) VALUES (13, 'Webcam');
INSERT INTO public.free_tool(outil_libre_id, type) VALUES (14, 'Tableau');
INSERT INTO public.free_tool(outil_libre_id, type) VALUES (15, 'Tableau');

/*Salles*/
INSERT INTO public.room(salle_id, capacity, capacity70) VALUES('E1001', 23, 16);
INSERT INTO public.room(salle_id, capacity, capacity70) VALUES('E1002', 10, 7);
INSERT INTO public.room(salle_id, capacity, capacity70) VALUES('E1003', 8, 5);
INSERT INTO public.room(salle_id, capacity, capacity70) VALUES('E1004', 4, 2);
INSERT INTO public.room(salle_id, capacity, capacity70) VALUES('E2001', 4, 2);
INSERT INTO public.room(salle_id, capacity, capacity70) VALUES('E2002', 15, 10);
INSERT INTO public.room(salle_id, capacity, capacity70) VALUES('E2003', 7, 4);
INSERT INTO public.room(salle_id, capacity, capacity70) VALUES('E2004', 9, 6);
INSERT INTO public.room(salle_id, capacity, capacity70) VALUES('E3001', 13, 9);
INSERT INTO public.room(salle_id, capacity, capacity70) VALUES('E3002', 8, 5);
INSERT INTO public.room(salle_id, capacity, capacity70) VALUES('E3003', 9, 6);
INSERT INTO public.room(salle_id, capacity, capacity70) VALUES('E3004', 4, 2);

/*Outils Salle*/
INSERT INTO public.room_tool(outil_salle_id, type, salle_id) VALUES(1, 'Ecran', 'E1002');
INSERT INTO public.room_tool(outil_salle_id, type, salle_id) VALUES(2, 'Pieuvre', 'E1003');
INSERT INTO public.room_tool(outil_salle_id, type, salle_id) VALUES(3, 'Tableau', 'E1004');
INSERT INTO public.room_tool(outil_salle_id, type, salle_id) VALUES(4, 'Ecran', 'E2002');
INSERT INTO public.room_tool(outil_salle_id, type, salle_id) VALUES(5, 'Webcam', 'E2002');
INSERT INTO public.room_tool(outil_salle_id, type, salle_id) VALUES(6, 'Tableau', 'E2004');
INSERT INTO public.room_tool(outil_salle_id, type, salle_id) VALUES(7, 'Ecran', 'E3001');
INSERT INTO public.room_tool(outil_salle_id, type, salle_id) VALUES(8, 'Webcam', 'E3001');
INSERT INTO public.room_tool(outil_salle_id, type, salle_id) VALUES(9, 'Pieuvre', 'E3001');
INSERT INTO public.room_tool(outil_salle_id, type, salle_id) VALUES(10, 'Ecran', 'E3003');
INSERT INTO public.room_tool(outil_salle_id, type, salle_id) VALUES(11, 'Pieuvre', 'E3003');

/*Réunions*/
INSERT INTO public.meeting(reunion_id, type, start_hour, end_hour, number_of_persons, is_reserved) VALUES(1, 'VC', 9, 10, 8, FALSE);
INSERT INTO public.meeting(reunion_id, type, start_hour, end_hour, number_of_persons, is_reserved) VALUES(2, 'VC', 9, 10, 6, FALSE);
INSERT INTO public.meeting(reunion_id, type, start_hour, end_hour, number_of_persons, is_reserved) VALUES(3, 'RC', 11, 12, 4, FALSE);
INSERT INTO public.meeting(reunion_id, type, start_hour, end_hour, number_of_persons, is_reserved) VALUES(4, 'RS', 11, 12, 2, FALSE);
INSERT INTO public.meeting(reunion_id, type, start_hour, end_hour, number_of_persons, is_reserved) VALUES(5, 'SPEC', 11, 12, 9, FALSE);
INSERT INTO public.meeting(reunion_id, type, start_hour, end_hour, number_of_persons, is_reserved) VALUES(6, 'RC', 9, 10, 7, FALSE);
INSERT INTO public.meeting(reunion_id, type, start_hour, end_hour, number_of_persons, is_reserved) VALUES(7, 'VC', 8, 9, 9, FALSE);
INSERT INTO public.meeting(reunion_id, type, start_hour, end_hour, number_of_persons, is_reserved) VALUES(8, 'SPEC', 8, 9, 10, FALSE);
INSERT INTO public.meeting(reunion_id, type, start_hour, end_hour, number_of_persons, is_reserved) VALUES(9, 'SPEC', 9, 10, 5, FALSE);
INSERT INTO public.meeting(reunion_id, type, start_hour, end_hour, number_of_persons, is_reserved) VALUES(10, 'RS', 9, 10, 4, FALSE);
INSERT INTO public.meeting(reunion_id, type, start_hour, end_hour, number_of_persons, is_reserved) VALUES(11, 'RC', 9, 10, 8, FALSE);
INSERT INTO public.meeting(reunion_id, type, start_hour, end_hour, number_of_persons, is_reserved) VALUES(12, 'VC', 11, 12, 12, FALSE);
INSERT INTO public.meeting(reunion_id, type, start_hour, end_hour, number_of_persons, is_reserved) VALUES(13, 'SPEC', 11, 12, 5, FALSE);
INSERT INTO public.meeting(reunion_id, type, start_hour, end_hour, number_of_persons, is_reserved) VALUES(14, 'VC', 8, 9, 3, FALSE);
INSERT INTO public.meeting(reunion_id, type, start_hour, end_hour, number_of_persons, is_reserved) VALUES(15, 'SPEC', 8, 9, 2, FALSE);
INSERT INTO public.meeting(reunion_id, type, start_hour, end_hour, number_of_persons, is_reserved) VALUES(16, 'VC', 8, 9, 12, FALSE);
INSERT INTO public.meeting(reunion_id, type, start_hour, end_hour, number_of_persons, is_reserved) VALUES(17, 'VC', 10, 11, 6, FALSE);
INSERT INTO public.meeting(reunion_id, type, start_hour, end_hour, number_of_persons, is_reserved) VALUES(18, 'RS', 11, 12, 2, FALSE);
INSERT INTO public.meeting(reunion_id, type, start_hour, end_hour, number_of_persons, is_reserved) VALUES(19, 'RS', 9, 10, 4, FALSE);
INSERT INTO public.meeting(reunion_id, type, start_hour, end_hour, number_of_persons, is_reserved) VALUES(20, 'RC', 9, 10, 7, FALSE);

/*INSERT INTO public.meeting_free_tool (reunion_id, outil_libre_id) VALUES (1, 1);*/
