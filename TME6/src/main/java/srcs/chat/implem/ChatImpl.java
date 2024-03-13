package srcs.chat.implem;

import java.util.concurrent.ConcurrentHashMap;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import srcs.ServerGrpc.ServerImplBase;
import com.google.protobuf.BoolValue;
import com.google.protobuf.Empty;
import srcs.ListChatter;
import srcs.grpc.util.BuilderUtil;
import srcs.ClientGrpc;
import srcs.ClientGrpc.ClientBlockingStub;

public class ChatImpl extends ServerImplBase {
	private ConcurrentHashMap<String, Channel> clientChannels = new ConcurrentHashMap<>();

	@Override
	public void subscribe(srcs.SubM request, io.grpc.stub.StreamObserver<com.google.protobuf.BoolValue> responseObserver) {
		String pseudo = request.getPseudo();
		String host = request.getHost();
		int port = request.getPort();

		Channel channel = BuilderUtil.disableStat(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());

		// atomic
		BoolValue subscribed = BoolValue.of(clientChannels.putIfAbsent(pseudo, channel) == null);
		responseObserver.onNext(subscribed);
		responseObserver.onCompleted();
	}

	@Override
	public void send(srcs.SendM request, io.grpc.stub.StreamObserver<com.google.protobuf.Int32Value> responseObserver) {

		// inform client
		clientChannels.forEach((pseudo, channel) -> {
			ClientBlockingStub clientStub = ClientGrpc.newBlockingStub(channel);
			clientStub.newMessage(request);
		});

		// nb client
		responseObserver.onNext(com.google.protobuf.Int32Value.of(clientChannels.size()));
		responseObserver.onCompleted();
	}

	@Override
	public void listChatter(com.google.protobuf.Empty request, io.grpc.stub.StreamObserver<srcs.ListChatter> responseObserver) {

		ListChatter list = ListChatter.newBuilder().addAllList(clientChannels.keySet()).build();
		responseObserver.onNext(list);
		responseObserver.onCompleted();

	}

	@Override
	public void unsubscribe(com.google.protobuf.StringValue request, io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {

		String pseudo = request.getValue();
		clientChannels.remove(pseudo);
		responseObserver.onNext(Empty.getDefaultInstance());
		responseObserver.onCompleted();

	}
}
