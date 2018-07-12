/**
 * NetworkDevicesCommunication.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package Layers.AMI.LandisGyr.Common.Control;

public class NetworkDevicesCommunication  implements java.io.Serializable {
    private java.lang.String[] endpoints;

    public NetworkDevicesCommunication() {
    }

    public NetworkDevicesCommunication(
           java.lang.String[] endpoints) {
           this.endpoints = endpoints;
    }


    /**
     * Gets the endpoints value for this NetworkDevicesCommunication.
     * 
     * @return endpoints
     */
    public java.lang.String[] getEndpoints() {
        return endpoints;
    }


    /**
     * Sets the endpoints value for this NetworkDevicesCommunication.
     * 
     * @param endpoints
     */
    public void setEndpoints(java.lang.String[] endpoints) {
        this.endpoints = endpoints;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NetworkDevicesCommunication)) return false;
        NetworkDevicesCommunication other = (NetworkDevicesCommunication) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.endpoints==null && other.getEndpoints()==null) || 
             (this.endpoints!=null &&
              java.util.Arrays.equals(this.endpoints, other.getEndpoints())));
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
        if (getEndpoints() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEndpoints());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEndpoints(), i);
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
        new org.apache.axis.description.TypeDesc(NetworkDevicesCommunication.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "NetworkDevicesCommunication"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endpoints");
        elemField.setXmlName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "Endpoints"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
