package eu.daiad.web.domain.application;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author nkarag
 */
@Entity(name = "dynamic_recommendation_translation")
@Table(schema = "public", name = "dynamic_recommendation_translation")
public class DynamicRecommendationTranslation {

    @Id()
    @Column(name = "id")
    @SequenceGenerator(sequenceName = "dynamic_recommendation_translation_id_seq", name = "dynamic_recommendation_translation_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "dynamic_recommendation_translation_id_seq", strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(name = "dynamic_recommendation_id", nullable = false)
    private int dynamicRecommendationId;

    @Column(name = "locale", columnDefinition = "bpchar", length = 2)
    private String locale;

    @Basic()
    private String title;

    @Basic()
    private String description;

    @Column(name = "image_link")
    private String imageLink;
        
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDynamicRecommendationId() {
        return dynamicRecommendationId;
    }

    public void setDynamicRecommendationId(int dynamicRecommendationId) {
        this.dynamicRecommendationId = dynamicRecommendationId;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
