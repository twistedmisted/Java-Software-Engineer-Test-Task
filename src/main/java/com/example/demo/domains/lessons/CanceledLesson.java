package com.example.demo.domains.lessons;

import com.example.demo.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.sql.Timestamp;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CanceledLesson extends BaseEntity {
	@OneToOne(fetch = FetchType.LAZY)
	@JsonIgnore
    Attendance attendance;
	@Column(name = "reason", columnDefinition = "TEXT")
	String reason;
	@CreationTimestamp
	@Column(updatable = false)
	Timestamp cancellationDate;
}
