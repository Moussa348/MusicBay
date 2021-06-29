package com.keita.musicbay.model;

import lombok.Builder;
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
    private boolean agreementSigned;

    public Agreement(){}

    @Builder
    public Agreement(Long id, String fileName, byte[] data, Track track, Transaction transaction, boolean agreementSigned) {
        super(id, fileName, data, track);
        this.transaction = transaction;
        this.agreementSigned = agreementSigned;
    }
}
