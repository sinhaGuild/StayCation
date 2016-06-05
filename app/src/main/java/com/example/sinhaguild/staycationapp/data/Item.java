
package com.example.sinhaguild.staycationapp.data;

import java.util.HashMap;
import java.util.Map;

public class Item {

    private String id;
    private int createdAt;
    private String prefix;
    private String suffix;
    private int width;
    private int height;
    private String visibility;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The createdAt
     */
    public int getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     *     The createdAt
     */
    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * 
     * @param prefix
     *     The prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * 
     * @return
     *     The suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * 
     * @param suffix
     *     The suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * 
     * @return
     *     The width
     */
    public int getWidth() {
        return width;
    }

    /**
     * 
     * @param width
     *     The width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * 
     * @return
     *     The height
     */
    public int getHeight() {
        return height;
    }

    /**
     * 
     * @param height
     *     The height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * 
     * @return
     *     The visibility
     */
    public String getVisibility() {
        return visibility;
    }

    /**
     * 
     * @param visibility
     *     The visibility
     */
    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
