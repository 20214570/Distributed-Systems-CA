package ds.examples.EnergyEfficiency;

import java.util.Iterator;
import java.util.Random;

import ds.examples.EnergyEfficiency.Service1ElectricityGrpc.Service1ElectricityBlockingStub;
import ds.examples.EnergyEfficiency.Service1ElectricityGrpc.Service1ElectricityStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

public class EnergyClient {

	
	private static Service1ElectricityBlockingStub blockingStub;
	private static Service1ElectricityStub asyncStub;


	public static void main(String[] args) {

		ManagedChannel channel = ManagedChannelBuilder
				.forAddress("localhost", 50051)
				.usePlaintext()
				.build();

		//stubs -- generate from proto
		blockingStub = Service1ElectricityGrpc.newBlockingStub(channel);

		asyncStub = Service1ElectricityGrpc.newStub(channel);

		//System.out.println("Starting lightSensorAsyn() method...");
		//lightSensorAsyn();
		//System.out.println("Starting lightSensorBlocking() method...");
		//lightSensorBlocking();

		System.out.println("Starting bridgeLights method...");
		bridgeLights();		
		System.out.println("...bridgeLights method completed!");
		
		System.out.println("Services completed! Channel shutting down now...");

		//channel.shutdown();
		
	}//closes main method

	//blocking server-streaming
	public static void lightSensorBlocking() {//blocking waits for response, async doesn't
		lightRequest request = lightRequest.newBuilder()
				.setNumbers(5).setMin(0).setMax(2000).build();

		try {
			Iterator<lightResponse> responses = blockingStub.lightSensor(request);

			while(responses.hasNext()) {
				lightResponse temp = responses.next();
				System.out.println("Receiving lux data (blocking) from sensor: " + temp.getNumbers());				
			}

		} catch (StatusRuntimeException e) {
			e.printStackTrace();
		}

	}


	public static void lightSensorAsyn() {

		lightRequest request = lightRequest.newBuilder()
				.setNumbers(10).setMin(0).setMax(2000).build();//10 random numbers between 0 and 2000


		StreamObserver<lightResponse> responseObserver = new StreamObserver<lightResponse>() {

			int count =0 ;

			@Override
			public void onNext(lightResponse value) {
				System.out.println("Receiving lux data (asyn) from sensor: " + value.getNumbers());
				count += 1;
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();

			}

			@Override
			public void onCompleted() {
				System.out.println("Stream from server is completed ... received "+ count+ " lumens numbers");
			}

		};

		asyncStub.lightSensor(request, responseObserver);

		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	public static void bridgeLights() {


		StreamObserver<bridgeResponse> responseObserver = new StreamObserver<bridgeResponse>() {

			int count =0 ;

			@Override
			public void onNext(bridgeResponse msg) {
				System.out.println("Receiving energy status data: " + msg.getEnergyStatus());
				count += 1;
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();

			}

			@Override
			public void onCompleted() {
				System.out.println("Stream is completed ... received "+ count+ " instances of pedestrian foot falls.");
			}

		};



		StreamObserver<bridgeMessage> requestObserver = asyncStub.bridgeLights(responseObserver);

		try {

			requestObserver.onNext(bridgeMessage.newBuilder().setPedestrianCount(100).build());
			requestObserver.onNext(bridgeMessage.newBuilder().setPedestrianCount(87).build());
			requestObserver.onNext(bridgeMessage.newBuilder().setPedestrianCount(60).build());
			requestObserver.onNext(bridgeMessage.newBuilder().setPedestrianCount(64).build());
			requestObserver.onNext(bridgeMessage.newBuilder().setPedestrianCount(30).build());
			requestObserver.onNext(bridgeMessage.newBuilder().setPedestrianCount(33).build());
			requestObserver.onNext(bridgeMessage.newBuilder().setPedestrianCount(43).build());
			requestObserver.onNext(bridgeMessage.newBuilder().setPedestrianCount(32).build());
			requestObserver.onNext(bridgeMessage.newBuilder().setPedestrianCount(21).build());
			requestObserver.onNext(bridgeMessage.newBuilder().setPedestrianCount(30).build());
			requestObserver.onNext(bridgeMessage.newBuilder().setPedestrianCount(30).build());

			// Mark the end of requests
			requestObserver.onCompleted();


			// Sleep for a bit before sending the next one.
			Thread.sleep(new Random().nextInt(1000) + 500);


		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}



		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}	
	
	
	
	/*
	//need to text this by sending back a string of results (can the results be hardcoded?)
	public static void turbineStatus() {
		//int num1 = 40;
		//int num2 = 50;
		String turbine = "Status A O K";
		

		turbineRequest request = turbineRequest.newBuilder().setturbine(turbine).build();
		turbineResponse response = blockingStub.turbineStatus(request);

		System.out.println("res: " + response +);
	}
	*/
	
}//closes the class
