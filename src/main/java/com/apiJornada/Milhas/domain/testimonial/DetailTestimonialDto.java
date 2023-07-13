package com.apiJornada.Milhas.domain.testimonial;

public record DetailTestimonialDto(
    Long id,
    String name,
    byte[] picture,
    String testimonial,
    Boolean active) {
  public DetailTestimonialDto(Testimonial entity) {
    this(entity.getId(), entity.getName(), entity.getPicture(), entity.getTestimonial(), entity.getActive());
  }
}
