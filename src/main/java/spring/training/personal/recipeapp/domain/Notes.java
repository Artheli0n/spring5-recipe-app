package spring.training.personal.recipeapp.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "notes")
    private Recipe recipe;

    // use this annotation for large objects (it creates a CLOB as this is a String) to have higher storage space allocated
    // (size depends on the database, often in the gigabytes)
    @Lob
    private String recipeNotes;

}
