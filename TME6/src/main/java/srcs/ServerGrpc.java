package srcs;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.26.0)",
    comments = "Source: srcs/proto/srcs.Message.proto")
public final class ServerGrpc {

  private ServerGrpc() {}

  public static final String SERVICE_NAME = "Server";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<SubM,
      com.google.protobuf.BoolValue> getSubscribeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "subscribe",
      requestType = SubM.class,
      responseType = com.google.protobuf.BoolValue.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<SubM,
      com.google.protobuf.BoolValue> getSubscribeMethod() {
    io.grpc.MethodDescriptor<SubM, com.google.protobuf.BoolValue> getSubscribeMethod;
    if ((getSubscribeMethod = ServerGrpc.getSubscribeMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getSubscribeMethod = ServerGrpc.getSubscribeMethod) == null) {
          ServerGrpc.getSubscribeMethod = getSubscribeMethod =
              io.grpc.MethodDescriptor.<SubM, com.google.protobuf.BoolValue>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "subscribe"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  SubM.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.BoolValue.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("subscribe"))
              .build();
        }
      }
    }
    return getSubscribeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<SendM,
      com.google.protobuf.Int32Value> getSendMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "send",
      requestType = SendM.class,
      responseType = com.google.protobuf.Int32Value.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<SendM,
      com.google.protobuf.Int32Value> getSendMethod() {
    io.grpc.MethodDescriptor<SendM, com.google.protobuf.Int32Value> getSendMethod;
    if ((getSendMethod = ServerGrpc.getSendMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getSendMethod = ServerGrpc.getSendMethod) == null) {
          ServerGrpc.getSendMethod = getSendMethod =
              io.grpc.MethodDescriptor.<SendM, com.google.protobuf.Int32Value>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "send"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  SendM.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Int32Value.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("send"))
              .build();
        }
      }
    }
    return getSendMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      ListChatter> getListChatterMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "listChatter",
      requestType = com.google.protobuf.Empty.class,
      responseType = ListChatter.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      ListChatter> getListChatterMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, ListChatter> getListChatterMethod;
    if ((getListChatterMethod = ServerGrpc.getListChatterMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getListChatterMethod = ServerGrpc.getListChatterMethod) == null) {
          ServerGrpc.getListChatterMethod = getListChatterMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, ListChatter>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "listChatter"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ListChatter.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("listChatter"))
              .build();
        }
      }
    }
    return getListChatterMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.StringValue,
      com.google.protobuf.Empty> getUnsubscribeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "unsubscribe",
      requestType = com.google.protobuf.StringValue.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.StringValue,
      com.google.protobuf.Empty> getUnsubscribeMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.StringValue, com.google.protobuf.Empty> getUnsubscribeMethod;
    if ((getUnsubscribeMethod = ServerGrpc.getUnsubscribeMethod) == null) {
      synchronized (ServerGrpc.class) {
        if ((getUnsubscribeMethod = ServerGrpc.getUnsubscribeMethod) == null) {
          ServerGrpc.getUnsubscribeMethod = getUnsubscribeMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.StringValue, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "unsubscribe"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.StringValue.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new ServerMethodDescriptorSupplier("unsubscribe"))
              .build();
        }
      }
    }
    return getUnsubscribeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ServerStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServerStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServerStub>() {
        @java.lang.Override
        public ServerStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServerStub(channel, callOptions);
        }
      };
    return ServerStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ServerBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServerBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServerBlockingStub>() {
        @java.lang.Override
        public ServerBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServerBlockingStub(channel, callOptions);
        }
      };
    return ServerBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ServerFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServerFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServerFutureStub>() {
        @java.lang.Override
        public ServerFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServerFutureStub(channel, callOptions);
        }
      };
    return ServerFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ServerImplBase implements io.grpc.BindableService {

    /**
     */
    public void subscribe(SubM request,
        io.grpc.stub.StreamObserver<com.google.protobuf.BoolValue> responseObserver) {
      asyncUnimplementedUnaryCall(getSubscribeMethod(), responseObserver);
    }

    /**
     */
    public void send(SendM request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Int32Value> responseObserver) {
      asyncUnimplementedUnaryCall(getSendMethod(), responseObserver);
    }

    /**
     */
    public void listChatter(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<ListChatter> responseObserver) {
      asyncUnimplementedUnaryCall(getListChatterMethod(), responseObserver);
    }

    /**
     */
    public void unsubscribe(com.google.protobuf.StringValue request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnimplementedUnaryCall(getUnsubscribeMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSubscribeMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                SubM,
                com.google.protobuf.BoolValue>(
                  this, METHODID_SUBSCRIBE)))
          .addMethod(
            getSendMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                SendM,
                com.google.protobuf.Int32Value>(
                  this, METHODID_SEND)))
          .addMethod(
            getListChatterMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                ListChatter>(
                  this, METHODID_LIST_CHATTER)))
          .addMethod(
            getUnsubscribeMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.StringValue,
                com.google.protobuf.Empty>(
                  this, METHODID_UNSUBSCRIBE)))
          .build();
    }
  }

  /**
   */
  public static final class ServerStub extends io.grpc.stub.AbstractAsyncStub<ServerStub> {
    private ServerStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServerStub(channel, callOptions);
    }

    /**
     */
    public void subscribe(SubM request,
        io.grpc.stub.StreamObserver<com.google.protobuf.BoolValue> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSubscribeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void send(SendM request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Int32Value> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSendMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listChatter(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<ListChatter> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getListChatterMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void unsubscribe(com.google.protobuf.StringValue request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUnsubscribeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ServerBlockingStub extends io.grpc.stub.AbstractBlockingStub<ServerBlockingStub> {
    private ServerBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServerBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.BoolValue subscribe(SubM request) {
      return blockingUnaryCall(
          getChannel(), getSubscribeMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Int32Value send(SendM request) {
      return blockingUnaryCall(
          getChannel(), getSendMethod(), getCallOptions(), request);
    }

    /**
     */
    public ListChatter listChatter(com.google.protobuf.Empty request) {
      return blockingUnaryCall(
          getChannel(), getListChatterMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty unsubscribe(com.google.protobuf.StringValue request) {
      return blockingUnaryCall(
          getChannel(), getUnsubscribeMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ServerFutureStub extends io.grpc.stub.AbstractFutureStub<ServerFutureStub> {
    private ServerFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServerFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServerFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.BoolValue> subscribe(
        SubM request) {
      return futureUnaryCall(
          getChannel().newCall(getSubscribeMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Int32Value> send(
        SendM request) {
      return futureUnaryCall(
          getChannel().newCall(getSendMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ListChatter> listChatter(
        com.google.protobuf.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getListChatterMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> unsubscribe(
        com.google.protobuf.StringValue request) {
      return futureUnaryCall(
          getChannel().newCall(getUnsubscribeMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SUBSCRIBE = 0;
  private static final int METHODID_SEND = 1;
  private static final int METHODID_LIST_CHATTER = 2;
  private static final int METHODID_UNSUBSCRIBE = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ServerImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ServerImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SUBSCRIBE:
          serviceImpl.subscribe((SubM) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.BoolValue>) responseObserver);
          break;
        case METHODID_SEND:
          serviceImpl.send((SendM) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Int32Value>) responseObserver);
          break;
        case METHODID_LIST_CHATTER:
          serviceImpl.listChatter((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<ListChatter>) responseObserver);
          break;
        case METHODID_UNSUBSCRIBE:
          serviceImpl.unsubscribe((com.google.protobuf.StringValue) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
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

  private static abstract class ServerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ServerBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Message.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Server");
    }
  }

  private static final class ServerFileDescriptorSupplier
      extends ServerBaseDescriptorSupplier {
    ServerFileDescriptorSupplier() {}
  }

  private static final class ServerMethodDescriptorSupplier
      extends ServerBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ServerMethodDescriptorSupplier(String methodName) {
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
      synchronized (ServerGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ServerFileDescriptorSupplier())
              .addMethod(getSubscribeMethod())
              .addMethod(getSendMethod())
              .addMethod(getListChatterMethod())
              .addMethod(getUnsubscribeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
