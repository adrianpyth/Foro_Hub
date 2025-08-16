CREATE TABLE topicos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    status VARCHAR(100) NOT NULL,
    autor_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_topicos_autor_id FOREIGN KEY (autor_id) REFERENCES usuarios(id),
    CONSTRAINT fk_topicos_curso_id FOREIGN KEY (curso_id) REFERENCES cursos(id),
    -- ✅ CLAVE ÚNICA CORREGIDA: Usar longitud específica para TEXT
    UNIQUE KEY unique_titulo_mensaje (titulo, mensaje(500))
);