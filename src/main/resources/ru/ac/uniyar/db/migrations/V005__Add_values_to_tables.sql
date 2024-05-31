INSERT INTO CITY(NAME) VALUES('Ярославль');
INSERT INTO CITY(NAME) VALUES('Москва');
INSERT INTO CITY(NAME) VALUES('Кострома');
INSERT INTO CITY(NAME) VALUES('Екатеринбург');
INSERT INTO CITY(NAME) VALUES('Рыбинск');
INSERT INTO CITY(NAME) VALUES('Тутаев');
INSERT INTO CITY(NAME) VALUES('Данилов');
INSERT INTO CITY(NAME) VALUES('Углич');
INSERT INTO CITY(NAME) VALUES('Санкт-Петербург');
INSERT INTO CITY(NAME) VALUES('Любим');
INSERT INTO CITY(NAME) VALUES('Ростов');
INSERT INTO CITY(NAME) VALUES('Смоленск');
INSERT INTO CITY(NAME) VALUES('Воронеж');
INSERT INTO CITY(NAME) VALUES('Гаврилов-Ям');

INSERT INTO CATEGORY(NAME, ADDING_TIME) VALUES('Образование', CURRENT_DATE + CURRENT_TIME);
INSERT INTO CATEGORY(NAME, ADDING_TIME) VALUES('Транспорт', CURRENT_DATE + CURRENT_TIME);
INSERT INTO CATEGORY(NAME, ADDING_TIME) VALUES('Бытовая техника', CURRENT_DATE + CURRENT_TIME);
INSERT INTO CATEGORY(NAME, ADDING_TIME) VALUES('Здравоохранение', CURRENT_DATE + CURRENT_TIME);
INSERT INTO CATEGORY(NAME, ADDING_TIME) VALUES('Парикмахерские', CURRENT_DATE + CURRENT_TIME);
INSERT INTO CATEGORY(NAME, ADDING_TIME) VALUES('Секретарские', CURRENT_DATE + CURRENT_TIME);
INSERT INTO CATEGORY(NAME, ADDING_TIME) VALUES('Разработка', CURRENT_DATE + CURRENT_TIME);
INSERT INTO CATEGORY(NAME, ADDING_TIME) VALUES('Тестирование', CURRENT_DATE + CURRENT_TIME);
INSERT INTO CATEGORY(NAME, ADDING_TIME) VALUES('Менеджмент', CURRENT_DATE + CURRENT_TIME);
INSERT INTO CATEGORY(NAME, ADDING_TIME) VALUES('Консультационные', CURRENT_DATE + CURRENT_TIME);
INSERT INTO CATEGORY(NAME, ADDING_TIME) VALUES('Психологические', CURRENT_DATE + CURRENT_TIME);
INSERT INTO CATEGORY(NAME, ADDING_TIME) VALUES('Юридические', CURRENT_DATE + CURRENT_TIME);
INSERT INTO CATEGORY(NAME, ADDING_TIME) VALUES('Экономические', CURRENT_DATE + CURRENT_TIME);

INSERT INTO SPECIALIST(ADDING_TIME, FULL_NAME, EDUCATION, PHONE)
VALUES(CURRENT_DATE + CURRENT_TIME,
       'Иванов Иван Иванович',
       'Неполное высшее образование (МГУ), бакалавр, 4 курс. Квалификация: программист, специальность - прикладная математика и ' ||
       'информатика. Имеется сертификат о прохождении курса "Unreal Engine C++ разработчик".',
       '89805616332'
);
INSERT INTO SPECIALIST(ADDING_TIME, FULL_NAME, EDUCATION, PHONE)
VALUES(CURRENT_DATE + CURRENT_TIME,
       'Петров Петр Петрович',
       'Высшее образование(ОмГУ), магистр. Квалификация - психолог, преподаватель психологии.',
       '89106439629'
);
INSERT INTO SPECIALIST(ADDING_TIME, FULL_NAME, EDUCATION, PHONE)
VALUES(CURRENT_DATE + CURRENT_TIME,
       'Васильев Василий Васильевич',
       'Два высших образования (ЯрГУ, МГУ). Квалификация - экономика, финансы и кредит; ' ||
       'юриспруденция,  юрист в судебных и правоохранительных органах.',
       '89051452953'
);
INSERT INTO SPECIALIST(ADDING_TIME, FULL_NAME, EDUCATION, PHONE)
VALUES(CURRENT_DATE + CURRENT_TIME,
       'Михайлов Михаил Михайлович',
       'Среднее специальное образование',
       '89208534943'
);

