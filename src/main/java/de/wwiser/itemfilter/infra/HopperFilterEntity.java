package de.wwiser.itemfilter.infra;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "hopper_filters")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HopperFilterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(columnDefinition = "BLOB")
    byte[] items;

    boolean inverted;

    UUID worldId;
    double coordinateX;
    double coordinateY;
    double coordinateZ;

    boolean enabled;

}
