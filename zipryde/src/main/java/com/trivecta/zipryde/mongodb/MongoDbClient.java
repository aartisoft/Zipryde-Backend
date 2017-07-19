package com.trivecta.zipryde.mongodb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;

@Component
public class MongoDbClient {

	static MongoClient mongoClient = new MongoClient("localhost", 27017);
	static MongoDatabase mongoDatabase = mongoClient.getDatabase("ZIPRYDE");
	static MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("driversession");
	
	private static Double noOfMiles = 5 / 3963.2 ;
		
	public List<UserGeoSpatialResponse> getNearByActiveDrivers(Double longitude,Double latitude){
		final List<UserGeoSpatialResponse> userResponseList = new ArrayList<UserGeoSpatialResponse>();
		List<Double> coordinates = new LinkedList<Double>();
		coordinates.add(longitude);
		coordinates.add(latitude);
		
		List circle = new ArrayList();
		circle.add(coordinates);
		circle.add(noOfMiles);
		
		FindIterable<Document> findIterable = mongoCollection.find(new Document("isActive",1).append("loc", 
				new Document("$geoWithin", 
						 new Document("$centerSphere",circle))));
		findIterable.forEach(new Block<Document>() {
			public void apply(final Document document) {
				Document geoDoc = (Document) document.get("loc");
				UserGeoSpatialResponse  userResponse = new UserGeoSpatialResponse();
				userResponse.setUserId(Integer.parseInt(document.get("userId").toString()));
				userResponse.setLatitude(new BigDecimal(geoDoc.get("lat").toString()));
				userResponse.setLongitude(new BigDecimal(geoDoc.get("lon").toString()));
				userResponseList.add(userResponse);
				/*System.out.println(document.get("userId") + "--->" + document.get("loc"));
				System.out.println(geoDoc.get("lon")+" -- > "+geoDoc.get("lat"));*/
			}			
		});
		
		return userResponseList;
	}
	
	@Async
	public void insertDriverSession(String userId,Double longitude,Double latitude) {
		// insert a document
		Document document = new Document("userId", userId).append("isActive", 1).append("loc", new Document("lon",longitude).append("lat", latitude));
		mongoCollection.insertOne(document);
	}
	
	@Async
	public void updateDriverOnlineStatus(String userId,int isActive) {
		Bson filter = Filters.eq("userId", userId);
		mongoCollection.updateOne(filter, new Document("$set", new Document("isActive", isActive)));
	}
	
	@Async
	public void updateDriverSession(String userId,Double longitude,Double latitude) {
		
		Bson filter = Filters.eq("userId", userId);
		Bson toUpdate = new Document("loc",new Document("lon",longitude).append("lat", latitude));
		Bson toInsert = new Document("isActive",1);
		UpdateOptions options = new UpdateOptions().upsert(true);
		mongoCollection.updateOne(filter, new Document("$set",toUpdate).append("$setOnInsert",toInsert),options);
	}
	
	/*public static void main(String args[]) {
		//getNearByDrivers1((Double)80.052893 ,(Double)12.918186);
		getNearByActiveDrivers((Double)80.052893 ,(Double)12.918186);
		Double  lat = new Double("9.9252007");
		Double lon = new Double("78.1197754");
		//insertDriverSession("30",d, (Double)40.7143528);
		//updateDriverStatus("3",0);
		//updateDriverStatus("15",lon,lat);
	}*/
}