INSERT INTO ANNOUNCEMENT(ADDING_TIME, CATEGORY_NAME, TITLE, CITY_NAME, DESCRIPTION, SPECIALIST_ID)
VALUES(CURRENT_DATE + CURRENT_TIME,
       'Разработка',
       'Unreal Engine разработчик',
       'Москва',
       'Разрабатываю игры на Unreal Engine с использованием C++',
       1
);
INSERT INTO ANNOUNCEMENT(ADDING_TIME, CATEGORY_NAME, TITLE, CITY_NAME, DESCRIPTION, SPECIALIST_ID)
VALUES(CURRENT_DATE + CURRENT_TIME,
       'Разработка',
       'Junior разработчик C++',
       'Москва',
       'Опыт разработки на C++ 2 года, есть опыт работы в команде. Не против получить опыт разработки для мобильных платформ' ||
       ' или перейти работать в эту параллельную айтишную сферу, не боюсь перемен.',
       1
);
INSERT INTO ANNOUNCEMENT(ADDING_TIME, CATEGORY_NAME, TITLE, CITY_NAME, DESCRIPTION, SPECIALIST_ID)
VALUES(CURRENT_DATE + CURRENT_TIME,
       'Психологические',
       'Практикующий психолог, коуч',
       'Рыбинск',
       'Я являюсь экспертом в межличностных отношениях. ' ||
       'Помогаю восстанавливать и создавать семьи полные любви и гармонии, помогаю найти своё предназначение.',
       2
);
INSERT INTO ANNOUNCEMENT(ADDING_TIME, CATEGORY_NAME, TITLE, CITY_NAME, DESCRIPTION, SPECIALIST_ID)
VALUES(CURRENT_DATE + CURRENT_TIME,
       'Юридические',
       'Практикующий юрист',
       'Москва',
       'Имею большой опыт юридической практики. Работаю с делами любой сложности, в различных сферах законодательства. ' ||
       'Я гарантирую: - ответственный и индивидуальный подход к решению ваших вопросов; - лучший способ защиты ваших интересов и права; ' ||
       '- оказание услуг по доступной цене; - анонимность; - вежливость и оперативность. Жду вашего звонка и заранее рад сотрудничеству!',
       3
      );
INSERT INTO ANNOUNCEMENT(ADDING_TIME, CATEGORY_NAME, TITLE, CITY_NAME, DESCRIPTION, SPECIALIST_ID)
VALUES(CURRENT_DATE + CURRENT_TIME,
       'Юридические',
       'Адвокат',
       'Москва',
       'Услуги адвоката для физических и юридических лиц. Опыт более 10 лет. Бесплатная консультация. 92% выигранных дел.',
       3
);
INSERT INTO ANNOUNCEMENT(ADDING_TIME, CATEGORY_NAME, TITLE, CITY_NAME, DESCRIPTION, SPECIALIST_ID)
VALUES(CURRENT_DATE + CURRENT_TIME,
       'Транспорт',
       'Водитель автобуса',
       'Ярославль',
       'Водитель категории B, C, D. Стаж с 1993 года. Исполнителен, пунктуален, не курящий. Основная работа — водитель автобуса.' ||
       ' Такси не предлагать.',
       4
);