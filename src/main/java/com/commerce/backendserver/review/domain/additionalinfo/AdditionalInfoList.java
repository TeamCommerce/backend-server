package com.commerce.backendserver.review.domain.additionalinfo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static java.util.Comparator.*;
import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

import com.commerce.backendserver.review.domain.Review;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class AdditionalInfoList {

    @OneToMany(
            mappedBy = "review",
            cascade = PERSIST,
            orphanRemoval = true)
    @OnDelete(action = CASCADE)
    private final List<AdditionalInfo> list = new ArrayList<>();

    ////== Construct Method ==//
    @Builder
    private AdditionalInfoList(
        List<AdditionalInfo> additionalInfoList,
        Review review
    ) {
        applyAdditionalInfo(additionalInfoList, review);
        sortByInfoName();
        this.list.addAll(additionalInfoList);
    }

    public static AdditionalInfoList of(
        List<AdditionalInfo> additionalInfoList,
        Review review
    ) {
        return AdditionalInfoList.builder()
            .additionalInfoList(additionalInfoList)
            .review(review)
            .build();
    }

    private void applyAdditionalInfo(
        List<AdditionalInfo> additionalInfoList,
        Review review
    ) {
        additionalInfoList.forEach(additionalInfo -> additionalInfo.registerReview(review));
        this.list.addAll(additionalInfoList);
    }

    private void sortByInfoName() {
        this.list.sort(comparingInt(info -> info.getInfoName().getOrder()));
    }
}
