package srcs.chat.implem;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.protobuf.BoolValue;
import com.google.protobuf.Empty;
import com.google.protobuf.Int32Value;
import com.google.protobuf.StringValue;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import srcs.ClientGrpc.ClientImplBase;
import srcs.ListChatter;
import srcs.SendM;
import srcs.ServerGrpc;
import srcs.ServerGrpc.ServerBlockingStub;
import srcs.SubM;
import srcs.grpc.util.BuilderUtil;

public class ChatProxy implements Chat {

	private MessageReceiver messageReceiver;
	private Channel channel;
	private ServerBlockingStub serverStub;
	private Server callbackServer;
	

	public ChatProxy(String host, int port, MessageReceiver messageReceiver) {
		this.messageReceiver = messageReceiver;
		this.channel = BuilderUtil.disableStat(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
		this.serverStub = ServerGrpc.newBlockingStub(channel);
	}

	class MessageReceiverImpl extends ClientImplBase {
		@Override
		public void newMessage(SendM request, StreamObserver<Empty> responseObserver) {
			messageReceiver.newMessage(request.getPseudo(), request.getMessage());
			responseObserver.onNext(Empty.getDefaultInstance());
			responseObserver.onCompleted();
		}
	}

	@Override
	public boolean subscribe(String pseudo, String host, int port) {
		try {
			callbackServer = ServerBuilder.forPort(port)
					.addService(new MessageReceiverImpl())
					.build()
					.start();
		} catch (IOException e) {
			return false;
		}

		BoolValue response = serverStub.subscribe(SubM.newBuilder().setPseudo(pseudo).setHost(host).setPort(port).build());
		return response.getValue();
	}

	@Override
	public int send(String pseudo, String message) {
		Int32Value response = serverStub.send(SendM.newBuilder().setPseudo(pseudo).setMessage(message).build());
		return response.getValue();
	}

	@Override
	public List<String> listChatter() {
		ListChatter response = serverStub.listChatter(Empty.getDefaultInstance());
		return response.getListList();
	}

	@Override
	public void unsubscribe(String pseudo) {
		serverStub.unsubscribe(StringValue.of(pseudo));
		if (callbackServer != null) {
			callbackServer.shutdownNow();
		}
	}
}
