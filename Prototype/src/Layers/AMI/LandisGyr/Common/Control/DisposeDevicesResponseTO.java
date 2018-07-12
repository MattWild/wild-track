/**
 * DisposeDevicesResponseTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package Layers.AMI.LandisGyr.Common.Control;

public class DisposeDevicesResponseTO  implements java.io.Serializable {
    private java.lang.String[] deviceIdentifierListFailed;

    private java.lang.String[] deviceIdentifierListSucceeded;

    private org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.ErrorInformation[] errors;

    public DisposeDevicesResponseTO() {
    }

    public DisposeDevicesResponseTO(
           java.lang.String[] deviceIdentifierListFailed,
           java.lang.String[] deviceIdentifierListSucceeded,
           org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.ErrorInformation[] errors) {
           this.deviceIdentifierListFailed = deviceIdentifierListFailed;
           this.deviceIdentifierListSucceeded = deviceIdentifierListSucceeded;
           this.errors = errors;
    }


    /**
     * Gets the deviceIdentifierListFailed value for this DisposeDevicesResponseTO.
     * 
     * @return deviceIdentifierListFailed
     */
    public java.lang.String[] getDeviceIdentifierListFailed() {
        return deviceIdentifierListFailed;
    }


    /**
     * Sets the deviceIdentifierListFailed value for this DisposeDevicesResponseTO.
     * 
     * @param deviceIdentifierListFailed
     */
    public void setDeviceIdentifierListFailed(java.lang.String[] deviceIdentifierListFailed) {
        this.deviceIdentifierListFailed = deviceIdentifierListFailed;
    }


    /**
     * Gets the deviceIdentifierListSucceeded value for this DisposeDevicesResponseTO.
     * 
     * @return deviceIdentifierListSucceeded
     */
    public java.lang.String[] getDeviceIdentifierListSucceeded() {
        return deviceIdentifierListSucceeded;
    }


    /**
     * Sets the deviceIdentifierListSucceeded value for this DisposeDevicesResponseTO.
     * 
     * @param deviceIdentifierListSucceeded
     */
    public void setDeviceIdentifierListSucceeded(java.lang.String[] deviceIdentifierListSucceeded) {
        this.deviceIdentifierListSucceeded = deviceIdentifierListSucceeded;
    }


    /**
     * Gets the errors value for this DisposeDevicesResponseTO.
     * 
     * @return errors
     */
    public org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.ErrorInformation[] getErrors() {
        return errors;
    }


    /**
     * Sets the errors value for this DisposeDevicesResponseTO.
     * 
     * @param errors
     */
    public void setErrors(org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.ErrorInformation[] errors) {
        this.errors = errors;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DisposeDevicesResponseTO)) return false;
        DisposeDevicesResponseTO other = (DisposeDevicesResponseTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.deviceIdentifierListFailed==null && other.getDeviceIdentifierListFailed()==null) || 
             (this.deviceIdentifierListFailed!=null &&
              java.util.Arrays.equals(this.deviceIdentifierListFailed, other.getDeviceIdentifierListFailed()))) &&
            ((this.deviceIdentifierListSucceeded==null && other.getDeviceIdentifierListSucceeded()==null) || 
             (this.deviceIdentifierListSucceeded!=null &&
              java.util.Arrays.equals(this.deviceIdentifierListSucceeded, other.getDeviceIdentifierListSucceeded()))) &&
            ((this.errors==null && other.getErrors()==null) || 
             (this.errors!=null &&
              java.util.Arrays.equals(this.errors, other.getErrors())));
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
        if (getDeviceIdentifierListFailed() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDeviceIdentifierListFailed());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDeviceIdentifierListFailed(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDeviceIdentifierListSucceeded() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDeviceIdentifierListSucceeded());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDeviceIdentifierListSucceeded(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getErrors() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getErrors());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getErrors(), i);
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
        new org.apache.axis.description.TypeDesc(DisposeDevicesResponseTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "DisposeDevicesResponseTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceIdentifierListFailed");
        elemField.setXmlName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "DeviceIdentifierListFailed"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("deviceIdentifierListSucceeded");
        elemField.setXmlName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "DeviceIdentifierListSucceeded"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "string"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errors");
        elemField.setXmlName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "Errors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "ErrorInformation"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "ErrorInformation"));
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
