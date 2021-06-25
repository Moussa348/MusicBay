package com.keita.musicbay.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Producer extends User implements Serializable {
}
