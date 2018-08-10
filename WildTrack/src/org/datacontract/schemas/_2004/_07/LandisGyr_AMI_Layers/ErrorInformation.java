/**
 * ErrorInformation.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers;

public class ErrorInformation  implements java.io.Serializable {
    private java.lang.String adapterCode;

    private org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.ErrorCategory category;

    private java.util.Calendar dateAndTime;

    private java.lang.String details;

    private org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.LayerCode layer;

    private java.lang.String reason;

    private java.lang.String requestID;

    public ErrorInformation() {
    }

    public ErrorInformation(
           java.lang.String adapterCode,
           org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.ErrorCategory category,
           java.util.Calendar dateAndTime,
           java.lang.String details,
           org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.LayerCode layer,
           java.lang.String reason,
           java.lang.String requestID) {
           this.adapterCode = adapterCode;
           this.category = category;
           this.dateAndTime = dateAndTime;
           this.details = details;
           this.layer = layer;
           this.reason = reason;
           this.requestID = requestID;
    }


    /**
     * Gets the adapterCode value for this ErrorInformation.
     * 
     * @return adapterCode
     */
    public java.lang.String getAdapterCode() {
        return adapterCode;
    }


    /**
     * Sets the adapterCode value for this ErrorInformation.
     * 
     * @param adapterCode
     */
    public void setAdapterCode(java.lang.String adapterCode) {
        this.adapterCode = adapterCode;
    }


    /**
     * Gets the category value for this ErrorInformation.
     * 
     * @return category
     */
    public org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.ErrorCategory getCategory() {
        return category;
    }


    /**
     * Sets the category value for this ErrorInformation.
     * 
     * @param category
     */
    public void setCategory(org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.ErrorCategory category) {
        this.category = category;
    }


    /**
     * Gets the dateAndTime value for this ErrorInformation.
     * 
     * @return dateAndTime
     */
    public java.util.Calendar getDateAndTime() {
        return dateAndTime;
    }


    /**
     * Sets the dateAndTime value for this ErrorInformation.
     * 
     * @param dateAndTime
     */
    public void setDateAndTime(java.util.Calendar dateAndTime) {
        this.dateAndTime = dateAndTime;
    }


    /**
     * Gets the details value for this ErrorInformation.
     * 
     * @return details
     */
    public java.lang.String getDetails() {
        return details;
    }


    /**
     * Sets the details value for this ErrorInformation.
     * 
     * @param details
     */
    public void setDetails(java.lang.String details) {
        this.details = details;
    }


    /**
     * Gets the layer value for this ErrorInformation.
     * 
     * @return layer
     */
    public org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.LayerCode getLayer() {
        return layer;
    }


    /**
     * Sets the layer value for this ErrorInformation.
     * 
     * @param layer
     */
    public void setLayer(org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.LayerCode layer) {
        this.layer = layer;
    }


    /**
     * Gets the reason value for this ErrorInformation.
     * 
     * @return reason
     */
    public java.lang.String getReason() {
        return reason;
    }


    /**
     * Sets the reason value for this ErrorInformation.
     * 
     * @param reason
     */
    public void setReason(java.lang.String reason) {
        this.reason = reason;
    }


    /**
     * Gets the requestID value for this ErrorInformation.
     * 
     * @return requestID
     */
    public java.lang.String getRequestID() {
        return requestID;
    }


    /**
     * Sets the requestID value for this ErrorInformation.
     * 
     * @param requestID
     */
    public void setRequestID(java.lang.String requestID) {
        this.requestID = requestID;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ErrorInformation)) return false;
        ErrorInformation other = (ErrorInformation) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.adapterCode==null && other.getAdapterCode()==null) || 
             (this.adapterCode!=null &&
              this.adapterCode.equals(other.getAdapterCode()))) &&
            ((this.category==null && other.getCategory()==null) || 
             (this.category!=null &&
              this.category.equals(other.getCategory()))) &&
            ((this.dateAndTime==null && other.getDateAndTime()==null) || 
             (this.dateAndTime!=null &&
              this.dateAndTime.equals(other.getDateAndTime()))) &&
            ((this.details==null && other.getDetails()==null) || 
             (this.details!=null &&
              this.details.equals(other.getDetails()))) &&
            ((this.layer==null && other.getLayer()==null) || 
             (this.layer!=null &&
              this.layer.equals(other.getLayer()))) &&
            ((this.reason==null && other.getReason()==null) || 
             (this.reason!=null &&
              this.reason.equals(other.getReason()))) &&
            ((this.requestID==null && other.getRequestID()==null) || 
             (this.requestID!=null &&
              this.requestID.equals(other.getRequestID())));
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
        if (getAdapterCode() != null) {
            _hashCode += getAdapterCode().hashCode();
        }
        if (getCategory() != null) {
            _hashCode += getCategory().hashCode();
        }
        if (getDateAndTime() != null) {
            _hashCode += getDateAndTime().hashCode();
        }
        if (getDetails() != null) {
            _hashCode += getDetails().hashCode();
        }
        if (getLayer() != null) {
            _hashCode += getLayer().hashCode();
        }
        if (getReason() != null) {
            _hashCode += getReason().hashCode();
        }
        if (getRequestID() != null) {
            _hashCode += getRequestID().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ErrorInformation.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "ErrorInformation"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("adapterCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "AdapterCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("category");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "Category"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "ErrorCategory"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dateAndTime");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "DateAndTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("details");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "Details"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("layer");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "Layer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "LayerCode"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("reason");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "Reason"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "RequestID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
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
