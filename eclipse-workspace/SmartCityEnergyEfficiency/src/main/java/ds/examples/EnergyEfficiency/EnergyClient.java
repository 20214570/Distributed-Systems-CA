package ds.examples.EnergyEfficiency;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import ds.examples.EnergyEfficiency.Service1ElectricityGrpc.Service1ElectricityBlockingStub;
import ds.examples.EnergyEfficiency.Service1ElectricityGrpc.Service1ElectricityStub;
import ds.examples.EnergyEfficiency.Service2RenewablesGrpc.Service2RenewablesBlockingStub;
import ds.examples.EnergyEfficiency.Service2RenewablesGrpc.Service2RenewablesStub;
import ds.examples.EnergyEfficiency.Service3MaintenanceGrpc.Service3MaintenanceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import javax.jmdns.ServiceInfo;

public class EnergyClient {

	
	private static Service1ElectricityBlockingStub blockingStub;
	private static Service1ElectricityStub asyncStub;
	private static Service2RenewablesStub asyncStub1;
	private static Service2RenewablesBlockingStub blockingStub2;
	private static Service3MaintenanceBlockingStub blockingStub3;

	public static void main(String[] args) throws InterruptedException {

		//Service discovery
		ServiceInfo serviceInfo;
		String service_type = "_grpc._tcp.local.";
		//Now retrieve the service info - all we are supplying is the service type
		serviceInfo = ServiceDiscovery.run(service_type);
		//Use the serviceInfo to retrieve the port
		int port = serviceInfo.getPort();
		String host = "localhost";
		//int port = 50051;
		
		ManagedChannel channel = ManagedChannelBuilder
				.forAddress("localhost", 50051)
				.usePlaintext()
				.build();

		//stubs -- generate from proto
		blockingStub = Service1ElectricityGrpc.newBlockingStub(channel);

		asyncStub = Service1ElectricityGrpc.newStub(channel);
		
		asyncStub1 = Service2RenewablesGrpc.newStub(channel);

		blockingStub2 = Service2RenewablesGrpc.newBlockingStub(channel);
		
		blockingStub3 = Service3MaintenanceGrpc.newBlockingStub(channel);
		
		
		
		
		//Service1Electricity methods start here
		System.out.println("\nStarting methods in Service1Electricity...\n");
		System.out.println("Starting server-streaming lightSensorBlocking() method...");
		lightSensorBlocking();
		System.out.println("lightSensorBlocking() method completed!\n");
		
		System.out.println("Starting server-streaming lightSensorAsyn() method...");
		lightSensorAsyn();
		System.out.println("lightSensorAsyn() method completed!\n");
		
		System.out.println("Starting bi-directional bridgeLights method...");
		bridgeLights();		
		System.out.println("...bridgeLights method completed!\n");
		System.out.println("Methods in Service1Electricity completed!\n");
		//Service1Electricity methods end here
			
		
		//Service2Renewables methods start here	
		System.out.println("Starting methods in Service2Renewables...\n");

		System.out.println("Starting unary turbineStatus method...");
		turbineStatus();		
		System.out.println("...turbineStatus method completed!\n");
		
		System.out.println("Starting client-streaming hydroAverageValues method...");
		hydroAverageValues();
		System.out.println("...hydroAverageValues method completed!\n");
		System.out.println("Methods in Service2Renewables completed!\n");
		//Service2Renewables methods end here			
		
		
		//Service3Maintenance methods start here	
		System.out.println("Starting methods in Service3Maintenance...\n");
		System.out.println("Starting unary remoteDiagnostics method...");
		remoteDiagnostics();
		System.out.println("...remoteDiagnostics method completed!\n");
		
		System.out.println("Starting unary predictiveMaintenance method...");
		predictiveMaintenance();
		System.out.println("...predictiveMaintenance method completed!\n");
		System.out.println("Methods in Service3Maintenance completed!\n");
		//Service3Maintenance methods end here
		
		System.out.println("All services completed! Channel shutting down now...");

		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
		
	}//closes main method

	//lightSensorBLocking method - blocking server-streaming
	public static void lightSensorBlocking() {//blocking waits for response, async doesn't
		
		//this builds a request that is sent to the server.
		lightRequest request = lightRequest.newBuilder()
				.setNumbers(5).setMin(0).setMax(2000).build();
		
		int deadlineMs = 20*1000;//value of deadline for response
		
		try {
			//response includes a deadline
			Iterator<lightResponse> responses = blockingStub.withDeadlineAfter(deadlineMs, TimeUnit.MILLISECONDS).lightSensor(request);
			//Iterator<lightResponse> responses = blockingStub.lightSensor(request);

			while(responses.hasNext()) {
				lightResponse temp = responses.next();
				System.out.println("Client receiving lux sensor data (blocking) from server: " + temp.getNumbers());				
			}

		} catch (StatusRuntimeException e) {
			e.printStackTrace();
		}

	}//closes lightSensorBlocking method

