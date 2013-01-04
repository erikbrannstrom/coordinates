/*
 * The MIT License
 *
 * Copyright 2012 Erik Brännström <erik.brannstrom@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package coordinates.models;

import coordinates.controllers.CoordinatesListener;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Erik Brännström <erik.brannstrom@gmail.com>
 */
public class Coordinates extends LinkedList<Coordinate> {
	private double xMax, yMax;
	private List<CoordinatesListener> listeners;

	public Coordinates()
	{
		super();
		this.listeners = new LinkedList<>();
	}
	
	public void addCoordinatesListener(CoordinatesListener listener)
	{
		this.listeners.add(listener);
	}

	@Override
	public boolean add(Coordinate c)
	{
		this.xMax = Math.max(this.xMax, c.x());
		this.yMax = Math.max(this.yMax, c.y());
		return super.add(c);
	}
	
	public double xMax()
	{
		return this.xMax;
	}
	
	public double yMax()
	{
		return this.yMax;
	}
	
	public static Coordinates fromStrings(List<String> data)
	{
		Coordinates coords = new Coordinates();
		Map<Integer, Coordinate> idToCoordinate = new TreeMap<>();
		Map<Coordinate, List<Integer>> paths = new HashMap<>();
		for (String line : data) {
			// Lines follow format: ID, X, Y, Z, Line ID 1, Line ID 2, Type
			String[] values = line.split("[ ]*,[ ]*");
			// Set all values to variables
			int id = Integer.parseInt(values[0]);
			double x = Double.parseDouble(values[1]);
			double y = Double.parseDouble(values[2]);
			double z = Double.parseDouble(values[3]);
			int lineTo1 = Integer.parseInt(values[4]);
			int lineTo2 = Integer.parseInt(values[5]);
			int type = Integer.parseInt(values[6]);
			// Create coordinate instance
			Coordinate c = new Coordinate(id, x, y, z, type);
			// Store id and line endings
			idToCoordinate.put(id, c);
			if (lineTo1 > 0 || lineTo2 > 0) {
				List<Integer> linesTo = new LinkedList<>();
				if (lineTo1 > 0) {
					linesTo.add(lineTo1);
				}
				if (lineTo2 > 0) {
					linesTo.add(lineTo2);
				}
				paths.put(c, linesTo);
			}
			// Add coordinate to list
			coords.add(c);
		}
		
		// Add all paths
		for (Coordinate coordinate : paths.keySet()) {
			for (Integer id : paths.get(coordinate)) {
				coordinate.addLine(idToCoordinate.get(id));
			}
		}
		
		// Return coordinates
		return coords;
	}

	public void replaceAll(Collection<Coordinate> coordinates)
	{
		this.clear();
		this.addAll(coordinates);
		for (CoordinatesListener coordinatesListener : this.listeners) {
			coordinatesListener.coordinatesUpdated();
		}
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		for (Coordinate coordinate : this) {
			builder.append(coordinate.toString()).append(System.lineSeparator());
		}
		return builder.toString();
	}
	
}
