package ds.examples.EnergyEfficiency;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import ds.examples.EnergyEfficiency.Service1ElectricityGrpc.Service1ElectricityBlockingStub;
import ds.examples.EnergyEfficiency.Service1ElectricityGrpc.Service1ElectricityStub;
import ds.examples.EnergyEfficiency.Service2RenewablesGrpc.Service2RenewablesBlockingStub;
import ds.examples.EnergyEfficiency.Service2RenewablesGrpc.Service2RenewablesStub;
import ds.examples.EnergyEfficiency.Service3MaintenanceGrpc.Service3MaintenanceBlockingStub;
import ds.examples.EnergyEfficiency.CalculateResponse;
import ds.examples.EnergyEfficiency.NumberMessage;
import ds.examples.EnergyEfficiency.remoteRequest;
import ds.examples.EnergyEfficiency.remoteResponse;
import ds.examples.EnergyEfficiency.maintenanceResponse;
import ds.examples.EnergyEfficiency.maintenanceRequest;
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

		
		//jmDNS service discovery
		ServiceInfo serviceInfo;
		String service_type = "_grpc._tcp.local.";//the service type is all that we give to jmDNS.
		//Now retrieve the service info - all we are supplying is the service type.
		serviceInfo = SimpleServiceDiscovery.run(service_type);//running a utility class.
		//Use the serviceInfo to retrieve the port
		int port = serviceInfo.getPort();
		String host = "localhost";
		
		
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
		System.out.println("Starting lightSensorBlocking() method...");
		lightSensorBlocking();
		System.out.println("lightSensorBlocking() method completed!\n");
		
		System.out.println("Starting lightSensorAsyn() method...");
		lightSensorAsyn();
		System.out.println("lightSensorAsyn() method completed!\n");
		
		System.out.println("Starting bridgeLights method...");
		bridgeLights();		
		System.out.println("...bridgeLights method completed!\n");
		//Service1Electricity methods end here
			
		
		//Service2Renewables methods start here	
		System.out.println("Starting turbineStatus method...");
		turbineStatus();		
		System.out.println("...turbineStatus method completed!\n");
		
		System.out.println("Starting hydroAverageValues method...");
		hydroAverageValues();
		System.out.println("...hydroAverageValues method completed!\n");
		//Service2Renewables methods end here			
		
		
		//Service3Maintenance methods start here	
		System.out.println("Starting remoteDiagnostics method...");
		remoteDiagnostics();
		System.out.println("...remoteDiagnostics method completed!\n");
		
		System.out.println("Starting predictiveMaintenance method...");
		predictiveMaintenance();
		System.out.println("...predictiveMaintenance method completed!\n");
		//Service3Maintenance methods end here
		

		System.out.println("Services completed! Channel shutting down now...");

		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
		
	}//closes main method

	//lightSensorBLocking method - blocking server-streaming
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

	}//closes lightSensorBlocking method

	//lightSensorAsyn method - async server streaming
	public static void lightSensorAsyn() {//async waits for response

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
				System.out.println("Stream from server is completed ... received "+ count+ " lux numbers");
			}

		};

		asyncStub.lightSensor(request, responseObserver);

		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}//closes lightSensorAsyn method
	
	
	//bridgeLights method - sends the number of pedestrians on the bridge to the server and the server can switch between mains electricity and kinetic generated electricity from footsteps.
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
	
	
	
	//turbineStatus method - sends the status of a wind turbine on the roof of a building.
	public static void turbineStatus() {
		
		try {
		String turbine = "What is the wind turbine status?";
		
		//builds request
		turbineRequest request = turbineRequest.newBuilder().setTurbine(turbine).build();
		//builds response
		turbineResponse response = blockingStub2.turbineStatus(request);

		System.out.println("Response from server:\n" + response.getTurbineStatus());
		
		} catch (StatusRuntimeException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	//hydroAverageValues method - calculates the average water flow over a hydro-electric dam.
	public static void hydroAverageValues() {

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


		StreamObserver<NumberMessage> requestObserver = asyncStub1.hydroAverageValues(responseObserver);
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

			
			Thread.sleep(10000);
			
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
		
		//builds request
		remoteRequest request = remoteRequest.newBuilder().setRemote(chiller).build();
		//builds response
		remoteResponse response = blockingStub3.remoteDiagnostics(request);
		//turbineResponse response = blockingStub2.turbineStatus(request);

		System.out.println("Response from server:\n" + response.getRemoteChillerStatus());
		
		} catch (StatusRuntimeException e) {
			e.printStackTrace();
		}
			
	}//closes the remoteDiagnostics method
	
	//predictiveMaintenance method
	public static void predictiveMaintenance() {
		try {
		String maintenance = "What is the status of the next energy efficiency appointment?";
		
		//builds request
		maintenanceRequest request = maintenanceRequest.newBuilder().setMaintenance(maintenance).build();
		//builds response
		maintenanceResponse response = blockingStub3.predictiveMaintenance(request);

		System.out.println("Response from server: " + response.getMaintenanceStatus());
		} catch (StatusRuntimeException e) {
			e.printStackTrace();
		}
		
		
	}//closes the predictiveMaintenance method
	
	
	
}//closes the class
