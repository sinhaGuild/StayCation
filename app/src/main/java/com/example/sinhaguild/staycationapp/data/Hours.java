
package com.example.sinhaguild.staycationapp.data;

import java.util.HashMap;
import java.util.Map;

public class Hours {

    private String status;
    private boolean isOpen;
    private boolean isLocalHoliday;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The isOpen
     */
    public boolean isIsOpen() {
        return isOpen;
    }

    /**
     * 
     * @param isOpen
     *     The isOpen
     */
    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    /**
     * 
     * @return
     *     The isLocalHoliday
     */
    public boolean isIsLocalHoliday() {
        return isLocalHoliday;
    }

    /**
     * 
     * @param isLocalHoliday
     *     The isLocalHoliday
     */
    public void setIsLocalHoliday(boolean isLocalHoliday) {
        this.isLocalHoliday = isLocalHoliday;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
