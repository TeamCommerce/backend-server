package com.commerce.backendserver.review.domain.additionalinfo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.OnDelete;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class AdditionalInfoList {

    @JsonIgnore
    @OneToMany(
            mappedBy = "review",
            cascade = PERSIST,
            orphanRemoval = true)
    @OnDelete(action = CASCADE)
    private final List<AdditionalInfo> list = new ArrayList<>();
}
