﻿INSERT INTO question (id_question, id_parent, is_list, is_need_answer, id_entity, text_question)
VALUES (01, 00, FALSE, FALSE, 'DEFAULT',
        '<b>Приветствуем в нашем виртуальном помощнике!</b>  Выберите интересующий пункт:'),
       (02, 01, FALSE, FALSE, 'DEFAULT', 'Приют для кошек'),
       (03, 01, FALSE, FALSE, 'DEFAULT', 'Приют для собак'),
       (04, 02, FALSE, FALSE, 'DEFAULT', 'Узнать информацию о приюте'),
       (05, 02, FALSE, FALSE, 'DEFAULT', 'Как взять животное из приюта'),
       (06, 02, TRUE, TRUE, 'DEFAULT', 'Прислать отчет о питомце:'),
       (07, 02, TRUE, FALSE, 'DEFAULT', 'Позвать волонтера'),
       (08, 03, TRUE, FALSE, 'DEFAULT', 'Наш приют. Рассказ о приюте'),
       (09, 03, TRUE, FALSE, 'DEFAULT', 'Расписание работы приюта, адрес, схема проезда'),
       (10, 03, TRUE, FALSE, 'DEFAULT', 'Контактные данные охраны для оформления пропуска на машину.'),
       (11, 03, TRUE, FALSE, 'DEFAULT', 'Общие рекомендации о технике безопасности на территории приюта'),
       (12, 03, TRUE, TRUE, 'DEFAULT', 'Контактные данные клиента для связи'),
       (13, 03, TRUE, FALSE, 'DEFAULT', 'Позвать волонтера'),
       (14, 04, TRUE, FALSE, 'DEFAULT', 'Правила знакомства с животным до того, как забрать его из приюта'),
       (15, 04, TRUE, FALSE, 'DEFAULT', 'Список необходимых документов'),
       (16, 04, TRUE, FALSE, 'DEFAULT', 'Список рекомендаций по транспортировке животного'),
       (17, 04, TRUE, FALSE, 'DEFAULT', 'Список рекомендаций по обустройству дома для щенка/котенка'),
       (18, 04, TRUE, FALSE, 'DEFAULT', 'Список рекомендаций по обустройству дома для взрослого животного'),
       (19, 04, TRUE, FALSE, 'DEFAULT',
        'Список рекомендаций по обустройству дома для животного с ограниченными возможностями'),
       (20, 04, TRUE, FALSE, 'DOG', 'Советы кинолога по первичному общению с собакой'),
       (21, 04, TRUE, FALSE, 'DOG', 'Рекомендации по проверенным кинологам '),
       (22, 04, TRUE, FALSE, 'DEFAULT', 'Список причин отказа в заборе животного из приюта'),
       (23, 04, TRUE, TRUE, 'DEFAULT', 'Контактные данные для связи'),
       (24, 04, TRUE, FALSE, 'DEFAULT', 'Позвать волонтера');