	//lightSensorAsyn method - async server streaming
	public static void lightSensorAsyn() {//async waits for response

		int deadlineMs = 20*1000;//value of deadline for response
		
		System.out.println("Client requesting lux sensor data from server...");

		lightRequest request = lightRequest.newBuilder()
				.setNumbers(10).setMin(0).setMax(2000).build();//clients requests 10 random numbers between 0 and 2000


		StreamObserver<lightResponse> responseObserver = new StreamObserver<lightResponse>() {

			int count =0 ;

			@Override
			public void onNext(lightResponse value) {
				System.out.println("Client receiving lux sensor data (asyn) stream from server: " + value.getNumbers());
				count += 1;
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();

			}

			@Override
			public void onCompleted() {
				System.out.println("Client received " + count+ " lux sensor data points (asyn) in server stream.");
			}

		};
		
		//includes a deadline
		asyncStub.withDeadlineAfter(deadlineMs, TimeUnit.MILLISECONDS).lightSensor(request, responseObserver);
		//asyncStub.lightSensor(request, responseObserver);

		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}//closes lightSensorAsyn method
	
	
	//bridgeLights method - sends the number of pedestrians on the bridge to the server and the server can switch between mains electricity and kinetic generated electricity from footsteps.
	public static void bridgeLights() {

		int deadlineMs = 20*1000;//value of deadline for response
		
		//this block waits and receives responses from the server
		StreamObserver<bridgeResponse> responseObserver = new StreamObserver<bridgeResponse>() {

			int count =0 ;

			@Override
			public void onNext(bridgeResponse msg) {
				System.out.println("Client receiving data... " + msg.getEnergyStatus());
				count += 1;
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();

			}

			@Override
			public void onCompleted() {
				System.out.println("Client and server streams are completed ... received "+ count+ " data points for pedestrian foot traffic.");
			}

		};
		
		//includes a deadline
		StreamObserver<bridgeMessage> requestObserver = asyncStub.withDeadlineAfter(deadlineMs, TimeUnit.MILLISECONDS).bridgeLights(responseObserver);

		try {
			//streams pedestrian count values to server.
			System.out.println("\nClient streaming pedestrian foot traffic data to server...");
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
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}//closes bridgeLights method	
	
	
	
	//turbineStatus method - sends the status of a wind turbine on the roof of a building.
	public static void turbineStatus() {
		
		try {
			
		String turbine = "What is the wind turbine status?";
		
		int deadlineMs = 20*1000;//value of deadline for response.
		
		//builds request
		turbineRequest request = turbineRequest.newBuilder().setTurbine(turbine).build();
		
		//builds response, includes a deadline.
		turbineResponse response = blockingStub2.withDeadlineAfter(deadlineMs, TimeUnit.MILLISECONDS).turbineStatus(request);

		System.out.println("Response from server:\n" + response.getTurbineStatus());
		
		} catch (StatusRuntimeException e) {
			e.printStackTrace();
		}
			
	}//closes turbineStatus method
	
	//hydroAverageValues method - calculates the average water flow over a hydro-electric dam.
	public static void hydroAverageValues() {
		
		int deadlineMs = 20*1000;//value of deadline for response.
		
		StreamObserver<CalculateResponse> responseObserver = new StreamObserver<CalculateResponse>() {

			@Override
			public void onNext(CalculateResponse msg) {
				System.out.println("Receiving average water flow figure from server: " + msg.getResult() +" gallons per second." );
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			}

			@Override
			public void onCompleted() {
				System.out.println("Client stream is completed...");
			}

		};


		StreamObserver<NumberMessage> requestObserver = asyncStub1.withDeadlineAfter(deadlineMs, TimeUnit.MILLISECONDS).hydroAverageValues(responseObserver);
		try {
			requestObserver.onNext(NumberMessage.newBuilder().setNumber(128).build());
			Thread.sleep(500);
			requestObserver.onNext(NumberMessage.newBuilder().setNumber(120).build());
			Thread.sleep(500);
			requestObserver.onNext(NumberMessage.newBuilder().setNumber(132).build());
			Thread.sleep(500);
			requestObserver.onNext(NumberMessage.newBuilder().setNumber(121).build());
			Thread.sleep(500);
			requestObserver.onNext(NumberMessage.newBuilder().setNumber(49).build());
			Thread.sleep(500);
			requestObserver.onNext(NumberMessage.newBuilder().setNumber(87).build());
			Thread.sleep(500);
			requestObserver.onNext(NumberMessage.newBuilder().setNumber(93).build());
			Thread.sleep(500);
			requestObserver.onNext(NumberMessage.newBuilder().setNumber(85).build());
			Thread.sleep(500);
			requestObserver.onNext(NumberMessage.newBuilder().setNumber(97).build());
			Thread.sleep(500);
			requestObserver.onNext(NumberMessage.newBuilder().setNumber(119).build());
			Thread.sleep(500);

			// Mark the end of requests
			requestObserver.onCompleted();

			Thread.sleep(5000);
			
		} catch (RuntimeException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}

	}//closes the hydroAverageValues method
	
	//remoteDiagnostics method
	public static void remoteDiagnostics() {
		
		try {
			
		String chiller = "What is the energy status of the chiller?";
		
		int deadlineMs = 20*1000;//value of deadline for response
		
		//builds request
		remoteRequest request = remoteRequest.newBuilder().setRemote(chiller).build();
		
		//builds response, includes a deadline
		remoteResponse response = blockingStub3.withDeadlineAfter(deadlineMs, TimeUnit.MILLISECONDS).remoteDiagnostics(request);

		System.out.println("Response from server:\n" + response.getRemoteChillerStatus());
		
		} catch (StatusRuntimeException e) {
			e.printStackTrace();
		}
			
	}//closes the remoteDiagnostics method
	
	//predictiveMaintenance method
	public static void predictiveMaintenance() {
		
		try {
			
		String maintenance = "What is the status of the next energy efficiency appointment?";
		
		int deadlineMs = 20*1000;//value of deadline for response
		
		//builds request
		maintenanceRequest request = maintenanceRequest.newBuilder().setMaintenance(maintenance).build();
		
		//builds response, includes a deadline
		maintenanceResponse response = blockingStub3.withDeadlineAfter(deadlineMs, TimeUnit.MILLISECONDS).predictiveMaintenance(request);

		System.out.println("Response from server: " + response.getMaintenanceStatus());
		} catch (StatusRuntimeException e) {
			e.printStackTrace();
		}
			
	}//closes the predictiveMaintenance method
	
}//closes the EnergyClient class
