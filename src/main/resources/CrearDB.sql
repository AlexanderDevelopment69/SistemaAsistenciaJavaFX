USE master;
GO

-- Desconectar a todos los usuarios y anular transacciones
ALTER DATABASE ControlAsistencia
    SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
GO

-- Eliminar la base de datos
DROP DATABASE ControlAsistencia;



CREATE DATABASE ControlAsistencia;
GO

USE ControlAsistencia;
GO

-- Crear la tabla "Personal"
CREATE TABLE Personal
(
    codigo    INT IDENTITY (1,1) PRIMARY KEY,
    nombres   VARCHAR(50),
    apellidos VARCHAR(50),
    sexo      VARCHAR(10),
    cargo     VARCHAR(50)
);
GO

-- Crear la tabla "Asistencia"
CREATE TABLE Asistencia
(
    codigo         INT IDENTITY (1,1) PRIMARY KEY,
    personalCodigo INT FOREIGN KEY REFERENCES Personal (codigo),
    asistio        BIT,
    fecha          DATETIME,
    detalle        VARCHAR(255)
);
GO