/**
 * CollectorCommunication.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package Layers.AMI.LandisGyr.Common.Control;

public class CollectorCommunication  implements java.io.Serializable {
    private java.lang.String[] collectorSerialNumbers;

    public CollectorCommunication() {
    }

    public CollectorCommunication(
           java.lang.String[] collectorSerialNumbers) {
           this.collectorSerialNumbers = collectorSerialNumbers;
    }


    /**
     * Gets the collectorSerialNumbers value for this CollectorCommunication.
     * 
     * @return collectorSerialNumbers
     */
    public java.lang.String[] getCollectorSerialNumbers() {
        return collectorSerialNumbers;
    }


    /**
     * Sets the collectorSerialNumbers value for this CollectorCommunication.
     * 
     * @param collectorSerialNumbers
     */
    public void setCollectorSerialNumbers(java.lang.String[] collectorSerialNumbers) {
        this.collectorSerialNumbers = collectorSerialNumbers;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CollectorCommunication)) return false;
        CollectorCommunication other = (CollectorCommunication) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.collectorSerialNumbers==null && other.getCollectorSerialNumbers()==null) || 
             (this.collectorSerialNumbers!=null &&
              java.util.Arrays.equals(this.collectorSerialNumbers, other.getCollectorSerialNumbers())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getCollectorSerialNumbers() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCollectorSerialNumbers());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCollectorSerialNumbers(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CollectorCommunication.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "CollectorCommunication"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("collectorSerialNumbers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "CollectorSerialNumbers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "string"));
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
