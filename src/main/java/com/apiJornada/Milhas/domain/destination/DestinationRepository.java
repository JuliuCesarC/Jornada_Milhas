package com.apiJornada.Milhas.domain.destination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepository extends JpaRepository<Destination, Long>{

	Page<ListDestinationDto> findAllByActiveTrue(Pageable pagination);

	Destination findByIdAndActiveTrue(Long id);
  
}
