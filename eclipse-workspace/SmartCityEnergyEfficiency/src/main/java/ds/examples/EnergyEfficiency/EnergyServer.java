package ds.examples.EnergyEfficiency;

import java.io.IOException;
import java.util.Random;
import ds.examples.EnergyEfficiency.EnergyServer;
import ds.examples.EnergyEfficiency.Service1ElectricityGrpc.Service1ElectricityImplBase;
import ds.examples.EnergyEfficiency.lightRequest;
import ds.examples.EnergyEfficiency.lightResponse;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;
import io.grpc.stub.StreamObserver;

public class EnergyServer extends Service1ElectricityImplBase {

	
	
	public static void main(String[] args) {
		
		//creates energyServer object.
		EnergyServer energyServer = new EnergyServer();

		//conventional port for gRPC.		
		int port = 50051;
		

		try {//try/catch to catch errors and throw exceptions.
			
			System.out.println("Starting gRPC Server...");
			
			//uses builder pattern and calls methods to specify the port, add the service, then build and start the server.
			Server server = ServerBuilder.forPort(port).addService(energyServer).build().start();

			System.out.println("Smart City (energy efficiency) server started, listening on " + port);

			server.awaitTermination();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}//closes main method
	
	//lightSensor
	//Illuminance is the measure of the amount of light received on the surface. It is typically expressed in lux (lm/m2).
	//Method below takes in lightRequest as a parameter and returns the generic lightResponse as type StreamObserver
	public void lightSensor(lightRequest request,StreamObserver<lightResponse> responseObserver) {

		//Finds out the content of the message sent by the client using getNumbers, getMin, getMax. 
		System.out.printf("receiving lux data: %d from: %d to: %d \n",
				request.getNumbers(), request.getMin(), request.getMax()  );
		//this method is part of the Print Stream class. "d" formats decimal integers.

		Random rand = new Random();

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


		responseObserver.onCompleted();


	}//closes lightSensor method
	
	//the power for the bridge lights switches between mains electricity and the foot fall kinetic generator depending on the level of foot traffic on the bridge.
	// see https://futurism.com/new-flooring-tech-generates-electricity-through-your-footsteps
	public StreamObserver<bridgeMessage> bridgeLights(StreamObserver<bridgeResponse> responseObserver) {
		
		return new StreamObserver<bridgeMessage> () {

			@Override
			public void onNext(bridgeMessage msg) {//when a message is sent to server from client
				
				//gets pedestrian count from client
				System.out.println("receiving bridgeLights pedestrian count: "+ msg.getPedestrianCount());
				
				//builds response
				bridgeResponse.Builder responseBuilder = bridgeResponse.newBuilder();
				
				if(msg.getPedestrianCount() <= 30) {
					//if pedestrian count is less than or equal to 30, the power for the bridge lights switches to the mains generator.
					responseBuilder.setEnergyStatus("Pedestrian count is less than or equal to 30 - using mains generator to power bridge lights.");	
				}else {
					//if pedestrian count is greater than 30, the power for the bridge lights switches to the the kinetic foot traffic generator.
					responseBuilder.setEnergyStatus("Pedestrian count is greater than 30 - using foot traffic electricity generator for bridge lights.");	
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
				System.out.println("receiving pedestrian count completed ");
				
				//completed too
				responseObserver.onCompleted();
			}
			
		};
	}
	
	
	/*
	//code below is valid, need to writer client side code to match it
	public void turbineStatus(turbineRequest request, StreamObserver<turbineResponse> responseObserver) {
		
		//Get content of message from client
		System.out.println("Receiving turbineStatus method " + request.getTurbine());

		//Build response
		turbineResponse.Builder responseBuilder = turbineResponse.newBuilder();
		
		responseBuilder.setTurbineStatus("Turbine ID: 4\nTurbine Brand Name: Windmaster 300\nNominal Power: 300 kW\nWind Speed: 29m/sec");
	
		responseObserver.onNext(responseBuilder.build());

		responseObserver.onCompleted();
	}//closes turbineStatus method
	*/
	
	
	
	
}//closes EnergyEfficiencyServer
