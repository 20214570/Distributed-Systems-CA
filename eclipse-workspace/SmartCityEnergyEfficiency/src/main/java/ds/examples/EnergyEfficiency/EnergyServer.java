package ds.examples.EnergyEfficiency;

import java.io.IOException;
import java.util.Random;
import ds.examples.EnergyEfficiency.EnergyServer;
import ds.examples.EnergyEfficiency.Service1ElectricityGrpc.Service1ElectricityImplBase;
import io.grpc.Context;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import ds.examples.EnergyEfficiency.lightRequest;
import ds.examples.EnergyEfficiency.lightResponse;


public class EnergyServer extends Service1ElectricityImplBase  {

	public static void main(String[] args) {
		
		//creates three separate energyServer objects, one for each service.
		EnergyServer energyServer = new EnergyServer();
		EnergyServer2 energyServer2 = new EnergyServer2();
		EnergyServer3 energyServer3 = new EnergyServer3();
		
		//conventional port for gRPC.		
		int port = 50051;
		
		//jmDNS service registration - as the services are all on the same port, I register/discover them as one. 
		String service_type = "_grpc._tcp.local.";
		String service_name = "Service1Electricity/Service2Renewables/Service3Maintenance";
		ServiceRegistration ssr = new ServiceRegistration();
		ssr.run(port, service_type, service_name);
		

		try {//try/catch to catch errors and throw exceptions.
			
			System.out.println("Starting gRPC Server...\n");
			
			//uses builder pattern and calls methods to specify the port, add the service, then build and start the server.
			//Running three servers from one port
			Server server = ServerBuilder.forPort(port).addService(energyServer).addService(energyServer2).addService(energyServer3).build().start();

			System.out.println("Smart City (energy efficiency) server started, listening on " + port + "\n");

			server.awaitTermination();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}//closes main method
	
	//lightSensor method
	//Illuminance is the measure of the amount of light received on the surface. It is typically expressed in lux (lm/m2).
	//Method below takes in lightRequest as a parameter and returns the generic lightResponse as type StreamObserver
	public void lightSensor(lightRequest request,StreamObserver<lightResponse> responseObserver) {

		//Finds out the content of the message sent by the client using getNumbers, getMin, getMax. 
		System.out.printf("\nReceiving lux data request from client: %d numbers between %d and %d.",
				request.getNumbers(), request.getMin(), request.getMax());
		//this method is part of the Print Stream class. "d" formats decimal integers.
		
		
		Random rand = new Random();
		
		
		//if statement checks if the client still needs a response
		if (Context.current().isCancelled()) {
			  responseObserver.onError(Status.CANCELLED.withDescription("lightSensor method cancelled by client.").asRuntimeException());
			  return;
		}
		 
		
		for(int i=0; i<request.getNumbers(); i++) {
			
			//generates random numbers for lux values using max and min values set in client.
			int random_value = rand.nextInt(request.getMax() - request.getMin()) + request.getMin() + 1;
			
			//builds a response with the builder method and sets the Number as random_value.
			lightResponse reply = lightResponse.newBuilder().setNumbers(random_value).build();

			responseObserver.onNext(reply);

			try {
				//wait for a second
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		responseObserver.onCompleted();//server tells the client that there are no more messages

	}//closes lightSensor method
	
	
	//the power for the bridge lights switches between mains electricity and the foot fall kinetic generator depending on the level of foot traffic on the bridge.
	// see https://futurism.com/new-flooring-tech-generates-electricity-through-your-footsteps
	public StreamObserver<bridgeMessage> bridgeLights(StreamObserver<bridgeResponse> responseObserver) {
		
		return new StreamObserver<bridgeMessage> () {

			@Override
			public void onNext(bridgeMessage msg) {//when a message is sent to server from client
				
				//gets pedestrian count from client
				System.out.println("Server receiving client stream of pedestrian foot traffic data from bridgeLights method: "+ msg.getPedestrianCount());
				
				//if statement checks if the client still needs a response
				if (Context.current().isCancelled()) {
					  responseObserver.onError(Status.CANCELLED.withDescription("bridgeLights method cancelled by client.").asRuntimeException());
					  return;
				}
				
				
				//builds response
				bridgeResponse.Builder responseBuilder = bridgeResponse.newBuilder();
				
				if(msg.getPedestrianCount() <= 30) {
					//if pedestrian count is less than or equal to 30, the power for the bridge lights switches to the mains generator.
					responseBuilder.setEnergyStatus("Energy status streamed from server: Pedestrian count is less than or equal to 30 - using mains generator to power bridge lights.");	
				}else {
					//if pedestrian count is greater than 30, the power for the bridge lights switches to the the kinetic foot traffic generator.
					responseBuilder.setEnergyStatus("Energy status streamed from server: Pedestrian count is greater than 30 - using foot traffic electricity generator for bridge lights.");	
				}
				
				//sends response to client
				responseObserver.onNext(responseBuilder.build());
				
			}

			
			@Override
			public void onError(Throwable t) {
				
				t.printStackTrace();
				
			}

			@Override
			public void onCompleted() {
				System.out.println("Streaming complete!\n");
				
				//completed too
				responseObserver.onCompleted();//server tells the client that there are no more messages
			}
			
		};
	}
	
}//closes EnergyEfficiencyServer
