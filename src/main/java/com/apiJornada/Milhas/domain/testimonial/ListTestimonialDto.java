package com.apiJornada.Milhas.domain.testimonial;

public record ListTestimonialDto(
    Long id,
    String name,
    byte[] picture,
    String testimonial) {
  public ListTestimonialDto(Testimonial entity) {
    this(entity.getId(), entity.getName(), entity.getPicture(), entity.getTestimonial());
  }
}
