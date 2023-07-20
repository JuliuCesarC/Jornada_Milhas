package com.apiJornada.Milhas.domain.destination;

import java.io.IOException;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "destination")
@Entity(name = "Destination")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Destination {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private byte[] picture;

  private String name;

  private BigDecimal price;

  private Boolean active = true;

  public Destination(CreateDestinationDto dto) throws IOException {
    this.picture = dto.picture().getBytes();
    this.name = dto.name();
    this.price = dto.price();
  }

  public void update(UpdateDestinationDto upDto) throws IOException {
    this.picture = upDto.picture().getBytes();
    this.name = upDto.name();
    this.price = upDto.price();
  }

  public void disable(){
    this.active = false;
  }
}
