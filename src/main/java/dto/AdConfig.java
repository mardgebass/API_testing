package dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "safeFlags",
    "highRiskFlags",
    "unsafeFlags",
    "wallUnsafeFlags",
    "showsAds"
})
@Generated("jsonschema2pojo")
public class AdConfig implements Serializable
{

    @JsonProperty("safeFlags")
    private List<String> safeFlags = null;
    @JsonProperty("highRiskFlags")
    private List<Object> highRiskFlags = null;
    @JsonProperty("unsafeFlags")
    private List<String> unsafeFlags = null;
    @JsonProperty("wallUnsafeFlags")
    private List<Object> wallUnsafeFlags = null;
    @JsonProperty("showsAds")
    private Boolean showsAds;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = -6469043134506822066L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AdConfig() {
    }

    /**
     * 
     * @param showsAds
     * @param unsafeFlags
     * @param wallUnsafeFlags
     * @param safeFlags
     * @param highRiskFlags
     */
    public AdConfig(List<String> safeFlags, List<Object> highRiskFlags, List<String> unsafeFlags, List<Object> wallUnsafeFlags, Boolean showsAds) {
        super();
        this.safeFlags = safeFlags;
        this.highRiskFlags = highRiskFlags;
        this.unsafeFlags = unsafeFlags;
        this.wallUnsafeFlags = wallUnsafeFlags;
        this.showsAds = showsAds;
    }

    @JsonProperty("safeFlags")
    public List<String> getSafeFlags() {
        return safeFlags;
    }

    @JsonProperty("safeFlags")
    public void setSafeFlags(List<String> safeFlags) {
        this.safeFlags = safeFlags;
    }

    public AdConfig withSafeFlags(List<String> safeFlags) {
        this.safeFlags = safeFlags;
        return this;
    }

    @JsonProperty("highRiskFlags")
    public List<Object> getHighRiskFlags() {
        return highRiskFlags;
    }

    @JsonProperty("highRiskFlags")
    public void setHighRiskFlags(List<Object> highRiskFlags) {
        this.highRiskFlags = highRiskFlags;
    }

    public AdConfig withHighRiskFlags(List<Object> highRiskFlags) {
        this.highRiskFlags = highRiskFlags;
        return this;
    }

    @JsonProperty("unsafeFlags")
    public List<String> getUnsafeFlags() {
        return unsafeFlags;
    }

    @JsonProperty("unsafeFlags")
    public void setUnsafeFlags(List<String> unsafeFlags) {
        this.unsafeFlags = unsafeFlags;
    }

    public AdConfig withUnsafeFlags(List<String> unsafeFlags) {
        this.unsafeFlags = unsafeFlags;
        return this;
    }

    @JsonProperty("wallUnsafeFlags")
    public List<Object> getWallUnsafeFlags() {
        return wallUnsafeFlags;
    }

    @JsonProperty("wallUnsafeFlags")
    public void setWallUnsafeFlags(List<Object> wallUnsafeFlags) {
        this.wallUnsafeFlags = wallUnsafeFlags;
    }

    public AdConfig withWallUnsafeFlags(List<Object> wallUnsafeFlags) {
        this.wallUnsafeFlags = wallUnsafeFlags;
        return this;
    }

    @JsonProperty("showsAds")
    public Boolean getShowsAds() {
        return showsAds;
    }

    @JsonProperty("showsAds")
    public void setShowsAds(Boolean showsAds) {
        this.showsAds = showsAds;
    }

    public AdConfig withShowsAds(Boolean showsAds) {
        this.showsAds = showsAds;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public AdConfig withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

}
