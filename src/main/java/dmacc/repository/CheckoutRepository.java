package dmacc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dmacc.beans.Checkout;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

}