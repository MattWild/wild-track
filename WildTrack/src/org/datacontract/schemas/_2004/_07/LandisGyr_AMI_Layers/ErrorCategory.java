/**
 * ErrorCategory.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers;

public class ErrorCategory implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ErrorCategory(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ProtocolException = "ProtocolException";
    public static final java.lang.String _ValidationException = "ValidationException";
    public static final java.lang.String _CommunicationException = "CommunicationException";
    public static final java.lang.String _ServerInternalException = "ServerInternalException";
    public static final ErrorCategory ProtocolException = new ErrorCategory(_ProtocolException);
    public static final ErrorCategory ValidationException = new ErrorCategory(_ValidationException);
    public static final ErrorCategory CommunicationException = new ErrorCategory(_CommunicationException);
    public static final ErrorCategory ServerInternalException = new ErrorCategory(_ServerInternalException);
    public java.lang.String getValue() { return _value_;}
    public static ErrorCategory fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ErrorCategory enumeration = (ErrorCategory)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ErrorCategory fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ErrorCategory.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "ErrorCategory"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
