package com.egen.egen_be_challenge.controllers;

import java.net.UnknownHostException;
import java.util.List;

import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.egen.egen_be_challenge.dao.AlertsDAOImpl;
import com.egen.egen_be_challenge.dao.MetricsDAOImpl;
import com.egen.egen_be_challenge.entities.Alerts;
import com.egen.egen_be_challenge.entities.Metrics;
import com.mongodb.MongoClient;

public class MorphiaMongo {

	private MongoClient mongoClient = null;

	public void insertAlert(long timestamp, String alert) {
		establishClient();
		AlertsDAOImpl alertsDAO = new AlertsDAOImpl(mongoClient, getMorphia(), "egen-challenge");
		alertsDAO.save(new Alerts(timestamp, alert));
	}

	public void insertMetrics(long timestamp, int weight) {
		establishClient();
		MetricsDAOImpl metricsDAOImpl = new MetricsDAOImpl(mongoClient, getMorphia(), "egen-challenge");
		metricsDAOImpl.save(new Metrics(timestamp, weight));
	}

	public List<Alerts> readAlerts() {
		establishClient();
		AlertsDAOImpl alertsDAOImpl = new AlertsDAOImpl(mongoClient, getMorphia(), "egen-challenge");
		return alertsDAOImpl.find().asList();
	}

	public List<Alerts> readAlerts(long startTimestamp, long endTimestamp) {
		establishClient();
		AlertsDAOImpl alertsDAOImpl = new AlertsDAOImpl(mongoClient, getMorphia(), "egen-challenge");
		Query<Alerts> query = getMorphia().createDatastore(mongoClient, "egen-challenge").createQuery(Alerts.class)
				.filter("timestamp >", startTimestamp).filter("timestamp <", endTimestamp);
		return alertsDAOImpl.find(query).asList();
	}

	public List<Metrics> readMetrics() {
		establishClient();
		MetricsDAOImpl metricsDAO = new MetricsDAOImpl(mongoClient, getMorphia(), "egen-challenge");
		return metricsDAO.find().asList();
	}

	public List<Metrics> readMetrics(long startTimestamp, long endTimestamp) {
		establishClient();
		MetricsDAOImpl metricsDAOImpl = new MetricsDAOImpl(mongoClient, getMorphia(), "egen-challenge");
		Query<Metrics> query = getMorphia().createDatastore(mongoClient, "egen-challenge").createQuery(Metrics.class)
				.filter("timestamp >", startTimestamp).filter("timestamp <", endTimestamp);

		return metricsDAOImpl.find(query).asList();
	}

	private Morphia getMorphia() {
		Morphia morphia = new Morphia();
		morphia.createDatastore(mongoClient, "egen-challenge");
		return morphia;
	}

	private void establishClient() {
		if (null == mongoClient) {
			try {
				mongoClient = new MongoClient("localhost", 27017);
			} catch (UnknownHostException e) {
				System.out.println("Failed at establishing mongo client.Host Not Available:" + e.getMessage());
			}
		}
	}
}