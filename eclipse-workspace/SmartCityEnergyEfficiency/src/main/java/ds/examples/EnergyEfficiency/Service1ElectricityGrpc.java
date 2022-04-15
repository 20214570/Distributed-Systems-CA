package ds.examples.EnergyEfficiency;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * Interface exported by the server.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: Service1Electricity.proto")
public final class Service1ElectricityGrpc {

  private Service1ElectricityGrpc() {}

  public static final String SERVICE_NAME = "EnergyEfficiency.Service1Electricity";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ds.examples.EnergyEfficiency.lightRequest,
      ds.examples.EnergyEfficiency.lightResponse> getLightSensorMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "lightSensor",
      requestType = ds.examples.EnergyEfficiency.lightRequest.class,
      responseType = ds.examples.EnergyEfficiency.lightResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<ds.examples.EnergyEfficiency.lightRequest,
      ds.examples.EnergyEfficiency.lightResponse> getLightSensorMethod() {
    io.grpc.MethodDescriptor<ds.examples.EnergyEfficiency.lightRequest, ds.examples.EnergyEfficiency.lightResponse> getLightSensorMethod;
    if ((getLightSensorMethod = Service1ElectricityGrpc.getLightSensorMethod) == null) {
      synchronized (Service1ElectricityGrpc.class) {
        if ((getLightSensorMethod = Service1ElectricityGrpc.getLightSensorMethod) == null) {
          Service1ElectricityGrpc.getLightSensorMethod = getLightSensorMethod = 
              io.grpc.MethodDescriptor.<ds.examples.EnergyEfficiency.lightRequest, ds.examples.EnergyEfficiency.lightResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "EnergyEfficiency.Service1Electricity", "lightSensor"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.examples.EnergyEfficiency.lightRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.examples.EnergyEfficiency.lightResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new Service1ElectricityMethodDescriptorSupplier("lightSensor"))
                  .build();
          }
        }
     }
     return getLightSensorMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ds.examples.EnergyEfficiency.bridgeMessage,
      ds.examples.EnergyEfficiency.bridgeResponse> getBridgeLightsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "bridgeLights",
      requestType = ds.examples.EnergyEfficiency.bridgeMessage.class,
      responseType = ds.examples.EnergyEfficiency.bridgeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<ds.examples.EnergyEfficiency.bridgeMessage,
      ds.examples.EnergyEfficiency.bridgeResponse> getBridgeLightsMethod() {
    io.grpc.MethodDescriptor<ds.examples.EnergyEfficiency.bridgeMessage, ds.examples.EnergyEfficiency.bridgeResponse> getBridgeLightsMethod;
    if ((getBridgeLightsMethod = Service1ElectricityGrpc.getBridgeLightsMethod) == null) {
      synchronized (Service1ElectricityGrpc.class) {
        if ((getBridgeLightsMethod = Service1ElectricityGrpc.getBridgeLightsMethod) == null) {
          Service1ElectricityGrpc.getBridgeLightsMethod = getBridgeLightsMethod = 
              io.grpc.MethodDescriptor.<ds.examples.EnergyEfficiency.bridgeMessage, ds.examples.EnergyEfficiency.bridgeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "EnergyEfficiency.Service1Electricity", "bridgeLights"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.examples.EnergyEfficiency.bridgeMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.examples.EnergyEfficiency.bridgeResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new Service1ElectricityMethodDescriptorSupplier("bridgeLights"))
                  .build();
          }
        }
     }
     return getBridgeLightsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static Service1ElectricityStub newStub(io.grpc.Channel channel) {
    return new Service1ElectricityStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static Service1ElectricityBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new Service1ElectricityBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static Service1ElectricityFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new Service1ElectricityFutureStub(channel);
  }

  /**
   * <pre>
   * Interface exported by the server.
   * </pre>
   */
  public static abstract class Service1ElectricityImplBase implements io.grpc.BindableService {

    /**
     */
    public void lightSensor(ds.examples.EnergyEfficiency.lightRequest request,
        io.grpc.stub.StreamObserver<ds.examples.EnergyEfficiency.lightResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getLightSensorMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<ds.examples.EnergyEfficiency.bridgeMessage> bridgeLights(
        io.grpc.stub.StreamObserver<ds.examples.EnergyEfficiency.bridgeResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(getBridgeLightsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getLightSensorMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                ds.examples.EnergyEfficiency.lightRequest,
                ds.examples.EnergyEfficiency.lightResponse>(
                  this, METHODID_LIGHT_SENSOR)))
          .addMethod(
            getBridgeLightsMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                ds.examples.EnergyEfficiency.bridgeMessage,
                ds.examples.EnergyEfficiency.bridgeResponse>(
                  this, METHODID_BRIDGE_LIGHTS)))
          .build();
    }
  }

  /**
   * <pre>
   * Interface exported by the server.
   * </pre>
   */
  public static final class Service1ElectricityStub extends io.grpc.stub.AbstractStub<Service1ElectricityStub> {
    private Service1ElectricityStub(io.grpc.Channel channel) {
      super(channel);
    }

    private Service1ElectricityStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Service1ElectricityStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new Service1ElectricityStub(channel, callOptions);
    }

    /**
     */
    public void lightSensor(ds.examples.EnergyEfficiency.lightRequest request,
        io.grpc.stub.StreamObserver<ds.examples.EnergyEfficiency.lightResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getLightSensorMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<ds.examples.EnergyEfficiency.bridgeMessage> bridgeLights(
        io.grpc.stub.StreamObserver<ds.examples.EnergyEfficiency.bridgeResponse> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getBridgeLightsMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   * Interface exported by the server.
   * </pre>
   */
  public static final class Service1ElectricityBlockingStub extends io.grpc.stub.AbstractStub<Service1ElectricityBlockingStub> {
    private Service1ElectricityBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private Service1ElectricityBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Service1ElectricityBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new Service1ElectricityBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<ds.examples.EnergyEfficiency.lightResponse> lightSensor(
        ds.examples.EnergyEfficiency.lightRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getLightSensorMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Interface exported by the server.
   * </pre>
   */
  public static final class Service1ElectricityFutureStub extends io.grpc.stub.AbstractStub<Service1ElectricityFutureStub> {
    private Service1ElectricityFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private Service1ElectricityFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Service1ElectricityFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new Service1ElectricityFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_LIGHT_SENSOR = 0;
  private static final int METHODID_BRIDGE_LIGHTS = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final Service1ElectricityImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(Service1ElectricityImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_LIGHT_SENSOR:
          serviceImpl.lightSensor((ds.examples.EnergyEfficiency.lightRequest) request,
              (io.grpc.stub.StreamObserver<ds.examples.EnergyEfficiency.lightResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_BRIDGE_LIGHTS:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.bridgeLights(
              (io.grpc.stub.StreamObserver<ds.examples.EnergyEfficiency.bridgeResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class Service1ElectricityBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    Service1ElectricityBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ds.examples.EnergyEfficiency.Service1ElectricityImpl.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Service1Electricity");
    }
  }

  private static final class Service1ElectricityFileDescriptorSupplier
      extends Service1ElectricityBaseDescriptorSupplier {
    Service1ElectricityFileDescriptorSupplier() {}
  }

  private static final class Service1ElectricityMethodDescriptorSupplier
      extends Service1ElectricityBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    Service1ElectricityMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (Service1ElectricityGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new Service1ElectricityFileDescriptorSupplier())
              .addMethod(getLightSensorMethod())
              .addMethod(getBridgeLightsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
