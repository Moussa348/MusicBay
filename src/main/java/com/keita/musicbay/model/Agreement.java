package com.keita.musicbay.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Agreement extends File implements Serializable {

    @ManyToOne
    private Transaction transaction;

}
