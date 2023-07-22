package com.apiJornada.Milhas.domain.destination;

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

  private byte[] imageOne;
  
  private byte[] imageTwo;

  private String name;

  private String target;

  private String description;

  private Boolean active = true;

  public Destination(CreateDestinationDto dto) throws IOException {
    this.imageOne = dto.imageOne().getBytes();
    this.imageTwo = dto.imageTwo().getBytes();
    this.name = dto.name();
    this.target = dto.target();
    this.description = dto.description();
  }

  public void update(UpdateDestinationDto upDto) throws IOException {
    if(upDto.imageOne() != null){
      this.imageOne = upDto.imageOne().getBytes();
    }
    if(upDto.imageTwo() != null){
      this.imageTwo = upDto.imageTwo().getBytes();
    }
    if(upDto.name() != null){
      this.name = upDto.name();
    }
    if(upDto.target() != null){
      this.target = upDto.target();
    }
    if(upDto.description() != null){
      this.description = upDto.description();
    }
  }

  public void disable(){
    this.active = false;
  }
}
