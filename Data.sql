
CREATE TABLE films (
    filmID int PRIMARY KEY, 
    title VARCHAR(255) NOT NULL,
    director VARCHAR(255) NOT NULL,
    releaseYear INT not NULL,
    length INT NOT NULL,
    mediaType VARCHAR(3) not null,
    original BOOLEAN,
    coverPath VARCHAR(255),
    lendCount int,
    borrowed boolean
    );

INSERT INTO films VALUES(0,'Eredet','Critopher Nolan', 2010, 148, 'DVD', true, 'eredetCover.jpg', 0, false);
INSERT INTO films VALUES(1,'Aljas nyolcas','Quentin Tarantino', 2015, 182, 'DVD', false, 'aljasCover.jpg', 2, true);
INSERT INTO films VALUES(2,'A remény rabjai','Frank Darabont', 1994, 142, 'VHS', true, 'remenyCover.jpg', 1, false);
INSERT INTO films VALUES(3,'Bosszúállók','Joss Whedon', 2012, 143, 'DVD', true, 'bosszualokCover.jpg', 0, false);
INSERT INTO films VALUES(4,'Titanic','James Cameron', 1997, 194, 'DVD', true, 'titanicCover.jpg', 2, true);
INSERT INTO films VALUES(5,'Baby Driver','Edgar Wright',2017, 112, 'DVD', true, 'babyDriverCover.jpg', 0, false);
INSERT INTO films VALUES(6,'Gyűrűk Ura Ket Torony','Peter Jackson',2001, 240, 'DVD', true, 'gyurukura2Cover.jpg', 0, false);
INSERT INTO films VALUES(7,'Gyűrűk Ura Gyuru Szovetsege','Peter Jackson',2002, 240, 'DVD', true, 'gyurukuraCover.jpg', 0, false);
INSERT INTO films VALUES(8,'Gyűrűk Ura Trilogia','Peter Jackson',2006, 240, 'DVD', true, 'gyurukuratrilogiaCover.jpg', 1, false);
INSERT INTO films VALUES(9,'Kártyavár season1','Beau Willimon', 2013, 51, 'DVD', true, 'houseofcardsCover.jpg', 3, false);
INSERT INTO films VALUES(10,'Kártyavár season2','Beau Willimon', 2014, 51, 'DVD', true, 'houseofcards2Cover.jpg', 1, false);
INSERT INTO films VALUES(11,'Kártyavár season3','Beau Willimon', 2015, 51, 'DVD', true, 'houseofcards3Cover.jpg', 1, false);


CREATE TABLE roles (
    filmID INT,
    starName VARCHAR(255)
);

INSERT INTO roles VALUES(0, 'Leonardo DiCaprio');
INSERT INTO roles VALUES(1, 'Kurt Russel');
INSERT INTO roles VALUES(1, 'Samuel L. Jackson');
INSERT INTO roles VALUES(2, 'Morgan Freeman');
INSERT INTO roles VALUES(2, 'Tim Robbins');
INSERT INTO roles VALUES(3, 'Robert Downey Jr.');
INSERT INTO roles VALUES(3, 'Chris Evans');
INSERT INTO roles VALUES(3, 'Mark Ruffalo');
INSERT INTO roles VALUES(3, 'Chris Hemsworth');
INSERT INTO roles VALUES(4, 'Leonardo DiCaprio');
INSERT INTO roles VALUES(4, 'Kate Winslet');
INSERT INTO roles VALUES(5,'Ansel Elgort');
INSERT INTO roles VALUES(5,'Kevin Spacey');
INSERT INTO roles VALUES(6,'Elijah Wood');
INSERT INTO roles VALUES(6,'Ian McKellen');
INSERT INTO roles VALUES(7,'Ian McKellen');
INSERT INTO roles VALUES(7,'Elijah Wood');
INSERT INTO roles VALUES(7,'Orlando Bloom');
INSERT INTO roles VALUES(8,'Elijah Wood');
INSERT INTO roles VALUES(8,'Orlando Bloom');
INSERT INTO roles VALUES(9,'Kevin Spacey');
INSERT INTO roles VALUES(9,'Robin Wright');
INSERT INTO roles VALUES(10,'Kevin Spacey');
INSERT INTO roles VALUES(10,'Robin Wright');
INSERT INTO roles VALUES(11,'Kevin Spacey');
INSERT INTO roles VALUES(11,'Robin Wright');


create table lendings (
    lendID int primary key,
    personName varchar(255),
    loanedFilmId int not null references films(filmID),
    time date,
    backTime date
);

insert into lendings values(0,'Kovács János', 1, '2018-04-24', '2018-06-01');
insert into lendings values(1,'Nemes Béla', 4, '2018-05-14', '2018-06-07');