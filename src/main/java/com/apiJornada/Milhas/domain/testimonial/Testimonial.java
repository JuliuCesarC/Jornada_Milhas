package com.apiJornada.Milhas.domain.testimonial;

import java.io.IOException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "client_testimonial")
@Entity(name = "Testimonial")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Testimonial {

  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private String name;
  
  private byte[] picture;
  
  private String testimonial;

  private Boolean active = true;

  public Testimonial(CreateTestimonialDto createDto) throws IOException {
    this.name = createDto.name();
    this.picture = createDto.picture().getBytes();
    this.testimonial = createDto.testimonial();
  }

  public void update(UpdateTestimonialDto updateDto) throws IOException {
    this.name = updateDto.name();
    this.picture = updateDto.picture().getBytes();
    this.testimonial = updateDto.testimonial();
  }

  public void delete() {
    this.active = false;
  }
}
