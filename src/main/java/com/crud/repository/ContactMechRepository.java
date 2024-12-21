package com.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.crud.entity.ContactMech;

public interface ContactMechRepository extends JpaRepository<ContactMech, Long> {}
