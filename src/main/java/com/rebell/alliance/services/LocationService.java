package com.rebell.alliance.services;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;

import com.lemmingapex.trilateration.TrilaterationFunction;

import java.util.Arrays;

import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

	public double[] getLocation(double[][] positions, double[] distances) {

		if (positions.length == 3) {
			for (int i = 0; i < positions.length; i++) {
				if (positions[i] == null) {
					String[] points = { "0.0", "0.0" };
					positions[i] = Arrays.stream(points).map(Double::valueOf).mapToDouble(Double::doubleValue).toArray();
					distances[i] = 0.0;
				}
			}
		}
		TrilaterationFunction trilaterationFunction = new TrilaterationFunction(positions, distances);
		NonLinearLeastSquaresSolver nSolver = new NonLinearLeastSquaresSolver(trilaterationFunction,
				new LevenbergMarquardtOptimizer());

		return nSolver.solve().getPoint().toArray();
	}

}
