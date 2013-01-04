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
package coordinates.views;

import coordinates.models.Coordinate;
import java.awt.geom.Line2D;

/**
 *
 * @author Erik Brännström <erik.brannstrom@gmail.com>
 */
public class CoordinateLine2D extends Line2D.Double {
	private Coordinate from, to;

	public CoordinateLine2D(Coordinate from, Coordinate to)
	{
		super();
		this.from = from;
		this.to = to;
		this.setLine(from.x(), from.y(), to.x(), to.y());
	}

	public Coordinate from()
	{
		return this.from;
	}

	public Coordinate to()
	{
		return this.to;
	}
	
}
