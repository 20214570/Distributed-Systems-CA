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
    comments = "Source: Service2Renewables.proto")
public final class Service2RenewablesGrpc {

  private Service2RenewablesGrpc() {}

  public static final String SERVICE_NAME = "EnergyEfficiency.Service2Renewables";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ds.examples.EnergyEfficiency.turbineRequest,
      ds.examples.EnergyEfficiency.turbineResponse> getTurbineStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "turbineStatus",
      requestType = ds.examples.EnergyEfficiency.turbineRequest.class,
      responseType = ds.examples.EnergyEfficiency.turbineResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ds.examples.EnergyEfficiency.turbineRequest,
      ds.examples.EnergyEfficiency.turbineResponse> getTurbineStatusMethod() {
    io.grpc.MethodDescriptor<ds.examples.EnergyEfficiency.turbineRequest, ds.examples.EnergyEfficiency.turbineResponse> getTurbineStatusMethod;
    if ((getTurbineStatusMethod = Service2RenewablesGrpc.getTurbineStatusMethod) == null) {
      synchronized (Service2RenewablesGrpc.class) {
        if ((getTurbineStatusMethod = Service2RenewablesGrpc.getTurbineStatusMethod) == null) {
          Service2RenewablesGrpc.getTurbineStatusMethod = getTurbineStatusMethod = 
              io.grpc.MethodDescriptor.<ds.examples.EnergyEfficiency.turbineRequest, ds.examples.EnergyEfficiency.turbineResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "EnergyEfficiency.Service2Renewables", "turbineStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.examples.EnergyEfficiency.turbineRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ds.examples.EnergyEfficiency.turbineResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new Service2RenewablesMethodDescriptorSupplier("turbineStatus"))
                  .build();
          }
        }
     }
     return getTurbineStatusMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static Service2RenewablesStub newStub(io.grpc.Channel channel) {
    return new Service2RenewablesStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static Service2RenewablesBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new Service2RenewablesBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static Service2RenewablesFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new Service2RenewablesFutureStub(channel);
  }

  /**
   * <pre>
   * Interface exported by the server.
   * </pre>
   */
  public static abstract class Service2RenewablesImplBase implements io.grpc.BindableService {

    /**
     */
    public void turbineStatus(ds.examples.EnergyEfficiency.turbineRequest request,
        io.grpc.stub.StreamObserver<ds.examples.EnergyEfficiency.turbineResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getTurbineStatusMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getTurbineStatusMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                ds.examples.EnergyEfficiency.turbineRequest,
                ds.examples.EnergyEfficiency.turbineResponse>(
                  this, METHODID_TURBINE_STATUS)))
          .build();
    }
  }

  /**
   * <pre>
   * Interface exported by the server.
   * </pre>
   */
  public static final class Service2RenewablesStub extends io.grpc.stub.AbstractStub<Service2RenewablesStub> {
    private Service2RenewablesStub(io.grpc.Channel channel) {
      super(channel);
    }

    private Service2RenewablesStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Service2RenewablesStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new Service2RenewablesStub(channel, callOptions);
    }

    /**
     */
    public void turbineStatus(ds.examples.EnergyEfficiency.turbineRequest request,
        io.grpc.stub.StreamObserver<ds.examples.EnergyEfficiency.turbineResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getTurbineStatusMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * Interface exported by the server.
   * </pre>
   */
  public static final class Service2RenewablesBlockingStub extends io.grpc.stub.AbstractStub<Service2RenewablesBlockingStub> {
    private Service2RenewablesBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private Service2RenewablesBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Service2RenewablesBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new Service2RenewablesBlockingStub(channel, callOptions);
    }

    /**
     */
    public ds.examples.EnergyEfficiency.turbineResponse turbineStatus(ds.examples.EnergyEfficiency.turbineRequest request) {
      return blockingUnaryCall(
          getChannel(), getTurbineStatusMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Interface exported by the server.
   * </pre>
   */
  public static final class Service2RenewablesFutureStub extends io.grpc.stub.AbstractStub<Service2RenewablesFutureStub> {
    private Service2RenewablesFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private Service2RenewablesFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected Service2RenewablesFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new Service2RenewablesFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ds.examples.EnergyEfficiency.turbineResponse> turbineStatus(
        ds.examples.EnergyEfficiency.turbineRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getTurbineStatusMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_TURBINE_STATUS = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final Service2RenewablesImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(Service2RenewablesImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_TURBINE_STATUS:
          serviceImpl.turbineStatus((ds.examples.EnergyEfficiency.turbineRequest) request,
              (io.grpc.stub.StreamObserver<ds.examples.EnergyEfficiency.turbineResponse>) responseObserver);
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
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class Service2RenewablesBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    Service2RenewablesBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ds.examples.EnergyEfficiency.Service2RenewablesImpl.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Service2Renewables");
    }
  }

  private static final class Service2RenewablesFileDescriptorSupplier
      extends Service2RenewablesBaseDescriptorSupplier {
    Service2RenewablesFileDescriptorSupplier() {}
  }

  private static final class Service2RenewablesMethodDescriptorSupplier
      extends Service2RenewablesBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    Service2RenewablesMethodDescriptorSupplier(String methodName) {
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
      synchronized (Service2RenewablesGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new Service2RenewablesFileDescriptorSupplier())
              .addMethod(getTurbineStatusMethod())
              .build();
        }
      }
    }
    return result;
  }
}
