package com.faltasproject.adapters.jpa.clases.persistence;

import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.faltasproject.domain.exceptions.NotFoundException;

@SpringBootTest
public class MateriaPersistanceJPATest {
	
	@Autowired
	MateriaPersistenceJPA materiaPersistenceJPA;
	
    @Test
    void testReadNotFound() {
        assertThrows(NotFoundException.class, () -> this.materiaPersistenceJPA.readByDni("00"));
    }

}
