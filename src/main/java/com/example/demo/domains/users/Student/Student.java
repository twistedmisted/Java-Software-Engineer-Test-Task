package com.example.demo.domains.users.Student;

import com.example.demo.domains.Studio;
import com.example.demo.domains.lessons.Attendance;
import com.example.demo.domains.lessons.LessonCredit;
import com.example.demo.domains.users.Student.accounts.Account;
import com.example.demo.domains.users.Teacher;
import com.example.demo.security.user.JwtUser;
import com.example.demo.security.user.Position;
import com.example.demo.security.user.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties(value = {"role", "canceledLessons", "authorities", "lessons", "events", "lessonCredits", "coachings", "competitionMember", "attendance"}, allowGetters = true)
public class Student extends JwtUser {

    {
        this.setRole(Set.of(Role.ROLE_USER));
        this.setPosition(Position.Student);
    }

    public Student(Long id) {
        super(id);
    }

    public Student(JwtUser jwtUser) {
        super(jwtUser);
    }

    @Column(name = "phone_number")
    String phoneNumber;
    String telegram;
    @Column(name = "work_address")
    String workAddress;
    @Column(name = "home_address")
    String homeAddress;
    @Column(name = "dance_level")
    String danceLevel;

    @ManyToOne
    @JsonIncludeProperties(value = {"studioName", "abbreviation", "id", "scheduleSlots"})
    Studio studio;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"student"})
    @JsonIgnore
    private Set<Attendance> attendance;

    @Transient
    public int getLessonCount() {
        // Calculate the lesson count based on related lesson data
        // The method automatically creates field called lessonCount
        if (attendance != null) {
            return attendance.size();
        } else {
            return 0;
        }
    }



    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"transactions"})
    Account account = new Account();

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"student"})
    Set<LessonCredit> lessonCredits;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_teacher",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    @JsonIncludeProperties(value = {"firstName", "lastName", "id"})
    Set<Teacher> teachers;

    String comment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        if (!super.equals(o)) return false;

        if (getPhoneNumber() != null ? !getPhoneNumber().equals(student.getPhoneNumber()) : student.getPhoneNumber() != null)
            return false;
        if (getTelegram() != null ? !getTelegram().equals(student.getTelegram()) : student.getTelegram() != null)
            return false;
        if (getWorkAddress() != null ? !getWorkAddress().equals(student.getWorkAddress()) : student.getWorkAddress() != null)
            return false;
        if (getHomeAddress() != null ? !getHomeAddress().equals(student.getHomeAddress()) : student.getHomeAddress() != null)
            return false;
        if (getDanceLevel() != null ? !getDanceLevel().equals(student.getDanceLevel()) : student.getDanceLevel() != null)
            return false;
        return getComment() != null ? getComment().equals(student.getComment()) : student.getComment() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getTelegram() != null ? getTelegram().hashCode() : 0);
        result = 31 * result + (getWorkAddress() != null ? getWorkAddress().hashCode() : 0);
        result = 31 * result + (getHomeAddress() != null ? getHomeAddress().hashCode() : 0);
        result = 31 * result + (getDanceLevel() != null ? getDanceLevel().hashCode() : 0);
        result = 31 * result + (getComment() != null ? getComment().hashCode() : 0);
        return result;
    }
}
