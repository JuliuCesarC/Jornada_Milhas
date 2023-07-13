package com.apiJornada.Milhas.domain.testimonial;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TestimonialRepository extends JpaRepository<Testimonial, Long> {

  Page<ListTestimonialDto> findAllByActiveTrue(Pageable pagination);

  Testimonial findByIdAndActiveTrue(Long id);

    @Query("""
          select t from Testimonial t
          where t.active = true
          order by rand()
          limit 3
      """)
  List<ListTestimonialDto> findRandomTestimonial();

}
