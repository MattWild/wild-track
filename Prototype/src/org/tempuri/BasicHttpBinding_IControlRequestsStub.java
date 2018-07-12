/**
 * BasicHttpBinding_IControlRequestsStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class BasicHttpBinding_IControlRequestsStub extends org.apache.axis.client.Stub implements Layers.AMI.LandisGyr.Common.Control.IControlRequests {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[9];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CancelCommand");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "commandID"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "commandSentDateTime"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"), java.util.Calendar.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        oper.setReturnClass(java.lang.Boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "CancelCommandResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetVersion");
        oper.setReturnType(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "ArrayOfKeyValueData"));
        oper.setReturnClass(org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.KeyValueData[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "GetVersionResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "KeyValueData"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("DisposeDevices");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "disposeDevicesReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "DisposeDevicesRequest"), Layers.AMI.LandisGyr.Common.Control.DisposeDevicesRequest.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "DisposeDevicesResponseTO"));
        oper.setReturnClass(Layers.AMI.LandisGyr.Common.Control.DisposeDevicesResponseTO.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "DisposeDevicesResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CheckCommDelaysOfGNDCollectors");
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        oper.setReturnClass(java.lang.Boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "CheckCommDelaysOfGNDCollectorsResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ManageNetworkPrefixes");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "manageNetworkPrefixReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "ManageNetworkPrefix"), Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefix.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "ManageNetworkPrefixRequest"));
        oper.setReturnClass(Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefixRequest.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "ManageNetworkPrefixesResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("GetNetworkPrefixes");
        oper.setReturnType(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "ArrayOfManageNetworkPrefixRequest"));
        oper.setReturnClass(Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefixRequest[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "GetNetworkPrefixesResult"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "ManageNetworkPrefixRequest"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("CheckCommDelaysForEndpoints");
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        oper.setReturnClass(java.lang.Boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "CheckCommDelaysForEndpointsResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("RefreshCommStateforGNDCollectors");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "message"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "CollectorCommunication"), Layers.AMI.LandisGyr.Common.Control.CollectorCommunication.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        oper.setReturnClass(java.lang.Boolean.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "RefreshCommStateforGNDCollectorsResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("RefreshComStateForNetworkDevices");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "networkDevicesComStatusReq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "NetworkDevicesCommunication"), Layers.AMI.LandisGyr.Common.Control.NetworkDevicesCommunication.class, false, false);
        param.setOmittable(true);
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[8] = oper;

    }

    public BasicHttpBinding_IControlRequestsStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public BasicHttpBinding_IControlRequestsStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public BasicHttpBinding_IControlRequestsStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "ArrayOfManageNetworkPrefixRequest");
            cachedSerQNames.add(qName);
            cls = Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefixRequest[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "ManageNetworkPrefixRequest");
            qName2 = new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "ManageNetworkPrefixRequest");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "CollectorCommunication");
            cachedSerQNames.add(qName);
            cls = Layers.AMI.LandisGyr.Common.Control.CollectorCommunication.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "DisposeDevicesRequest");
            cachedSerQNames.add(qName);
            cls = Layers.AMI.LandisGyr.Common.Control.DisposeDevicesRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "DisposeDevicesResponseTO");
            cachedSerQNames.add(qName);
            cls = Layers.AMI.LandisGyr.Common.Control.DisposeDevicesResponseTO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "ManageNetworkPrefix");
            cachedSerQNames.add(qName);
            cls = Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefix.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "ManageNetworkPrefixRequest");
            cachedSerQNames.add(qName);
            cls = Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefixRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "NetworkDevicesCommunication");
            cachedSerQNames.add(qName);
            cls = Layers.AMI.LandisGyr.Common.Control.NetworkDevicesCommunication.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers.Control", "DisposeDeviceType");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers_Control.DisposeDeviceType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers.Control", "DisposeOperationType");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers_Control.DisposeOperationType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers.Control", "OperationType");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers_Control.OperationType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "ArrayOfErrorInformation");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.ErrorInformation[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "ErrorInformation");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "ErrorInformation");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "ArrayOfKeyValueData");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.KeyValueData[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "KeyValueData");
            qName2 = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "KeyValueData");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "ErrorCategory");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.ErrorCategory.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "ErrorInformation");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.ErrorInformation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "KeyValueData");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.KeyValueData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://schemas.datacontract.org/2004/07/LandisGyr.AMI.Layers", "LayerCode");
            cachedSerQNames.add(qName);
            cls = org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.LayerCode.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "ArrayOfstring");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("http://schemas.microsoft.com/2003/10/Serialization/Arrays", "string");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public java.lang.Boolean cancelCommand(java.lang.String commandID, java.util.Calendar commandSentDateTime) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://LandisGyr.AMI.Layers/Common/Control/IControlRequests/CancelCommand");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "CancelCommand"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {commandID, commandSentDateTime});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.Boolean) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.Boolean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.KeyValueData[] getVersion() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://LandisGyr.AMI.Layers/Common/Control/IControlRequests/GetVersion");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "GetVersion"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.KeyValueData[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.KeyValueData[]) org.apache.axis.utils.JavaUtils.convert(_resp, org.datacontract.schemas._2004._07.LandisGyr_AMI_Layers.KeyValueData[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public Layers.AMI.LandisGyr.Common.Control.DisposeDevicesResponseTO disposeDevices(Layers.AMI.LandisGyr.Common.Control.DisposeDevicesRequest disposeDevicesReq) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://LandisGyr.AMI.Layers/Common/Control/IControlRequests/DisposeDevices");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "DisposeDevices"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {disposeDevicesReq});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (Layers.AMI.LandisGyr.Common.Control.DisposeDevicesResponseTO) _resp;
            } catch (java.lang.Exception _exception) {
                return (Layers.AMI.LandisGyr.Common.Control.DisposeDevicesResponseTO) org.apache.axis.utils.JavaUtils.convert(_resp, Layers.AMI.LandisGyr.Common.Control.DisposeDevicesResponseTO.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.Boolean checkCommDelaysOfGNDCollectors() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://LandisGyr.AMI.Layers/Common/Control/IControlRequests/CheckCommDelaysOfGNDCollectors");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "CheckCommDelaysOfGNDCollectors"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.Boolean) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.Boolean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefixRequest manageNetworkPrefixes(Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefix manageNetworkPrefixReq) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://LandisGyr.AMI.Layers/Common/Control/IControlRequests/ManageNetworkPrefixes");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "ManageNetworkPrefixes"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {manageNetworkPrefixReq});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefixRequest) _resp;
            } catch (java.lang.Exception _exception) {
                return (Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefixRequest) org.apache.axis.utils.JavaUtils.convert(_resp, Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefixRequest.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefixRequest[] getNetworkPrefixes() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://LandisGyr.AMI.Layers/Common/Control/IControlRequests/GetNetworkPrefixes");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "GetNetworkPrefixes"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefixRequest[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefixRequest[]) org.apache.axis.utils.JavaUtils.convert(_resp, Layers.AMI.LandisGyr.Common.Control.ManageNetworkPrefixRequest[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.Boolean checkCommDelaysForEndpoints() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://LandisGyr.AMI.Layers/Common/Control/IControlRequests/CheckCommDelaysForEndpoints");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "CheckCommDelaysForEndpoints"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.Boolean) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.Boolean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.Boolean refreshCommStateforGNDCollectors(Layers.AMI.LandisGyr.Common.Control.CollectorCommunication message) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://LandisGyr.AMI.Layers/Common/Control/IControlRequests/RefreshCommStateforGNDCollectors");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "RefreshCommStateforGNDCollectors"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {message});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.Boolean) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.Boolean.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void refreshComStateForNetworkDevices(Layers.AMI.LandisGyr.Common.Control.NetworkDevicesCommunication networkDevicesComStatusReq) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://LandisGyr.AMI.Layers/Common/Control/IControlRequests/RefreshComStateForNetworkDevices");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://LandisGyr.AMI.Layers/Common/Control", "RefreshComStateForNetworkDevices"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {networkDevicesComStatusReq});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
