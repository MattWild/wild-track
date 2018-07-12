/**
 * ManageNetworkPrefix.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package Layers.AMI.LandisGyr.Common.Control;

public class ManageNetworkPrefix  implements java.io.Serializable {
    private int NMSServerId;

    private java.lang.String name;

    private java.lang.String prefix;

    private int prefixBitSize;

    private int classSize;

    private org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers_Control.OperationType operation;

    public ManageNetworkPrefix() {
    }

    public ManageNetworkPrefix(
           int NMSServerId,
           java.lang.String name,
           java.lang.String prefix,
           int prefixBitSize,
           int classSize,
           org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers_Control.OperationType operation) {
           this.NMSServerId = NMSServerId;
           this.name = name;
           this.prefix = prefix;
           this.prefixBitSize = prefixBitSize;
           this.classSize = classSize;
           this.operation = operation;
    }


    /**
     * Gets the NMSServerId value for this ManageNetworkPrefix.
     * 
     * @return NMSServerId
     */
    public int getNMSServerId() {
        return NMSServerId;
    }


    /**
     * Sets the NMSServerId value for this ManageNetworkPrefix.
     * 
     * @param NMSServerId
     */
    public void setNMSServerId(int NMSServerId) {
        this.NMSServerId = NMSServerId;
    }


    /**
     * Gets the name value for this ManageNetworkPrefix.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this ManageNetworkPrefix.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the prefix value for this ManageNetworkPrefix.
     * 
     * @return prefix
     */
    public java.lang.String getPrefix() {
        return prefix;
    }


    /**
     * Sets the prefix value for this ManageNetworkPrefix.
     * 
     * @param prefix
     */
    public void setPrefix(java.lang.String prefix) {
        this.prefix = prefix;
    }


    /**
     * Gets the prefixBitSize value for this ManageNetworkPrefix.
     * 
     * @return prefixBitSize
     */
    public int getPrefixBitSize() {
        return prefixBitSize;
    }


    /**
     * Sets the prefixBitSize value for this ManageNetworkPrefix.
     * 
     * @param prefixBitSize
     */
    public void setPrefixBitSize(int prefixBitSize) {
        this.prefixBitSize = prefixBitSize;
    }


    /**
     * Gets the classSize value for this ManageNetworkPrefix.
     * 
     * @return classSize
     */
    public int getClassSize() {
        return classSize;
    }


    /**
     * Sets the classSize value for this ManageNetworkPrefix.
     * 
     * @param classSize
     */
    public void setClassSize(int classSize) {
        this.classSize = classSize;
    }


    /**
     * Gets the operation value for this ManageNetworkPrefix.
     * 
     * @return operation
     */
    public org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers_Control.OperationType getOperation() {
        return operation;
    }


    /**
     * Sets the operation value for this ManageNetworkPrefix.
     * 
     * @param operation
     */
    public void setOperation(org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers_Control.OperationType operation) {
        this.operation = operation;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ManageNetworkPrefix)) return false;
        ManageNetworkPrefix other = (ManageNetworkPrefix) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.NMSServerId == other.getNMSServerId() &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.prefix==null && other.getPrefix()==null) || 
             (this.prefix!=null &&
              this.prefix.equals(other.getPrefix()))) &&
            this.prefixBitSize == other.getPrefixBitSize() &&
            this.classSize == other.getClassSize() &&
            ((this.operation==null && other.getOperation()==null) || 
             (this.operation!=null &&
              this.operation.equals(other.getOperation())));
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
        _hashCode += getNMSServerId();
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getPrefix() != null) {
            _hashCode += getPrefix().hashCode();
        }
        _hashCode += getPrefixBitSize();
        _hashCode += getClassSize();
        if (getOperation() != null) {
            _hashCode += getOperation().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ManageNetworkPrefix.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "ManageNetworkPrefix"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NMSServerId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "NMSServerId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prefix");
        elemField.setXmlName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "Prefix"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prefixBitSize");
        elemField.setXmlName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "PrefixBitSize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("classSize");
        elemField.setXmlName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "ClassSize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("operation");
        elemField.setXmlName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "Operation"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers.Control", "OperationType"));
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
