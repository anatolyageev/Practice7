//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.02.24 at 11:28:45 AM EET 
//


package ua.nure.ageev.practice7.entity;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Performance_characteristics complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Performance_characteristics">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="range" type="{http://nure.ua/Firearms/p7}Range"/>
 *         &lt;element name="target_ange" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="clip" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="optician" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Performance_characteristics", propOrder = {
    "range",
    "targetAnge",
    "clip",
    "optician"
})
public class PerformanceCharacteristics {

    @XmlElement(required = true)
    protected Range range;
    @XmlElement(name = "target_ange", required = true)
    protected BigInteger targetAnge;
    protected boolean clip;
    protected boolean optician;

    /**
     * Gets the value of the range property.
     * 
     * @return
     *     possible object is
     *     {@link Range }
     *     
     */
    public Range getRange() {
        return range;
    }

    /**
     * Sets the value of the range property.
     * 
     * @param value
     *     allowed object is
     *     {@link Range }
     *     
     */
    public void setRange(Range value) {
        this.range = value;
    }

    /**
     * Gets the value of the targetAnge property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTargetAnge() {
        return targetAnge;
    }

    /**
     * Sets the value of the targetAnge property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTargetAnge(BigInteger value) {
        this.targetAnge = value;
    }

    /**
     * Gets the value of the clip property.
     * 
     */
    public boolean isClip() {
        return clip;
    }

    /**
     * Sets the value of the clip property.
     * 
     */
    public void setClip(boolean value) {
        this.clip = value;
    }

    /**
     * Gets the value of the optician property.
     * 
     */
    public boolean isOptician() {
        return optician;
    }

    /**
     * Sets the value of the optician property.
     * 
     */
    public void setOptician(boolean value) {
        this.optician = value;
    }

    @Override
    public String toString() {
        return
                "range=" + range +
                ", targetAnge=" + targetAnge +
                ", clip=" + clip +
                ", optician=" + optician +
                '}';
    }
}
