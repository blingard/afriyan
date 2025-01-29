package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.Certificates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICertificatesRepository extends JpaRepository<Certificates, Long> {
    List<Certificates> findCertificatesByStatusIsTrue();
}
