package org.ligot.afriyan.repository;

import org.ligot.afriyan.entities.ForgetPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IForgetPasswordRepository extends JpaRepository<ForgetPassword, Long> {
    Optional<List<ForgetPassword>> findByPhoneAndCodeAndActiveIsTrue(String phone, String code);
    Optional<List<ForgetPassword>> findByPhoneAndActiveIsTrue(String phone);
    Optional<List<ForgetPassword>> findByPhoneAndCreateDateBeforeAndActiveIsTrue(String phone, Date dateCreation);
}
