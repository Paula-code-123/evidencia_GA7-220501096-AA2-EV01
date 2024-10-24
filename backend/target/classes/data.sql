CREATE TABLE lista_reproduccion (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255) NOT NULL
);

CREATE TABLE cancion (
    id BIGINT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    artista VARCHAR(200) NOT NULL,
    album VARCHAR(255) NOT NULL,
    anno INT NOT NULL,
    genero VARCHAR(200) NOT NULL
);

CREATE TABLE cancion_lista_reproduccion (
    cancion_id BIGINT,
    lista_reproduccion_id BIGINT,
    PRIMARY KEY (cancion_id, lista_reproduccion_id),
    FOREIGN KEY (cancion_id) REFERENCES cancion(id),
    FOREIGN KEY (lista_reproduccion_id) REFERENCES lista_reproduccion(id)
);

CREATE TABLE Usuario (
    idUsuario BIGINT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

INSERT INTO Usuario (idUsuario, username, password) 
VALUES (1, 'admin', '$2a$10$tmV0JCUvLqixYOGxWsoOjOpjYiQrddm7jA689/aXw.QDK2zwtDvU.');

-- Insertar canciones
INSERT INTO cancion (id, titulo, artista, album, anno, genero) VALUES 
(1, 'Canción 1', 'Artista 1', 'Álbum 1', 2020, 'Pop'),
(2, 'Canción 2', 'Artista 2', 'Álbum 2', 2021, 'Rock'),
(3, 'Canción 3', 'Artista 3', 'Álbum 3', 2019, 'Jazz'),
(4, 'Canción 4', 'Artista 4', 'Álbum 4', 2018, 'Pop'),
(5, 'Canción 5', 'Artista 5', 'Álbum 5', 2019, 'Electrónica'),
(6, 'Canción 6', 'Artista 6', 'Álbum 6', 2018, 'Balada'),
(7, 'Canción 7', 'Artista 7', 'Álbum 7', 2010, 'Balada'),
(8, 'Canción 8', 'Artista 8', 'Álbum 8', 2016, 'Salsa'),
(9, 'Canción 9', 'Artista 9', 'Álbum 9', 2017, 'Salsa'),
(10, 'Canción 10', 'Artista 10', 'Álbum 10', 2010, 'Rock'),
(11, 'Canción 11', 'Artista 11', 'Álbum 11', 2011, 'Rock');
 