package com.example.demo.domains.lessons;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;


@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class PlainLesson extends Lesson{
}
