package com.aline.aline.entities;

import com.aline.aline.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "User")
public class User implements UserDetails{
    @MongoId(value = FieldType.OBJECT_ID)
    @JsonIgnore
    private ObjectId id;

    private String name;

    @Email(message = "Please enter a valid email address.")
    @NotEmpty(message = "Email cannot be empty.")
    @Indexed(unique = true)
    private String email;

    @NotEmpty(message = "Password cannot be empty.")
    @Length(max = 15, message = "Password should not exceed  15 characters long.")
    @Length(min = 5, message = "Password must be at least 5 characters long.")
    private String password;

    private List<UserRole> role;

    @JsonIgnore
    private Boolean status = true;

    @CreatedDate
    @JsonIgnore
    private Date createdOn;

    @LastModifiedDate
    @JsonIgnore
    private Date updatedOn;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.stream()
                .map(x -> new SimpleGrantedAuthority(x.name()))
                .collect(Collectors.toList());
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
