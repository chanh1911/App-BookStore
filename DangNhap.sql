CREATE DATABASE DangNhap;
use DangNhap;

CREATE TABLE PUser(
UserName VARCHAR(15),
PassWord VARCHAR(15),
NameUer NVARCHAR(15),
DayCreate DATE);

Drop table PUser

Select * from PUser where UserName='chanh1'


INSERT INTO PUser(UserName,PassWord,NameUer,DayCreate)
VALUES
('chanh','chanh',N'Nhóm chanh','2022-11-23');
INSERT INTO PUser(UserName,PassWord,NameUer,DayCreate)
VALUES
('chanh2','chanh1',N'Nhóm 22','2022-11-23');

Select * from PUser

delete from PUser where  UserName='chanh9'
Select Name from PUser where UserName='chanh'

CREATE TABLE NewBook(
NameBook VARCHAR(50),
NameTG VARCHAR(15),
DayCreate DATE);




CREATE TABLE CreateBook(
ID int IDENTITY(1,1) PRIMARY KEY,
NameBook NVARCHAR(150),
NameTG NVARCHAR(15),
IDTG VARCHAR(15),
ViewBook VARCHAR(max),
Tim VARCHAR(max),
Content NVARCHAR(max),
IMG varbinary(max),
DayCreate DATE);

Drop table CreateBook

INSERT INTO CreateBook(NameBook,NameTG,IDTG,ViewBook,Tim,Content,IMG,DayCreate)
values
(?,?,?,?,?,?,?);


Select * from CreateBook

Select *
from CreateBook 
where NameBook like '%noi%'




UPDATE CreateBook 
SET NameBook = N'Hanoi' ,  Content = N'Mua dong', IMG= CONVERT(varbinary,
WHERE ID = 3














Select Top 10 * 
from CreateBook
order by DayCreate DESC

Select Top 10 * 
from CreateBook
order by ViewBook DESC

Select Top 10 * 
from CreateBook
order by Tim DESC


Select * from PUser where UserName='chanh1'


select Sum(isnull(cast(Tim as int),0)) as Tim
from CreateBook
where IDTG='chanh'

Select * from PUser where UserName='chanh'

UPDATE CreateBook 
SET NameBook = N'Hanoi' ,  Content = N'Mua dong'
WHERE ID = '6'

DELETE FROM CreateBook
WHERE ID ='chanh'

SELECT COUNT(ID) as Book
from CreateBook

Select * from CreateBook where IDTG='chanh'

UPDATE CreateBook 
SET ViewBook = 1
WHERE ID = '1'




Select * from PUser where UserName='chanh'

CREATE TABLE LuotThich(
IDBook VARCHAR(15),
UserName VARCHAR(15),
DayCreate DATE);

Select * from LuotThich

INSERT INTO LuotThich(IDBook,UserName,DayCreate)
VALUES
('2','chanh','2022-11-23');


