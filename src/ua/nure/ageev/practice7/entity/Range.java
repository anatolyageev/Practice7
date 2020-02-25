//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.02.24 at 11:28:45 AM EET 
//


package ua.nure.ageev.practice7.entity;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Range complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Range">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="short_range" type="{http://nure.ua/Firearms/p7}short_range"/>
 *         &lt;element name="middle_range" type="{http://nure.ua/Firearms/p7}middle_range"/>
 *         &lt;element name="long_range" type="{http://nure.ua/Firearms/p7}long_range"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Range", propOrder = {
    "shortRangeOrMiddleRangeOrLongRange"
})
public class Range {

    @XmlElementRefs({
        @XmlElementRef(name = "short_range", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "middle_range", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "long_range", type = JAXBElement.class, required = false)
    })
    protected JAXBElement<? extends Comparable> shortRangeOrMiddleRangeOrLongRange;

    /**
     * Gets the value of the shortRangeOrMiddleRangeOrLongRange property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public JAXBElement<? extends Comparable> getShortRangeOrMiddleRangeOrLongRange() {
        return shortRangeOrMiddleRangeOrLongRange;
    }

    /**
     * Sets the value of the shortRangeOrMiddleRangeOrLongRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
     *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
     *     
     */
    public void setShortRangeOrMiddleRangeOrLongRange(JAXBElement<? extends Comparable> value) {
        this.shortRangeOrMiddleRangeOrLongRange = value;
    }

    @Override
    public String toString() {
        if(((JAXBElement)shortRangeOrMiddleRangeOrLongRange).getValue()==null){
            return "Nul";
        }
        Number l = 0;
        Object wrapped = ((JAXBElement)shortRangeOrMiddleRangeOrLongRange).getValue();
     //   Object wrapped = shortRangeOrMiddleRangeOrLongRange.getValue();
        if(wrapped.getClass() == BigInteger.class){
             l = (BigInteger)wrapped;
        }
        else {
             l = (Integer) wrapped;
        }
        return l.toString();
    }
}
