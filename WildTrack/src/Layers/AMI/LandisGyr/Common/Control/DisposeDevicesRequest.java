/**
 * DisposeDevicesRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package Layers.AMI.LandisGyr.Common.Control;

public class DisposeDevicesRequest  implements java.io.Serializable {
    private java.lang.String[] deviceIdentifiers;

    private org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers_Control.DisposeDeviceType deviceType;

    private org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers_Control.DisposeOperationType operationType;

    public DisposeDevicesRequest() {
    }

    public DisposeDevicesRequest(
           java.lang.String[] deviceIdentifiers,
           org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers_Control.DisposeDeviceType deviceType,
           org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers_Control.DisposeOperationType operationType) {
           this.deviceIdentifiers = deviceIdentifiers;
           this.deviceType = deviceType;
           this.operationType = operationType;
    }


    /**
     * Gets the deviceIdentifiers value for this DisposeDevicesRequest.
     * 
     * @return deviceIdentifiers
     */
    public java.lang.String[] getDeviceIdentifiers() {
        return deviceIdentifiers;
    }


    /**
     * Sets the deviceIdentifiers value for this DisposeDevicesRequest.
     * 
     * @param deviceIdentifiers
     */
    public void setDeviceIdentifiers(java.lang.String[] deviceIdentifiers) {
        this.deviceIdentifiers = deviceIdentifiers;
    }


    /**
     * Gets the deviceType value for this DisposeDevicesRequest.
     * 
     * @return deviceType
     */
    public org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers_Control.DisposeDeviceType getDeviceType() {
        return deviceType;
    }


    /**
     * Sets the deviceType value for this DisposeDevicesRequest.
     * 
     * @param deviceType
     */
    public void setDeviceType(org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers_Control.DisposeDeviceType deviceType) {
        this.deviceType = deviceType;
    }


    /**
     * Gets the operationType value for this DisposeDevicesRequest.
     * 
     * @return operationType
     */
    public org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers_Control.DisposeOperationType getOperationType() {
        return operationType;
    }


    /**
     * Sets the operationType value for this DisposeDevicesRequest.
     * 
     * @param operationType
     */
    public void setOperationType(org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers_Control.DisposeOperationType operationType) {
        this.operationType = operationType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DisposeDevicesRequest)) return false;
        DisposeDevicesRequest other = (DisposeDevicesRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.deviceIdentifiers==null && other.getDeviceIdentifiers()==null) || 
             (this.deviceIdentifiers!=null &&
              java.util.Arrays.equals(this.deviceIdentifiers, other.getDeviceIdentifiers()))) &&
            ((this.deviceType==null && other.getDeviceType()==null) || 
             (this.deviceType!=null &&
              this.deviceType.equals(other.getDeviceType()))) &&
            ((this.operationType==null && other.getOperationType()==null) || 
             (this.operationType!=null &&
              this.operationType.equals(other.getOperationType())));
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
        if (getDeviceIdentifiers() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDeviceIdentifiers());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDeviceIdentifiers(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDeviceType() != null) {
            _hashCode += getDeviceType().hashCode();
        }
        if (getOperationType() != null) {
            _hashCode += getOperationType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DisposeDevicesRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "DisposeDevicesRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceIdentifiers");
        elemField.setXmlName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "DeviceIdentifiers"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "DeviceType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers.Control", "DisposeDeviceType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operationType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "OperationType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers.Control", "DisposeOperationType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
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
