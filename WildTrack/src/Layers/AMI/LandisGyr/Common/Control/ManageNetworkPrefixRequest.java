/**
 * ManageNetworkPrefixRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package Layers.AMI.LandisGyr.Common.Control;

public class ManageNetworkPrefixRequest  extends Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefix  implements java.io.Serializable {
    private java.lang.String errorDetails;

    private java.lang.Integer numberOfCollectors;

    public ManageNetworkPrefixRequest() {
    }

    public ManageNetworkPrefixRequest(
           int NMSServerId,
           java.lang.String name,
           java.lang.String prefix,
           int prefixBitSize,
           int classSize,
           org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers_Control.OperationType operation,
           java.lang.String errorDetails,
           java.lang.Integer numberOfCollectors) {
        super(
            NMSServerId,
            name,
            prefix,
            prefixBitSize,
            classSize,
            operation);
        this.errorDetails = errorDetails;
        this.numberOfCollectors = numberOfCollectors;
    }


    /**
     * Gets the errorDetails value for this ManageNetworkPrefixRequest.
     * 
     * @return errorDetails
     */
    public java.lang.String getErrorDetails() {
        return errorDetails;
    }


    /**
     * Sets the errorDetails value for this ManageNetworkPrefixRequest.
     * 
     * @param errorDetails
     */
    public void setErrorDetails(java.lang.String errorDetails) {
        this.errorDetails = errorDetails;
    }


    /**
     * Gets the numberOfCollectors value for this ManageNetworkPrefixRequest.
     * 
     * @return numberOfCollectors
     */
    public java.lang.Integer getNumberOfCollectors() {
        return numberOfCollectors;
    }


    /**
     * Sets the numberOfCollectors value for this ManageNetworkPrefixRequest.
     * 
     * @param numberOfCollectors
     */
    public void setNumberOfCollectors(java.lang.Integer numberOfCollectors) {
        this.numberOfCollectors = numberOfCollectors;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ManageNetworkPrefixRequest)) return false;
        ManageNetworkPrefixRequest other = (ManageNetworkPrefixRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.errorDetails==null && other.getErrorDetails()==null) || 
             (this.errorDetails!=null &&
              this.errorDetails.equals(other.getErrorDetails()))) &&
            ((this.numberOfCollectors==null && other.getNumberOfCollectors()==null) || 
             (this.numberOfCollectors!=null &&
              this.numberOfCollectors.equals(other.getNumberOfCollectors())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getErrorDetails() != null) {
            _hashCode += getErrorDetails().hashCode();
        }
        if (getNumberOfCollectors() != null) {
            _hashCode += getNumberOfCollectors().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ManageNetworkPrefixRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "ManageNetworkPrefixRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorDetails");
        elemField.setXmlName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "ErrorDetails"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numberOfCollectors");
        elemField.setXmlName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "NumberOfCollectors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
