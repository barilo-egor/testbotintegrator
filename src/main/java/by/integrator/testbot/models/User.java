package by.integrator.testbot.models;

import lombok.*;

import javax.persistence.Entity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User extends BaseEntity{
    private Integer telegramId;
    private String fullName;
    private Integer age;
    private String address;
}
