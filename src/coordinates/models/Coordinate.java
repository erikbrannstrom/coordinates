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

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Erik Brännström <erik.brannstrom@gmail.com>
 */
public class Coordinate extends Point2D.Double {

	private int id;
	private double cX, cY, cZ;
	private List<Coordinate> linesTo;
	private int type;

	public Coordinate(int id, double x, double y, double z, int type)
	{
		this.id = id;
		this.cX = x;
		this.cY = y;
		this.cZ = z;
		this.setLocation(x, y);
		this.type = type;
		this.linesTo = new LinkedList<>();
	}
	
	public Coordinate(double x, double y, double z)
	{
		this(0, x, y, z, 0);
	}

	public double x()
	{
		return cX;
	}
	
	public void x(double x)
	{
		this.cX = x;
	}

	public double y()
	{
		return cY;
	}

	public void y(double y)
	{
		this.cY = y;
	}
	
	public double z()
	{
		return cZ;
	}
	
	public void z(double z)
	{
		this.cZ = z;
	}
	
	public void addLine(Coordinate endPoint)
	{
		this.linesTo.add(endPoint);
	}
	
	public void removeLine(Coordinate endPoint)
	{
		this.linesTo.remove(endPoint);
	}

	public List<Coordinate> linesTo()
	{
		return linesTo;
	}

	@Override
	public String toString()
	{
		// Lines follow format: ID, X, Y, Z, Line ID 1, Line ID 2, Type
		String res = id + ", " + cX + ", " + cY + ", " + cZ + ", ";
		for (Coordinate coordinate : linesTo) {
			res += coordinate.id() + ", ";
		}
		for (int i = 0; i < 2-linesTo.size(); i++) {
			res += "0, ";
		}
		return res + this.type;
	}

	private int id()
	{
		return this.id;
	}
	
}
